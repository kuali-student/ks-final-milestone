package org.kuali.student.myplan.course.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.myplan.course.dataobject.CourseSearchItemImpl;
import org.kuali.student.myplan.course.dataobject.FacetItem;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;

/**
 * Logic for building list of FacetItems and coding CourseSearchItems.
 */
public class GenEduReqFacet extends AbstractFacet {

	private final Logger logger = Logger.getLogger(GenEduReqFacet.class);

	public GenEduReqFacet() {
		super();
		super.setShowUnknownKey(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void process(CourseSearchItem item) {

		// TODO: factory for context
		ContextInfo context = new ContextInfo();

		FacetItem itemFacet = new FacetItem();
		String genEdString = item.getGenEduReq();
		// Set of keys which pertain to this course.
		Set<String> facetKeys = new HashSet<String>();
		// If no gen edu req info was set then setup for an "Unknown" facet.
		if (genEdString == null
				|| genEdString.equals(CourseSearchItem.EMPTY_RESULT_VALUE_KEY)
				|| genEdString.equals("")) {
			facetKeys.add(FACET_KEY_DELIMITER + getUnknownFacetKey()
					+ FACET_KEY_DELIMITER);
		} else {
			// TODO: UW SPECIFIC
			// Remove white space before tokenizing.
			genEdString = genEdString.replaceAll("\\s+", "");
			String k[] = genEdString.split(",");
			List<String> keys = new ArrayList<String>(Arrays.asList(k));
			for (String key : keys) {
				/*
				 * Doing this to fix a bug in IE8 which is trimming off the I&S
				 * as I
				 */
				/* Reversing the above fix for this process */
				if (key.contains("&amp;")) {
					key = key.replace("&amp;", "&");
				}
				if (isNewFacetKey(FACET_KEY_DELIMITER + key
						+ FACET_KEY_DELIMITER)) {
					EnumeratedValueInfo e = KsapFrameworkServiceLocator
							.getEnumerationHelper().getGenEdReqEnumInfo(
									KsapFrameworkServiceLocator
											.getEnumerationHelper()
											.getEnumCodeForAbbrVal(key),
									context);
					if (e == null)
						continue;
					key = e.getAbbrevValue();
					String title = e.getValue();
					if (!StringUtils.isEmpty(title)) {
						itemFacet.setTitle(title);
						itemFacet.setKey(FACET_KEY_DELIMITER + key
								+ FACET_KEY_DELIMITER);
						itemFacet.setDisplayName(key);
						facetItems.add(itemFacet);
					}
				}
				facetKeys.add(FACET_KEY_DELIMITER + key + FACET_KEY_DELIMITER);
			}
		}
		((CourseSearchItemImpl) item).setGenEduReqFacetKeys(facetKeys);
	}

}
