package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Day9 {
	private static final Logger LOGGER = Logger.getLogger(Day9.class.getName());

	private Day9() {
	}

	public static void run() throws IOException {
		List<List<Long>> rr = Files.readAllLines(Paths.get("inputs/input9.txt")).stream()
				.map(l -> Arrays.stream(l.split(" ")) //
						.map(Long::parseLong) //
						.toList())
				.toList(); //

		long sum1 = rr.stream().map(Day9::findLastElementsInRows).map(Day9::extrapolateForward).mapToLong(x -> x).sum();
		long sum2 = rr.stream().map(Day9::findFirstElmentsInRows).map(Day9::extrapolateBackward).mapToLong(x -> x)
				.sum();

		LOGGER.info(() -> "Part 1: " + sum1);
		LOGGER.info(() -> "Part 2: " + sum2);
	}

	private static long extrapolateForward(List<Long> list) {
		List<Long> result = new ArrayList<>(Collections.nCopies(list.size(), 0L));
		for (int i = 1; i < list.size(); i++) {
			result.set(i, list.get(i) + result.get(i - 1));
		}
		return lastElement(result);
	}

	private static long extrapolateBackward(List<Long> list) {
		List<Long> result = new ArrayList<>(Collections.nCopies(list.size(), 0L));
		for (int i = 1; i < list.size(); i++) {
			result.set(i, list.get(i) - result.get(i - 1));
		}
		return lastElement(result);
	}

	private static List<Long> findLastElementsInRows(List<Long> list) {
		List<Long> www = list;
		List<Long> result = new ArrayList<>();
		while (true) {
			result.addFirst(lastElement(www));
			www = calcNextRow(www);
			if (www.stream().allMatch(i -> i == 0)) {
				result.addFirst(lastElement(www));
				break;
			}
		}
		return result;
	}

	private static List<Long> findFirstElmentsInRows(List<Long> list) {
		List<Long> www = list;
		List<Long> result = new ArrayList<>();
		while (true) {
			result.addFirst(firstElement(www));
			www = calcNextRow(www);
			if (www.stream().allMatch(i -> i == 0)) {
				result.addFirst(firstElement(www));
				break;
			}
		}
		return result;
	}

	private static List<Long> calcNextRow(List<Long> list) {
		return IntStream.range(0, list.size() - 1).mapToObj(i -> list.get(i + 1) - list.get(i)).toList();
	}

	private static <T> T lastElement(List<T> list) {
		return list.getLast();
	}

	private static <T> T firstElement(List<T> list) {
		return list.getFirst();
	}

}
