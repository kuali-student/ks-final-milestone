package org.kuali.student.ap.coursesearch.util;

import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.CourseSearchItem.CreditType;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.dataobject.FacetItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Logic for building list of FacetItems and coding CourseSearchItems.
 */
public class CreditsFacet extends AbstractFacet {

    /**
     * DISPLAY_MAX will be the cutoff for when we switch to 6+
     */
	private static final int DISPLAY_MAX = 6;

	private HashSet<Float> creditFacetSet = new HashSet<Float>();

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
		Float[] list = creditFacetSet.toArray(new Float[0]);
		Arrays.sort(list);

		for (Float credit : list) {
			String display = credit.toString();
			facetItems.add(new FacetItem(display, display.replace(".0", "")));
		}

		return facetItems;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void process(CourseSearchItem course) {
		float min = course.getCreditMin();
        float max = course.getCreditMax();

		List<Float> list = new ArrayList<Float>();
        if(course.getCreditType()==null){
            list.add(min);
        }else{
		switch (course.getCreditType()) {
            case RANGE:
                for (float x = min; x <= max; x++) {
                    list.add(x);
                }
                break;
            case FIXED:
                list.add(min);
                break;
            case MULTIPLE:
                if (course.getMultipleCredits() != null) {
                    for (BigDecimal credit : course.getMultipleCredits()) {
                        list.add(credit.floatValue());
                    }
                }
                else {
                    list.add(min);
                }
                break;
            case UNKNOWN:
            default:
                list.add(min);
                break;

            }
        }

		creditFacetSet.addAll(list);

		Set<String> facetKeys = new java.util.LinkedHashSet<String>();
		for (Float credit : list) {
		    if (credit < DISPLAY_MAX)
			    facetKeys.add(credit.toString());
            else
                facetKeys.add(DISPLAY_MAX + "+");
        }
		if (CreditType.RANGE.equals(course.getCreditType())
				&& max >= DISPLAY_MAX)
			facetKeys.add(DISPLAY_MAX + "+");
		((CourseSearchItemImpl) course).setCreditsFacetKeys(facetKeys);
	}
}
