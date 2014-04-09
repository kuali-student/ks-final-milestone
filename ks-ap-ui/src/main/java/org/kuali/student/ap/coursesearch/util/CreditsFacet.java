package org.kuali.student.ap.coursesearch.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.CourseSearchItem.CreditType;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.dataobject.FacetItem;

/**
 * Logic for building list of FacetItems and coding CourseSearchItems.
 */
public class CreditsFacet extends AbstractFacet {

	private static final int DISPLAY_MAX = 25;

	private HashSet<Integer> creditFacetSet = new HashSet<Integer>();

	public CreditsFacet() {
		super();
	}

	/**
	 * Overriding because the credits facet list needs to be in numeric order
	 * rather than string order.
	 * 
	 * @return A list of FacetItems.
	 */
	@Override
	public List<FacetItem> getFacetItems() {
		Integer[] list = creditFacetSet.toArray(new Integer[0]);
		Arrays.sort(list);

		for (Integer credit : list) {
			String display = credit.toString();
			facetItems.add(new FacetItem(display, display));
		}

		return facetItems;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void process(CourseSearchItem course) {
		int min = (int) course.getCreditMin();
		int max = (int) course.getCreditMax();

		List<Integer> list = new ArrayList<Integer>();
		switch (course.getCreditType()) {
		case Range:
			for (int x = min; x <= max; x++) {
				if (x > DISPLAY_MAX)
					continue;
				list.add(x);
			}
			break;
		case Fixed:
			list.add(min);
			break;
		case Multiple:
			list.add(min);
			list.add(max);
			break;
		case Unknown:
		default:
			list.add(min);
			break;

		}

		creditFacetSet.addAll(list);

		Set<String> facetKeys = new java.util.LinkedHashSet<String>();
		for (Integer credit : list)
			facetKeys.add(credit.toString());
		if (CreditType.Range.equals(course.getCreditType())
				&& max > DISPLAY_MAX)
			facetKeys.add("> " + DISPLAY_MAX);
		((CourseSearchItemImpl) course).setCreditsFacetKeys(facetKeys);
	}
}
