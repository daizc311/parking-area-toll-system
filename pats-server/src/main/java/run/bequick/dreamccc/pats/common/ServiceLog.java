package run.bequick.dreamccc.pats.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h3>服务日志-注解</h3>
 *
 * @author Daizc
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceLog {

    /**
     * 操作描述，用于打印标准日志
     *
     * @return 打印的具体日志，
     */
    String value() default "";

    /**
     * 用于填充actionDescription中占位符的表达式
     *
     * @return 取参数的表达式
     */
    String[] paramEl() default {};

    /**
     * 用于获取注释本注解的具体类
     * <p>填写ServiceLog.class会自动尝试获取类型</p>
     *
     * @return 需要打印日志的类
     */
    Class<?> loggingClass() default ServiceLog.class;

}
