package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Day1 {
	private static final Logger LOGGER = Logger.getLogger(Day1.class.getName());

	private Day1() {
	}

	public static void run() throws IOException {
		String data = new String(Files.readAllBytes(Paths.get("inputs/input1.txt")));
		LOGGER.info(() -> "Hello " + data);
	}

}
