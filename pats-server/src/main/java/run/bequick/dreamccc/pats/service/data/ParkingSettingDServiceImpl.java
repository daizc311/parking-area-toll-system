package run.bequick.dreamccc.pats.service.data;

import cn.hutool.core.text.StrFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.ParkingSetting;
import run.bequick.dreamccc.pats.repository.CarParkingStatusRepository;
import run.bequick.dreamccc.pats.repository.ParkingSettingRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ParkingSettingDServiceImpl implements ParkingSettingDService {
    public static final String SETTING_ID = "PATS";
    private final ParkingSettingRepository repository;
    private final CarParkingStatusRepository cpsRepository;

    @Override
    public ParkingSetting getSetting() {
        return repository.findById(SETTING_ID).orElseThrow(() -> new BusinessException("系统设置错误，请重新初始化系统"));
    }

    @Override
    public ParkingSetting saveSetting(ParkingSetting parkingSetting) {
        parkingSetting.setId(SETTING_ID);

        final Integer spacesTotal = parkingSetting.getParkingSpacesTotal();
        final long inStorageTotal = cpsRepository.count();
        if (spacesTotal < inStorageTotal) {
            throw new BusinessException(StrFormatter.format("停车场车位数量[{}]应该大于当前当前已入库数量[{}]", spacesTotal, inStorageTotal));
        }

        return repository.save(parkingSetting);
    }

    @Override
    @ServiceLog("初始化系统设置 - {pos}")
    public ParkingSetting init() {
        return repository.findById(SETTING_ID)
                .orElseGet(() -> {
                    final ParkingSetting parkingSetting = new ParkingSetting();
                    parkingSetting.setId("PATS");
                    parkingSetting.setSystemName("停车场收费系统");
                    // 默认车位总数
                    parkingSetting.setParkingSpacesTotal(100);
                    // 默认首个周期计费
                    parkingSetting.setFirstCycleCanBilling(true);
                    // 默认 30分钟  2元
                    parkingSetting.setBillingCycle(1800L);
                    parkingSetting.setBillingAmount(BigDecimal.valueOf(200L, 2));
                    // 次卡最多允许停放8小时
                    parkingSetting.setMaxCountAmount(BigDecimal.valueOf(32L, 2));
                    repository.save(parkingSetting);
                    return parkingSetting;
                });
    }
}
