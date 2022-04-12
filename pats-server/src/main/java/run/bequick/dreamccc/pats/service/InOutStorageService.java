package run.bequick.dreamccc.pats.service;

import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.param.ApiFeePaymentParam;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;

import javax.transaction.Transactional;
import java.math.BigDecimal;

public interface InOutStorageService {

    void inStorage(ApiInStorageParam param);

    boolean feePayment(ApiFeePaymentParam param);

    BigDecimal calcAmountToPaid(Long carId);

    @Transactional
    boolean feePayment(CarParkingStatus carParkingStatus, BigDecimal payAmount, ParkingCard parkingCard);

    boolean outStorage(CarInfo carInfo);

    boolean outStorage(String numberPlate);
}
