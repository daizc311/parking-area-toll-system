package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.CarParkingLogDO;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.domain.ParkingCardAmountLogDO;

import javax.transaction.Transactional;
import java.math.BigDecimal;

public interface ParkingCardAmountLogDODService {

    /**
     * 添加充值/消费记录
     *
     * @param type     充值/消费
     * @param card     停车卡
     * @param original 原始金额
     * @param amount   变动金额
     * @param result   最终金额
     */
    @Transactional
    void addAmountLog(ParkingCardAmountLogDO.AmountChangeType type, ParkingCard card,
                      BigDecimal original, BigDecimal amount, BigDecimal result);
}
