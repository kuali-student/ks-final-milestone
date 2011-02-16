package org.kuali.student.core.dictionary.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultCell;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.common.search.service.SearchDispatcher;

public class MockSearchDispatcher implements SearchDispatcher
{

 @Override
 public SearchResult dispatchSearch (SearchRequest searchRequest)
 {
  SearchResult result = new SearchResult ();
  List<SearchResultRow> rows = new ArrayList ();
  SearchResultRow row = new SearchResultRow ();
  rows.add (row);
  SearchResultCell cell = new SearchResultCell ();
  cell.setKey ("mockKey");
  cell.setValue ("mockValue");
  List<SearchResultCell> cells = new ArrayList ();
  cells.add (cell);
  row.setCells (cells);
  result.setRows (rows);
//  System.out.println ("Generating mock search result for " + searchRequest.getSearchKey ());
  return result;
 }


}
