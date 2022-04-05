package run.bequick.dreamccc.pats.service;

import run.bequick.dreamccc.pats.param.ApiFeePaymentParam;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.param.ApiOutStorageParam;

public interface InOutStorageService {

    boolean inStorage(ApiInStorageParam param);

    boolean feePayment(ApiFeePaymentParam param);

    boolean outStorage(ApiOutStorageParam param);
}
