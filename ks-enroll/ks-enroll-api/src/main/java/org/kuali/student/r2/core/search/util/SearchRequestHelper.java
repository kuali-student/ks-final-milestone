/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.search.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;

/**
 * Help convert to and from Search objects
 *
 * @author nwright
 */
public class SearchRequestHelper {

    public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    private DateFormat dateFormat;
    private SearchRequestInfo searchRequest;

    public SearchRequestHelper(SearchRequestInfo searchRequest) {
        this.searchRequest = searchRequest;
    }

    public DateFormat getDateFormat() {
        if (dateFormat == null) {
            return DEFAULT_DATE_FORMAT;
        }
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * get the parameter as a list of strings
     *
     * @param paramKey
     * @return the list which may be empty, null if the paramKey is not set
     */
    public List<String> getParamAsList(String paramKey) {
        for (SearchParamInfo param : this.searchRequest.getParams()) {
            if (param.getKey().equals(paramKey)) {
                return param.getValues();
            }
        }
        return null;
    }

    /**
     * get the parameter as a string
     *
     * @param paramKey
     * @return null if no values are set, the first value if more than one set
     */
    public String getParamAsString(String paramKey) {
        List<String> list = this.getParamAsList(paramKey);
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
