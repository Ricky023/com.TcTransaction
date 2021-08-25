package com.wmeimob.api.fbssw.core.compensation.command;

import com.wmeimon.api.fbssw.bean.TransactionRecover;
import com.wmeimon.api.fbssw.enums.CompensationActionEnum;

import java.io.Serializable;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:18
 */
public class TcCompensationAction implements Serializable {

    private static final long serialVersionUID = 7474184793963072848L;


    private CompensationActionEnum compensationActionEnum;


    private TransactionRecover transactionRecover;

    public CompensationActionEnum getCompensationActionEnum() {
        return compensationActionEnum;
    }

    public void setCompensationActionEnum(CompensationActionEnum compensationActionEnum) {
        this.compensationActionEnum = compensationActionEnum;
    }

    public TransactionRecover getTransactionRecover() {
        return transactionRecover;
    }

    public void setTransactionRecover(TransactionRecover transactionRecover) {
        this.transactionRecover = transactionRecover;
    }
}
