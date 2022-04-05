package run.bequick.dreamccc.pats.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.api.PatsActionApi;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.param.ApiFeePaymentParam;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.param.ApiOutStorageParam;
import run.bequick.dreamccc.pats.service.InOutStorageService;
import run.bequick.dreamccc.pats.service.data.CarInfoDService;

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
    public DrResponse<Object> feePayment(@RequestBody @Validated ApiFeePaymentParam param) {

        inOutStorageService.feePayment(param);
        return DrResponse.success();
    }

    @Override
    public DrResponse<Object> outStorage(@RequestBody @Validated ApiOutStorageParam param) {

        inOutStorageService.outStorage(param);
        return DrResponse.success();
    }

}
