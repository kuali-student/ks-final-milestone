package org.kuali.student.ap.framework.course;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.kuali.student.r2.core.search.infc.SearchResultRow;

/**
 * Strategy interface for specifying course search behavior.
 */
public interface CourseSearchStrategy {

	static enum ProjectedOrder {
		AU, FA, WI, SP, SU;
	}

	static enum ScheduledOrder {
		WI, SP, SU, AU, FA;
	}

	static final Comparator<String> NUMERIC = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (o1 == null && o2 == null)
				return 0;
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			if (o1.startsWith("<") && !o2.startsWith("<"))
				return -1;
			if (o2.startsWith("<") && !o1.startsWith("<"))
				return 1;
			int i1;
			try {
				i1 = Integer.parseInt(o1);
			} catch (NumberFormatException e) {
				i1 = Integer.MAX_VALUE;
			}
			int i2;
			try {
				i2 = Integer.parseInt(o2);
			} catch (NumberFormatException e) {
				i2 = Integer.MAX_VALUE;
			}
			return i1 == i2 ? 0 : i1 < i2 ? -1 : 1;
		}
	};

	static final Comparator<String> ALPHA = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (o1 == null && o2 == null)
				return 0;
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			if (o1.equals(o2))
				return 0;
			if ("Unknown".equals(o1) || "None".equals(o1))
				return 1;
			if ("Unknown".equals(o2) || "None".equals(o2))
				return -1;
			return o1.compareTo(o2);
		}
	};

	static final Comparator<String> TERMS = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (o1 == null && o2 == null)
				return 0;
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			if (o1.equals(o2))
				return 0;
			if ("Unknown".equals(o1) || "None".equals(o1))
				return 1;
			if ("Unknown".equals(o2) || "None".equals(o2))
				return -1;

			String[] s1 = o1.split(" ");
			String[] s2 = o2.split(" ");
			if (s1.length != 2 && s2.length != 2)
				return o1.compareTo(o2);
			if (s1.length != 2)
				return 1;
			if (s2.length != 2)
				return -1;

			int year1;
			try {
				year1 = Integer.parseInt(s1[1]);
				if (year1 < 0 || year1 > 100)
					year1 = -1;
			} catch (NumberFormatException e) {
				year1 = -1;
			}
			int year2;
			try {
				year2 = Integer.parseInt(s2[1]);
				if (year2 < 0 || year2 > 100)
					year2 = -1;
			} catch (NumberFormatException e) {
				year2 = -1;
			}

			if (year1 != -1 && year2 != -1) {
				if (year1 < year2)
					return -1;
				if (year1 > year2)
					return 1;
				ScheduledOrder so1;
				try {
					so1 = Enum.valueOf(ScheduledOrder.class, s1[0]);
				} catch (IllegalArgumentException e) {
					so1 = null;
				}
				ScheduledOrder so2;
				try {
					so2 = Enum.valueOf(ScheduledOrder.class, s2[0]);
				} catch (IllegalArgumentException e) {
					so2 = null;
				}
				if (so1 == null && so2 == null)
					return 0;
				if (so1 == null)
					return 1;
				if (so2 == null)
					return -1;
				return so1.compareTo(so2);
			}
			if (year1 != -1)
				return -1;
			if (year2 != -1)
				return 1;

			ProjectedOrder po1;
			try {
				po1 = Enum.valueOf(ProjectedOrder.class, s1[1]);
			} catch (IllegalArgumentException e) {
				po1 = null;
			}
			ProjectedOrder po2;
			try {
				po2 = Enum.valueOf(ProjectedOrder.class, s2[1]);
			} catch (IllegalArgumentException e) {
				po2 = null;
			}
			if (po1 == null && po2 == null)
				return 0;
			if (po1 == null)
				return 1;
			if (po2 == null)
				return -1;
			return po1.compareTo(po2);
		}
	};

	/**
	 * Create a new instance of the course search form.
	 * 
	 * @return A new instance of the course search form.
	 */
	CourseSearchForm createSearchForm();

	/**
	 * Get a specific cell value for a search row.
	 * 
	 * @param row
	 *            The search result row.
	 * @param key
	 *            The column key.
	 */
	String getCellValue(SearchResultRow row, String key);

	Map<String, Credit> getCreditMap();

	Credit getCreditByID(String id);

	boolean isCourseOffered(CourseSearchForm form, CourseSearchItem course);

	void populateFacets(CourseSearchForm form, List<CourseSearchItem> courses);

	List<CourseSearchItem> courseSearch(CourseSearchForm form, String studentId);

	Map<String, String> fetchCourseDivisions();

	String extractDivisions(Map<String, String> divisionMap, String query,
			List<String> divisions, boolean isSpaceAllowed);

	Map<String, Comparator<String>> getFacetSort();

}
