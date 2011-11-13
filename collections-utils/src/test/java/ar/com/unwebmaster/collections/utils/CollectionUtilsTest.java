package ar.com.unwebmaster.collections.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import ar.com.unwebmaster.collections.CollectionDecorator;
import ar.com.unwebmaster.collections.closures.Block;
import ar.com.unwebmaster.collections.closures.Predicate;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

//FIXME: test!! test!! test!!
public class CollectionUtilsTest extends TestCase {
	public CollectionUtilsTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(CollectionUtilsTest.class);
	}

	public void testAllDoExecuteMethod() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		final List<Integer> results = new ArrayList<Integer>(5);
		CollectionUtils.allDo(numbers, new Block<Integer>() {

			@Override
			public void value(Integer element) {
				results.add(element);
			}
		});
		for (Integer index = 0; index < 5; index++) {
			assertTrue("Falta el numero " + index + 1, results.get(index).equals(index + 1));
		}
	}

	public void testSelectEvens() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		Iterable<Integer> results = CollectionUtils.select(numbers, new Predicate<Integer>() {

			@Override
			public Boolean satisfy(Integer element) {
				return element % 2 == 0;
			}
		});
		Integer index = 0;
		for (Integer result : results) {
			index++;
			assertTrue("Falta el numero " + index * 2, result.equals(index * 2));
		}
	}

	public void testCollectionDecorator() {
		Collection<Integer> collection = CollectionDecorator.instance(HashSet.class);
		collection.addAll(Arrays.asList(1, 2, 3, 4, 5));
		Integer index = 0;
		for (Integer element : collection) {
			index++;
			assertTrue("Falta el numero " + index, element.equals(index));
		}
	}
}