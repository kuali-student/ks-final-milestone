package org.kuali.student.ap.coursesearch.controller;

import org.kuali.student.ap.coursesearch.CourseSearchForm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Opaque key representing a unique search form.
 * <p>
 * This key is used to match cached results across multiple requests for the
 * same search, and is considered to be unique if all search inputs on the
 * form used to construct the key are unique.
 * </p>
 */
public class FormKey {
    private final List<String> criteria;
    private final boolean savedCourses;

    public FormKey(CourseSearchForm f) {
        List<String> c = new ArrayList<String>();
        c.add(f.getSearchQuery());
        c.add(f.getSearchTerm());
        c.addAll(f.getAdditionalCriteria());
        savedCourses = f.isSavedCourses();
        criteria = Collections.unmodifiableList(c);
    }

    public List<String> getCriteria() {
        return criteria;
    }

    public boolean isSavedCourses() {
        return savedCourses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((criteria == null) ? 0 : criteria.hashCode());
        result = prime * result + (savedCourses ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FormKey other = (FormKey) obj;
        if (criteria == null) {
            if (other.criteria != null)
                return false;
        } else if (!criteria.equals(other.criteria))
            return false;
        if (savedCourses != other.savedCourses)
            return false;
        return true;
    }

}
