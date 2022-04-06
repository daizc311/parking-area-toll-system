package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.domain.ParkingCardAmountLogDO;
import run.bequick.dreamccc.pats.repository.ParkingCardRepository;

import java.math.BigDecimal;
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
    public Optional<ParkingCard> getByCardNo(String cardNo) {
        return repository.getByCardNo(cardNo);
    }

    @Override
    public ParkingCard pay(ParkingCard parkingCard, BigDecimal amount) {
        switch (parkingCard.getType()) {
            case DISPOSABLE:
            case USER_PERSISTENCE:
                final BigDecimal original = parkingCard.getAmount();
                final BigDecimal result = original.subtract(amount);
                if (result.signum() == -1) {
                    throw new BusinessException("停车卡余额不足");
                }
                parkingCard.setAmount(result);
                logDODService.addAmountLog(ParkingCardAmountLogDO.AmountChangeType.CONSUME,
                        parkingCard, original, amount, result);
                return repository.save(parkingCard);
            default:
                throw new BusinessException("未知的停车卡类型");
        }
    }

    @Override
    public ParkingCard recharge(ParkingCard parkingCard, BigDecimal amount) {
        switch (parkingCard.getType()) {
            case DISPOSABLE:
                throw new BusinessException("一次性停车卡不支持充值");
            case USER_PERSISTENCE:
                final BigDecimal original = parkingCard.getAmount();
                final BigDecimal result = original.add(amount);
                parkingCard.setAmount(result);
                logDODService.addAmountLog(ParkingCardAmountLogDO.AmountChangeType.CONSUME,
                        parkingCard, original, amount, result);
                return repository.save(parkingCard);
            default:
                throw new BusinessException("未知的停车卡类型");
        }
    }
}
