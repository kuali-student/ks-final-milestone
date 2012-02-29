/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.statement.config.context.util;

import java.util.List;

import org.kuali.student.common.search.dto.SearchParam;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;

/**
 * <p><b><u>Warning</u></b><br/>
 * DO NOT change the public method signatures of this class.<br/>
 * The natural language templates are coded against this class's public methods.
 * If the method signatures are changed then all the templates referencing 
 * this class will need to be changed as well.</p>
 * 
 * This class is inserted into the template engine to get Clu and CluSet 
 * information. <code>$cluSet</code> is this class.
 * <p>
 * Example:
 * <code>"Student must have completed $intValue of $cluSet.getCluSetAsShortName()"</code>
 * </p>
 * 
 * {@link MockCluSetInfo} wrapper class.
 */
public class NLCluSet {

	private String cluSetId;
	private List<CluInfo> cluList;
	private CluSetInfo cluSet;
	
	public NLCluSet(String cluSetId, List<CluInfo> cluList) {
		this.cluSetId = cluSetId;
		this.cluList = cluList;
	}
	
	public NLCluSet(String cluSetId, List<CluInfo> cluList, CluSetInfo cluSet) {
		this.cluSetId = cluSetId;
		this.cluList = cluList;
		this.cluSet = cluSet;
	}
	
	/**
	 * Gets the CLU set id.
	 * 
	 * @return Clu set id
	 */
	public String getCluSetId() {
		return cluSetId;
	}

	/**
	 * Gets a list of CLUs.
	 *  
	 * @return List of CLUs
	 */
	public List<CluInfo> getCluList() {
		return this.cluList;
	}

	/**
	 * Gets a particular CLU's official identifier short name.
	 * 
	 * @param index Index in CLU set
	 * @return CLU official identifier short name
	 */
	public String getCluAsShortName(int index) {
		return this.cluList.get(index).getOfficialIdentifier().getShortName();
	}

	/**
	 * Gets a particular CLU's official identifier code at <code>index</code>
	 * @param index
	 * @return CLU's official identifier code
	 */
	public String getCluAsCode(int index) {
		return this.cluList.get(index).getOfficialIdentifier().getCode();
	}

	/**
	 * Gets all the CLUs' official identifier short name in the CLU set 
	 * as a comma separated list.
	 *   
	 * @return Comma separated list of CLUs' official identifier short name 
	 */
	public String getCluSetAsShortName() {
		return getCluSetAsShortName(",");
	}
	
	/**
     * Gets all the CLUs' official identifier short name in the CLU set 
     * as a list of values separated by the specified separator.
     * 
     * @param The string value that is used to separate the values in the list.
     * @return Character separated list of CLUs' official identifier short name 
     */
	public String getCluSetAsShortName(String separator) {
        StringBuilder sb = new StringBuilder();
        if (this.cluList.size() > 1) {
            sb.append("(");
        }
        for(CluInfo clu : this.cluList) {
            sb.append(clu.getOfficialIdentifier().getShortName());
            if (this.cluList.indexOf(clu) < (this.cluList.size() - 1)) {
                sb.append(separator + " ");
            }
        }
        if (this.cluList.size() > 1) {
            sb.append(")");
        }       
        return getString(sb);
    }

	/**
	 * Gets all the CLUs' official identifier long name in the CLU set 
	 * as a comma separated list.
	 * 
	 * @return Comma separated list of CLUs' official identifier long name 
	 */
	public String getCluSetAsLongName() {
		return getCluSetAsLongName(",");
	}
	
	/**
	 * Gets all the CLUs' official identifier long name in the CLU set 
     * as a list of values separated by the specified separator.
	 * 	  
	 * @param separator The string value that is used to separate the values in the list.
	 * @return Character separated list of CLUs' official identifier long name 
	 */
	public String getCluSetAsLongName(String separator) {
	    StringBuilder sb = new StringBuilder();
        if (this.cluList.size() > 1) {
            sb.append("(");
        }       
        for(CluInfo clu : this.cluList) {
            sb.append(clu.getOfficialIdentifier().getLongName());
            if (this.cluList.indexOf(clu) < (this.cluList.size() - 1)) {
                sb.append(separator + " ");
            }
        }
        if (this.cluList.size() > 1) {
            sb.append(")");
        }       
        return getString(sb);
	}

	/**
	 * Gets all the CLUs' official identifier code in the CLU set 
	 * as a comma separated list.
	 * 
	 * @return Comma separated list of CLUs' official identifier code 
	 */
	public String getCluSetAsCode() {
		return getCluSetAsCode(",");
	}
	
	/**
     * Gets all the CLUs' official identifier code in the CLU set 
     * as a list of values separated by the specified separator.
     * 
     * @param separator The string value that is used to separate the values in the list.
     * @return Character separated list of CLUs' official identifier code 
     */
    public String getCluSetAsCode(String separator) {
        StringBuilder sb = new StringBuilder();
        if (this.cluList.size() > 1) {
            sb.append("(");
        }       
        for(CluInfo clu : this.cluList) {
            sb.append(clu.getOfficialIdentifier().getCode());
            if (this.cluList.indexOf(clu) < (this.cluList.size() - 1)) {
                sb.append(separator + " ");
            }
        }
        if (this.cluList.size() > 1) {
            sb.append(")");
        }       
        return getString(sb);
    }

	private String getString(StringBuilder sb) {
		return (sb.length() == 0 ? "No CLUs available in CluSet" : sb.toString());
	}

	public String toString() {
		if(this.cluList == null) {
			return "Null CluSet";
		}
		return "id=" + this.cluSetId;
	}
	
	public String getQueryValueFromParam(String param) {
		String value = "";
		if (cluSet.getMembershipQuery() != null && !cluSet.getMembershipQuery().getQueryParamValueList().isEmpty()) 
			for (SearchParam searchParam : cluSet.getMembershipQuery().getQueryParamValueList()) 
				if (searchParam.getKey().equals(param)) 
					return (String)searchParam.getValue();
		return value;
	}	
}
