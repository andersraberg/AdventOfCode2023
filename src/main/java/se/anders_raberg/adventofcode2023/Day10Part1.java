package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import se.anders_raberg.adventofcode2023.utilities.Pair;

public class Day10Part1 {
	private static final Logger LOGGER = Logger.getLogger(Day10Part1.class.getName());

	private Day10Part1() {
	}

	private enum Direction {
		NORTH, SOUTH, WEST, EAST;
	}

	private static final Map<Pair<Direction, String>, Direction> NEXT_DIRECTION = new HashMap<>();

	static {
		NEXT_DIRECTION.put(new Pair<>(Direction.NORTH, "|"), Direction.NORTH);
		NEXT_DIRECTION.put(new Pair<>(Direction.NORTH, "F"), Direction.EAST);
		NEXT_DIRECTION.put(new Pair<>(Direction.NORTH, "7"), Direction.WEST);
		NEXT_DIRECTION.put(new Pair<>(Direction.SOUTH, "|"), Direction.SOUTH);
		NEXT_DIRECTION.put(new Pair<>(Direction.SOUTH, "J"), Direction.WEST);
		NEXT_DIRECTION.put(new Pair<>(Direction.SOUTH, "L"), Direction.EAST);
		NEXT_DIRECTION.put(new Pair<>(Direction.WEST, "-"), Direction.WEST);
		NEXT_DIRECTION.put(new Pair<>(Direction.WEST, "L"), Direction.NORTH);
		NEXT_DIRECTION.put(new Pair<>(Direction.WEST, "F"), Direction.SOUTH);
		NEXT_DIRECTION.put(new Pair<>(Direction.EAST, "-"), Direction.EAST);
		NEXT_DIRECTION.put(new Pair<>(Direction.EAST, "J"), Direction.NORTH);
		NEXT_DIRECTION.put(new Pair<>(Direction.EAST, "7"), Direction.SOUTH);
	}

	private record Coord(int y, int x) {
		public Coord step(Direction dir) {
			return switch (dir) {
			case NORTH -> new Coord(y - 1, x);
			case SOUTH -> new Coord(y + 1, x);
			case EAST -> new Coord(y, x + 1);
			case WEST -> new Coord(y, x - 1);
			};
		}
	}

	public static void run() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("inputs/input10.txt"));
		String[][] grid = new String[lines.size()][lines.get(0).length()];

		for (int y = 0; y < lines.size(); y++) {
			String[] row = lines.get(y).trim().split("");
			for (int x = 0; x < row.length; x++) {
				grid[y][x] = row[x];
			}
		}

		Coord startPos = findStart(grid);
		Coord pos = startPos;
		Direction dir = Direction.SOUTH; // From visual inspection of input.

		int stepCounter = 0;
		do {
			pos = pos.step(dir);
			dir = NEXT_DIRECTION.get(new Pair<>(dir, grid[pos.y][pos.x]));
			stepCounter++;
		} while (!pos.equals(findStart(grid)));

		LOGGER.info("Part 1:" + stepCounter / 2);
	}

	private static Coord findStart(String[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if ("S".equals(grid[y][x])) {
					return new Coord(y, x);
				}
			}
		}
		throw new IllegalArgumentException();
	}

}
