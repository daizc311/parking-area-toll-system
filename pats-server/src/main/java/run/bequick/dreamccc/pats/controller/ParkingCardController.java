package run.bequick.dreamccc.pats.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrFormatter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.enums.AppRoleEnum;
import run.bequick.dreamccc.pats.enums.ParkingCardTypeEnum;
import run.bequick.dreamccc.pats.param.NewParkingCardParam;
import run.bequick.dreamccc.pats.param.ParkingCardBindCustomerParam;
import run.bequick.dreamccc.pats.param.RechargeParam;
import run.bequick.dreamccc.pats.service.data.CustomerDService;
import run.bequick.dreamccc.pats.service.data.ParkingCardDService;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/parkingCard")
public class ParkingCardController {
    private final ParkingCardDService parkingCardDService;
    private final CustomerDService customerDService;

    @Operation(summary = "充值", tags = {"ParkingCard"})
    @PostMapping("/recharge")
    public DrResponse<ParkingCard> recharge(@RequestBody @Validated RechargeParam rechargeParam) {

        final var parkingCard = parkingCardDService.findById(rechargeParam.getParkingCardId())
                .orElseThrow(() -> new BusinessException("找不到停车卡"));
        final var recharge = parkingCardDService.recharge(rechargeParam.getOrderNum(), parkingCard, rechargeParam.getAmount());
        return DrResponse.data(recharge);
    }

    @Operation(summary = "创建新停车卡", tags = {"ParkingCard"})
    @PostMapping("/newParkingCard")
    public DrResponse<ParkingCard> newParkingCard(@RequestBody @Validated NewParkingCardParam param) {

        if (param.getType().equals(ParkingCardTypeEnum.USER_PERSISTENCE)) {
            throw new BusinessException(StrFormatter.format("无法新建该类型[{}]的停车卡", param.getType()));
        }

        final var parkingCard = new ParkingCard();
        parkingCard.setAmount(new BigDecimal(0));
        parkingCard.setCardPwd(UUID.fastUUID().toString(true));
        parkingCard.setCardNo("PRC-" + UUID.fastUUID().toString(true));
        parkingCard.setType(param.getType());
        return DrResponse.data(parkingCardDService.save(parkingCard));
    }

    @PreAuthorize(AppRoleEnum.ROLE_FINANCIAL_MANAGER)
    @Operation(summary = "[管理员]为客户绑定停车卡", tags = {"ParkingCard"})
    @PostMapping("/bindCustomer")
    public DrResponse<ParkingCard> bindCustomer(@RequestBody ParkingCardBindCustomerParam bindUserParam) {


        final var customer = customerDService.findById(bindUserParam.getCustomerId())
                .orElseThrow(() -> new BusinessException("找不到用户"));
        final var parkingCard = parkingCardDService.findById(bindUserParam.getParkingCardId())
                .orElseThrow(() -> new BusinessException("找不到停车卡"));

        return DrResponse.data(parkingCardDService.bindCustomer(parkingCard, customer));
    }

}
