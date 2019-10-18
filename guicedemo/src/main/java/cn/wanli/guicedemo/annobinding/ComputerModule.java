package cn.wanli.guicedemo.annobinding;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author wanli
 * @date 2019-09-26 17:07
 */
public class ComputerModule extends AbstractModule {
    @Override
    protected void configure() {
        //add configuration logic here
        bind(Computer.class).annotatedWith(Apple.class).to(MacBookPro.class);
        bind(Computer.class).annotatedWith(Names.named("huawei")).to(HuaweiMateBook.class);
    }
}
