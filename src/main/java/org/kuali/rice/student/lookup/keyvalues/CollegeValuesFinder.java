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
public class CollegeValuesFinder extends KeyValuesBase {

	public List<KeyLabelPair> getKeyValues() {
		List<KeyLabelPair> colleges = new ArrayList<KeyLabelPair>();

	   	OrganizationService orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization","OrganizationService"));
	   		   	
	   	String searchTypeKey="org.search.orgQuickViewByOrgType";
	   	
	   	List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
	   	QueryParamValue qpOrgType = new QueryParamValue();
	   	qpOrgType.setKey("org.queryParam.orgType");
	   	qpOrgType.setValue("kuali.org.College");
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
				//colleges.add(new KeyLabelPair(orgId, orgShortName));
				colleges.add(new KeyLabelPair(orgShortName, orgShortName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return colleges;
	}

}
