package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.CarParkingLogDO;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.repository.CarParkingStatusRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarParkingStatusDServiceImpl implements CarParkingStatusDService {
    private final CarParkingStatusRepository repository;
    private final CarParkingLogDODService carParkingLogDODService;

    @Override
    @Transactional
    @ServiceLog(value = "新增在库记录 - {pos} - carId:{},date:{}",paramEl = {"#root[0].id","#root[1]"})
    public void addStorageStatus(CarInfo carInfo, Date inStorageTime) {
        // 添加在库记录
        var addCPS = new CarParkingStatus();
        addCPS.setCarInfo(carInfo);
        addCPS.setInStorageDate(inStorageTime);
        CarParkingStatus carParkingStatus = repository.save(addCPS);
        // 添加出入库日志
        carParkingLogDODService.addStorageLog(CarParkingLogDO.CarParkingType.IN,carParkingStatus);
    }

    @Override
    @Transactional
    @ServiceLog(value = "删除在库记录 - {pos} - carId:{},date:{}",paramEl = {"#root[0].id","#root[1]"})
    public void deleteStorageStatus(CarInfo carInfo, Date outStorageTime) {
        try {
            // 查询在库记录
            var byCarInfo = repository.findByCarInfo(carInfo);
            byCarInfo.setInStorageDate(outStorageTime);
            // 添加出入库日志
            carParkingLogDODService.addStorageLog(CarParkingLogDO.CarParkingType.OUT,byCarInfo);
            // 删除在库记录
            repository.delete(byCarInfo);
        } catch (EntityNotFoundException enfe) {
            throw new BusinessException("未查询到该车辆的在库记录，无法进行出库");
        }
    }

}
