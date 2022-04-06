package run.bequick.dreamccc.pats.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.param.ApiFeePaymentParam;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.param.ApiOutStorageParam;


@RequestMapping("/api/action")
public interface PatsActionApi {

    /**
     * <p>API入库接口</p>
     * 外部调用此接口以完成车辆入库登记
     *
     * @return DrResponse
     */
    @PostMapping("/inStorage")
    @Schema(title="API入库接口")
    DrResponse<Object> inStorage(@RequestBody @Validated ApiInStorageParam param);

    /**
     * <p>API缴费支付接口</p>
     * 当外部已经进行停车费的收取时，需要调用此接口，因此需要传入停车费相关信息
     *
     * @return DrResponse
     */
    @PostMapping("/feePayment")
    DrResponse<Object> feePayment(@RequestBody @Validated ApiFeePaymentParam param);

    /**
     * <p>API出库接口</p>
     * 通过此接口出库时
     *
     * @return DrResponse
     */
    @PostMapping("/outStorage")
    DrResponse<Object> outStorage(@RequestBody @Validated ApiOutStorageParam param);
}
