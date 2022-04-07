package run.bequick.dreamccc.pats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.domain.ParkingSetting;
import run.bequick.dreamccc.pats.service.data.CarParkingStatusDService;
import run.bequick.dreamccc.pats.service.data.ParkingSettingDService;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/setting")
public class SettingController {
    private final ParkingSettingDService parkingSettingDService;

    @PostMapping("/getSetting")
    public DrResponse<ParkingSetting> getSetting() {
        return DrResponse.data(parkingSettingDService.getSetting());
    }

    @PostMapping("/saveSetting")
    public DrResponse<ParkingSetting> saveSetting(@RequestBody ParkingSetting parkingSetting) {
        return DrResponse.data(parkingSettingDService.saveSetting(parkingSetting));
    }

}
