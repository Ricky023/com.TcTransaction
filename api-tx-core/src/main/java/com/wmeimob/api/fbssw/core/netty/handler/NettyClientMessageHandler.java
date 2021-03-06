package com.wmeimob.api.fbssw.core.netty.handler;

import com.wmeimob.api.fbssw.core.concurrent.task.BlockTask;
import com.wmeimob.api.fbssw.core.concurrent.task.BlockTaskHelper;
import com.wmeimon.api.fbssw.config.TxConfig;
import com.wmeimon.api.fbssw.enums.NettyMessageActionEnum;
import com.wmeimon.api.fbssw.enums.NettyResultEnum;
import com.wmeimon.api.fbssw.holder.IdWorkerUtils;
import com.wmeimon.api.fbssw.netty.HeartBeat;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-08-11 16:02
 */
@Component
@ChannelHandler.Sharable
public class NettyClientMessageHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private static volatile ChannelHandlerContext ctx;

    @Autowired
    private static final HeartBeat HEART_BEAT = new HeartBeat();

    private TxConfig txConfig;

    public void setTxConfig(TxConfig txConfig) {
        this.txConfig = txConfig;
    }


    public Object sendTxManagerMessage(HeartBeat heartBeat) {
        if (ctx != null && ctx.channel() != null && ctx.channel().isActive()) {
            final String sendKey = IdWorkerUtils.getInstance().createTaskKey();
            BlockTask sendTask = BlockTaskHelper.getInstance().getTask(sendKey);
            heartBeat.setKey(sendKey);
            ctx.writeAndFlush(heartBeat);
            final ScheduledFuture<?> schedule = ctx.executor()
                    .schedule(() -> {
                        if (!BlockTask.isNotify()) {
                            if (NettyMessageActionEnum.GET_TRANSACTION_GROUP_STATUS.getCode()
                                    == heartBeat.getAction()) {
                                sendTask.setAsyncCall(objects -> NettyResultEnum.TIME_OUT.getCode());
                            } else if (NettyMessageActionEnum.FIND_TRANSACTION_GROUP_INFO.getCode()
                                    == heartBeat.getAction()) {
                                sendTask.setAsyncCall(objects -> null);
                            } else {
                                sendTask.setAsyncCall(objects -> false);
                            }
                            sendTask.signal();
                        }
                    }, txConfig.getDelayTime(), TimeUnit.SECONDS);
            //??????????????????????????????tm?????? ???????????????????????????????????? ????????????????????????????????????????????????????????????
            sendTask.await();

            //???????????????????????????????????????????????????????????? ?????????????????????????????????????????????
            if (!schedule.isDone()) {
                schedule.cancel(false);
            }
            try {
                return sendTask.getAsyncCall().callBack();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            } finally {
                BlockTaskHelper.getInstance().removeByKey(sendKey);
            }

        } else {
            return null;
        }

    }

    /**
     * tm ?????? ????????????
     *
     * @param heartBeat ???????????????????????????
     */
    public void asyncSendTxManagerMessage(HeartBeat heartBeat) {
        if (ctx != null && ctx.channel() != null && ctx.channel().isActive()) {
            ctx.writeAndFlush(heartBeat);
        }

    }
}
