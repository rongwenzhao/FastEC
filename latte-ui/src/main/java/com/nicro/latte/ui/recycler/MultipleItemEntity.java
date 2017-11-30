package com.nicro.latte.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * 当RecycleView中的数据很多时，可能会造成内存溢出。
 * 所以我们用weakRefreence或softReference来存储数据，这样在内存不足时会清理不使用的内存数据。
 * Created by rongwenzhao on 2017/11/26.
 */

public class MultipleItemEntity implements MultiItemEntity {
    //http://blog.csdn.net/u012332679/article/details/57489179
    //SoftReference与WeakReference类似，不同之处是，SoftReference只有在内存快要达到OOM时才会被回收，
    // 而WeakReference一旦不用就被回收。
    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();//真正的数据存储项。
    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFREENCE =
            new SoftReference<LinkedHashMap<Object, Object>>(MULTIPLE_FIELDS, ITEM_QUEUE);

    MultipleItemEntity(LinkedHashMap<Object, Object> fields) {
        FIELDS_REFREENCE.get().putAll(fields);
    }

    public static MultipleEntityBuilder builder() {
        return new MultipleEntityBuilder();
    }

    /**
     * 返回数据项的类型
     *
     * @return
     */
    @Override
    public int getItemType() {
        return (int) FIELDS_REFREENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    /**
     * 获取缓存中其他具体数据的方法
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T> T getField(Object key) {
        return (T) FIELDS_REFREENCE.get().get(key);
    }

    /**
     * 返回全部缓存数据
     *
     * @return
     */
    public final LinkedHashMap<?, ?> getFields() {
        return FIELDS_REFREENCE.get();
    }

    /**
     * 对缓存数据进行赋值
     *
     * @param key
     * @param value
     * @return
     */
    public final MultipleItemEntity setField(Object key, Object value) {
        FIELDS_REFREENCE.get().put(key, value);
        return this;
    }

}
