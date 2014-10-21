package org.kuali.student.ap.coursesearch.util;

import java.util.List;

import org.junit.Test;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.dataobject.FacetItem;

import static org.junit.Assert.assertEquals;

public class CourseLevelFacetTest {
	@Test
	public void testGetFacetItems() throws Exception {
		CourseLevelFacet facet = new CourseLevelFacet();
		CourseSearchItemImpl course500 = new CourseSearchItemImpl();
		course500.setLevel("500");
		facet.process(course500);
		CourseSearchItemImpl course600 = new CourseSearchItemImpl();
		course600.setLevel("600");
		facet.process(course600);

		List<FacetItem> list = facet.getFacetItems();

		assertEquals(list.size(), 2);
		assertEquals(list.get(0).getDisplayName(), "500");
		assertEquals(list.get(1).getDisplayName(), "600");
	}

	@Test
	public void testProcess() throws Exception {
		CourseLevelFacet facet = new CourseLevelFacet();
		CourseSearchItemImpl course = new CourseSearchItemImpl();
		course.setLevel("500");
		facet.process(course);
		assertEquals(course.getCourseLevelFacetKey(), "500");
	}
}
