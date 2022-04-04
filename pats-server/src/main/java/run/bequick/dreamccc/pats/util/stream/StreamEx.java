package run.bequick.dreamccc.pats.util.stream;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <h2>StreamUtil</h2>
 * <p>函数式接口功能补充</p>
 *
 * @author Daizc
 * @date 2019/12/11
 */
public class StreamEx {

    /**
     * <h3>distinctByKey</h3>
     * <p>可用于在filter中根据属性过滤</p>
     *
     * @param function 接受一个 接受 类型A 返回 类型B 的函数式接口
     * @return Predicate 返回一个 接受 类型A 返回 Boolean型 的函数式接口
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> function) {
        Map<Object, Boolean> filterMap = new ConcurrentHashMap<>();

        return t -> filterMap.putIfAbsent(function.apply(t), Boolean.TRUE) == null;
    }


    /**
     * <h3>safeGet</h3>
     * <p>自动处理空指针异常</p>
     *
     * @param supplier 不接受参数 直接返回 类型R 的函数式接口
     * @return R 函数式接口的处理结果
     */
    public static <R> R safeGet(Supplier<R> supplier) {

        R get = null;
        try {
            get = supplier.get();
        } catch (NullPointerException ignored) {
        }
        return get;
    }

    public static <T, U extends Comparable<? super U>> Comparator<T> comparingEx(
            Function<? super T, ? extends U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable)
                (c1, c2) -> {
                    U c1result;
                    U c2result;
                    try {
                        c1result = keyExtractor.apply(c1);
                    } catch (NullPointerException npe) {
                        return Integer.MAX_VALUE;
                    }
                    try {
                        c2result = keyExtractor.apply(c2);
                    } catch (NullPointerException npe) {
                        return Integer.MIN_VALUE;
                    }
                    return c1result.compareTo(c2result);
                };
    }

    /**
     * <h3>entry</h3>
     * <p>创建一个<b>Entry<KEY,VALUE>实体</b>的函数</p>
     * <p>可用于在map中将实体转为Entry</p>
     *
     * @param keyFunction   接受一个 接受 类型A 返回 类型B 的函数式接口
     * @param valueFunction 接受一个 接受 类型A 返回 类型B 的函数式接口
     * @return Function<T, Entry < KEY, VALUE>>
     */
    public static <KEY, VALUE, T> Function<T, Entry<KEY, VALUE>> entry(Function<T, KEY> keyFunction, Function<T, VALUE> valueFunction) {

        return t -> new Entry<>(keyFunction.apply(t), valueFunction.apply(t));
    }


}
