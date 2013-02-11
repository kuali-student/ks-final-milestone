package org.kuali.student.myplan.course.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.myplan.course.dataobject.CourseSearchItemImpl;
import org.kuali.student.myplan.course.dataobject.FacetItem;

/**
 * Logic for building list of Course Level FacetItems and coding
 * CourseSearchItems.
 */
public class CourseLevelFacet extends AbstractFacet {

	private static final Logger LOG = Logger.getLogger(CourseLevelFacet.class);

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
		String key = course.getLevel();

		int level;
		try {
			level = key == null ? 0 : Integer.valueOf(key);
		} catch (NumberFormatException e) {
			LOG.warn("Invalid number in course level " + key, e);
			level = 0;
		}
		courseFacetSet.add(level);

		key = FACET_KEY_DELIMITER + level + FACET_KEY_DELIMITER;

		// Code the item with the facet key.
		Set<String> keys = new HashSet<String>();
		keys.add(key);
		((CourseSearchItemImpl) course).setCourseLevelFacetKeys(keys);
	}
}
