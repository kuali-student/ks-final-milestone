package org.kuali.student.myplan.course.util;

import org.kuali.student.myplan.course.dataobject.CourseSearchItem;
import org.kuali.student.myplan.course.dataobject.FacetItem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
*  Logic for building list of Course Level FacetItems and coding CourseSearchItems.
*/
public class CourseLevelFacet extends AbstractFacet {

    private HashSet<Integer> courseFacetSet = new HashSet<Integer>();

    public CourseLevelFacet() {
        super();
    }

    /**
     * Overriding because the course level facet list needs to be in numeric order rather than string order.
     *
     * @return A list of FacetItems.
     */
    @Override
    public List<FacetItem> getFacetItems() {
        Integer[] list = courseFacetSet.toArray( new Integer[0] );
        Arrays.sort(list);

        for( Integer credit : list ) {
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
        int level = Integer.valueOf( key );
        courseFacetSet.add( level );

        key = FACET_KEY_DELIMITER + key + FACET_KEY_DELIMITER;

        //  Code the item with the facet key.
        Set<String> keys = new HashSet<String>();
        keys.add(key);
        course.setCourseLevelFacetKeys(keys);
    }
}
