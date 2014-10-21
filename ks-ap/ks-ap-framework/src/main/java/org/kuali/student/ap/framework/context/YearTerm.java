/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.ap.framework.context;

/**
 * Stores and handles the different representations of a term
 */
public interface YearTerm extends Comparable<YearTerm> {

	/**
	 * Get the term ID.
	 * 
	 * @return The term ID.
	 */
	String getTermId();

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

    /**
     * Get the abbreviation of the term name for display (i.e. &quot;SP 13&quot; for Spring 2013).
     *
     * @return The abbreviation of the term name for display (i.e. &quot;SP 13&quot; for Spring 2013).
     */
    String getAbbrivation();



}
