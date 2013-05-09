/* Copyright 2011 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.myplan.course.form;

import java.util.List;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.course.ClassFinderForm;
import org.kuali.student.ap.framework.course.CourseSearchForm;
import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;

public class CourseSearchFormImpl extends UifFormBase implements
		CourseSearchForm, ClassFinderForm {

	private static final long serialVersionUID = 4898118410378641665L;

	// original course search properties
	private String searchQuery;
	private String searchTerm = SEARCH_TERM_ANY_ITEM;
	private List<String> campusSelect;

	// class finder properties
	private String criteriaKey;
	private List<String> facet;
	private int start;
	private int count;
	private String sort;
	private boolean reverse;

	public void setCampusSelect(List<String> campusSelect) {
		this.campusSelect = campusSelect;
	}

	@Override
	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	@Override
	public String getCriteriaKey() {
		return criteriaKey;
	}

	public void setCriteriaKey(String criteriaKey) {
		this.criteriaKey = criteriaKey;
	}

	@Override
	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	@Override
	public String getQuery() {
		return searchQuery;
	}

	@Override
	public void setQuery(String query) {
		this.searchQuery = query;
	}

	@Override
	public boolean isCriterion(String facet) {
		try {
			Enum.valueOf(CourseLevel.class, facet);
			return true;
		} catch (IllegalArgumentException e) {
		}
		List<EnumeratedValueInfo> enumeratedValueInfoList = KsapFrameworkServiceLocator
				.getEnumerationHelper().getEnumerationValueInfoList(
						"kuali.lu.campusLocation");
		for (EnumeratedValueInfo ev : enumeratedValueInfoList)
			if (ev.getCode().equals(facet))
				return true;
		TermHelper th = KsapFrameworkServiceLocator.getTermHelper();
		for (Term t : th.getCurrentTerms())
			if (t.getId().equals(facet))
				return true;
		AcademicCalendarService acal = KsapFrameworkServiceLocator
				.getAcademicCalendarService();
		try {
			for (TypeInfo t : acal.getTermTypes(KsapFrameworkServiceLocator
					.getContext().getContextInfo()))
				if (t.getKey().equals(facet))
					return true;
		} catch (InvalidParameterException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		}
		return false;
	}

	public List<String> getFacet() {
		return facet;
	}

	public void setFacet(List<String> facet) {
		this.facet = facet;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public List<String> getCampusSelect() {
		return campusSelect;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((campusSelect == null) ? 0 : campusSelect.hashCode());
		result = prime * result + ((facet == null) ? 0 : facet.hashCode());
		result = prime * result + count;
		result = prime * result + (reverse ? 1231 : 1237);
		result = prime * result
				+ ((searchQuery == null) ? 0 : searchQuery.hashCode());
		result = prime * result
				+ ((searchTerm == null) ? 0 : searchTerm.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + start;
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
		CourseSearchFormImpl other = (CourseSearchFormImpl) obj;
		if (campusSelect == null) {
			if (other.campusSelect != null)
				return false;
		} else if (!campusSelect.equals(other.campusSelect))
			return false;
		if (facet == null) {
			if (other.facet != null)
				return false;
		} else if (!facet.equals(other.facet))
			return false;
		if (count != other.count)
			return false;
		if (reverse != other.reverse)
			return false;
		if (searchQuery == null) {
			if (other.searchQuery != null)
				return false;
		} else if (!searchQuery.equals(other.searchQuery))
			return false;
		if (searchTerm == null) {
			if (other.searchTerm != null)
				return false;
		} else if (!searchTerm.equals(other.searchTerm))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		if (start != other.start)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CourseSearchFormImpl [searchQuery=" + searchQuery
				+ ", searchTerm=" + searchTerm + ", campusSelect="
				+ campusSelect + ", facet=" + facet + ", start=" + start
				+ ", count=" + count + ", sort=" + sort + ", reverse="
				+ reverse + "]";
	}

}
