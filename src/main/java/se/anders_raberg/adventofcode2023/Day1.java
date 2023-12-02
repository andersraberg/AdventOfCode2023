package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {
    private static final Logger LOGGER = Logger.getLogger(Day1.class.getName());

    private static final Map<String, String> LETTERS_TO_VALUE = Map.of(//
            "one", "1", //
            "two", "2", //
            "three", "3", //
            "four", "4", //
            "five", "5", //
            "six", "6", //
            "seven", "7", //
            "eight", "8", //
            "nine", "9");

    private static final Pattern PATTERN = Pattern
            .compile("(" + LETTERS_TO_VALUE.keySet().stream().reduce((a, b) -> a + "|" + b).orElseThrow() + ")");

    private Day1() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input1.txt"));
        LOGGER.info("Part 1: " + lines.stream().map(Day1::removeNonDigits).mapToInt(Day1::firstLastToInt).sum());
        LOGGER.info("Part 2: " + lines.stream().map(Day1::parseSpelledOut).map(Day1::removeNonDigits)
                .mapToInt(Day1::firstLastToInt).sum());
    }

    private static String removeNonDigits(String str) {
        return str.replaceAll("[^0-9]", "");
    }

    private static int firstLastToInt(String str) {
        return Integer.parseInt(str.substring(0, 1) + str.substring(str.length() - 1));
    }

    private static String parseSpelledOut(String str) {
        Matcher m = PATTERN.matcher(str);
        if (m.find()) {
            return str.substring(0, m.start(1)) + LETTERS_TO_VALUE.get(m.group(1))
                    + parseSpelledOut(str.substring(m.start(1) + 1));
        } else {
            return str;
        }
    }

}