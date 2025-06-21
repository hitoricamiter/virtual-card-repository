package ru.zaikin.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public final class BaseBpmUtils {
    public static List<String> getStackTrace(StackTraceElement[] commonStackTrace) {
        return Arrays.stream(commonStackTrace).filter((stackTraceElement) -> {
            String className = stackTraceElement.getClassName();
            return className.startsWith("ru.zaikin");
        }).map((stackTraceElement) -> String.format("%n%s", stackTraceElement)).collect(Collectors.toList());
    }
}