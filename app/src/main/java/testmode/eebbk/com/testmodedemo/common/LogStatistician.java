package testmode.eebbk.com.testmodedemo.common;

import java.util.List;

import testmode.eebbk.com.testmodedemo.model.LogEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/17
 * 【实现的主要功能】
 * 【修改者，修改日期，修改内容】
 */
public interface LogStatistician {
    String statistcis(List<LogEntity> logEntities);
}
