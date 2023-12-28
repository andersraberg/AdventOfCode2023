package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.collect.Sets;

public class Day11Part1 {
	private static final Logger LOGGER = Logger.getLogger(Day11Part1.class.getName());

	private record Galaxy(int y, int x) {
	}

	private Day11Part1() {
	}

	public static void run() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("inputs/input11.txt"));
		List<String> expanded = turn(expand(turn(expand(lines))));
		Set<Galaxy> galaxies = new HashSet<>();

		for (int y = 0; y < expanded.size(); y++) {
			String[] row = expanded.get(y).trim().split("");
			for (int x = 0; x < row.length; x++) {
				if (row[x].equals("#")) {
					galaxies.add(new Galaxy(y, x));
				}
			}
		}

		LOGGER.info(() -> "Part 1: " + Sets.combinations(galaxies, 2).stream().mapToInt(Day11Part1::distance).sum());
	}

	private static int distance(Set<Galaxy> galaxies) {
		List<Galaxy> tmp = new ArrayList<>(galaxies);
		return Math.abs(tmp.get(0).x - tmp.get(1).x) + Math.abs(tmp.get(0).y - tmp.get(1).y);
	}

	private static List<String> expand(List<String> input) {
		List<String> result = new ArrayList<>();
		for (String line : input) {
			result.add(line);
			if (!line.contains("#")) {
				result.add(line);
			}
		}
		return result;
	}

	private static List<String> turn(List<String> input) {
		List<String> result = new ArrayList<>();
		int minLength = input.stream().mapToInt(String::length).min().orElse(0);
		for (int i = 0; i < minLength; i++) {
			StringBuilder sb = new StringBuilder();

			for (String str : input) {
				sb.append(str.charAt(i));
			}

			result.add(sb.toString());
		}
		return result;
	}
}
