package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.domain.ParkingCardAmountLogDO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ParkingCardDService {

    ParkingCard save(ParkingCard parkingCard);

    List<ParkingCard> listAll();

    Optional<ParkingCard> findById(Long id);

    Optional<ParkingCard> findByCardNo(String cardNo);

    List<ParkingCardAmountLogDO> listPayLogByCarPackingStatus(CarParkingStatus carParkingStatus);

    ParkingCard pay(CarParkingStatus carParkingStatus, ParkingCard parkingCard, BigDecimal amount);

    ParkingCard recharge(String orderNum, ParkingCard parkingCard, BigDecimal amount);

    ParkingCard bindCustomer(ParkingCard parkingCard, Customer customer);
}
