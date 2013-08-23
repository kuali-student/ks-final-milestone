/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.search.util;

import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Get date format.
     * If not set returns the default date format MM/dd/yyy hh:mm aa
     */
    public DateFormat getDateFormat() {
        if (dateFormat == null) {
            return DEFAULT_DATE_FORMAT;
        }
        return dateFormat;
    }

    /**
     * Set the date format.
     * @param dateFormat new date format to use
     */
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Get the result value for the specified row and column name
     * @param rowIndex index to the row
     * @param resultKey key or name of the column desired
     * @throws IndexOutOfBoundsException if the row index is exceeds the 
     *         number of rows in the result
     */
    public String get(int rowIndex, String resultKey) {
        Map<String, String> map = this.listOfMap.get(rowIndex);
        return map.get(resultKey);
    }

    /**
     * Get the result value but parse it as an integer
     * @param rowIndex row index
     * @param resultKey key or name of column to get
     * @throws IndexOutOfBoundsException if the row index is exceeds the 
     *         number of rows in the result
     * @throws  NumberFormatException  if the column value does not contain a
     *               parsable integer.
     */
    public Integer getAsInteger(int rowIndex, String resultKey) {
        String value = this.get(rowIndex, resultKey);
        if (value == null) {
            return null;
        }
        return Integer.parseInt(value);
    }
/**
     * Get the result value but parse it as a date
     * @param rowIndex row index
     * @param resultKey key or name of column to get
     * @throws IndexOutOfBoundsException if the row index is exceeds the 
     *         number of rows in the result
     * @throws  IllegalArgumentException  if the column value does not contain a
     *               parsable date using the supplied date format.
     */
    public Date getAsDate(int rowIndex, String resultKey) {
        String value = this.get(rowIndex, resultKey);
        if (value == null) {
            return null;
        }
        try {
            return getDateFormat().parse(value);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(value + " is not a date in the expected format "
                    + getDateFormat ().toString() + " " + rowIndex + " " + resultKey);
        }
    }

    /**
     * Convert to a list of maps for easy access.
     * 
     * This is used internally by the helper but made it public so 
     * programmers who don't want to use the full helper may wish to 
     * convert to a map themselves and then manipulate
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
     * This is used internally but made this public because programmers who 
     * don't want to use the full helper may wish to
     * use this method easily create a map from a single row.
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
