package org.kuali.student.myplan.course.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.ap.framework.course.CourseSearchItem.CreditType;
import org.kuali.student.myplan.course.dataobject.CourseSearchItemImpl;
import org.kuali.student.myplan.course.dataobject.FacetItem;

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
			String key = FACET_KEY_DELIMITER + display + FACET_KEY_DELIMITER;
			facetItems.add(new FacetItem(key, display));
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
		case range:
			for (int x = min; x <= max; x++) {
				if (x > DISPLAY_MAX)
					continue;
				list.add(x);
			}
			break;
		case fixed:
			list.add(min);
			break;
		case multiple:
			list.add(min);
			list.add(max);
			break;
		case unknown:
		default:
			list.add(min);
			break;

		}

		creditFacetSet.addAll(list);

		Set<String> facetKeys = new java.util.LinkedHashSet<String>();
		for (Integer credit : list)
			facetKeys.add(FACET_KEY_DELIMITER + credit.toString()
					+ FACET_KEY_DELIMITER);
		if (CreditType.range.equals(course.getCreditType())
				&& max > DISPLAY_MAX)
			facetKeys.add(FACET_KEY_DELIMITER + "> " + DISPLAY_MAX
					+ FACET_KEY_DELIMITER);
		((CourseSearchItemImpl) course).setCreditsFacetKeys(facetKeys);
	}
}
