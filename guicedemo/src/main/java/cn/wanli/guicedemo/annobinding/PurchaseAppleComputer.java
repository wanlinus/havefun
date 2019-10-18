package cn.wanli.guicedemo.annobinding;

import javax.inject.Inject;

/**
 * @author wanli
 * @date 2019-09-26 17:01
 */
public class PurchaseAppleComputer {

    private final Computer computer;

    /**
     * 使用自定义注解@Apple 注入
     *
     * @param computer 注入Apple Computer
     */
    @Inject
    public PurchaseAppleComputer(@Apple Computer computer) {
        this.computer = computer;
    }

    public void happy() {
        computer.working();
    }


}
