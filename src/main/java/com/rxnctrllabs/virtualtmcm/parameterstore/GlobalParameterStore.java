package com.rxnctrllabs.virtualtmcm.parameterstore;

import java.util.HashMap;
import java.util.Map;

public final class GlobalParameterStore {

    private static final Map<Integer, Long> GLOBAL_PARAMETERS = new HashMap<>();

    static {
    }

    private GlobalParameterStore() {
    }

    public static Long getGlobalParameter(final Integer type) {
        return GLOBAL_PARAMETERS.getOrDefault(type, 0L);
    }

    public static void setGlobalParameter(final Integer type, final Long value) {
        GLOBAL_PARAMETERS.put(type, value);
    }
}
