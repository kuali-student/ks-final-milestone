/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.search.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

/**
 * Help convert to and from Search objects
 *
 * @author nwright
 */
public class SearchResultHelper {

    private SearchResultInfo searchResult;
    List<LinkedHashMap<String, String>> listOfMap;

    public SearchResultHelper(SearchResultInfo searchResult) {
        this.searchResult = searchResult;
        this.listOfMap = SearchResultHelper.convertToMap(searchResult);
    }

    public SearchResultInfo getSearchResult() {
        return searchResult;
    }
    public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    private DateFormat dateFormat;

    public DateFormat getDateFormat() {
        if (dateFormat == null) {
            return DEFAULT_DATE_FORMAT;
        }
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String get(int row, String resultKey) {
        Map<String, String> map = this.listOfMap.get(row);
        return map.get(resultKey);
    }

    public Integer getAsInteger(int index, String resultKey) {
        String value = this.get(index, resultKey);
        if (value == null) {
            return null;
        }
        return Integer.parseInt(value);
    }

    public Date getAsDate(int index, String resultKey) {
        String value = this.get(index, resultKey);
        if (value == null) {
            return null;
        }
        try {
            return dateFormat.parse(value);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(value + " is not a date in the expected format "
                    + dateFormat.toString() + " " + index + " " + resultKey);
        }
    }

    /**
     * Convert to a list of maps for easy access
     *
     * @param searchResult
     * @return Linked hash map to preserve the ordering of the fields in the
     * rows
     */
    public static List<LinkedHashMap<String, String>> convertToMap(SearchResultInfo searchResult) {
        List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
        for (SearchResultRowInfo row : searchResult.getRows()) {
            list.add(toStringMap(row));
        }
        return list;
    }

    /**
     * Little utility to convert a row into a map for easy access to specific
     * values
     *
     * @param row
     * @return a Linked hash map so the order of the values is preserved
     */
    public static LinkedHashMap<String, String> toStringMap(SearchResultRowInfo row) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        for (SearchResultCellInfo cell : row.getCells()) {
            map.put(cell.getKey(), cell.getValue());
        }
        return map;
    }
}
