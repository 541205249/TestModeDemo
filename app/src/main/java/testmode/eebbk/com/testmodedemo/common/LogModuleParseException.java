package testmode.eebbk.com.testmodedemo.common;

/**
 * @author LiXiaoFeng
 * @date 2018/4/17
 */
public class LogModuleParseException extends RuntimeException {
    public LogModuleParseException(String message) {
        super(message);
    }

    public LogModuleParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
