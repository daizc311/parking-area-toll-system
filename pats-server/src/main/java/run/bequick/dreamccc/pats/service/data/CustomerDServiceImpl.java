package run.bequick.dreamccc.pats.service.data;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrFormatter;
import lombok.RequiredArgsConstructor;
import org.jasypt.digest.config.SimpleStringDigesterConfig;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.param.ThreeFactor;
import run.bequick.dreamccc.pats.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerDServiceImpl implements CustomerDService {
    private final CustomerRepository repository;
    private final ParkingCardDService parkingCardService;
    private final CarInfoDService carInfoService;

    @Override
    public Customer getById(Long id) {
        return repository.getById(id);
    }

    @Override
    @ServiceLog(value = "新增客户 - {pos} - realName:{},mobile:{}", paramEl = {"#root[0].realName", "#root[0].mobile"})
    public Customer saveCustomer(ThreeFactor threeFactor, String loginName, String password) {

        var salt = UUID.fastUUID().toString(true);
        var encryptPassword = getPasswordEncryptor(salt).encryptPassword(password);

        Customer addC = new Customer();
        addC.setIdNumber(threeFactor.getIdNumber());
        addC.setMobile(threeFactor.getMobile());
        addC.setRealName(threeFactor.getRealName());
        addC.setLoginName(loginName);
        addC.setPassword(encryptPassword);
        addC.setSalt(salt);
        return repository.save(addC);
    }

    @Override
    @ServiceLog(value = "客户登录 - {pos} - loginName:{}", paramEl = {"#root[0]"})
    public Customer login(String loginName, String password) {

        Customer byLoginName = repository.getByLoginName(loginName)
                .orElseGet(() -> repository.getByMobile(loginName)
                        .orElseThrow(() -> new BusinessException("没有找到用户")));
        if (getPasswordEncryptor(byLoginName.getSalt()).checkPassword(password, byLoginName.getPassword())) {
            return byLoginName;
        }
        throw new BusinessException("用户名密码不正确");
    }

    @Override
    @ServiceLog(value = "绑定停车卡信息 - {pos} - customerId:{},cardNo:{}", paramEl = {"#root[0].id", "#root[1]"})
    public Customer bindParkingCard(Customer customer, String cardNo, String paramCardPwd) {
        ParkingCard parkingCard = parkingCardService.getByCardNo(cardNo)
                .orElseThrow(() -> new BusinessException(StrFormatter.format("找不到卡号为[{}]的停车卡", cardNo)));

        if (!Objects.equals(parkingCard.getCardPwd(), paramCardPwd)) {
            throw new BusinessException("停车卡密码错误");
        }
        if (Objects.isNull(customer.getParkingCards())) {
            customer.setParkingCards(new ArrayList<>());
        }
        customer.getParkingCards().add(parkingCard);
        return repository.save(customer);
    }

    @Override
    @ServiceLog(value = "绑定车辆信息 - {pos} - customerId:{},numberPlate:{}", paramEl = {"#root[0].id", "#root[1]"})
    public Customer bindCarInfo(Customer customer, String numberPlate) {
        final CarInfo carInfo = carInfoService.getByNumberPlate(numberPlate)
                .orElseThrow(() -> new BusinessException(StrFormatter.format("找不到车牌号为[{}]车辆信息", numberPlate)));

        if (Objects.isNull(customer.getCarInfos())) {
            customer.setCarInfos(new ArrayList<>());
        }
        customer.getCarInfos().add(carInfo);
        return repository.save(customer);
    }


    private PasswordEncryptor getPasswordEncryptor(String salt) {
        var passwordEncryptor = new ConfigurablePasswordEncryptor();
        var digesterConfig = new SimpleStringDigesterConfig();
        var saltGenerator = new StringFixedSaltGenerator(salt);
        digesterConfig.setSaltGenerator(saltGenerator);
        passwordEncryptor.setConfig(digesterConfig);
        passwordEncryptor.setStringOutputType("base64");
        passwordEncryptor.setAlgorithm("MD5");
        return passwordEncryptor;
    }
}
