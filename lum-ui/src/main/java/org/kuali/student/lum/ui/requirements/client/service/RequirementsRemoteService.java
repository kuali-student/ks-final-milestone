package org.kuali.student.lum.ui.requirements.client.service;

import java.util.List;

import org.kuali.student.core.search.dto.Result;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.model.CourseRuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

/**
 * @author Zdenek Zraly
 * This class lists all of the methods that the remote calls between client and servlet make, 
 * most of these will be verbatim from the web service  
 *
 */
//TODO how do we do exceptions
public interface RequirementsRemoteService {
    public String getNaturalLanguageForReqComponentInfo(ReqComponentInfo compInfo, String nlUsageTypeKey) throws Exception;
    public String getNaturalLanguageForLuStatementInfo(String cluId, LuStatementInfo luStatementInfo, String nlUsageTypeKey) throws Exception;  
    public CourseRuleInfo getCourseAndRulesInfo(String cluId) throws Exception;
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception;
    public List<Result> getAllClus() throws Exception;
    public LuStatementInfo getLuStatementForCluAndStatementType(String cluId, String luStatementTypeKey) throws Exception;
    public String getRuleRationale(String cluId, String luStatementTypeKey) throws Exception;
    public StatementVO getStatementVO(String cluId, String luStatementTypeKey) throws Exception;
}
