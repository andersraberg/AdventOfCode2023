package se.anders_raberg.adventofcode2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7Part1 {
	private static final Logger LOGGER = Logger.getLogger(Day7Part1.class.getName());
	private static final Pattern PATTERN = Pattern.compile("(\\w+) (\\d+)");

	private enum Type {
		FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD;
	}

	private enum Card {
		C2, C3, C4, C5, C6, C7, C8, C9, CT, CJ, CQ, CK, CA;
	}

	private record Hand(List<Card> cards, Integer bid) implements Comparable<Hand> {
		public static Hand parseHand(String str) {
			Matcher m = PATTERN.matcher(str);
			if (m.matches()) {
				return new Hand(parse(m.group(1)), Integer.parseInt(m.group(2)));
			} else
				throw new IllegalArgumentException(str);
		}

		private static List<Card> parse(String str) {
			return Arrays.stream(str.split("")).map(c -> Card.valueOf("C" + c)).toList();
		}

		@Override
		public int compareTo(Hand o) {
			int compareTo = getType(this).compareTo(getType(o));
			if (compareTo != 0) {
				return compareTo;
			} else {
				return compare(this.cards, o.cards);
			}
		}

		private int compare(List<Card> cards1, List<Card> cards2) {
			for (int i = 0; i < cards1.size(); i++) {
				int compareTo = cards2.get(i).compareTo(cards1.get(i));
				if (compareTo != 0) {
					return compareTo;
				}
			}
			return 0;
		}
	}

	private Day7Part1() {
	}

	public static void run() throws IOException {
		List<Hand> list = new ArrayList<>(
				Files.readAllLines(Paths.get("inputs/input7.txt")).stream().map(Hand::parseHand).sorted().toList());
		Collections.reverse(list);

		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum = sum + list.get(i).bid() * (i + 1);
		}

		LOGGER.info("Part 1: " + sum);
	}

	private static Type getType(Hand hand) {
		if (countCards(hand).contains(5L)) {
			return Type.FIVE_OF_A_KIND;
		} else if (countCards(hand).contains(4L)) {
			return Type.FOUR_OF_A_KIND;
		} else if (countCards(hand).contains(3L) && countCards(hand).contains(2L)) {
			return Type.FULL_HOUSE;
		} else if (countCards(hand).contains(3L)) {
			return Type.THREE_OF_KIND;
		} else if (countCards(hand).stream().filter(x -> x == 2).count() == 2) {
			return Type.TWO_PAIR;
		} else if (countCards(hand).stream().filter(x -> x == 2).count() == 1) {
			return Type.ONE_PAIR;
		} else if (countCards(hand).stream().max(Long::compare).orElseThrow() == 1) {
			return Type.HIGH_CARD;
		}
		throw new IllegalArgumentException();
	}

	private static Collection<Long> countCards(Hand hand) {
		return hand.cards.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values();
	}
}
