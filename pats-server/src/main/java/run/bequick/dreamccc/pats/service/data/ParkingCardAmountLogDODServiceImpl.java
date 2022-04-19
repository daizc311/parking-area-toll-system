package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.domain.ParkingCardAmountLogDO;
import run.bequick.dreamccc.pats.repository.ParkingCardAmountLogDORepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingCardAmountLogDODServiceImpl implements ParkingCardAmountLogDODService {
    private final ParkingCardAmountLogDORepository repository;

    @Override
    @ServiceLog(value = "保存停车卡充值/消费日志 - {pos} - cardId:{},type:{},after:{},change:{},before:{}",
            paramEl = {"#root[1].id", "#root[0]", "#root[3]", "#root[4]", "#root[5]"})
    public void addAmountLog(ParkingCardAmountLogDO.AmountChangeType type, ParkingCard card, String changeEventId,
                             BigDecimal after, BigDecimal change, BigDecimal before) {
        final var addDO = new ParkingCardAmountLogDO();
        addDO.setAfterAmount(after);
        addDO.setChangeAmount(change);
        addDO.setBeforeAmount(before);
        addDO.setChangeEventId(changeEventId);
        addDO.setType(type);
        addDO.setCardId(card.getId());
        Optional.ofNullable(card.getCustomer())
                .map(AbstractPersistable::getId)
                .ifPresent(addDO::setUserId);
        repository.save(addDO);
    }

    @Override
    public List<ParkingCardAmountLogDO> listByCarPackingStatusId(Long id) {
        return repository.findAllByChangeEventIdAndType(id.toString(), ParkingCardAmountLogDO.AmountChangeType.CONSUME);
    }
}
