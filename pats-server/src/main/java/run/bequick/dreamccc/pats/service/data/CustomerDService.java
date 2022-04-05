package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.param.ThreeFactor;

public interface CustomerDService {

    Customer saveCustomer(ThreeFactor threeFactor, String loginName, String password);

    Customer login(String loginName, String password);
}
