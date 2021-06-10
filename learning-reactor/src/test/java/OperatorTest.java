import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class OperatorTest {

	@Test
	@DisplayName("Operator map, apply a synchronous function to each method")
	void map() {
		Flux.range(1, 5)
			.map(i -> i * 10)
			.subscribe(System.out::println);
	}

	@Test
	@DisplayName("Operator flatMap")
	void flatMap() {
		Flux.range(1, 5)
			.flatMap(i -> Flux.range(i * 10, 2))
			.subscribe(System.out::println);
	}

	@Test
	@DisplayName("Operator flatMapMany. Converts a Mono into a Flux.")
	void flatMapMany() {
		Mono.just(3)
			.flatMapMany(i -> Flux.range(1, i))
			.subscribe(System.out::println);
	}

	@Test
	@DisplayName("Operator concat, creates one Flux with items from the 1st and then the 2nd original Fluxes")
	void concat() throws InterruptedException {
		// delayElements operator delay the publishing of each element for a given time
		Flux<Integer> oneToFive = Flux.range(1, 5).delayElements(Duration.ofMillis(200));
		Flux<Integer> sixToTen = Flux.range(6, 5).delayElements(Duration.ofMillis(400));
		Flux.concat(oneToFive, sixToTen).subscribe(System.out::println);
		// equivalent to :::: oneToFive.concatWith(sixToTen).subscribe(System.out::println);

		Thread.sleep(4000);
	}

	@Test
	@DisplayName("Operator merge, creates one Flux with the items from the original Fluxes in any order")
	void merge() throws InterruptedException {
		// delayElements operator delay the publishing of each element for a given time
		Flux<Integer> oneToFive = Flux.range(1, 5).delayElements(Duration.ofMillis(200));
		Flux<Integer> sixToTen = Flux.range(6, 5).delayElements(Duration.ofMillis(400));
		Flux.merge(oneToFive, sixToTen).subscribe(System.out::println);
		// equivalent to :::: oneToFive.mergeWith(sixToTen).subscribe(System.out::println);

		Thread.sleep(4000);
	}

	@Test
	@DisplayName("Operator zip, creates pairs of elements of two Fluxes (as there were items in both)")
	void zip() throws InterruptedException {
		// delayElements operator delay the publishing of each element for a given time
		Flux<Integer> oneToFive = Flux.range(1, 4);
		Flux<Integer> sixToTen = Flux.range(6, 5);
		Flux.zip(oneToFive, sixToTen, (item1, item2) -> item1 + ", " + + item2)
			.subscribe(System.out::println);
		// equivalent to :::: oneToFive.zipWith(sixToTen).subscribe(System.out::println);
	}

}
