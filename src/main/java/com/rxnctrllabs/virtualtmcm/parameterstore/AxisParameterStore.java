package com.rxnctrllabs.virtualtmcm.parameterstore;

import java.util.HashMap;
import java.util.Map;

public final class AxisParameterStore {

    private static final Map<Integer, Long> AXIS_PARAMETERS = new HashMap<>();

    static {
        AXIS_PARAMETERS.put(0, 0L);
        AXIS_PARAMETERS.put(1, 0L);
        AXIS_PARAMETERS.put(2, 0L);
        AXIS_PARAMETERS.put(3, 0L);
        AXIS_PARAMETERS.put(4, 1000L);
        AXIS_PARAMETERS.put(5, 500L);
        AXIS_PARAMETERS.put(6, 128L);
        AXIS_PARAMETERS.put(7, 8L);
        AXIS_PARAMETERS.put(8, 0L);
        AXIS_PARAMETERS.put(9, 0L);
        AXIS_PARAMETERS.put(10, 0L);
        AXIS_PARAMETERS.put(11, 0L);
        AXIS_PARAMETERS.put(12, 0L);
        AXIS_PARAMETERS.put(13, 0L);
        AXIS_PARAMETERS.put(130, 0L);
        AXIS_PARAMETERS.put(135, 0L);
        AXIS_PARAMETERS.put(138, 0L);
        AXIS_PARAMETERS.put(140, 4L);
        AXIS_PARAMETERS.put(141, 0L);
        AXIS_PARAMETERS.put(149, 0L);
        AXIS_PARAMETERS.put(153, 0L);
        AXIS_PARAMETERS.put(154, 0L);
        AXIS_PARAMETERS.put(160, 0L);
        AXIS_PARAMETERS.put(161, 0L);
        AXIS_PARAMETERS.put(162, 0L);
        AXIS_PARAMETERS.put(163, 0L);
        AXIS_PARAMETERS.put(164, 0L);
        AXIS_PARAMETERS.put(165, 0L);
        AXIS_PARAMETERS.put(166, 0L);
        AXIS_PARAMETERS.put(167, 0L);
        AXIS_PARAMETERS.put(168, 0L);
        AXIS_PARAMETERS.put(169, 0L);
        AXIS_PARAMETERS.put(170, 0L);
        AXIS_PARAMETERS.put(171, 0L);
        AXIS_PARAMETERS.put(172, 0L);
        AXIS_PARAMETERS.put(173, 0L);
        AXIS_PARAMETERS.put(174, 0L);
        AXIS_PARAMETERS.put(175, 0L);
        AXIS_PARAMETERS.put(176, 0L);
        AXIS_PARAMETERS.put(177, 0L);
        AXIS_PARAMETERS.put(178, 0L);
        AXIS_PARAMETERS.put(179, 0L);
        AXIS_PARAMETERS.put(180, 0L);
        AXIS_PARAMETERS.put(181, 0L);
        AXIS_PARAMETERS.put(182, 0L);
        AXIS_PARAMETERS.put(183, 0L);
        AXIS_PARAMETERS.put(184, 0L);
        AXIS_PARAMETERS.put(193, 0L);
        AXIS_PARAMETERS.put(194, 0L);
        AXIS_PARAMETERS.put(195, 0L);
        AXIS_PARAMETERS.put(196, 0L);
        AXIS_PARAMETERS.put(197, 0L);
        AXIS_PARAMETERS.put(200, 0L);
        AXIS_PARAMETERS.put(204, 0L);
        AXIS_PARAMETERS.put(206, 0L);
        AXIS_PARAMETERS.put(208, 0L);
        AXIS_PARAMETERS.put(213, 0L);
        AXIS_PARAMETERS.put(214, 0L);
    }

    public static Long getAxisParameter(Integer type) {
        return AXIS_PARAMETERS.getOrDefault(type, 0L);
    }

    public static void setAxisParameter(final int type, final long value) {
        AXIS_PARAMETERS.put(type, value);
    }

    private AxisParameterStore() {
    }
}
