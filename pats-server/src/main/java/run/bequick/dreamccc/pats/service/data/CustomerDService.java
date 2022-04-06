package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.common.WhateverStringParam;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.param.ThreeFactor;

public interface CustomerDService {

    Customer getById(Long id);

    Customer saveCustomer(ThreeFactor threeFactor, String loginName, String password);

    Customer login(String loginName, String password);

    Customer bindParkingCard(Customer customer, String cardNo, String paramCardPwd);

    Customer bindCarInfo(Customer customer, String numberPlate);
}
