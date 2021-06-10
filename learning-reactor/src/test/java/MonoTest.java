import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

	@Test
	@DisplayName("Nothing happens until you subscribe")
	void firstMono() {
		Mono.just("A")
				.log();
	}

	@Test
	void monoWithConsumer() {
		Mono.just("A")
				.log()
				.subscribe(s -> System.out.println("value = " + s));
	}

	@Test
	@DisplayName("Do something with the received value")
	void monoWithDoOn() {
		Mono
			.just("A")
			.log()
			.doOnSubscribe(subs -> System.out.println("Subscribed = " + subs))
			.doOnRequest(request -> System.out.println("Request = " + request))
			.doOnSuccess(complete -> System.out.println("Complete = " + complete))
			.subscribe(System.out::println);
	}

	@Test
	@DisplayName("What if publisher don't emit any value")
	void emptyMono() {
		Mono
			.empty()
			.log()
			.subscribe(System.out::println);
	}

	@Test
	@DisplayName("Pass a consumer to the subscribe method")
	void emptyCompleteconsumerMono() {
		Mono
			.empty()
			.log()
			.subscribe(
				System.out::println,
				null,
				() -> System.out.println("Done")
			);
	}

	@Test
	@DisplayName("Simulate an unchecked exception")
	void errorRuntimeExceptionMono() {
		Mono
			.error(new RuntimeException())
			.log()
			.subscribe();
	}

	@Test
	@DisplayName("Simulate a checked exception")
	void errorExceptionMono() {
		Mono
			.error(new Exception())
			.log()
			.subscribe();
	}

	@Test
	@DisplayName("Process the error, it raises blocking Exception anyway")
	void errorDoOnErrorMono() {
		Mono
			.error(new Exception())
			.doOnError(e -> System.out.println("Error = " + e))
			.log()
			.subscribe();
	}

	@Test
	@DisplayName("Simulate a checked exception and catch the exception to resume")
	void errorOnErrorResumeMono() {
		Mono
			.error(new Exception())
			.onErrorResume(e -> {
				System.out.println("Caught error = " + e);
				return Mono.just("B");
			})
			.log()
			.subscribe();
	}

	@Test
	@DisplayName("Another approach to same result as previous: when exception happens return something")
	void errorOnErrorReturnMono() {
		Mono
			.error(new Exception())
			.onErrorReturn("B")
			.log()
			.subscribe();
	}
}
