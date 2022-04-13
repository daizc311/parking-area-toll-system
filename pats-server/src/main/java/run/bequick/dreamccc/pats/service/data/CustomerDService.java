package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.param.ThreeFactor;

import java.util.Optional;

public interface CustomerDService {

    Optional<Customer> findById(Long id);

    Customer saveCustomer(ThreeFactor threeFactor, String loginName, String password);

    Customer login(String loginName, String password);

    Customer bindParkingCard(Customer customer, String cardNo, String paramCardPwd);

    Customer bindCarInfo(Customer customer, String numberPlate);
}
