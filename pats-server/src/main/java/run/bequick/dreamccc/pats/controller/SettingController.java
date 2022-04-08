package run.bequick.dreamccc.pats.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.domain.ParkingSetting;
import run.bequick.dreamccc.pats.service.data.ParkingSettingDService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/setting")
public class SettingController {
    private final ParkingSettingDService parkingSettingDService;

    @Operation(summary = "获取系统设置", tags = {"Setting"})
    @PostMapping("/getSetting")
    public DrResponse<ParkingSetting> getSetting() {
        return DrResponse.data(parkingSettingDService.getSetting());
    }

    @Operation(summary = "保存系统设置", tags = {"Setting"})
    @PostMapping("/saveSetting")
    public DrResponse<ParkingSetting> saveSetting(@RequestBody ParkingSetting parkingSetting) {
        return DrResponse.data(parkingSettingDService.saveSetting(parkingSetting));
    }

}
