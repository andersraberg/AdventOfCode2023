package se.anders_raberg.adventofcode2023;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.LongStream;

public class Day6 {
	private static final Logger LOGGER = Logger.getLogger(Day6.class.getName());
	private static final List<Race> RACES_PART_1 = List.of( //
			new Race(47, 400), //
			new Race(98, 1213), //
			new Race(66, 1011), //
			new Race(98, 1540));

	private static final List<Race> RACES_PART_2 = List.of(new Race(47986698, 400121310111540L));

	private record Race(long time, long distance) {
	}

	private Day6() {
	}

	public static void run() {
		LOGGER.info(() -> "Part 1: " + calc(RACES_PART_1));
		LOGGER.info(() -> "Part 2: " + calc(RACES_PART_2));
	}

	private static long calc(List<Race> races) {
		return races.stream() //
				.map(race -> LongStream.rangeClosed(0, race.time()) //
                                .map(t -> (race.time() - t) * t) //
                                .filter(r -> r > race.distance()) //
	                        .count()) //
				.reduce(Math::multiplyExact).orElseThrow();
	}

}
