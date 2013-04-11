package org.kuali.student.ap.framework.context;

public interface YearTerm extends Comparable<YearTerm> {

	/**
	 * Get the term, as an ATP type.
	 * 
	 * @return The the term, as an ATP type.
	 */
	String getTermType();

	/**
	 * Get the year.
	 * 
	 * @return The year.
	 */
	int getYear();

	/**
	 * Get the term name for display (i.e. &quot;Spring&quot; for Spring 2013).
	 * 
	 * @return The term name for display (i.e. &quot;Spring&quot; for Spring
	 *         2013).
	 */
	String getTermName();

	/**
	 * Get the short term name for display (i.e. &quot;SP 2013&quot; for Spring
	 * 2013).
	 * 
	 * @return The short term name for display (i.e. &quot;SP 2013&quot; for
	 *         Spring 2013).
	 */
	String getShortName();

	/**
	 * Get the long term name for display (i.e. &quot;SP 2013&quot; for Spring
	 * 2013).
	 * 
	 * @return The long term name for display (i.e. &quot;Spring 2013&quot; for
	 *         Spring 2013).
	 */
	String getLongName();

}
