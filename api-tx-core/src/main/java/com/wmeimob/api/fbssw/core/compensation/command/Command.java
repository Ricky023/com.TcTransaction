package com.wmeimob.api.fbssw.core.compensation.command;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:17
 */
@FunctionalInterface
public interface Command {

    /**
     * 执行命令接口
     * @param tcCompensationAction
     */
    void execute(TcCompensationAction tcCompensationAction);
}
