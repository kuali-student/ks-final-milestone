package org.kuali.student.ap.coursesearch.util;

import java.util.Set;

/**
 *  Converts a Set of facet keys into a String representation.
 */
public class FacetKeyFormatter {
    public static String format(Set<String> facetKeys) {
        StringBuilder keys = new StringBuilder();
        for (String key : facetKeys) {
            keys.append(key);
        }
        return keys.toString();
    }
}
