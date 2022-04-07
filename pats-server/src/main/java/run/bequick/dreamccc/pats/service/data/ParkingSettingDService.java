package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.ParkingSetting;

public interface ParkingSettingDService {

    ParkingSetting getSetting();

    ParkingSetting saveSetting(ParkingSetting parkingSetting);

    ParkingSetting init();

}
