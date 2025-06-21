package ru.zaikin.enumeration;

public enum BpmLogActivityType {
    START("start"),
    COMPLETE("complete"),
    ERROR("error");

    private final String value;

    BpmLogActivityType(String value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
