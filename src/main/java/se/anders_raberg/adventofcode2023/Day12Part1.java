package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12Part1 {
	private static final Logger LOGGER = Logger.getLogger(Day12Part1.class.getName());
	private static final Pattern PATTERN = Pattern.compile("(#+)");

	private Day12Part1() {
	}

	public static void run() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("inputs/input12.txt"));

		long sum = lines.stream() //
				.map(Day12Part1::permutate) //
				.mapToLong(c -> c.stream() //
						.filter(Day12Part1::okArrangment) //
						.count()) //
				.sum();

		LOGGER.info(() -> "Part 1: " + sum);
	}

	private static List<String> permutate(String input) {
		List<String> result = new ArrayList<>();
		if (input.contains("?")) {
			result.addAll(permutate(input.replaceFirst("\\?", "#")));
			result.addAll(permutate(input.replaceFirst("\\?", ".")));
		} else {
			result.add(input);
		}
		return result;
	}

	private static boolean okArrangment(String input) {
		String[] split = input.split(" ");
		Matcher m = PATTERN.matcher(split[0]);
		for (Integer groupLen : getGroupLengths(split[1])) {
			boolean found = m.find();
			if (!found || m.group(1).length() != groupLen) {
				return false;
			}
		}
		return !m.find();
	}

	private static List<Integer> getGroupLengths(String input) {
		return Arrays.stream(input.split(",")).map(Integer::parseInt).toList();
	}

}
