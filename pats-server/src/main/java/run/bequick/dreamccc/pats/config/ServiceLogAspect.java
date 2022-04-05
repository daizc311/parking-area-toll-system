package run.bequick.dreamccc.pats.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogAspect {

    private final static Logger defaultLogger = LoggerFactory.getLogger("ServiceLog");

    @Around("")
    public Object serviceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();

        Logger logger = getLogger();

        try {
            logger.info("in");
            Object proceed = joinPoint.proceed();
            logger.info("out");
            return proceed;
        } catch (Throwable e) {
            logger.info("error");
            throw e;
        }
    }


    public Logger getLogger(){
        return defaultLogger;
    }


}
