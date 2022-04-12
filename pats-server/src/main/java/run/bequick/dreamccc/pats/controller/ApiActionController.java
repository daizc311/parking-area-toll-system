package run.bequick.dreamccc.pats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.api.PatsActionApi;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.param.ApiFeePaymentParam;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.param.ApiOutStorageParam;
import run.bequick.dreamccc.pats.service.InOutStorageService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiActionController implements PatsActionApi {

    private final InOutStorageService inOutStorageService;

    @Override
    public DrResponse<Object> inStorage(@RequestBody @Validated ApiInStorageParam param) {

        inOutStorageService.inStorage(param);
        return DrResponse.success();
    }

    @Override
    public DrResponse<Boolean> feePayment(@RequestBody @Validated ApiFeePaymentParam param) {

        final var isFeePayment = inOutStorageService.feePayment(param);
        final DrResponse<Boolean> drResponse = DrResponse.data(isFeePayment);
        drResponse.setMessage(isFeePayment ? "付款成功，车辆出库完成。" : "付款成功，但支付金额未达到总价，需要继续支付");
        return drResponse;
    }

    @Override
    public DrResponse<Boolean> outStorage(@RequestBody @Validated ApiOutStorageParam param) {

        final var isOutStorage = inOutStorageService.outStorage(param.getNumberPlate());
        final DrResponse<Boolean> drResponse = DrResponse.data(isOutStorage);
        drResponse.setMessage(isOutStorage ? "车辆出库完成" : "车辆未支付完成或支付后停留时间过长，请重新支付");
        return drResponse;
    }

}
