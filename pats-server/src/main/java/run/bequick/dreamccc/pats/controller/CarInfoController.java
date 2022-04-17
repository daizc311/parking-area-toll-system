package run.bequick.dreamccc.pats.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.enums.AppRoleEnum;
import run.bequick.dreamccc.pats.param.CarInfoBindCustomerParam;
import run.bequick.dreamccc.pats.service.data.CarInfoDService;
import run.bequick.dreamccc.pats.service.data.CustomerDService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/carInfo")
public class CarInfoController {
    private final CarInfoDService carInfoDService;
    private final CustomerDService customerDService;

    @Operation(summary = "查询所有车辆信息", tags = {"CarInfo"})
    @PostMapping("/listAll")
    public DrResponse<List<CarInfo>> listAll() {

        return DrResponse.data(carInfoDService.listAll());
    }


    @PreAuthorize(AppRoleEnum.ROLE_FINANCIAL_MANAGER)
    @Operation(summary = "[管理员]为客户绑定车辆信息", tags = {"CarInfo"})
    @PostMapping("/bindCustomer")
    public DrResponse<CarInfo> bindCustomer(@RequestBody CarInfoBindCustomerParam bindUserParam) {


        final var customer = customerDService.findById(bindUserParam.getCustomerId())
                .orElseThrow(() -> new BusinessException("找不到用户"));
        final var carInfo = carInfoDService.findById(bindUserParam.getCarInfoId())
                .orElseThrow(() -> new BusinessException("找不到车辆信息"));

        return DrResponse.data(carInfoDService.bindCustomer(carInfo, customer));
    }

}
