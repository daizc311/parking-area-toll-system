package run.bequick.dreamccc.pats.common;

/**
 * <h3>简单封装的业务异常</h3>
 *
 * @author Daizc
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
