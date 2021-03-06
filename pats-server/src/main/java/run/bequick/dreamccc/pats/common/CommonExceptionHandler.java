package run.bequick.dreamccc.pats.common;

import cn.hutool.core.text.StrFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <h3>公共异常处理</h3>
 *
 * @author Daizc
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public DrResponse<?> businessException(BusinessException exception) {
        return DrResponse.failed(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public DrResponse<?> exception(Exception exception) {
        log.error(StrFormatter.format("发生错误:{}", exception.getMessage()), exception);
        return DrResponse.failed(exception.getMessage());
    }

}
