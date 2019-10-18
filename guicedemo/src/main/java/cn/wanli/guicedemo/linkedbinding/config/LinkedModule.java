package cn.wanli.guicedemo.linkedbinding.config;

import cn.wanli.guicedemo.linkedbinding.Linked2Impl;
import cn.wanli.guicedemo.linkedbinding.LinkedService;
import cn.wanli.guicedemo.linkedbinding.LinkedServiceImpl;
import com.google.inject.AbstractModule;

/**
 * @author wanli
 * @date 2019-09-26 16:12
 */
public class LinkedModule extends AbstractModule {
    /**
     * Link from a type to any of its subtypes, such as an implementing class or an extending class.
     */
    @Override
    protected void configure() {
        bind(LinkedService.class).to(LinkedServiceImpl.class);
        bind(LinkedServiceImpl.class).to(Linked2Impl.class);
    }
}
