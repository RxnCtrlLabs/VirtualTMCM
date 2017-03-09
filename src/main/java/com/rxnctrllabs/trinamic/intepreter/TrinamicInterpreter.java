package com.rxnctrllabs.trinamic.intepreter;

import com.rxnctrllabs.trinamic.command.TrinamicCommand;

import java.util.List;

public class TrinamicInterpreter {

    public static List<TrinamicCommand> convertTrinamicScript(final String script) {
        final TrinamicScript trinamicScript = new TrinamicScript();

        final String[] lines = script.split(RegexPattern.EOL);
        for (final String line : lines) {
            trinamicScript.addLine(line);
        }

        return TrinamicCommandStringConverter.convertTrinamicScript(trinamicScript);
    }
}
