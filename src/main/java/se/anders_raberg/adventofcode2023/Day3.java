package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import se.anders_raberg.adventofcode2023.utilities.Pair;

public class Day3 {
	private static final Logger LOGGER = Logger.getLogger(Day3.class.getName());
	private static final Pattern PART_NUMBER_PATTERN = Pattern.compile("(\\d+)");
	private static final Pattern SYMBOL_PATTERN = Pattern.compile("([^0-9\\.])");

	private static final List<Pair<Integer, Set<Coord>>> PART_NUMBERS_WITH_BORDERS = new ArrayList<>();
	private static final Set<Coord> SYMBOLS = new HashSet<>();
	private static final Set<Coord> GEAR_SYMBOLS = new HashSet<>();

	private record Coord(int x, int y) {

		public Set<Coord> getBorders() {
			return Set.of( //
					new Coord(x + 1, y), //
					new Coord(x - 1, y), //
					new Coord(x, y + 1), //
					new Coord(x, y - 1), //
					new Coord(x + 1, y + 1), //
					new Coord(x - 1, y - 1), //
					new Coord(x + 1, y - 1), //
					new Coord(x - 1, y + 1));
		}
	}

	private Day3() {
	}

	public static void run() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("inputs/input3.txt"));
		for (int i = 0; i < lines.size(); i++) {
			Matcher m = PART_NUMBER_PATTERN.matcher(lines.get(i));
			while (m.find()) {
				PART_NUMBERS_WITH_BORDERS.add(new Pair<>(Integer.parseInt(m.group(1)),
						genCoordsForPartBorder(genCoordsForPart(i, m.start(), m.end()))));
			}
			m = SYMBOL_PATTERN.matcher(lines.get(i));
			while (m.find()) {
				SYMBOLS.add(new Coord(m.start(), i));
				if (m.group(1).equals("*")) {
					GEAR_SYMBOLS.add(new Coord(m.start(), i));
				}
			}
		}

		LOGGER.info(() -> "Part 1: " + PART_NUMBERS_WITH_BORDERS.stream()
				.filter(p -> p.second().stream().anyMatch(SYMBOLS::contains)).mapToInt(Pair::first).sum());

		List<List<Integer>> list = GEAR_SYMBOLS.stream().map(gear -> PART_NUMBERS_WITH_BORDERS.stream()
				.filter(part -> part.second().contains(gear)).map(Pair::first).toList()).filter(li -> li.size() == 2)
				.toList();

		LOGGER.info(() -> "Part 2: " + list.stream().mapToInt(l1 -> l1.stream().reduce(1, Math::multiplyExact)).sum());
	}

	private static Set<Coord> genCoordsForPart(int row, int start, int end) {
		return IntStream.range(start, end).boxed().map(c -> new Coord(c, row)).collect(Collectors.toSet());
	}

	private static Set<Coord> genCoordsForPartBorder(Set<Coord> partCoords) {
		return partCoords.stream().map(Coord::getBorders).flatMap(Collection::stream)
				.filter(q -> !partCoords.contains(q)).collect(Collectors.toSet());
	}

}
