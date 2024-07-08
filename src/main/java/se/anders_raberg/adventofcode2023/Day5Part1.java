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

public class Day5Part1 {
	private static final Logger LOGGER = Logger.getLogger(Day5Part1.class.getName());
	//
	private static final Pattern PATTERN = Pattern.compile("seeds:(.+)" + //
			"seed-to-soil map:(.+)" + //
			"soil-to-fertilizer map:(.+)" + //
			"fertilizer-to-water map:(.+)" + //
			"water-to-light map:(.+)" + //
			"light-to-temperature map:(.+)" + //
			"temperature-to-humidity map:(.+)" + //
			"humidity-to-location map:(.+)" + //
			"(.+)", Pattern.DOTALL);

	private record MapLine(long destStart, long sourceStart, long range) {
	}

	private static class Mapping {
		private static final Pattern PATTERN_MAP_LINE = Pattern.compile("(\\d+) (\\d+) (\\d+)", Pattern.DOTALL);
		private final List<MapLine> _mapLines = new ArrayList<>();

		public Mapping(String str) {
			Matcher m = PATTERN_MAP_LINE.matcher(str);
			while (m.find()) {
				_mapLines.add(new MapLine(Long.parseLong(m.group(1)), Long.parseLong(m.group(2)),
						Long.parseLong(m.group(3))));
			}
		}

		public long get(long source) {
			for (MapLine a : _mapLines) {
				if (source >= a.sourceStart && source < a.sourceStart + a.range) {
					return a.destStart + source - a.sourceStart;
				}
			}
			return source;
		}
	}

	private Day5Part1() {
	}

	public static void run() throws IOException {
		String input = new String(Files.readAllBytes(Paths.get("inputs/input5.txt"))).trim();

		Matcher m = PATTERN.matcher(input);
		if (m.matches()) {
			Mapping seedToSoil = new Mapping(m.group(2));
			Mapping soilToFertilizer = new Mapping(m.group(3));
			Mapping fertilizerToWater = new Mapping(m.group(4));
			Mapping waterToLight = new Mapping(m.group(5));
			Mapping lightToTemperature = new Mapping(m.group(6));
			Mapping temperatureToHumidity = new Mapping(m.group(7));
			Mapping humidityToLocation = new Mapping(m.group(8));

			long lowestLocation = Arrays.stream(m.group(1).trim().split(" ")).map(Long::parseLong) //
					.map(seedToSoil::get) //
					.map(soilToFertilizer::get) //
					.map(fertilizerToWater::get) //
					.map(waterToLight::get) //
					.map(lightToTemperature::get) //
					.map(temperatureToHumidity::get) //
					.map(humidityToLocation::get).min(Long::compareTo).orElseThrow();

			LOGGER.info(() -> "Part 1: " + lowestLocation);
		} else {
			throw new IllegalArgumentException(input);
		}

	}

}
