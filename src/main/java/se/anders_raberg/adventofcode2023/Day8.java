package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.util.ArithmeticUtils;

public class Day8 {
	private static final Logger LOGGER = Logger.getLogger(Day8.class.getName());
	private static final Pattern PATTERN = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");
	private static final Map<String, Node> MAPPING = new HashMap<>();
	private static final List<String> TURNS = new ArrayList<>();

	private record Node(String left, String right) {
	}

	private Day8() {
	}

	public static void run() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("inputs/input8.txt"));
		TURNS.addAll(Arrays.asList(lines.getFirst().split("")));

		for (String string : lines) {
			Matcher m = PATTERN.matcher(string);
			if (m.matches()) {
				MAPPING.put(m.group(1), new Node(m.group(2), m.group(3)));
			}
		}

		LOGGER.info(() -> "Part 1: " + calc("AAA"));

		List<Integer> pathLengths = MAPPING.keySet().stream().filter(k -> k.endsWith("A")).map(Day8::calc).toList();

		long lcm = pathLengths.getFirst();
		for (int i = 1; i < pathLengths.size(); i++) {
			lcm = ArithmeticUtils.lcm(lcm, pathLengths.get(i));
		}

		LOGGER.info("Part 2 : " + lcm);
	}

	private static int calc(String start) {
		int counter = 0;
		String current = start;
		while (!current.endsWith("Z")) {
			int pos = counter % (TURNS.size());
			if ("R".equals(TURNS.get(pos))) {
				current = MAPPING.get(current).right;
			} else {
				current = MAPPING.get(current).left;
			}
			counter = (counter + 1);
		}
		return counter;
	}

}