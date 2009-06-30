/**
 * 
 */
package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.web.ui.KeyLabelPair;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;

/**
 * @author delyea
 *
 */
public class DepartmentValuesFinder extends KeyValuesBase {

	public List<KeyLabelPair> getKeyValues() {
		List<KeyLabelPair> departments = new ArrayList<KeyLabelPair>();

	   	OrganizationService orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://org.kuali.student/core/organization","OrganizationService"));
	   		   	
	   	String searchTypeKey="org.search.orgQuickViewByOrgType";
	   	
	   	List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
	   	QueryParamValue qpOrgType = new QueryParamValue();
	   	qpOrgType.setKey("org.queryParam.orgType");
	   	qpOrgType.setValue("kuali.org.Department");
	   	queryParamValues.add(qpOrgType);
	   	
	   	try {
			List<Result> results = orgService.searchForResults(searchTypeKey, queryParamValues);
			for(Result result:results){
				String orgId="";
				String orgShortName="";
				for(ResultCell resultCell:result.getResultCells()){
					if("org.resultColumn.orgId".equals(resultCell.getKey())){
						orgId=resultCell.getValue();
					}else if("org.resultColumn.orgShortName".equals(resultCell.getKey())){
						orgShortName=resultCell.getValue();
					}
				}
				//departments.add(new KeyLabelPair(orgId, orgShortName));
				departments.add(new KeyLabelPair(orgShortName, orgShortName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return departments;
	}

}
