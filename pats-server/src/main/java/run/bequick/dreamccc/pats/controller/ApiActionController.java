package run.bequick.dreamccc.pats.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.api.PatsActionApi;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.param.ApiFeePaymentParam;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.param.ApiOutStorageParam;

@RestController
public class ApiActionController implements PatsActionApi {

    @Override
    public DrResponse<Object> inStorage(@Validated ApiInStorageParam param){

        return DrResponse.success();
    }

    @Override
    public DrResponse<Object> feePayment(@Validated ApiFeePaymentParam param){

        return DrResponse.success();
    }

    @Override
    public DrResponse<Object> outStorage(@Validated ApiOutStorageParam param){

        return DrResponse.success();
    }

}
