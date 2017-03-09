package com.rxnctrllabs.trinamic.intepreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TrinamicScript {

    private final List<String> commandStrings = new ArrayList<>();
    private final Map<String, Integer> labelToCommandNumberMap = new HashMap<>();

    void addLine(final String scriptLine) {
        final String trimmedScriptLine = scriptLine.replaceFirst(RegexPattern.COMMENT, "").trim();

        if (trimmedScriptLine.matches(RegexPattern.LABEL)) {
            String[] splitLabelLine = trimmedScriptLine.split(":");
            String labelName = splitLabelLine[0];

            this.labelToCommandNumberMap.put(labelName, this.commandStrings.size());

            if (splitLabelLine.length > 1) {
                String commandString = splitLabelLine[1].trim();
                this.commandStrings.add(commandString);
            }
        } else {
            if (!trimmedScriptLine.isEmpty()) {
                this.commandStrings.add(trimmedScriptLine);
            }
        }
    }

    int getCommandCount() {
        return this.commandStrings.size();
    }

    List<String> getCommandStrings() {
        return this.commandStrings;
    }

    Map<String, Integer> getLabelToCommandNumberMap() {
        return this.labelToCommandNumberMap;
    }
}
