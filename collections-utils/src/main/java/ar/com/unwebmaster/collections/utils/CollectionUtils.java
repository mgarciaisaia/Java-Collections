package ar.com.unwebmaster.collections.utils;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.unwebmaster.collections.closures.Block;
import ar.com.unwebmaster.collections.closures.Function;
import ar.com.unwebmaster.collections.closures.Predicate;

public class CollectionUtils {

	public static <T> void allDo(Iterable<T> elements, Block<T> block) {
		for (T element : elements) {
			block.value(element);
		}
	}

	public static <T> Iterable<T> select(Collection<T> source, final Predicate<T> selector) {
		final Collection<T> selection = sameCollection(source);
		allDo(source, new Block<T>() {

			@Override
			public void value(T element) {
				if(selector.satisfy(element)) {
					selection.add(element);
				}
			}
		});
		return selection;
	}

	public static <T> Iterable<T> reject(Collection<T> source, Predicate<T> selector) {
		return select(source, Predicate.not(selector));
	}

	public static <T, R> Iterable<R> collect(Collection<T> source, Function<T, R> function) {
		Collection<R> transformation = collectionWithOtherGeneric(source);
		for (T element : source) {
			transformation.add(function.value(element));
		}
		return transformation;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> Collection<T> sameCollection(Collection<T> source) {
		try {
			return source.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<T>();
	}
	
	@SuppressWarnings("unchecked")
	private static <T, R> Collection<R> collectionWithOtherGeneric(Collection<T> source) {
		try {
			return source.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<R>();
	}
}