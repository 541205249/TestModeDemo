package testmode.eebbk.com.testmodedemo.common;

/**
 * @author LiXiaoFeng
 * @date 2018/4/11
 * <p>
 * 日志筛选器
 */
public interface LogFilter {
    boolean accept(String target);
}