package org.kuali.student.enrollment.registration.client.service.impl.util;

import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;

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

    private SearchResult searchResult;

    public static SearchResultHelper wrap(SearchResult searchResult){
        return new SearchResultHelper(searchResult);
    }

    private SearchResultHelper(SearchResult searchResult){
        this.searchResult = searchResult;
    }

    @Override
    public Iterator<KeyValue> iterator() {
        return new SearchResultsIterator(searchResult);
    }

    public class SearchResultsIterator implements Iterator<KeyValue>{
        int index = -1;
        private Map<String, Integer> colNameToIndexMap;
        private SearchResult searchResult;

        public SearchResultsIterator(SearchResult searchResult) {
            super();
            this.searchResult = searchResult;
            //Initialize the mapping of column name to index.
            colNameToIndexMap = new HashMap<String, Integer> ();
            if(searchResult!=null && !searchResult.getRows().isEmpty()){
                int index = 0;
                for(SearchResultCell cell : searchResult.getRows().get(0).getCells()){
                    colNameToIndexMap.put(cell.getKey(), index++);
                }
            }
        }

        KeyValue keyValue = new KeyValue() {
            @Override
            public String get(String columnName) {
                return searchResult.getRows().get(index).getCells().get(colNameToIndexMap.get(columnName)).getValue();
            }
        };


        @Override
        public boolean hasNext() {
            return index + 1 < searchResult.getRows().size();
        }

        @Override
        public KeyValue next() {
            index++;
            if (index >= searchResult.getRows().size()){
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
