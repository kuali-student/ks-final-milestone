package org.kuali.student.ap.coursesearch.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.dataobject.FacetItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logic for building list of Course Level FacetItems and coding
 * CourseSearchItems.
 */
public class CourseLevelFacet extends AbstractFacet {

	private static final Logger LOG = LoggerFactory.getLogger(CourseLevelFacet.class);

	private HashSet<Integer> courseFacetSet = new HashSet<Integer>();

	public CourseLevelFacet() {
		super();
	}

	/**
	 * Overriding because the course level facet list needs to be in numeric
	 * order rather than string order.
	 * 
	 * @return A list of FacetItems.
	 */
	@Override
	public List<FacetItem> getFacetItems() {
		Integer[] list = courseFacetSet.toArray(new Integer[0]);
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
		String key = course.getLevel();

		int level;
		try {
			level = key == null ? 0 : Integer.valueOf(key);
		} catch (NumberFormatException e) {
			LOG.warn(String.format("Invalid number in course level %s", key), e);
			level = 0;
		}
		courseFacetSet.add(level);

		key = String.valueOf(level);

		// Code the item with the facet key.
		Set<String> keys = new HashSet<String>();
		keys.add(key);
		((CourseSearchItemImpl) course).setCourseLevelFacetKeys(keys);
	}
}
