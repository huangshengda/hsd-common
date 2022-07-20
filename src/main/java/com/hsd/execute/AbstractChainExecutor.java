package com.hsd.execute;

/**
 * 链式执行器
 *
 * @author huangshengda
 * @date 2022-07-20 17:50
 */
public abstract class AbstractChainExecutor {

    private Executor next;

    public void setNext(Executor next) {
        this.next = next;
    }

    protected void nextExecute() {
        if (next != null) {
            next.execute();
        }
    }
}
