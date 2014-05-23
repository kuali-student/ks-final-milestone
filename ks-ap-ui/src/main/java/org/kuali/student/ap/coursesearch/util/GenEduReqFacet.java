package org.kuali.student.ap.coursesearch.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.dataobject.FacetItem;
import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * Logic for building list of FacetItems and coding CourseSearchItems.
 */
public class GenEduReqFacet extends AbstractFacet {

	public GenEduReqFacet() {
		super();
		super.setShowUnknownKey(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void process(CourseSearchItem item) {

		ContextInfo context = KsapFrameworkServiceLocator.getContext().getContextInfo();

		FacetItem itemFacet = new FacetItem();
		List<String> genEdStrings = item.getGenEduReqs();
		// Set of keys which pertain to this course.
		Set<String> facetKeys = new HashSet<String>();
		// If no gen edu req info was set then setup for an "Unknown" facet.
		if (genEdStrings == null || genEdStrings.isEmpty()) {
			facetKeys.add(getUnknownFacetKey());
		} else {

			// Remove white space before tokenizing.
			for (String key : genEdStrings) {
				/*
				 * Doing this to fix a bug in IE8 which is trimming off the I&S
				 * as I
				 */
				/* Reversing the above fix for this process */
				if (key.contains("&amp;")) {
					key = key.replace("&amp;", "&");
				}
				if (isNewFacetKey(key)) {
                    itemFacet.setTitle(key);
                    itemFacet.setKey(key);
                    itemFacet.setDisplayName(key);
                    facetItems.add(itemFacet);
				}
				facetKeys.add(key);
			}
		}
		((CourseSearchItemImpl) item).setGenEduReqFacetKeys(facetKeys);
	}

}
