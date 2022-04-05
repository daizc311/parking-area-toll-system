package run.bequick.dreamccc.pats.service;

import run.bequick.dreamccc.pats.param.ApiFeePaymentParam;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.param.ApiOutStorageParam;

public interface InOutStorageService {

    void inStorage(ApiInStorageParam param);

    void feePayment(ApiFeePaymentParam param);

    void outStorage(ApiOutStorageParam param);
}
