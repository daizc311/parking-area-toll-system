package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.repository.ParkingSettingRepository;

@Service
@RequiredArgsConstructor
public class ParkingSettingDServiceImpl implements ParkingSettingDService{
    private final ParkingSettingRepository repository;
}
