import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

public class FluxTest {

	@Test
	void firstFlux() {
		Flux
			.just("A", "B", "C")
			.log()
			.subscribe();
	}

	@Test
	// Generate data from a Collection (an Iterable)
	void fluxFromIterable() {
		Flux
			.fromIterable(Arrays.asList("A", "B", "C"))
			.log()
			.subscribe();
	}

	@Test
	// Generate data from a range of integers
	void fluxFromRange() {
		Flux
			.range(10, 5)  // 10, 11, 12, 13, 14
			.log()
			.subscribe();
	}

	@Test
	// In another parallel thread a sequence (starting) from 0 is generated each second
	// that's why I need to put to sleep my thread
	// the parallel thread stops because when finishing this test, the program ends (but what in a production server...?)
	void fluxFromIntervalPossibleInfiniteValues() throws InterruptedException {
		Flux
			.interval(Duration.ofSeconds(1)) // 0, 1, 2, 3, 4, 5,...
			.log()
			.subscribe();
		Thread.sleep(5000);
	}

	@Test
	// In another parallel thread a sequence (starting) from 0 is generated each second
	// that's why I need to put to sleep my thread
	// the parallel thread stops because when finishing this test, the program ends (but what in a production server...?)
	// take(2) makes it stop after the first two values are generated
	void fluxFromInterval() throws InterruptedException {
		Flux
			.interval(Duration.ofSeconds(1)) // 0, 1
			.log()
			.take(2) // makes it stop after two values are generated
			.subscribe();
		Thread.sleep(5000);
	}

	@Test
	// Generate a range (from 1 to 5)
	// And I set in the INITIAL REQUEST (this doesn't happen next time it is run) that I take only the 3 frst values
	void fluxRequest() {
		Flux
			.range(1, 5) // 1, 2, 3
			.log()
			.subscribe(null,
				null,
				null,
				s -> s.request(3) // this is the initial request, where I request a number of elements
			);
	}

	@Test
	// Generate a range (from 1 to 5)
	// Set that ANY TIME it is requested, which will be requested repeatedly until Flux onComplete(), only 3 first values will be returned
	void fluxLimitRate() {
		Flux
			.range(1, 11) // 1,2,3 - 4,5,6 - 7,8,9 - 10,11
			.log()
			.limitRate(3) // limit the number of elements to 3
			.subscribe();
	}
}
