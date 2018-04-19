package testmode.eebbk.com.testmodedemo.filter;

import testmode.eebbk.com.testmodedemo.model.LogEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/11
 * <p>
 * 日志筛选器
 */
public interface LogFilter {
    boolean accept(LogEntity logEntity);
}
