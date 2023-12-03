package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import se.anders_raberg.adventofcode2023.utilities.Pair;

public class Day2 {
    private static final Logger LOGGER = Logger.getLogger(Day2.class.getName());
    private static final Pattern PATTERN_LINE = Pattern.compile("Game (\\d+): (.*)");
    private static final Pattern PATTERN_RED = Pattern.compile("(\\d+) red");
    private static final Pattern PATTERN_GREEN = Pattern.compile("(\\d+) green");
    private static final Pattern PATTERN_BLUE = Pattern.compile("(\\d+) blue");

    private record DrawSet(int red, int green, int blue) {
        public boolean drawPossible(int r, int g, int b) {
            return red <= r && green <= g && blue <= b;
        }
    }

    private record Game(Set<DrawSet> drawSet) {
        public boolean gamePossible(int r, int g, int b) {
            return drawSet.stream().allMatch(d -> d.drawPossible(r, g, b));
        }

        public int power() {
            return drawSet.stream().mapToInt(d -> d.red).max().orElseThrow()
                    * drawSet.stream().mapToInt(d -> d.green).max().orElseThrow()
                    * drawSet.stream().mapToInt(d -> d.blue).max().orElseThrow();
        }
    }

    private Day2() {
    }

    public static void run() throws IOException {
        Map<Integer, Game> games = Files.readAllLines(Paths.get("inputs/input2.txt")).stream() //
                .map(Day2::splitLine) //
                .collect(Collectors.toMap(Pair::first, p -> parseGame(p.second())));

        LOGGER.info(() -> "Part 1: " + games.entrySet().stream() //
                .filter(g -> g.getValue().gamePossible(12, 13, 14)) //
                .mapToInt(Entry::getKey) //
                .sum());

        LOGGER.info(() -> "Part 2: " + games.values().stream().mapToLong(Game::power).sum());
    }

    private static Pair<Integer, String> splitLine(String line) {
        Matcher m = PATTERN_LINE.matcher(line);
        if (m.find()) {
            return new Pair<>(Integer.parseInt(m.group(1)), m.group(2));
        } else {
            throw new IllegalArgumentException(line);
        }
    }

    private static Game parseGame(String str) {
        return new Game(Arrays.stream(str.split("; ")).map(s -> new DrawSet(parseDrawSet(PATTERN_RED, s),
                parseDrawSet(PATTERN_GREEN, s), parseDrawSet(PATTERN_BLUE, s))).collect(Collectors.toSet()));
    }

    private static int parseDrawSet(Pattern pattern, String string) {
        Matcher m = pattern.matcher(string);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }
}