package com.wmeimob.api.fbssw.core.compensation.command;

import com.wmeimob.api.fbssw.core.compensation.TcCompensationService;
import com.wmeimon.api.fbssw.bean.TransactionInvocation;
import com.wmeimon.api.fbssw.bean.TransactionRecover;
import com.wmeimon.api.fbssw.enums.CompensationActionEnum;
import com.wmeimon.api.fbssw.enums.TransactionStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:16
 */
public class TcCompensationCommand implements Command {

    @Autowired
    private TcCompensationService txCompensationService;


    @Override
    public void execute(TcCompensationAction tcCompensationAction) {
        txCompensationService.submit(tcCompensationAction);
    }

    public String saveTxCompensation(TransactionInvocation invocation, String groupId, String taskId) {
        TcCompensationAction action = new TcCompensationAction();
        action.setCompensationActionEnum(CompensationActionEnum.SAVE);
        TransactionRecover recover = new TransactionRecover();
        recover.setRetriedCount(1);
        recover.setStatus(TransactionStatusEnum.BEGIN.getCode());
        recover.setId(groupId);
        recover.setTransactionInvocation(invocation);
        recover.setGroupId(groupId);
        recover.setTaskId(taskId);
        recover.setCreateTime(new Date());
        action.setTransactionRecover(recover);
        execute(action);
        return recover.getId();
    }

    public void removeTxCompensation(String compensateId) {
        TcCompensationAction action = new TcCompensationAction();
        action.setCompensationActionEnum(CompensationActionEnum.DELETE);
        TransactionRecover recover = new TransactionRecover();
        recover.setId(compensateId);
        action.setTransactionRecover(recover);
        execute(action);
    }
}
