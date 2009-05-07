/**
 * 
 */
package org.kuali.student.lum.ui.requirements.client.service;

import java.util.List;

import org.kuali.student.core.search.dto.Result;
import org.kuali.student.lum.lu.dto.LuNlStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.model.CourseRuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author zzraly
 */
public interface RequirementsServiceAsync {
    public void getNaturalLanguageForReqComponentInfo(ReqComponentInfo compInfo, String nlUsageTypeKey, AsyncCallback<String> asyncCallback);
    public void getNaturalLanguageForLuStatementInfo(String cluId, LuNlStatementInfo luStatementInfo, String nlUsageTypeKey, AsyncCallback<String> asyncCallback);
    public void getCourseAndRulesInfo(String cluId, AsyncCallback<CourseRuleInfo> asyncCallback);
    public void getReqComponentTypesForLuStatementType(String luStatementTypeKey, AsyncCallback<List<ReqComponentTypeInfo>> asyncCallback);
    public void getAllClus(AsyncCallback<List<Result>> asyncCallback);  
    public void getLuStatementForCluAndStatementType(String cluId, String luStatementTypeKey, AsyncCallback<LuStatementInfo> asyncCallback); 
    public void getRuleRationale(String cluId, String luStatementTypeKey, AsyncCallback<String> asyncCallback);
    public void getStatementVO(String cluId, String luStatementTypeKey, AsyncCallback<StatementVO> asyncCallback);
}
