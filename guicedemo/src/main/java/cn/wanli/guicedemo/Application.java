package cn.wanli.guicedemo;

import cn.wanli.guicedemo.annobinding.ComputerModule;
import cn.wanli.guicedemo.annobinding.PurchaseAppleComputer;
import cn.wanli.guicedemo.annobinding.PurchaseHuaweiComputer;
import cn.wanli.guicedemo.instance.DatabaseProp;
import cn.wanli.guicedemo.instance.InstanceModule;
import cn.wanli.guicedemo.linkedbinding.LinkedService;
import cn.wanli.guicedemo.linkedbinding.config.LinkedModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author wanli
 * @date 2019-09-26 16:18
 */
public class Application {
    public static void main(String[] args) {
        System.out.println("---- linked binding ----------");
        linkedBinding();
        System.out.println("\n---- annotation binding -----");
        annoBinding();
        System.out.println("\n---- instance binding -------");
        instanceBinding();
    }

    private static void linkedBinding() {
        Injector injector = Guice.createInjector(new LinkedModule());
        LinkedService instance = injector.getInstance(LinkedService.class);
        instance.sayHello();
    }

    /**
     * 使用注解形式注入
     */
    private static void annoBinding() {
        Injector injector = Guice.createInjector(new ComputerModule());
        injector.getInstance(PurchaseAppleComputer.class).happy();

        injector.getInstance(PurchaseHuaweiComputer.class).happy();
    }

    /**
     * 直接注入
     */
    private static void instanceBinding() {
        Injector injector = Guice.createInjector(new InstanceModule());
        System.out.println(injector.getInstance(DatabaseProp.class));
    }
}
