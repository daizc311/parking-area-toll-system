package run.bequick.dreamccc.pats;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import run.bequick.dreamccc.pats.common.WhateverStringParam;
import run.bequick.dreamccc.pats.controller.*;
import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.param.CustomerRegisterParam;
import run.bequick.dreamccc.pats.param.ThreeFactor;
import run.bequick.dreamccc.pats.security.JwtUsernamePasswordAuthenticationToken;
import run.bequick.dreamccc.pats.security.UserType;
import run.bequick.dreamccc.pats.service.data.CarInfoDService;

import java.util.Collections;

@SpringBootTest
class ParkingAreaTollSystemApplicationTests {

    @Autowired
    ApiActionController apiActionController;
    @Autowired
    CustomerController customerController;
    @Autowired
    FakedLoginController fakedLoginController;
    @Autowired
    SettingController settingController;
    @Autowired
    UserController userController;
    @Autowired
    CarInfoDService carInfoDService;


    @Test
    void InOutStorageTest() {
        inStorage();
        addCustomerAndBindCar();
        rechargeParkingCard();
        pay();
        outStorage();
    }

    void inStorage() {
        final var storageParam = new ApiInStorageParam();
        final var simpleCarInfo = new ApiInStorageParam.SimpleCarInfo();
        simpleCarInfo.setColor("白色");
        simpleCarInfo.setModelName("长安 CS75");
        simpleCarInfo.setNumberPlate("渝A" + UUID.fastUUID().toString(true).substring(0, 6));
        storageParam.setCarInfo(simpleCarInfo);
        storageParam.setInStorageTime(new DateTime("2022-04-12 20:45:19", "yyyy-MM-dd hh:mm:ss"));
        apiActionController.inStorage(storageParam);
    }

    Customer addCustomerAndBindCar() {
        final var threeFactor = new ThreeFactor();
        threeFactor.setIdNumber("500107199603110000");
        threeFactor.setMobile(UUID.fastUUID().toString(true).substring(0, 11));
        threeFactor.setRealName("戴智超");
        final var threeFactorId = customerController.registerThreeFactor(threeFactor).getData();
        final var customerRegisterParam = new CustomerRegisterParam();
        customerRegisterParam.setLoginName(UUID.fastUUID().toString(true));
        customerRegisterParam.setPassword("123456");
        customerRegisterParam.setThreeFactor(threeFactorId);
        final var customer = customerController.register(customerRegisterParam).getData();


        final var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new JwtUsernamePasswordAuthenticationToken(
                UserType.CUSTOMER, customer.getId(), customer.getLoginName(), null,
                Collections.singletonList(new SimpleGrantedAuthority("CUSTOMER"))
        ));
        SecurityContextHolder.setContext(context);

        final var carInfo = carInfoDService.findByNumberPlate("渝A1234568").orElseThrow();

        final var drResponse = customerController.bindCarInfo(new WhateverStringParam(carInfo.getNumberPlate()));

        return customer;
    }

    void rechargeParkingCard() {

    }

    void pay() {

    }

    void outStorage() {

    }

}
