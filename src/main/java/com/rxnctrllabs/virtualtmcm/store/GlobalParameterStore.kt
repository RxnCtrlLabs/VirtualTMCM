package com.rxnctrllabs.virtualtmcm.store

import java.util.concurrent.ConcurrentHashMap

object GlobalParameterStore {

    private val GLOBAL_PARAMETERS = ConcurrentHashMap<Int, Long>()

    init {
        GLOBAL_PARAMETERS[7] = 65L
        GLOBAL_PARAMETERS[8] = 91L
    }

    fun getGlobalParameter(type: Int): Long {
        var value = GLOBAL_PARAMETERS[type]
        if (value == null) {
            GLOBAL_PARAMETERS[type] = 0
        }

        value = value ?: 0L

        if (type == 7 || type == 8) {
            GLOBAL_PARAMETERS[type] = value + 1
        }
        if (type == 8) {
            GLOBAL_PARAMETERS[type] = value + 2
        }

        println("Getting global param: $type, with value: $value")
        return value
    }

    fun setGlobalParameter(type: Int, value: Long) {
        println("Saving global param: $type, with value: $value")
        GLOBAL_PARAMETERS[type] = value
    }
}
