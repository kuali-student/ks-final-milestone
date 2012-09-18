package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/09/14
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TypeSearch {

    public String getSearchTypeKey();

    public SearchResult search(SearchRequest searchRequest);
}
