package com.kirkk.hello;

public class Value {
    private String value;

    public String getValue() {
        return value;
    }

    public Value setValue(final String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Value{" +
                "value='" + value + '\'' +
                '}';
    }
}
