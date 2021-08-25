package com.wmeimob.api.fbssw.core.server.impl;

import com.wmeimob.api.fbssw.core.server.TcTransactionFactoryService;
import com.wmeimob.api.fbssw.core.server.handler.ActorTxTransactionHandler;
import com.wmeimob.api.fbssw.core.server.handler.InsideCompensationHandler;
import com.wmeimob.api.fbssw.core.server.handler.StartCompensationHandler;
import com.wmeimob.api.fbssw.core.server.handler.StartTcTransactionHandler;
import com.wmeimon.api.fbssw.bean.TcTransactionInfo;
import com.wmeimon.api.fbssw.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-21 23:36
 */
public class TcTransactionFactoryServiceImpl implements TcTransactionFactoryService {

    @Override
    public Class factoryOf(TcTransactionInfo info) throws Throwable {

        if (StringUtils.isNoneBlank(info.getCompensationId())) {
            return StartCompensationHandler.class;
        }
        if (StringUtils.isBlank(info.getTcGroupId())) {
            return StartTcTransactionHandler.class;
        } else {
            if (Objects.equals(CommonConstant.COMPENSATE_ID, info.getTcGroupId())) {
                return InsideCompensationHandler.class;
            }
            return ActorTxTransactionHandler.class;
        }
    }
}
