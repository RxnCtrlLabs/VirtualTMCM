package com.rxnctrllabs.trinamic.intepreter;

class RegexPattern {

    static final String COMMENT = "//[^\n]*";

    static final String EOL = "\n";

    static final String LABEL = "[_A-Za-z][_0-9A-Za-z]*:[^\n]*";

    static String withDelimiter(final String delimiter) {
        String withDelimiterPattern = "(?<=%1$s)";
        return String.format(withDelimiterPattern, delimiter);
    }
}
