package cn.wanli.guicedemo.instance;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author wanli
 * @date 2019-09-26 17:25
 */
public class InstanceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(String.class)
                .annotatedWith(Names.named("JDBC URL"))
                .toInstance("jdbc:mysql://localhost/pizza");
        bind(Integer.class)
                .annotatedWith(Names.named("login timeout seconds"))
                .toInstance(10);
    }


}
