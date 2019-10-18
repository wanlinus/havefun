package cn.wanli.guicedemo.instance;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author wanli
 * @date 2019-09-26 17:37
 */
public class DatabaseProp {

    @Inject
    @Named("JDBC URL")
    private String dbConfig;

    @Override
    public String toString() {
       return dbConfig;
    }
}
