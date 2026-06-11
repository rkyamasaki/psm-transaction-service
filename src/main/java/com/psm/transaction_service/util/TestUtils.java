package com.psm.transaction_service.util;

import java.lang.reflect.Constructor;

public class TestUtils {

    public static <T> T instantiate(Class<T> type) {
        try {
            Constructor<T> constructor =
                    type.getDeclaredConstructor();

            constructor.setAccessible(true);

            return constructor.newInstance();
        } catch (Exception exception) {
            throw new RuntimeException(
                    "Could not instantiate " + type.getSimpleName(),
                    exception
            );
        }
    }
}
