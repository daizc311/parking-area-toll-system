package run.bequick.dreamccc.pats.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.param.ApiOutStorageParam;

@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * <p>API入库接口</p>
     * @return DrResponse
     */
    @RequestMapping("/inStorage")
    public DrResponse<Object> inStorage(@Validated ApiInStorageParam param){

        return DrResponse.success();
    }

    /**
     * <p>API出库接口</p>
     * 通过此接口出库时，默认由外部已经进行停车费的收取，因此需要传入停车费相关信息
     * @return DrResponse
     */
    @RequestMapping("/outStorage")
    public DrResponse<Object> outStorage(@Validated ApiOutStorageParam param){

        return DrResponse.success();
    }
}
