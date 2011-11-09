package ar.com.unwebmaster.collections.closures;

public abstract class Predicate<T> {
	public abstract Boolean satisfy(T element);

	public static <T> Predicate<T> not(final Predicate<T> predicate) {
		return new Predicate<T>() {

			@Override
			public Boolean satisfy(T element) {
				return !predicate.satisfy(element);
			}
		};
	}
}