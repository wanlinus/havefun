package cn.wanli.guicedemo.annobinding;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author wanli
 * @date 2019-09-26 17:14
 */
public class PurchaseHuaweiComputer {
    private Computer computer;

    /**
     * 使用@Named huawei注解注入
     *
     * @param computer huawei Computer
     */
    @Inject
    public PurchaseHuaweiComputer(@Named("huawei") Computer computer) {
        this.computer = computer;
    }

    public void happy() {
        computer.working();
    }
}
