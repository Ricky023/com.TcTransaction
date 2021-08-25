package com.wmeimon.api.fbssw.netty;

import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:40
 */
public class TcTransactionGroup implements Serializable {

    private static final long serialVersionUID = -8826219545126676832L;

    /**
     * 事务组id
     */
    private String id;

    /**
     * 事务等待时间
     */
    private int waitTime;

    /**
     * 事务状态
     */
    private int status;

    private List<TcTransactionItem> itemList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TcTransactionItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TcTransactionItem> itemList) {
        this.itemList = itemList;
    }
}
