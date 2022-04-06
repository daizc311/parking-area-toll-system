package run.bequick.dreamccc.pats.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <h3>服务日志-切面</h3>
 *
 * @author Daizc
 */
@Aspect
@Component
public class ServiceLogAspect {

    private final static Logger defaultLogger = LoggerFactory.getLogger("ServiceLog");
    public static final String POS = "{pos}";
    private static final Map<String, SpelExpression> SPEL_CACHE = new HashMap<>();
    private static final Map<Class<?>, Logger> LOGGER_CACHE = new HashMap<>();

    static {
        LOGGER_CACHE.put(ServiceLog.class, defaultLogger);
    }

    @Around(value = "@annotation(serviceLog)")
    public Object serviceLog(ProceedingJoinPoint joinPoint, ServiceLog serviceLog) throws Throwable {
        var point = (MethodInvocationProceedingJoinPoint) joinPoint;
        var targetArgs = point.getArgs();


        var signature = point.getSignature();
        var targetMethodName = signature.getName();
        var targetClazz = signature.getDeclaringType();
        var targetInstance = point.getTarget();
        Class<?>[] classes = Arrays.stream(targetArgs).map(Object::getClass).toArray(Class[]::new);

        Logger logger = this.getLogger(serviceLog);
        String logFormat = this.getLogFormat(serviceLog, signature);
        String[] logParams = this.getLogParams(serviceLog, targetClazz.getMethod(targetMethodName, classes), targetInstance, targetArgs);

        try {
            this.logInfo(logger, "开始", logFormat, logParams);
            Object proceed = joinPoint.proceed();
            this.logInfo(logger, "完成", logFormat, logParams);
            return proceed;
        } catch (Throwable e) {
            this.logInfo(logger, "异常", logFormat, logParams);
            throw e;
        }
    }

    private String[] getLogParams(ServiceLog serviceLog, Method method, Object obj, Object[] targetArgs) {

        return Arrays.stream(serviceLog.paramEl())
                .map(el -> {
                    try {
                        SpelExpression spelExpression = SPEL_CACHE.computeIfAbsent(el, elStr -> new SpelExpressionParser().parseRaw(elStr));
                        return spelExpression.getValue(targetArgs);
                    } catch (EvaluationException e) {
                        defaultLogger.warn("解析SPEL发生异常", e);
                        return e.getMessage();
                    }
                })
                .filter(Objects::nonNull)
                .map(Object::toString)
                .toArray(String[]::new);
    }

    public void logInfo(Logger logger, String pos, String format, String[] params) {

        logger.info(format.replace(POS, pos), params);
    }

    public String getLogFormat(ServiceLog serviceLog, Signature signature) {
        String logFormat = serviceLog.value();
        if ("".equals(logFormat)) {
            return signature.getName() + " - " + POS;
        }
        return logFormat;
    }

    public Logger getLogger(ServiceLog serviceLog) {

        return LOGGER_CACHE.computeIfAbsent(serviceLog.loggingClass(), LoggerFactory::getLogger);
    }


}
