package run.bequick.dreamccc.pats.service.data;

import cn.hutool.core.text.StrFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.ParkingSetting;
import run.bequick.dreamccc.pats.repository.CarParkingStatusRepository;
import run.bequick.dreamccc.pats.repository.ParkingSettingRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ParkingSettingDServiceImpl implements ParkingSettingDService {
    public static final String SETTING_ID = "PATS";
    private final ParkingSettingRepository repository;
    private final CarParkingStatusRepository cpsRepository;

    @Override
    public ParkingSetting getSetting() {
        return repository.getById(SETTING_ID);
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
        try {
            return repository.getById(SETTING_ID);
        } catch (EntityNotFoundException enfe) {
            final ParkingSetting parkingSetting = new ParkingSetting();
            parkingSetting.setId("PATS");
            // 默认车位总数
            parkingSetting.setParkingSpacesTotal(100);
            // 默认首个周期计费
            parkingSetting.setFirstCycleCanBilling(true);
            // 默认 30分钟  2元
            parkingSetting.setBillingCycle(30L);
            parkingSetting.setBillingAmount(BigDecimal.valueOf(200L, 2));
            repository.save(parkingSetting);
            return parkingSetting;
        }
    }
}
