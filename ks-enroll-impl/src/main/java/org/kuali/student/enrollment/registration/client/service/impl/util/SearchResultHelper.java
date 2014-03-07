package org.kuali.student.enrollment.registration.client.service.impl.util;

import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 2/10/14
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultHelper implements Iterable<SearchResultHelper.KeyValue> {

    private SearchResultInfo searchResultInfo;

    public static SearchResultHelper wrap(SearchResultInfo searchResultInfo){
        return new SearchResultHelper(searchResultInfo);
    }

    private SearchResultHelper(SearchResultInfo searchResultInfo){
        this.searchResultInfo = searchResultInfo;
    }

    @Override
    public Iterator<KeyValue> iterator() {
        return new SearchResultsIterator(searchResultInfo);
    }

    public class SearchResultsIterator implements Iterator{
        int index = -1;
        private Map<String, Integer> colNameToIndexMap;
        private SearchResultInfo searchResultInfo;

        public SearchResultsIterator(SearchResultInfo searchResultInfo) {
            super();
            this.searchResultInfo = searchResultInfo;
            //Initialize the mapping of column name to index.
            colNameToIndexMap = new HashMap<String, Integer> ();
            if(searchResultInfo!=null && !searchResultInfo.getRows().isEmpty()){
                int index = 0;
                for(SearchResultCellInfo cell : searchResultInfo.getRows().get(0).getCells()){
                    colNameToIndexMap.put(cell.getKey(), index++);
                }
            }
        }

        KeyValue keyValue = new KeyValue() {
            @Override
            public String get(String columnName) {
                return searchResultInfo.getRows().get(index).getCells().get(colNameToIndexMap.get(columnName)).getValue();
            }
        };


        @Override
        public boolean hasNext() {
            return index + 1 < searchResultInfo.getRows().size();
        }

        @Override
        public Object next() {
            index++;
            if (index >= searchResultInfo.getRows().size()){
                throw new NoSuchElementException();
            }
            return keyValue;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("You can't remove Search Results");
        }
    }

    public interface KeyValue {
        public String get(String columnName);
    }
}
