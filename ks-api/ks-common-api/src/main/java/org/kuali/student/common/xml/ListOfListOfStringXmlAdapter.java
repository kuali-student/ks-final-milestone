/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

/**
 * @author Kuali Student Team
 *
 */
public class ListOfListOfStringXmlAdapter extends XmlAdapter<String, List<List<String>>> {

	/**
	 * 
	 */
	public ListOfListOfStringXmlAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String marshal(List<List<String>> v) throws Exception {
		
		StringBuilder builder = new StringBuilder();

		boolean first = true;
		
		for (List<String> list : v) {
		
			if (!first)
				builder.append(":");
			else
				first = false;
			
			builder.append(StringUtils.join(list.iterator(), ";"));
		}
		
		return builder.toString();
	}

	@Override
	public List<List<String>> unmarshal(String v) throws Exception {

		List<List<String>>dataList = new ArrayList<List<String>>();
		
		String[]outerParts = StringUtils.split(v, ":");
		
		for (String outerPart : outerParts) {
			
			String[]innerParts = StringUtils.split(outerPart, ";");
			
			List<String>innerList = new ArrayList<String> (innerParts.length);
			
			for (String parts : innerParts) {
				
				innerList.add(parts);

			
			}
		
			
			dataList.add(innerList);
		}
		
		
		return dataList;
	}

	

}
