package org.kuali.student.common.search.service.impl;

import java.util.Map;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

public class SearchConfigFormatter
{

 private StringBuilder builder = new StringBuilder (5000);
 private ObjectStructureDefinition os;
 private String rowSeperator = "\n";
 private String colSeperator = "|";
 private Map<String, SearchTypeInfo> searchInfoTypeMap;
// private Map<String, SearchCriteriaTypeInfo> searchCriteriaTypeMap;
// private Map<String, SearchResultTypeInfo> searchResultTypeInfoMap;
 private Map<String, String> queryMap;

 public SearchConfigFormatter (Map<String, SearchTypeInfo> searchInfoTypeMap,
                               Map<String, String> queryMap)
 {
  this.searchInfoTypeMap = searchInfoTypeMap;
  this.queryMap = queryMap;
 }

 public String getRowSeperator ()
 {
  return rowSeperator;
 }

 public void setRowSeperator (String rowSeperator)
 {
  this.rowSeperator = rowSeperator;
 }

 public String getColSeparator ()
 {
  return colSeperator;
 }

 public void setColSeparator (String separator)
 {
  this.colSeperator = separator;
 }

 private String pad (String str, int size)
 {
  StringBuilder padStr = new StringBuilder (size);
  padStr.append (str);
  while (padStr.length () < size)
  {
   padStr.append (' ');
  }
  return padStr.toString ();
 }

 public String formatForWiki ()
 {
  for (String name : this.searchInfoTypeMap.keySet ())
  {
   formatSearchType (name, this.searchInfoTypeMap.get (name));
  }

  return builder.toString ();
 }

 private void formatSearchType (String name, SearchTypeInfo st)
 {
  builder.append (rowSeperator);
  builder.append ("h1. " + st.getName () + " (" + name + ")");
  builder.append ("{anchor:" + name + "}");
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append ("*Search Type Key*");
  builder.append (colSeperator);
  builder.append (st.getKey ());
  builder.append (colSeperator);
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append ("*Name*");
  builder.append (colSeperator);
  builder.append (st.getName ());
  builder.append (colSeperator);
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append ("*Description*");
  builder.append (colSeperator);
  builder.append (st.getDesc ());
  builder.append (colSeperator);
  builder.append (rowSeperator);

  builder.append (rowSeperator);
  builder.append ("h2. "
                  + st.getSearchCriteriaTypeInfo ().getQueryParams ().size ()
                  + " Possible Search Criteria");
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append ("*Type Key*");
  builder.append (colSeperator);
  builder.append (st.getSearchCriteriaTypeInfo ().getKey ());
  builder.append (colSeperator);
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append ("*Name*");
  builder.append (colSeperator);
  builder.append (st.getSearchCriteriaTypeInfo ().getName ());
  builder.append (colSeperator);
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append ("*Description*");
  builder.append (colSeperator);
  builder.append (st.getSearchCriteriaTypeInfo ().getDesc ());
  builder.append (colSeperator);
  builder.append (rowSeperator);

  // criteria table
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Name");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Optional");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("DataType");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Read Only");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Type Key");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Implementation");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append (rowSeperator);
  for (QueryParamInfo qp : st.getSearchCriteriaTypeInfo ().getQueryParams ())
  {
   builder.append (colSeperator);
   builder.append (qp.getFieldDescriptor ().getName ());
   builder.append (colSeperator);
   builder.append (qp.isOptional ());
   builder.append (colSeperator);
   builder.append (qp.getFieldDescriptor ().getDataType ());
   builder.append (colSeperator);
   builder.append (qp.getFieldDescriptor ().isReadOnly ());
   builder.append (colSeperator);
   builder.append (qp.getKey ());
   builder.append (colSeperator);
   builder.append ("{code:borderStyle=none}" + queryMap.get (qp.getKey ()) + "{code}");
   builder.append (colSeperator);
   builder.append (rowSeperator);
  }


  builder.append (rowSeperator);
  builder.append ("h2. "
                  + st.getSearchResultTypeInfo ().getResultColumns ().size ()
                  + " Result Columns Returned");
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append ("*Type Key*");
  builder.append (colSeperator);
  builder.append (st.getSearchResultTypeInfo ().getKey ());
  builder.append (colSeperator);
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append ("*Name*");
  builder.append (colSeperator);
  builder.append (st.getSearchResultTypeInfo ().getName ());
  builder.append (colSeperator);
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append ("*Description*");
  builder.append (colSeperator);
  builder.append (st.getSearchResultTypeInfo ().getDesc ());
  builder.append (colSeperator);
  builder.append (rowSeperator);

  // results table
  builder.append (rowSeperator);
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Name");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("DataType");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append ("Type Key");
  builder.append (colSeperator);
  builder.append (colSeperator);
  builder.append (rowSeperator);
  for (ResultColumnInfo rc : st.getSearchResultTypeInfo ().getResultColumns ())
  {
   builder.append (colSeperator);
   builder.append (rc.getName ());
   builder.append (colSeperator);
   builder.append (rc.getDataType ());
   builder.append (colSeperator);
   builder.append (rc.getKey ());
   builder.append (colSeperator);
   builder.append (rowSeperator);
  }

  // impl
  builder.append (rowSeperator);
  builder.append ("h2. JPQL Implementation");
  builder.append (rowSeperator);
  builder.append ("{code}");
  builder.append (rowSeperator);
  builder.append (queryMap.get (st.getKey ()));
  builder.append (rowSeperator);
  builder.append ("{code}");
  builder.append (rowSeperator);
 }

 private String escapeWiki(String str) {
		StringBuilder bldr = new StringBuilder(str.length());
		boolean precededByBackSlash = false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
			case '\\':
			case '[':
			case '*':
			case ']':
			case '|':
				if (!precededByBackSlash) {
					bldr.append('\\');
				}
				break;
			default:
				break;
			}
			bldr.append(c);
			if (c == '\\') {
				precededByBackSlash = true;
			} else {
				precededByBackSlash = false;
			}
		}
		return bldr.toString();
	}

}
