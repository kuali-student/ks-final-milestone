/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.myplan.course.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.course.CourseSearchForm;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.ap.framework.course.CourseSearchStrategy;
import org.kuali.student.myplan.course.form.CourseSearchFormImpl;
import org.kuali.student.myplan.course.util.CampusSearch;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/course/**")
public class CourseSearchController extends UifControllerBase {

	private static final Logger LOG = Logger
			.getLogger(CourseSearchController.class);

	/**
	 * HTTP session attribute key for holding recent search results.
	 */
	private static final String RESULTS_ATTR = CourseSearchController.class
			.getName() + ".results";

	/**
	 * Opaque key representing a unique search form.
	 * <p>
	 * This key is used to match cached results across multiple requests for the
	 * same search, and is considered to be unique if all search inputs on the
	 * form used to construct the key are unique.
	 * </p>
	 */
	private static class FormKey {
		private final String searchQuery;
		private final String searchTerm;
		private final String[] campusSelect;

		private FormKey(CourseSearchForm f) {
			this.searchQuery = f.getSearchQuery();
			this.searchTerm = f.getSearchTerm();
			this.campusSelect = f.getCampusSelect() == null ? null : f
					.getCampusSelect().toArray(
							new String[f.getCampusSelect().size()]);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(campusSelect);
			result = prime * result
					+ ((searchQuery == null) ? 0 : searchQuery.hashCode());
			result = prime * result
					+ ((searchTerm == null) ? 0 : searchTerm.hashCode());
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
			if (!Arrays.equals(campusSelect, other.campusSelect))
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
			return true;
		}

	}

	/**
	 * Input command processor for supporting DataTables server-side processing.
	 * 
	 * @see <a
	 *      href="http://datatables.net/usage/server-side">http://datatables.net/usage/server-side</a>
	 */
	private static class DataTablesInputs {
		private final int iDisplayStart, iDisplayLength, iColumns,
				iSortingCols, sEcho;
		private final String sSearch;
		private final Pattern patSearch;
		private final boolean bRegex;
		private final boolean[] bSearchable_, bRegex_, bSortable_;
		private final String[] sSearch_, sSortDir_, mDataProp_;
		private final Pattern[] patSearch_;
		private final int[] iSortCol_;

		private DataTablesInputs(HttpServletRequest request) {
			String s;
			iDisplayStart = (s = request.getParameter("iDisplayStart")) == null ? 0
					: Integer.parseInt(s);
			iDisplayLength = (s = request.getParameter("iDisplayLength")) == null ? 0
					: Integer.parseInt(s);
			iColumns = (s = request.getParameter("iColumns")) == null ? 0
					: Integer.parseInt(s);
			bRegex = (s = request.getParameter("bRegex")) == null ? false
					: new Boolean(s);
			patSearch = (sSearch = request.getParameter("sSearch")) == null
					|| !bRegex ? null : Pattern.compile(sSearch);
			bSearchable_ = new boolean[iColumns];
			sSearch_ = new String[iColumns];
			patSearch_ = new Pattern[iColumns];
			bRegex_ = new boolean[iColumns];
			bSortable_ = new boolean[iColumns];
			for (int i = 0; i < iColumns; i++) {
				bSearchable_[i] = (s = request.getParameter("bSearchable_" + i)) == null ? false
						: new Boolean(s);
				bRegex_[i] = (s = request.getParameter("bRegex_" + i)) == null ? false
						: new Boolean(s);
				patSearch_[i] = (sSearch_[i] = request.getParameter("sSearch_"
						+ i)) == null
						|| !bRegex_[i] ? null : Pattern.compile(sSearch_[i]);
				bSortable_[i] = (s = request.getParameter("bSortable_" + i)) == null ? false
						: new Boolean(s);
			}
			iSortingCols = (s = request.getParameter("iSortingCols")) == null ? 0
					: Integer.parseInt(s);
			iSortCol_ = new int[iSortingCols];
			sSortDir_ = new String[iSortingCols];
			for (int i = 0; i < iSortingCols; i++) {
				iSortCol_[i] = (s = request.getParameter("iSortCol_" + i)) == null ? 0
						: Integer.parseInt(s);
				sSortDir_[i] = request.getParameter("sSortDir_" + i);
			}
			mDataProp_ = new String[iColumns];
			for (int i = 0; i < iColumns; i++)
				mDataProp_[i] = request.getParameter("mDataProp_" + i);
			sEcho = (s = request.getParameter("sEcho")) == null ? 0 : Integer
					.parseInt(s);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(super.toString());
			sb.append("\n\tiDisplayStart = ");
			sb.append(iDisplayStart);
			sb.append("\n\tiDisplayLength = ");
			sb.append(iDisplayLength);
			sb.append("\n\tiColumns = ");
			sb.append(iColumns);
			sb.append("\n\tsSearch = ");
			sb.append(sSearch);
			sb.append("\n\tbRegex = ");
			sb.append(bRegex);
			for (int i = 0; i < iColumns; i++) {
				sb.append("\n\tbSearchable_").append(i).append(" = ");
				sb.append(bSearchable_[i]);
				sb.append("\n\tsSearch_").append(i).append(" = ");
				sb.append(sSearch_[i]);
				sb.append("\n\tbRegex_").append(i).append(" = ");
				sb.append(bRegex_[i]);
				sb.append("\n\tbSortable_").append(i).append(" = ");
				sb.append(bSortable_[i]);
			}
			sb.append("\n\tiSortingCols = ");
			sb.append(iSortingCols);
			for (int i = 0; i < iSortingCols; i++) {
				sb.append("\n\tiSortCol_").append(i).append(" = ");
				sb.append(iSortCol_[i]);
				sb.append("\n\tsSortDir_").append(i).append(" = ");
				sb.append(sSortDir_[i]);
			}
			for (int i = 0; i < iColumns; i++) {
				sb.append("\n\tmDataProp_").append(i).append(" = ");
				sb.append(mDataProp_[i]);
			}
			sb.append("\n\tsEcho = ");
			sb.append(sEcho);
			return sb.toString();
		}
	}

	/**
	 * Simple object for tracking facet click/count state.
	 * 
	 * @see SessionSearchInfo
	 */
	private static class FacetState implements Serializable {
		private static final long serialVersionUID = 1719950239861974273L;

		private boolean checked = true;
		private int count;
	}

	/**
	 * Simple object representing pre-processed search data.
	 * 
	 * @see SessionSearchInfo
	 * @see CourseSearchItem#getSearchColumns()
	 * @see CourseSearchItem#getFacetColumns()
	 */
	private static class SearchInfo implements Serializable {
		private static final long serialVersionUID = 8697147011424347285L;

		private final CourseSearchItem item;
		private final String[] sortColumns;
		private final String[][] facetColumns;

		private SearchInfo(CourseSearchItem item) {
			this.item = item;
			sortColumns = item.getSortColumns();
			facetColumns = item.getFacetColumns();
		}

		@Override
		public String toString() {
			return "SearchInfo [searchColumns=" + item.getCourseId()
					+ ", sortColumns=" + Arrays.toString(sortColumns)
					+ ", facetColumns=" + Arrays.toString(facetColumns) + "]";
		}
	}

	/**
	 * Session-bound search results cache. This object backs the facet and data
	 * table result views on the KSAP course search front-end. Up to three
	 * searches are stored in the HTTP session via these objects.
	 */
	private static class SessionSearchInfo {

		/**
		 * The search form input data used to key this session info object.
		 */
		private final FormKey formKey;

		/**
		 * The search result column data.
		 */
		private final List<SearchInfo> searchResults;

		/**
		 * The calculated facet state.
		 */
		private final List<Map<String, FacetState>> facetState;

		/**
		 * The oneClick flag records whether or not any facet state leaf nodes
		 * have been switched to false. Until oneClick has been set, count
		 * updates and clickAll requests will be ignored.
		 * 
		 * @see #facetClick(String, int)
		 * @see #facetClickAll()
		 * @see #updateFacetCounts()
		 */
		private boolean oneClick = false;

		/**
		 * Compose search information based on materialized inputs.
		 * 
		 * <p>
		 * This constructor is potentially expensive - it is where the actual
		 * search is performed on the back end when pulling data or facet table
		 * results.
		 * </p>
		 * 
		 * @param searcher
		 *            The controller's strategy instance.
		 * @param formKey
		 *            The form key that will be used as an attribute handle to
		 *            the search results in the HTTP session.
		 * @param form
		 *            The search form.
		 * @param principalName
		 *            The principal name of the current user.
		 * @see CourseSearchController#getJsonResponse(HttpServletResponse,
		 *      HttpServletRequest)
		 * @see CourseSearchController#getFacetValues(HttpServletResponse,
		 *      HttpServletRequest)
		 */
		private SessionSearchInfo(CourseSearchStrategy searcher,
				FormKey formKey, CourseSearchForm form, String principalName) {
			// Verify that form key data matches the actual search form
			assert (formKey.searchQuery == null && form.getSearchQuery() == null)
					|| (formKey.searchQuery != null && (formKey.searchQuery
							.equals(form.getSearchQuery()))) : formKey.searchQuery
					+ " " + form.getSearchQuery();
			assert (formKey.searchTerm == null && form.getSearchTerm() == null)
					|| (formKey.searchTerm != null && (formKey.searchTerm
							.equals(form.getSearchTerm()))) : formKey.searchTerm
					+ " " + form.getSearchTerm();
			String[] cs = null;
			assert (formKey.campusSelect == null && form.getCampusSelect() == null)
					|| (formKey.campusSelect != null && (Arrays.equals(
							formKey.campusSelect,
							cs = form.getCampusSelect().toArray(
									new String[form.getCampusSelect().size()])))) : formKey.campusSelect
					+ " " + cs;
			this.formKey = formKey;

			// Hit search back end and break down into column values
			List<CourseSearchItem> courses = searcher.courseSearch(form,
					principalName);
			List<SearchInfo> resultList = new java.util.ArrayList<SearchInfo>(
					courses.size());
			for (CourseSearchItem course : courses)
				resultList.add(new SearchInfo(course));
			this.searchResults = Collections.unmodifiableList(Collections
					.synchronizedList(resultList));

			// Calculate initial facet state when search results are non-empty
			if (searchResults.isEmpty())
				facetState = Collections.emptyList();
			else {
				int nFacetColumns = searchResults.iterator().next().facetColumns.length;
				List<Map<String, FacetState>> facetStateMap = new java.util.ArrayList<Map<String, FacetState>>(
						nFacetColumns);
				for (int i = 0; i < nFacetColumns; i++)
					facetStateMap
							.add(new java.util.LinkedHashMap<String, FacetState>());
				for (SearchInfo row : searchResults) {
					// Validate that the number of facet columns is uniform
					// across all search rows
					assert row.facetColumns != null
							|| row.facetColumns.length == nFacetColumns : row.facetColumns == null ? "null"
							: row.facetColumns.length + " != " + nFacetColumns;
					// Update facet counts for all columns, creating pre-checked
					// state nodes as needed
					for (int i = 0; i < nFacetColumns; i++) {
						Map<String, FacetState> fm = facetStateMap.get(i);
						for (String key : row.facetColumns[i]) {
							assert key.startsWith(";") && key.endsWith(";")
									&& key.length() >= 3 : key;
							String facetKey = key
									.substring(1, key.length() - 1);
							FacetState fs = fm.get(facetKey);
							if (fs == null)
								fm.put(facetKey, fs = new FacetState());
							fs.count++;
						}
					}
				}
				for (int i = 0; i < nFacetColumns; i++)
					facetStateMap.set(i, Collections
							.synchronizedMap(Collections
									.unmodifiableMap(facetStateMap.get(i))));
				facetState = Collections.synchronizedList(Collections
						.unmodifiableList(facetStateMap));
			}
		}

		/**
		 * Get the facet state associated with a specific facet column value.
		 * 
		 * @param key
		 *            The facet column value to use as the facet key.
		 * @param col
		 *            The facet column index number.
		 * @return The facet state associated with the indicated column value.
		 */
		private FacetState getFacetState(String key, int col) {
			Map<String, FacetState> fm = facetState.get(col);
			assert key.startsWith(";") && key.endsWith(";")
					&& key.length() >= 3 : key;
			String facetKey = key.substring(1, key.length() - 1);
			FacetState fs = fm.get(facetKey);
			assert fs != null : col + " " + facetKey;
			if (fs == null)
				fm.put(facetKey, fs = new FacetState());
			return fs;
		}

		/**
		 * Walk through the facet columns in the search results and update
		 * counts based on the current facet state. The method assumes that the
		 * facet state structure is fully formed but that the click state has
		 * change since the last time counts were calculated.
		 */
		private void updateFacetCounts() {
			if (searchResults.isEmpty())
				return;

			// Determine the number of facet columns - this should be uniform
			// across the facet state table and the facet columns in each row
			int nFacetCols = searchResults.iterator().next().facetColumns.length;
			assert facetState.size() == nFacetCols : facetState.size() + " != "
					+ nFacetCols;

			// Reset the count on all facet state leaf nodes to 0
			for (Map<String, FacetState> fm : facetState)
				for (Entry<String, FacetState> fce : fm.entrySet())
					fce.getValue().count = 0;

			// Iterate search results
			for (SearchInfo row : searchResults) {

				// Validate facet column count matches facet table size
				assert row.facetColumns != null
						|| row.facetColumns.length == nFacetCols : row.facetColumns == null ? "null"
						: row.facetColumns.length + " != " + nFacetCols;

				// identify filtered rows before counting
				boolean filtered = false;
				for (int i = 0; !filtered && i < nFacetCols; i++) {
					// Find one match for one checked facet on this row
					boolean hasOne = false;
					for (String fci : row.facetColumns[i])
						if (!hasOne && getFacetState(fci, i).checked)
							hasOne = true;
					assert !filtered : "filtered state changed";
					filtered = !hasOne;
				}
				if (!filtered)
					// count all cells in all non-filtered rows
					for (int i = 0; i < nFacetCols; i++)
						for (String fci : row.facetColumns[i])
							getFacetState(fci, i).count++;
			}
		}

		/**
		 * Update checked state on all facets following a click event from the
		 * browser.
		 * 
		 * @param key
		 *            The facet key clicked. May be 'All'.
		 * @param i
		 *            The facet column the click is related to.
		 */
		private void facetClick(String key, int i) {
			Map<String, FacetState> fsm = facetState.get(i);
			if ("All".equals(key))
				for (FacetState fs : fsm.values())
					fs.checked = true;
			else {
				FacetState fs = null;

				// Determine if all facets are checked prior to recording
				// This state corresponds to the "All" checkbox being
				// checked on the browser, in which case no other boxes
				// in the group appear to be checked.
				boolean all = true;
				for (FacetState ifs : facetState.get(i).values())
					if (!all)
						continue;
					else
						all = all && ifs.checked;

				// when all checked, clear all but the clicked box
				if (all) {
					for (Entry<String, FacetState> fe : fsm.entrySet())
						if (fe.getValue().checked = key.equals(fe.getKey())) {
							assert fs == null : fs + " " + fe;
							fs = fe.getValue();
						}
					assert fs != null : i + " " + key;
					oneClick = true;
				} else {
					// when all are not checked, toggle the clicked box
					fs = facetState.get(i).get(key);
					assert fs != null : i + " " + key;
					// Update checked status of facet
					if (!(fs.checked = !fs.checked))
						oneClick = true;
					// unchecking the last box in the group, check all
					boolean none = true;
					for (FacetState ifs : facetState.get(i).values())
						if (!none)
							continue;
						else
							none = !ifs.checked;
					if (none)
						for (FacetState tfs : fsm.values())
							tfs.checked = true;
				}
			}
			if (oneClick)
				updateFacetCounts();
		}

		private void facetClickAll() {
			if (oneClick) {
				for (int i = 0; i < facetState.size(); i++)
					for (FacetState fs : facetState.get(i).values())
						fs.checked = true;
				updateFacetCounts();
				oneClick = false;
			}
		}

		private List<SearchInfo> getFilteredResults(
				final DataTablesInputs dataTablesInputs) {
			final List<SearchInfo> filteredResults = new java.util.ArrayList<SearchInfo>(
					searchResults);

			/**
			 * Tracks loop state for search filtering.
			 */
			class Iter implements Iterator<SearchInfo> {

				/**
				 * Backing iterator.
				 */
				Iterator<SearchInfo> i = filteredResults.iterator();

				/**
				 * The row data last returned by {@link #next()}.
				 */
				SearchInfo current;

				/**
				 * The removed state of the current row
				 */
				boolean removed = false;

				/**
				 * The search string on the current column.
				 */
				String searchString = dataTablesInputs.sSearch;

				/**
				 * The search patter on the current column, null if non-regex.
				 */
				Pattern searchPattern = dataTablesInputs.patSearch;

				/**
				 * Current column pointer.
				 */
				int j = -1;

				/**
				 * Column iterator.
				 */
				Iterator<String[]> fi = new Iterator<String[]>() {
					@Override
					public boolean hasNext() {
						// break column loop once row has been removed,
						// or when all columns have been seen
						return !removed && j < current.facetColumns.length - 1;
					}

					@Override
					public String[] next() {
						// break column loop once row has been removed
						if (removed)
							throw new IllegalStateException(
									"Row has been removed");
						// increase column pointer, update search fields
						j++;
						if (dataTablesInputs.sSearch_[j] != null) {
							searchString = dataTablesInputs.sSearch_[j];
							searchPattern = dataTablesInputs.patSearch_[j];
						} else {
							searchString = dataTablesInputs.sSearch;
							searchPattern = dataTablesInputs.patSearch;
						}
						return current.facetColumns[j];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};

				/**
				 * Determine whether or not DataTables defines the current
				 * column as searchable.
				 * 
				 * @return True if DataTables defines the current column as
				 *         searchable.
				 */
				private boolean isSearchable() {
					return dataTablesInputs.bSearchable_[j];
				}

				/**
				 * Get the column iterator, after resetting to the start of the
				 * row.
				 * 
				 * @return The column iterator, reset to the start of the row.
				 */
				private Iterable<String[]> facets() {
					return new Iterable<String[]>() {
						@Override
						public Iterator<String[]> iterator() {
							j = -1;
							return fi;
						}
					};
				}

				@Override
				public boolean hasNext() {
					return i.hasNext();
				}

				@Override
				public SearchInfo next() {
					removed = false;
					return current = i.next();
				}

				@Override
				public void remove() {
					removed = true;
					// if (LOG.isDebugEnabled())
					// LOG.debug("Removed " + this);
					i.remove();
				}

				@Override
				public String toString() {
					return "Iter [current="
							+ Arrays.toString(current.facetColumns[j])
							+ ", removed=" + removed + ", searchString="
							+ searchString + ", searchPattern=" + searchPattern
							+ ", j=" + j + "]";
				}
			}
			Iter li = new Iter();
			while (li.hasNext()) {
				SearchInfo ln = li.next();
				assert ln == li.current;
				for (String[] cell : li.facets()) {
					if (li.isSearchable()) {
						if (li.searchString == null
								|| li.searchString.trim().equals(""))
							continue;
						if (cell == null || cell.length == 0)
							li.remove();
						else {
							boolean match = false;
							for (String c : cell) {
								if (match)
									continue;
								if (li.searchPattern == null) {
									if (c.toUpperCase().equals(
											li.searchString.toUpperCase()))
										match = true;
								} else if (li.searchPattern.matcher(c)
										.matches())
									match = true;
							}
							if (!match)
								li.remove();
						}
					}
				}
			}

			// Perform sorting if requested
			if (dataTablesInputs.iSortingCols > 0)
				Collections.sort(filteredResults, new Comparator<SearchInfo>() {
					@Override
					public int compare(SearchInfo o1, SearchInfo o2) {
						for (int i = 0; i < dataTablesInputs.iSortingCols; i++) {
							String s1 = o1.sortColumns[dataTablesInputs.iSortCol_[i]];
							String s2 = o2.sortColumns[dataTablesInputs.iSortCol_[i]];
							if (s1 == null && s2 == null)
								continue;
							if (s1 == null)
								return "desc"
										.equals(dataTablesInputs.sSortDir_[i]) ? 1
										: -1;
							if (s2 == null)
								return "desc"
										.equals(dataTablesInputs.sSortDir_[i]) ? -1
										: 1;
							int rv = s1.compareTo(s2);
							if (rv != 0)
								return rv;
						}
						return 0;
					}
				});

			return filteredResults;
		}
	}

	private CourseSearchStrategy searcher = KsapFrameworkServiceLocator
			.getCourseSearchStrategy();

	private CampusSearch campusSearch = new CampusSearch();

	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Synchronously retrieve session bound search results for an incoming
	 * request.
	 * 
	 * <p>
	 * This method ensures that only one back-end search per HTTP session is
	 * running at the same time for the same set of criteria. This is important
	 * since the browser fires requests for the facet table and the search table
	 * independently, so this consideration constrains those two requests to
	 * operating synchronously on the same set of results.
	 * </p>
	 * 
	 * @param request
	 *            The incoming request.
	 * @return Session-bound search results for the request.
	 */
	private SessionSearchInfo getSearchResults(HttpServletRequest request) {
		String user = KsapFrameworkServiceLocator.getUserSessionHelper()
				.getStudentId();

		// Populate search form from HTTP request
		CourseSearchForm form = searcher.createSearchForm();
		form.setSearchQuery(request.getParameter("queryText"));
		form.setCampusSelect(Arrays.asList(request.getParameter("campusParam")
				.split("\\s*,\\s*")));
		form.setSearchTerm(request.getParameter("termParam"));
		if (LOG.isDebugEnabled())
			LOG.debug("Search form : " + form);

		// Check HTTP session for cached search results
		FormKey k = new FormKey(form);
		@SuppressWarnings("unchecked")
		Map<FormKey, SessionSearchInfo> results = (Map<FormKey, SessionSearchInfo>) request
				.getSession().getAttribute(RESULTS_ATTR);
		if (results == null)
			request.getSession()
					.setAttribute(
							RESULTS_ATTR,
							results = Collections
									.synchronizedMap(new java.util.LinkedHashMap<FormKey, SessionSearchInfo>()));
		SessionSearchInfo table;
		// Synchronize on the result table to constrain sessions to
		// one back-end search at a time
		synchronized (results) {
			// dump search results in excess of 3
			while (results.size() > 3) {
				Iterator<?> ei = results.entrySet().iterator();
				ei.next();
				ei.remove();
			}
			results.put(
					k, // The back-end search happens here --------V
					(table = results.remove(k)) == null ? table = new SessionSearchInfo(
							searcher, k, form, user) : table);
		}
		return table;
	}

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		return (UifFormBase) searcher.createSearchForm();
	}

	@RequestMapping(value = "/course/{courseCd}", method = RequestMethod.GET)
	public String get(@PathVariable("courseCd") String courseCd,
			@ModelAttribute("KualiForm") CourseSearchForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String number = "";
		String subject = "";
		String courseId = "";
		courseCd = courseCd.toUpperCase();
		StringBuffer campus = new StringBuffer();
		List<KeyValue> campusKeys = campusSearch.getKeyValues();
		for (KeyValue k : campusKeys) {
			campus.append(k.getKey().toString());
			campus.append(",");
		}
		String[] splitStr = courseCd.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
		if (splitStr.length == 2) {
			number = splitStr[1];
			subject = splitStr[0];
		} else {
			StringBuffer splitBuff = new StringBuffer();
			for (int i = 0; i < splitStr.length; i++) {
				splitBuff.append(splitStr[i]);
			}
			response.sendRedirect("../course?searchQuery=" + splitBuff
					+ "&searchTerm=any&campusSelect=" + campus);
			return null;

		}
		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();
		Map<String, String> divisionMap = strategy.fetchCourseDivisions();

		ArrayList<String> divisions = new ArrayList<String>();
		strategy.extractDivisions(divisionMap, subject, divisions, false);
		if (divisions.size() > 0) {
			subject = divisions.get(0);
		}

		SearchRequestInfo searchRequest = new SearchRequestInfo(
				"myplan.course.getcluid");
		SearchResult searchResult = null;
		try {
			searchRequest.addParam("number", number);
			searchRequest.addParam("subject", subject.trim());
			searchRequest.addParam("currentTerm", KsapFrameworkServiceLocator
					.getAtpHelper().getCurrentAtpId());
			searchRequest.addParam("lastScheduledTerm",
					KsapFrameworkServiceLocator.getAtpHelper()
							.getLastScheduledAtpId());
			searchResult = KsapFrameworkServiceLocator.getCluService().search(
					searchRequest,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		for (SearchResultRow row : searchResult.getRows()) {
			courseId = searcher.getCellValue(row, "lu.resultColumn.cluId");
		}
		if (courseId.equalsIgnoreCase("")) {
			response.sendRedirect("../course?searchQuery=" + courseCd
					+ "&searchTerm=any&campusSelect=" + campus);
			return null;

		}
		response.sendRedirect("../inquiry?methodToCall=start&viewId=CourseDetails-InquiryView&courseId="
				+ courseId);
		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(@ModelAttribute("KualiForm") UifFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		super.start(form, result, request, response);
		form.setViewId("CourseSearch-FormView");
		form.setView(super.getViewService()
				.getViewById("CourseSearch-FormView"));
		return getUIFModelAndView(form);
	}

	@RequestMapping(value = "/course/", method = RequestMethod.GET)
	public String doGet(@ModelAttribute("KualiForm") UifFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		return "redirect:/myplan/course";
	}

	@RequestMapping(params = "methodToCall=start")
	public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		if (!Boolean.valueOf(request.getAttribute(
				CourseSearchConstants.IS_ACADEMIC_CALENDER_SERVICE_UP)
				.toString())) {
			KsapFrameworkServiceLocator.getAtpHelper().addServiceError(
					"searchTerm");
		}
		super.start(form, result, request, response);
		return getUIFModelAndView(form);
	}

	@RequestMapping(value = "/course/search")
	public void getJsonResponse(HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		SessionSearchInfo table = getSearchResults(request);
		SearchInfo firstRow = table.searchResults.iterator().next();

		// Parse and validate incoming jQuery datatables inputs
		final DataTablesInputs dataTablesInputs = new DataTablesInputs(request);
		if (LOG.isDebugEnabled())
			LOG.debug(dataTablesInputs);
		assert table != null;
		assert table.searchResults.isEmpty()
				|| dataTablesInputs.iColumns >= firstRow.item
						.getSearchColumns().length : firstRow.item
				.getSearchColumns().length + " > " + dataTablesInputs.iColumns;
		assert table.searchResults.isEmpty()
				|| dataTablesInputs.iColumns >= firstRow.sortColumns.length : firstRow.sortColumns.length
				+ " > " + dataTablesInputs.iColumns;
		assert table.searchResults.isEmpty()
				|| dataTablesInputs.iColumns >= firstRow.facetColumns.length : firstRow.facetColumns.length
				+ " > " + dataTablesInputs.iColumns;
		assert table.searchResults.isEmpty()
				|| dataTablesInputs.iColumns == firstRow.facetColumns.length
				|| dataTablesInputs.iColumns == firstRow.sortColumns.length
				|| dataTablesInputs.iColumns == firstRow.item
						.getSearchColumns().length : "Max("
				+ firstRow.facetColumns.length + ","
				+ firstRow.sortColumns.length + ","
				+ firstRow.item.getSearchColumns().length + ") != "
				+ dataTablesInputs.iColumns;

		// DataTables search filter is tied to facet click state on the front
		// end, but is only loosely coupled on the server side.
		List<SearchInfo> filteredResults = table
				.getFilteredResults(dataTablesInputs);

		// Render JSON response for DataTables
		ObjectNode json = mapper.createObjectNode();
		json.put("iTotalRecords", table.searchResults.size());
		json.put("iTotalDisplayRecords", filteredResults.size());
		json.put("sEcho", Integer.toString(dataTablesInputs.sEcho));
		ArrayNode aaData = mapper.createArrayNode();
		for (int i = 0; i < Math.min(filteredResults.size(),
				dataTablesInputs.iDisplayLength); i++) {
			ArrayNode cs = mapper.createArrayNode();
			String[] scol = filteredResults.get(dataTablesInputs.iDisplayStart
					+ i).item.getSearchColumns();
			for (String col : scol)
				cs.add(col);
			for (int j = scol.length; j < dataTablesInputs.iColumns; j++)
				cs.add((String) null);
			aaData.add(cs);
		}
		json.put("aaData", aaData);
		String jsonString = mapper.writeValueAsString(json);
		if (LOG.isDebugEnabled())
			LOG.debug("JSON output : "
					+ (jsonString.length() < 512 ? jsonString : jsonString
							.substring(0, 512)));

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "No-cache");
		response.setHeader("Cache-Control", "No-store");
		response.setHeader("Cache-Control", "max-age=0");
		response.getWriter().println(jsonString);
	}

	@RequestMapping(value = "/course/facetValues")
	public void getFacetValues(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		SessionSearchInfo table = getSearchResults(request);

		// Update click state based on inputs - see myplan.search.js
		String fclick = request.getParameter("fclick");
		String fcol = request.getParameter("fcol");
		if (fclick != null && fcol != null)
			table.facetClick(fclick, Integer.parseInt(fcol));
		else
			table.facetClickAll();

		// Create the oFacets object used by myplan.search.js
		ObjectNode oFacets = mapper.createObjectNode();
		oFacets.put("sQuery", table.formKey.searchQuery);
		oFacets.put("sTerm", table.formKey.searchTerm);
		ArrayNode aCampus = oFacets.putArray("aCampus");
		for (String c : table.formKey.campusSelect)
			aCampus.add(c);
		ArrayNode aFacetState = oFacets.putArray("aFacetState");
		for (Map<String, FacetState> row : table.facetState) {
			ObjectNode ofm = aFacetState.addObject();
			for (Entry<String, FacetState> fse : row.entrySet()) {
				ObjectNode ofs = ofm.putObject(fse.getKey());
				ofs.put("checked", fse.getValue().checked);
				ofs.put("count", fse.getValue().count);
			}
		}
		String jsonString = mapper.writeValueAsString(oFacets);
		if (LOG.isDebugEnabled())
			LOG.debug("JSON output : "
					+ (jsonString.length() < 512 ? jsonString : jsonString
							.substring(0, 512)));

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "No-cache");
		response.setHeader("Cache-Control", "No-store");
		response.setHeader("Cache-Control", "max-age=0");
		response.getWriter().println(jsonString);
	}

	@RequestMapping(params = "methodToCall=searchForCourses")
	public ModelAndView searchForCourses(
			@ModelAttribute("KualiForm") CourseSearchFormImpl form,
			BindingResult result, HttpServletRequest httprequest,
			HttpServletResponse httpresponse) {
		return getUIFModelAndView(form,
				CourseSearchConstants.COURSE_SEARCH_RESULT_PAGE);
	}

	public List<String> getResults(SearchRequestInfo request, String division,
			String code) {

		if (division != null && code != null) {
			request.addParam("division", division);
			request.addParam("code", code);
		} else if (division != null && code == null)
			request.addParam("division", division);
		else
			return Collections.emptyList();

		request.addParam("lastScheduledTerm", KsapFrameworkServiceLocator
				.getAtpHelper().getLastScheduledAtpId());
		request.addParam("currentTerm", KsapFrameworkServiceLocator
				.getAtpHelper().getCurrentAtpId());
		SearchResult searchResult;
		try {
			if ((searchResult = KsapFrameworkServiceLocator.getCluService()
					.search(request,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo())) == null)
				return Collections.emptyList();
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CLU lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("CLU lookup error", e);
		}
		List<String> results = new java.util.ArrayList<String>(searchResult
				.getRows().size());
		for (SearchResultRow row : searchResult.getRows())
			results.add(searcher.getCellValue(row, "courseCode"));
		return results;
	}

}
