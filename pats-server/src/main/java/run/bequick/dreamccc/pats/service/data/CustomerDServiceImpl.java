package run.bequick.dreamccc.pats.service.data;

import cn.hutool.core.lang.UUID;
import lombok.RequiredArgsConstructor;
import org.jasypt.digest.config.SimpleStringDigesterConfig;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.param.ThreeFactor;
import run.bequick.dreamccc.pats.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerDServiceImpl implements CustomerDService {
    private final CustomerRepository repository;

    @Override
    @ServiceLog(value = "新增客户 - {pos} - realName:{},mobile:{}", paramEl = {"#root[0].realName", "#root[0].mobile"})
    public Customer saveCustomer(ThreeFactor threeFactor, String loginName, String password) {

        var salt = UUID.fastUUID().toString().replace("-", "");
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

        Customer byLoginName = repository.getByLoginName(loginName).orElseThrow(() -> new BusinessException("没有找到用户"));
        if (getPasswordEncryptor(byLoginName.getSalt()).checkPassword(password, byLoginName.getPassword())) {
            return byLoginName;
        }
        throw new BusinessException("用户名密码不正确");
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
