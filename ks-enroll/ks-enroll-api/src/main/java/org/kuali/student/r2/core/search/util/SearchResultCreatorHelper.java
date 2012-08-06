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

    public DateFormat getDateFormat() {
        if (dateFormat == null) {
            return DEFAULT_DATE_FORMAT;
        }
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers.clear();
        if (headers == null) {
            return;
        }
        this.headers.addAll(headers);
    }

    public SearchResultInfo getSearchResult() {
        return searchResult;
    }  
    
    /** 
     * Process all the results of the query converting them to a SearchResultRow and 
     * adding it to the search result
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
     * convert an object[] with the specified headers into a search result row 
     * and add it to the search result
     *
     * @param data a list of data values
     * @return a search result row
     */
    public void addSearchResultRow(Object[] data) {
        if (headers.size () != data.length) {
            throw new IllegalArgumentException(headers.size () + " header size does not match data length " + data.length);
        }
        SearchResultRowInfo row = new SearchResultRowInfo();
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
            this.searchResult.getRows().add(row);
        }
        
    }

}
