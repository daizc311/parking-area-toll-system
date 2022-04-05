package run.bequick.dreamccc.pats.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
    public DrResponse<Object> inStorage(@Validated ApiInStorageParam param){

        boolean ok = inOutStorageService.inStorage(param);
        return DrResponse.data(ok);
    }

    @Override
    public DrResponse<Object> feePayment(@Validated ApiFeePaymentParam param){

        boolean ok = inOutStorageService.feePayment(param);
        return DrResponse.data(ok);
    }

    @Override
    public DrResponse<Object> outStorage(@Validated ApiOutStorageParam param){

        boolean ok = inOutStorageService.outStorage(param);
        return DrResponse.data(ok);
    }

}
