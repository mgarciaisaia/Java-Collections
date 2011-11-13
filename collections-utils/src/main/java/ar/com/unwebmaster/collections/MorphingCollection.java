package ar.com.unwebmaster.collections;

import java.util.ArrayList;
import java.util.Collection;

public class MorphingCollection<E> extends CollectionDecorator<E> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MorphingCollection<E> morphInto(Class<? extends Collection> collectionType) {
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

	public MorphingCollection<E> instance(Class<? extends Collection<E>> collectionType) {
		return new MorphingCollection<E>().morphInto(collectionType);
	}

	protected MorphingCollection() {
	}

	private Collection<E> elements = new ArrayList<E>();
}