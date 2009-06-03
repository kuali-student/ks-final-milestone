package org.kuali.student.lum.ui.requirements.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.search.dto.Result;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.model.CourseRuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Zdenek Zraly
 */
public class RequirementsServiceGwt extends RemoteServiceServlet implements RequirementsRpcService {

    private static final long serialVersionUID = 822326113643828855L;

    private RequirementsRpcService serviceImpl;  
    
    public RequirementsRpcService getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(RequirementsRpcService serviceImpl) {
    	this.serviceImpl = serviceImpl;
    }
    
    public String[] getReqComponentTemplates(ReqComponentInfo compInfo) throws Exception {
        return serviceImpl.getReqComponentTemplates(compInfo);
    }
    
    public String getNaturalLanguageForReqComponentInfo(ReqComponentInfo compInfo, String nlUsageTypeKey) throws Exception {
        return serviceImpl.getNaturalLanguageForReqComponentInfo(compInfo, nlUsageTypeKey);
    }
    
    public String getNaturalLanguageForStatementVO(String cluId, StatementVO statementVO, String nlUsageTypeKey) throws Exception {
        return serviceImpl.getNaturalLanguageForStatementVO(cluId, statementVO, nlUsageTypeKey);
    }
    
    public CourseRuleInfo getCourseAndRulesInfo(String cluId) throws Exception {
        return serviceImpl.getCourseAndRulesInfo(cluId);
    }     
    
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception {
        return serviceImpl.getReqComponentTypesForLuStatementType(luStatementTypeKey);
    }
    
    public Map<String, String> getAllClus() throws Exception {
        return serviceImpl.getAllClus();
    }
    
    public Map<String, String> getAllClusets() throws Exception {
        return serviceImpl.getAllClusets();
    }    
    
    public LuStatementInfo getLuStatementForCluAndStatementType(String cluId, String luStatementTypeKey)  throws Exception {
        return serviceImpl.getLuStatementForCluAndStatementType(cluId, luStatementTypeKey);
    }    
    
    public String getRuleRationale(String cluId, String luStatementTypeKey) throws Exception { 
        return serviceImpl.getRuleRationale(cluId, luStatementTypeKey);
    }
    
    public StatementVO getStatementVO(String cluId, String luStatementTypeKey) throws Exception {
        return serviceImpl.getStatementVO(cluId, luStatementTypeKey);
    }

}
