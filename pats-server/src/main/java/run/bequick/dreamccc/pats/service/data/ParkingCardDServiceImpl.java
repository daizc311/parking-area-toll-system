package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.domain.ParkingCardAmountLogDO;
import run.bequick.dreamccc.pats.repository.ParkingCardRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingCardDServiceImpl implements ParkingCardDService {
    private final ParkingCardRepository repository;
    private final ParkingCardAmountLogDODService logDODService;

    @Override
    public ParkingCard save(@Validated ParkingCard parkingCard) {
        return repository.save(parkingCard);
    }

    @Override
    public Optional<ParkingCard> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<ParkingCard> findByCardNo(String cardNo) {
        return repository.getByCardNo(cardNo);
    }

    @Override
    public List<ParkingCardAmountLogDO> listPayLogByCarPackingStatus(CarParkingStatus carParkingStatus) {

        return logDODService.listByCarPackingStatusId(carParkingStatus.getId());
    }

    @Override
    @Transactional
    public ParkingCard pay(CarParkingStatus carParkingStatus, ParkingCard parkingCard, BigDecimal amount) {
        switch (parkingCard.getType()) {
            case DISPOSABLE:
            case USER_PERSISTENCE:
                final BigDecimal original = parkingCard.getAmount();
                final BigDecimal result = original.subtract(amount);
                if (result.signum() == -1) {
                    throw new BusinessException("停车卡余额不足");
                }
                parkingCard.setAmount(result);
                logDODService.addAmountLog(ParkingCardAmountLogDO.AmountChangeType.CONSUME, parkingCard,
                        carParkingStatus.getId(), original, amount, result);
                return repository.save(parkingCard);
            default:
                throw new BusinessException("未知的停车卡类型");
        }
    }

    @Override
    public ParkingCard recharge(Long orderId, ParkingCard parkingCard, BigDecimal amount) {
        switch (parkingCard.getType()) {
            case DISPOSABLE:
                throw new BusinessException("一次性停车卡不支持充值");
            case USER_PERSISTENCE:
                final BigDecimal original = parkingCard.getAmount();
                final BigDecimal result = original.add(amount);
                parkingCard.setAmount(result);
                logDODService.addAmountLog(ParkingCardAmountLogDO.AmountChangeType.CONSUME, parkingCard,
                        orderId, original, amount, result);
                return repository.save(parkingCard);
            default:
                throw new BusinessException("未知的停车卡类型");
        }
    }
}
