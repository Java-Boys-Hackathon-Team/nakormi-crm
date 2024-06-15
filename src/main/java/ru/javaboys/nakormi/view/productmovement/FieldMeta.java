package ru.javaboys.nakormi.view.productmovement;

public class FieldMeta<V> {
    private final boolean enable;
    private final V value;

    public FieldMeta(boolean enable, V value) {
        this.enable = enable;
        this.value = value;
    }

    public boolean isEnable() {
        return enable;
    }

    public V getValue() {
        return value;
    }

    public static <V> FieldMeta<V> enabled() {
        return new FieldMeta<>(true, null);
    }

    public static <V> FieldMeta<V> disabled() {
        return new FieldMeta<>(false, null);
    }

    public static <V> FieldMeta<V> disabled(V value) {
        return new FieldMeta<>(false, value);
    }
}