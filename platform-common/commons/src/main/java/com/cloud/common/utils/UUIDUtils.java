package com.cloud.common.utils;

import java.util.UUID;

public class UUIDUtils
{

    private UUIDUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
