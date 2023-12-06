package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import se.anders_raberg.adventofcode2023.utilities.Triple;

public class Day4 {
	private static final Logger LOGGER = Logger.getLogger(Day4.class.getName());
	private static final Pattern PATTERN = Pattern.compile("Card +(\\d+)\\:(.*) \\| (.*)");

	private Day4() {
	}

	public static void run() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("inputs/input4.txt"));
		int startingLineCount = lines.size();

		int points = lines.stream() //
				.map(Day4::parseLine) //
				.map(Day4::winningNumberCount) //
				.mapToInt(Day4::calcPoints) //
				.sum();

		LOGGER.info(() -> "Part 1:" + points);

		List<Triple<Integer, Set<Integer>, Set<Integer>>> cardList = new ArrayList<>(
				lines.stream().map(Day4::parseLine).toList());

		Map<Integer, Triple<Integer, Set<Integer>, Set<Integer>>> map = cardList.stream()
				.collect(Collectors.toMap(Triple::first, Function.identity()));

		for (int i = 0; i < cardList.size(); i++) {
			int count = winningNumberCount(cardList.get(i));
			int next = cardList.get(i).first() + 1;
			for (int j = next; j < Math.min(next + count, startingLineCount + 1); j++) {
				cardList.add(map.get(j));
			}
		}

		LOGGER.info(() -> "Part 2:" + cardList.size());
	}

	private static int calcPoints(int count) {
		return count == 0 ? 0 : (int) Math.pow(2, count - 1.0);
	}

	private static int winningNumberCount(Triple<Integer, Set<Integer>, Set<Integer>> card) {
		return Sets.intersection(card.second(), card.third()).size();
	}

	private static Triple<Integer, Set<Integer>, Set<Integer>> parseLine(String line) {
		Matcher m = PATTERN.matcher(line);
		if (m.matches()) {
			return new Triple<>(Integer.parseInt(m.group(1)), parseSet(m.group(2)), parseSet(m.group(3)));
		} else {
			throw new IllegalArgumentException(line);
		}

	}

	private static Set<Integer> parseSet(String str) {
		return Arrays.stream(str.trim().split(" +")).map(Integer::parseInt).collect(Collectors.toSet());
	}

}
