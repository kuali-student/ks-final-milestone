/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.search.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    /**
     * Get the date format to be used to parse dates
     *
     * @return default date format if not set MM/dd/yyyy hh:mm aa
     */
    public DateFormat getDateFormat() {
        if (dateFormat == null) {
            return DEFAULT_DATE_FORMAT;
        }
        return dateFormat;
    }

    /**
     * Set the date format to be used to parse dates
     *
     * @param dateFormat format to be used
     */
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Get the parameter as a list of strings
     *
     * Parameters are actually all lists but most just have a single value.
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
     * Get the parameter as a list of strings
     *
     * Parameters are actually all lists but most just have a single value.
     *
     * @param paramKey
     * @return the list which may be empty, null if the paramKey is not set
     */
    public List<Date> getParamAsListOfDate(String paramKey) {
        List<String> list = this.getParamAsList(paramKey);
        if (list == null) {
            return null;
        }
        List<Date> dates = new ArrayList<Date>(list.size());
        for (String value : list) {
            try {
                Date date = this.getDateFormat().parse(value);
                dates.add(date);
            } catch (ParseException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        return dates;
    }

    /**
     * Get the parameter as a list of Integers
     *
     * @param paramKey
     * @return the list which may be empty, null if the paramKey is not set
     * @throws NumberFormatException if parameter is not an integer
     */
    public List<Integer> getParamAsListOfInteger(String paramKey) {
        List<String> list = this.getParamAsList(paramKey);
        if (list == null) {
            return null;
        }
        List<Integer> integers = new ArrayList<Integer>(list.size());
        for (String value : list) {
            Integer integer = Integer.parseInt(value);
            integers.add(integer);
        }
        return integers;
    }

    /**
     * Get the parameter as a string
     *
     * @param paramKey
     * @return null if no values are set, the first value if more than one set
     * @throws IllegalArgumentException if one of the parameter values is not a
     * date
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

    /**
     * Get the parameter as a date
     *
     * @param paramKey
     * @return null if no values are set, the first value if more than one set
     * @throws IllegalArgumentException if parameter is not a date
     */
    public Date getParamAsDate(String paramKey) {
        String value = this.getParamAsString(paramKey);
        if (value == null) {
            return null;
        }
        try {
            return this.getDateFormat().parse(value);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Get the parameter as an integer
     *
     * @param paramKey
     * @return null if no values are set, the first value if more than one set
     * @throws NumberFormatException if parameter is not an integer
     */
    public Integer getParamAsInteger(String paramKey) {
        String value = this.getParamAsString(paramKey);
        if (value == null) {
            return null;
        }
        return Integer.parseInt(value);
    }
}
