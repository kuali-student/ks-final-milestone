package org.kuali.student.myplan.course.util;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.kuali.student.myplan.course.dataobject.CourseSearchItemImpl;
import org.kuali.student.myplan.course.dataobject.FacetItem;

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
		assertEquals(course.getCourseLevelFacetKey(), ";500;");
	}
}
