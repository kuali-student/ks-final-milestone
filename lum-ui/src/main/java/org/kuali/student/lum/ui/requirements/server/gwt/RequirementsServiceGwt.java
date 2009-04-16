package org.kuali.student.lum.ui.requirements.server.gwt;

import java.util.List;

import org.kuali.student.core.search.dto.Result;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.CourseRuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Zdenek Zraly
 */
public class RequirementsServiceGwt extends RemoteServiceServlet implements RequirementsService {

    private static final long serialVersionUID = 822326113643828855L;

    private RequirementsService serviceImpl;  
    
    public RequirementsService getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(RequirementsService serviceImpl) {
    	this.serviceImpl = serviceImpl;
    }
    
    public String getNaturalLanguageForReqComponent(String reqComponentId, String nlUsageTypeKey) throws Exception {
        return serviceImpl.getNaturalLanguageForReqComponent(reqComponentId, nlUsageTypeKey);
    }
    
    public CourseRuleInfo getCourseInfo(String cluId) throws Exception {
        return serviceImpl.getCourseInfo(cluId);
    }     
    
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception {
        return serviceImpl.getReqComponentTypesForLuStatementType(luStatementTypeKey);
    }
    
    public List<Result> getAllClus() throws Exception {
        return serviceImpl.getAllClus();
    }
    
    public LuStatementInfo getLuStatementForCluAndStatementType(String cluId, String luStatementTypeKey)  throws Exception {
        return serviceImpl.getLuStatementForCluAndStatementType(cluId, luStatementTypeKey);
    }    
    
    public String[] getNaturalLanguage(String cluId, String luStatementTypeKey) throws Exception {
        return serviceImpl.getNaturalLanguage(cluId, luStatementTypeKey);
    }
    
    public String getRuleRationale(String cluId, String luStatementTypeKey) throws Exception { 
        return serviceImpl.getRuleRationale(cluId, luStatementTypeKey);
    }
    
    public StatementVO getStatementVO(String cluId, String luStatementTypeKey) throws Exception {
        return serviceImpl.getStatementVO(cluId, luStatementTypeKey);
    }

}
