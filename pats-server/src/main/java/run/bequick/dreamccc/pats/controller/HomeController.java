package run.bequick.dreamccc.pats.controller;

import cn.hutool.core.date.DateTime;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.domain.CarParkingLogDO;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.param.EChatData;
import run.bequick.dreamccc.pats.repository.CarParkingLogDORepository;
import run.bequick.dreamccc.pats.repository.CarParkingStatusRepository;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final CarParkingStatusRepository carParkingStatusRepository;
    private final CarParkingLogDORepository carParkingLogDORepository;

    @Operation(summary = "[图表]当前在库情况图", tags = {"Home", "Chart"})
    @PostMapping("/chartByInStorageDate")
    public DrResponse<List<EChatData>> chartByInStorageDate() {

        final var now = new DateTime();
        final var calendar1 = now.toCalendar();
        calendar1.add(Calendar.DATE, -1);
        final var last1 = calendar1.getTime();

        final var calendar7 = now.toCalendar();
        calendar7.add(Calendar.DATE, -7);
        final var last7 = calendar7.getTime();

        final var calendar30 = now.toCalendar();
        calendar30.add(Calendar.DATE, -30);
        final var last30 = calendar30.getTime();

        final var collectMap = carParkingStatusRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(carParkingStatus -> {
                    final var date = carParkingStatus.getInStorageDate();
                    if (date.before(last30)) {
                        return "30天之前";
                    } else if (date.before(last7)) {
                        return "7天之前";
                    } else if (date.before(last1)) {
                        return "1天之前";
                    } else {
                        return "今天";
                    }
                }, Collectors.counting()));

        final var collect = collectMap.keySet()
                .stream()
                .map(s -> new EChatData(s, collectMap.get(s)))
                .collect(Collectors.toList());

        return DrResponse.data(collect);
    }

    @Operation(summary = "[图表]最近7天入库情况图", tags = {"Home", "Chart"})
    @PostMapping("/chartByInStorageLast7")
    public DrResponse<List<EChatData>> chartByInStorageLast7() {

        final var calendar30 = new DateTime().toCalendar();
        calendar30.add(Calendar.DATE, -7);
        final var last30 = calendar30.getTime();
        final var collectMap = carParkingLogDORepository.findAll().stream()
                .filter(carParkingLogDO -> carParkingLogDO.getParkingDate().after(last30))
                .filter(carParkingLogDO -> carParkingLogDO.getType().equals(CarParkingLogDO.CarParkingType.IN))
                .collect(Collectors.groupingBy(
                        carParkingLogDO -> new DateTime(carParkingLogDO.getParkingDate()).toString("yyyy-MM-dd"),
                        Collectors.counting()
                ));

        final var collect = collectMap.keySet()
                .stream()
                .map(s -> new EChatData(s, collectMap.get(s)))
                .collect(Collectors.toList());
        return DrResponse.data(collect);
    }


    @Operation(summary = "当前在库车辆信息", tags = {"Home"})
    @PostMapping("/allCarParkingStatus")
    public DrResponse<List<CarParkingStatus>> allCarParkingStatus() {

        return DrResponse.data(carParkingStatusRepository.findAll());
    }


    @Operation(summary = "最近出入库记录", tags = {"Home"})
    @PostMapping("/allCarParkingLog")
    public DrResponse<List<CarParkingLogDO>> allCarParkingLog() {

        return DrResponse.data(carParkingLogDORepository.findAll());
    }
}
