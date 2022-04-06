package run.bequick.dreamccc.pats.util.stream;

import lombok.Data;

import java.util.Map;

/**
 * <h2>Entry</h2>
 * <p>请添加描述</p>
 *
 * @author Daizc
 * @date 2019/12/12
 */
@Data
public class Entry<K, V> {

    private K key;

    private V value;

    public Entry() {
    }

    public Entry(Map.Entry<K, V> entry) {
        this.key = entry.getKey();
        this.value = entry.getValue();
    }

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
