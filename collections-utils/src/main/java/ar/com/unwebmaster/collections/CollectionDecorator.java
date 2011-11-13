package ar.com.unwebmaster.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import ar.com.unwebmaster.collections.closures.Block;
import ar.com.unwebmaster.collections.closures.Function;
import ar.com.unwebmaster.collections.closures.Predicate;

public class CollectionDecorator<E> implements Collection<E> {
	public E get(Integer index) {
		Iterator<E> iterator = this.iterator();
		while (index-- > 0) {
			iterator.next();
		}
		return iterator.next();
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public boolean contains(Object object) {
		return elements.contains(object);
	}

	@Override
	public Iterator<E> iterator() {
		return elements.iterator();
	}

	@Override
	public Object[] toArray() {
		return elements.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return elements.toArray(array);
	}

	@Override
	public boolean add(E element) {
		return elements.add(element);
	}

	@Override
	public boolean remove(Object object) {
		return elements.remove(object);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return elements.containsAll(collection);
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		return elements.addAll(collection);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return elements.removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return elements.retainAll(collection);
	}

	@Override
	public void clear() {
		elements.clear();
	}

	public void allDo(Block<E> block) {
		for (E element : elements) {
			block.value(element);
		}
	}

	public Iterable<E> select(final Predicate<E> selector) {
		final Collection<E> selection = this.copy();
		// TODO: add a copy()-like which returns clear()'d collection
		selection.clear();
		this.allDo(new Block<E>() {

			@Override
			public void value(E element) {
				if (selector.satisfy(element)) {
					selection.add(element);
				}
			}
		});
		return selection;
	}

	public Iterable<E> reject(Predicate<E> selector) {
		return this.select(Predicate.not(selector));
	}

	public <R> Iterable<R> collect(Function<E, R> function) {
		Collection<R> transformation = this.<R>generic();
		for (E element : elements) {
			transformation.add(function.value(element));
		}
		return transformation;
	}

	@SuppressWarnings("unchecked")
	public <T> CollectionDecorator<T> generic() {
		return CollectionDecorator.<T>instance((Class<? extends Collection<T>>)elements.getClass());
	}

	@SuppressWarnings("unchecked")
	public CollectionDecorator<E> copy() {
		CollectionDecorator<E> copy = CollectionDecorator.instance((Class<? extends Collection<E>>) elements.getClass());
		copy.addAll(elements);
		return copy;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected CollectionDecorator<E> morphInto(Class<? extends Collection> collectionType) {
		// FIXME: only MorphingCollections should morphInto :)
		Collection<E> collection;
		try {
			collection = collectionType.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Couldn't morph into " + collectionType.getName(), e);
		}
		collection.addAll(elements);
		elements = collection;
		return this;
	}

	@SuppressWarnings("rawtypes")
	public static <E> CollectionDecorator<E> instance(Class<? extends Collection> collectionType) {
		return new CollectionDecorator<E>().morphInto(collectionType);
	}

	protected CollectionDecorator() {
	}

	protected Collection<E> elements = new ArrayList<E>();
}