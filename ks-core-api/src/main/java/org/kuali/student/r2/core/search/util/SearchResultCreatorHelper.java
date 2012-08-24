/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.search.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

/**
 * Help convert to and from Search objects
 *
 * @author nwright
 */
public class SearchResultCreatorHelper {

    public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    private final List<String> headers = new ArrayList<String>();
    private final SearchResultInfo searchResult = new SearchResultInfo();
    private DateFormat dateFormat;

    /**
     * Get the date format to be used for parsing and formatting dates
     * @return the default format if not set MM/dd/yy hh:mm aa
     */
    public DateFormat getDateFormat() {
        if (dateFormat == null) {
            return DEFAULT_DATE_FORMAT;
        }
        return dateFormat;
    }

    /**
     * Set the date format to be used for parsing and formatting dates
     * @param dateFormat 
     */
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Get the column headers or ResultKeys that will be applied to 
     * a set of results when being created
     */
    public List<String> getHeaders() {
        return headers;
    }

     /**
     * Get the column headers or ResultKeys that will be applied to 
     * a set of results when being created
     */
    public void setHeaders(List<String> headers) {
        this.headers.clear();
        if (headers == null) {
            return;
        }
        this.headers.addAll(headers);
    }

    /**
     * Get the search result that is created using this helper.
     */
    public SearchResultInfo getSearchResult() {
        return searchResult;
    }  
    
    /** 
     * Process all the results of the query converting them to a SearchResultRow and 
     * adding it to the search result
     * 
     * Calls addSearchResultRow for each row returned by the query
     * 
     * @param query 
     */
    public void processQuery (Query query) {
        List results = query.getResultList();
        for (Object result : results) {
            Object[] resultArray = (Object[]) result;
            this.addSearchResultRow(resultArray);
        }
    }
    
    
    /**
     * Convert an object[] with the specified headers into a search result row 
     * and add it to the search result
     *
     * Made public so a programmer could process their own query to perhaps filter
     * it before adding each search result row
     * 
     * @param data a list of data values
     * @return a search result row
     * @throws IllegalArgumentException if the size of the data array does not match the size of the headers
     */
    public void addSearchResultRow(Object[] data) {
        if (headers.size () != data.length) {
            throw new IllegalArgumentException(headers.size () + " header size does not match data length " + data.length);
        }
        SearchResultRowInfo row = new SearchResultRowInfo();
        this.searchResult.getRows().add(row);
        for (int i = 0; i < headers.size (); i++) {
            String dataStr = null;
            Object datum = data[i];
            if (datum == null) {
                dataStr = null;
            }
            if (datum instanceof Date) {
                Date date = (Date) datum;
                dataStr = getDateFormat().format(date);
            } else {
                dataStr = datum.toString();
            }
            row.addCell(headers.get(i), dataStr);
        }    
    }
}
