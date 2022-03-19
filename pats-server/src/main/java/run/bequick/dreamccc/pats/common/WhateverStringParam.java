package run.bequick.dreamccc.pats.common;

import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * <h2>可以接受任意String类型的参数</h2>
 *
 * @author Daizc-kl
 * @date 2020/12/11 14:44
 */
public class WhateverStringParam implements Map<String, String> {

    private static final String WHAT_EVER_STRING = "whateverString";

    @NotBlank
    private String value;

    public String getValue() {
        return value;
    }

    public String get() {
        return getValue();
    }

    @Override
    public int size() {
        return isEmpty() ? 0 : 1;
    }

    @Override
    public boolean isEmpty() {
        return null == value;
    }

    @Override
    public boolean containsKey(Object key) {
        return key instanceof String;
    }

    @Override
    public boolean containsValue(Object value) {
        return Objects.equals(this.value, value);
    }

    @Override
    public String get(Object key) {
        return this.value;
    }

    @Override
    public String put(String key, String value) {
        String temp = this.value;
        this.value = value;
        return temp;
    }

    @Override
    public String remove(Object key) {
        if (this.containsKey(key)) {
            String temp = this.value;
            this.value = null;
            return temp;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        this.value = null;
    }

    @Override
    public Set<String> keySet() {
        return Collections.singleton(WHAT_EVER_STRING);
    }

    @Override
    public Collection<String> values() {
        return Collections.singleton(this.value);
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return Collections.singleton(new SingleMapEntry(WHAT_EVER_STRING, this.value));
    }

    public WhateverStringParam() {
    }

    public WhateverStringParam(@NotBlank String value) {
        this.value = value;
    }

    static final class SingleMapEntry implements Entry<String, String> {
        final String key; // non-null
        String val;       // non-null

        SingleMapEntry(String key, String val) {
            this.key = key;
            this.val = val;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return val;
        }

        @Override
        public String setValue(String value) {
            return val = value;
        }

        public int hashCode() {
            return key.hashCode() ^ val.hashCode();
        }

        public String toString() {
            return key + "=" + val;
        }

        public boolean equals(Object o) {
            Object k, v;
            Entry<?, ?> e;
            return ((o instanceof Map.Entry) &&
                    (k = (e = (Entry<?, ?>) o).getKey()) != null &&
                    (v = e.getValue()) != null &&
                    (k == key || k.equals(key)) &&
                    (v == val || v.equals(val)));
        }
    }
}