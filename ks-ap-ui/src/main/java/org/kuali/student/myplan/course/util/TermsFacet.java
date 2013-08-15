package org.kuali.student.myplan.course.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.myplan.course.dataobject.CourseSearchItemImpl;
import org.kuali.student.myplan.course.dataobject.FacetItem;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

/**
 * Logic for building list of FacetItems and coding CourseSearchItems.
 */
public class TermsFacet extends AbstractFacet {

	private static final String PROJECTED_TERM_PREFIX = "Projected ";

	public TermsFacet() {
		super();
		super.setShowUnknownKey(false);
	}

	/**
	 * Put the terms in a predictable order.
	 * 
	 * @return
	 */
	@Override
	public List<FacetItem> getFacetItems() {
		// Call getFacetItems to have the Unknown facet item added.
		Collections.sort(super.getFacetItems(), new TermsFacetItemComparator());
		return facetItems;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void process(CourseSearchItem course) {

		// The Set of facet keys which pertain to this course.
		Set<String> facetKeys = new HashSet<String>();

		// Terms
		if (null != course.getTermInfoList() && 0 != course.getTermInfoList().size()) {
			for (String term : course.getTermInfoList()) {
				// Title-case the term name.
				String termName;
				try {
					termName = PROJECTED_TERM_PREFIX
							+ KsapFrameworkServiceLocator.getTypeService()
									.getType(term, KsapFrameworkServiceLocator.getContext().getContextInfo()).getName()
									.substring(0, 2).toUpperCase();
				} catch (DoesNotExistException e) {
					throw new IllegalArgumentException("Invalid term type " + term, e);
				} catch (InvalidParameterException e) {
					throw new IllegalArgumentException("Invalid term type " + term, e);
				} catch (MissingParameterException e) {
					throw new IllegalArgumentException("Invalid term type " + term, e);
				} catch (OperationFailedException e) {
					throw new IllegalStateException("Type lookup error", e);
				} catch (PermissionDeniedException e) {
					throw new IllegalStateException("Type lookup error", e);
				}
				String key = FACET_KEY_DELIMITER + termName + FACET_KEY_DELIMITER;

				// If an FacetItem doesn't exist for this key then create one
				// and add it to the Facet.
				if (isNewFacetKey(key)) {
					facetItems.add(new FacetItem(key, termName));
				}

				facetKeys.add(key);
			}
		}

		// Scheduled terms.
		if (null == course.getScheduledTermsList() || 0 == course.getScheduledTermsList().size()) {
			String key = FACET_KEY_DELIMITER + getUnknownFacetKey() + FACET_KEY_DELIMITER;
			facetKeys.add(key);
		} else {
			for (String t : course.getScheduledTermsList()) {
				String termFacetKey = KsapFrameworkServiceLocator.getTermHelper().getTerm(t).getName();

				// Convert Winter 2012 to WI 12
				Matcher m = CourseSearchConstants.TERM_PATTERN.matcher(termFacetKey);
				if (m.matches()) {
					termFacetKey = m.group(1).substring(0, 2).toUpperCase() + " " + m.group(2);
				}

				String key = FACET_KEY_DELIMITER + termFacetKey + FACET_KEY_DELIMITER;
				if (isNewFacetKey(key)) {
					facetItems.add(new FacetItem(key, termFacetKey));
				}
				facetKeys.add(key);
			}
		}

		// Add the set of keys to the courseSearchItem.
		((CourseSearchItemImpl) course).setTermsFacetKeys(facetKeys);
	}

	/**
	 * Ordering for Terms Facet items.
	 */
	class TermsFacetItemComparator implements Comparator<FacetItem> {

		@Override
		public int compare(FacetItem fi1, FacetItem fi2) {
			// Unknown is always last.
			if (fi1.getKey().equals(FACET_KEY_DELIMITER + TermsFacet.this.unknownFacetKey + FACET_KEY_DELIMITER)) {
				return 1;
			}

			if (fi2.getKey().equals(FACET_KEY_DELIMITER + TermsFacet.this.unknownFacetKey + FACET_KEY_DELIMITER)) {
				return -1;
			}

			// If the facet items that end with a year are scheduled terms and
			// should precede terms.
			boolean isYear1 = fi1.getKey().matches(".*\\d{2}" + FACET_KEY_DELIMITER + "$");
			boolean isYear2 = fi2.getKey().matches(".*\\d{2}" + FACET_KEY_DELIMITER + "$");

			// Two scheduled terms.
			if (isYear1 && isYear2) {
				// TODO: For now just ignore the year and projected keywords
				String termKey1 = fi1.getKey().replaceAll(FACET_KEY_DELIMITER, "").toUpperCase();
				termKey1 = termKey1.replaceAll(" \\d{2}", "");
				String termKey2 = fi2.getKey().replaceAll(FACET_KEY_DELIMITER, "").toUpperCase();
				termKey2 = termKey2.replaceAll(" \\d{2}", "");

				return TermsFacet.TermOrder.valueOf(termKey1).compareTo(TermsFacet.TermOrder.valueOf(termKey2));
			}

			if (isYear1 && !isYear2) {
				return -1;
			}

			if (!isYear1 && isYear2) {
				return 1;
			}

			// Two terms.
			if (!isYear1 && !isYear2) {
				String termKey1 = fi1.getKey().replaceAll(FACET_KEY_DELIMITER, "")
						.replaceAll(PROJECTED_TERM_PREFIX, "").toUpperCase();
				String termKey2 = fi2.getKey().replaceAll(FACET_KEY_DELIMITER, "")
						.replaceAll(PROJECTED_TERM_PREFIX, "").toUpperCase();
				return TermsFacet.TermOrder.valueOf(termKey1).compareTo(TermsFacet.TermOrder.valueOf(termKey2));
			}
			return 0;
		}
	}

	private enum TermOrder {
		FA, AU, WI, SP, SU
	}

}
