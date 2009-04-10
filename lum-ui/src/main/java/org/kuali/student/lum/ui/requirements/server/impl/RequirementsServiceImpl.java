package org.kuali.student.lum.ui.requirements.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldTypeInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.ui.requirements.client.model.CourseRuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

/**
 * @author Zdenek Zraly
 */
public class RequirementsServiceImpl implements RequirementsService {

	LuService service;

	public CourseRuleInfo getCourseInfo(String cluId) throws Exception {
	  
        CourseRuleInfo courseInfo = new CourseRuleInfo();
        
        //retrieve course info
        CluInfo cluInfo;
        try {        
            cluInfo = service.getClu(cluId);                   
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to retrieve cluInfo for clu " + cluId, ex);
        }                
        courseInfo.setCourseInfo(cluInfo);	  
        courseInfo.setId(cluInfo.getId());
	    
	    //retrieve all statements associated with given course (we could retrieve only pre and co-req ?)
        List<LuStatementInfo> luStatementInfoList;
        try {        
            luStatementInfoList = service.getLuStatementsForClu(cluId);            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to retrieve LuStatements for clu " + cluId, ex);
        }
	    courseInfo.setLuStatementInfoList(luStatementInfoList); 
	    
	    return courseInfo;
	}
	
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception {
                
        List<ReqComponentTypeInfo> reqComponentTypeInfoList;
        try {
/*
            List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>(0);
            QueryParamValue cluId = new QueryParamValue();
            cluId.setKey("queryParam.CD");
            cluId.setValue("Code");
            queryParamValues.add(cluId);            
            List<Result> clus = service.searchForResults("lu.search.clus", queryParamValues);
            
            for (Result clu : clus) {
                System.out.println("CLU: " + clu.getResultCells().get(0).getValue());
            }
  */          
            reqComponentTypeInfoList = service.getReqComponentTypesForLuStatementType(luStatementTypeKey);           
           //reqComponentTypeInfoList = this.getReqComponentTypesForLuStatementTypeSTUB(luStatementTypeKey);                        
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to find Requirement Component Types based on LU Statement Type Key:" + luStatementTypeKey, ex);
        }
        
        return reqComponentTypeInfoList;
    }  
    
    public List<Result> getAllClus() throws Exception {
        
        List<Result> clus;
        try {
            List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>(0);           
            clus = service.searchForResults("lu.search.clus", queryParamValues);                         
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to retrieve Clus", ex);
        }
        
        return clus;
    }      
    
    //retrieve statement based on CLU ID and STATEMENT TYPE
    public LuStatementInfo getLuStatementForCluAndStatementType(String cluId, String luStatementTypeKey) throws Exception {
        
        try {
            List<LuStatementInfo> stmtInfoList = service.getLuStatementsForClu(cluId);
            
            for (LuStatementInfo statementInfo : stmtInfoList) {
                if (statementInfo.getType().equals(luStatementTypeKey)) {
                    return statementInfo;
                }
            }
            
            System.out.println("Did not find LuStatementInfo based on cluid: " + cluId + " and luStatementTypeKey: " + luStatementTypeKey);                                       
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to find Lu Statement based on CLU id:" + cluId + " and Statement type key: " + luStatementTypeKey, ex);
        }
        
        return null;
    }
    
    private String composeStatementVO(LuStatementInfo luStatementInfo, StatementVO statementVO) throws Exception {
        
        List<String> statementIDs = luStatementInfo.getLuStatementIds();       
        List<String> reqComponentIDs = luStatementInfo.getReqComponentIds();
        
        if ((statementIDs != null) && (reqComponentIDs != null) && (statementIDs.size() > 0) && (reqComponentIDs.size() > 0))
        {
            return "Internal error: found both Statements and Requirement Components on the same level of boolean expression";
        }
        
        if ((statementIDs != null) && (statementIDs.size() > 0)) {
            //retrieve all statements
            for (String stmtID : statementIDs) {
                LuStatementInfo tempStmtInfo;                    
                try {
                    tempStmtInfo = service.getLuStatement(stmtID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("Unable to retrieve Lu Statemetn based on luStatementID: " + stmtID, ex);
                }
                StatementVO tempStmtVO = new StatementVO(tempStmtInfo);
                composeStatementVO(tempStmtInfo, tempStmtVO);
                statementVO.addStatementVO(tempStmtVO);
            }            
        } else {
            //retrieve all req. component LEAFS
            for (String reqCompID : reqComponentIDs) {
                    
                ReqComponentInfo tempReqCompInfo;
                try {
                    tempReqCompInfo = service.getReqComponent(reqCompID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("Unable to retrieve Lu Statemetn based on reqComponentID: " + reqCompID, ex);
                }
                
                ReqComponentVO tempReqCompVO = new ReqComponentVO(tempReqCompInfo);
                
                try {
                    tempReqCompVO.setTypeDesc(service.getReqComponentType(tempReqCompInfo.getType()).getDesc());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("Unable to retrieve Lu Statemetn based on reqComponentID: " + reqCompID, ex);
                }                                

                statementVO.addReqComponentVO(tempReqCompVO);
            }               
        }        
        
        return "";
    }
    
    public StatementVO getStatementVO(String cluId, String luStatementTypeKey) throws Exception {
        
        LuStatementInfo luStatementInfo = getLuStatementForCluAndStatementType(cluId, luStatementTypeKey);        
        StatementVO rootStatementVO = new StatementVO(luStatementInfo);
        String error = composeStatementVO(luStatementInfo, rootStatementVO);
        if (error.isEmpty() == false) {
            throw new Exception(error + "cluId: " + cluId + ", luStatementTypeKey: " + luStatementTypeKey);            
        }       
        
        return rootStatementVO;
        
        /*
        StatementVO statementVO = null;
        StatementVO statementVO1 = null;
        StatementVO statementVO2 = null;
        ReqComponentVO reqComponentVO1 = null;
        ReqComponentVO reqComponentVO2 = null;
        ReqComponentVO reqComponentVO3 = null;

        // stub starts
        // S0
        List<String> s0luStatementIds = new ArrayList<String>();
        s0luStatementIds.add("S1");
        s0luStatementIds.add("S2");

        LuStatementInfo luStatementInfo = new LuStatementInfo();
        luStatementInfo.setDesc("Root statement");
        luStatementInfo.setId("S0");
        luStatementInfo.setLuStatementIds(s0luStatementIds);
        luStatementInfo.setOperator(StatementOperatorTypeKey.AND);

        // S1
        List<String> s1luReqComponentIds = new ArrayList<String>();
        s1luReqComponentIds.add("RC1");
        s1luReqComponentIds.add("RC2");

        LuStatementInfo luStatementInfo1 = new LuStatementInfo();
        luStatementInfo1.setDesc("Statement 1");
        luStatementInfo1.setId("S1");
        luStatementInfo1.setReqComponentIds(s1luReqComponentIds);
        luStatementInfo1.setOperator(StatementOperatorTypeKey.OR);        

        // S2
        List<String> s2luReqComponentIds = new ArrayList<String>();
        s1luReqComponentIds.add("RC3");

        LuStatementInfo luStatementInfo2 = new LuStatementInfo();
        luStatementInfo2.setDesc("Statement 2");
        luStatementInfo2.setId("S2");
        luStatementInfo2.setReqComponentIds(s2luReqComponentIds);

        // RC1
        ReqComponentInfo reqComponentInfo1 = new ReqComponentInfo();
        reqComponentInfo1.setDesc("Requirement Component 1");
        reqComponentInfo1.setId("RC1");
        reqComponentInfo1.setType("PreReq");

        // RC2
        ReqComponentInfo reqComponentInfo2 = new ReqComponentInfo();
        reqComponentInfo2.setDesc("Requirement Component 2");
        reqComponentInfo2.setId("RC2");
        reqComponentInfo2.setType("PreReq");
        
        // RC3
        ReqComponentInfo reqComponentInfo3 = new ReqComponentInfo();
        reqComponentInfo3.setDesc("Requirement Component 3");
        reqComponentInfo3.setId("RC3");
        reqComponentInfo3.setType("PreReq");
        
        // tie it all together
        statementVO = new StatementVO(luStatementInfo);
        statementVO1 = new StatementVO(luStatementInfo1);
        statementVO2 = new StatementVO(luStatementInfo2);
        reqComponentVO1 = new ReqComponentVO(reqComponentInfo1);
        reqComponentVO2 = new ReqComponentVO(reqComponentInfo2);
        reqComponentVO3 = new ReqComponentVO(reqComponentInfo3);
        statementVO.addStatementVO(statementVO1);
        statementVO.addStatementVO(statementVO2);
        statementVO1.addReqComponentVO(reqComponentVO1);
        statementVO1.addReqComponentVO(reqComponentVO2);
        statementVO2.addReqComponentVO(reqComponentVO3);
        // stub ends
        return statementVO; */
    }

    public String[] getNaturalLanguage(String cluId, String luStatementTypeKey) throws Exception {
               
        String[] naturalLanguage = {"Course Catalog", "EDUC 100", "Department Brochure", "Student must have completed EDUC 100 \"Introduction to Education" +
                " and Diversity in Schools\""};
        return naturalLanguage;
    }     
    
    public String getRuleRationale(String cluId, String luStatementTypeKey) throws Exception {        
        return "To be prepared for this course, students must have in-depth knowledge of the topics from at least on introductory education course.";
    }        
        
    
    /******************************************************************************************************************
     * 
     *                                                     GETTERS & SETTERS 
     *
     *******************************************************************************************************************/         
        
    public LuService getService() {
        return service;
    }

    public void setService(LuService service) {
        this.service = service;
    } 
	
	
    /******************************************************************************************************************
     * 
     *                                                     STUBS OF LU SERVICE
     *
     *******************************************************************************************************************/             
	
	List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementTypeSTUB(String luStatementTypeKey) {
	    List<ReqComponentTypeInfo> reqComponentTypeInfoList = new ArrayList<ReqComponentTypeInfo>();
	    ReqComponentTypeInfo reqInfo = new ReqComponentTypeInfo();
	    reqInfo.setName("Courses completed");
	    reqInfo.setDesc("This is a description and example...1");
	    reqComponentTypeInfoList.add(reqInfo);
        ReqComponentTypeInfo reqInfo1 = new ReqComponentTypeInfo();
        reqInfo1.setName("Minimum GPA for courses");
        reqInfo1.setDesc("Student must have a minimum GPA for all of <Courses>");
        reqComponentTypeInfoList.add(reqInfo1);
        ReqComponentTypeInfo reqInfo2 = new ReqComponentTypeInfo();
        reqInfo2.setName("Credits completed in courses");
        reqInfo2.setDesc("This is a description and example...3");
        reqComponentTypeInfoList.add(reqInfo2);        
        reqInfo2.setName("Minimum GPA");
        reqInfo2.setDesc("This is a description and example...4");
        reqComponentTypeInfoList.add(reqInfo2);       
        reqInfo2.setName("Credits completed from CLU sets");
        reqInfo2.setDesc("This is a description and example...5");
        reqComponentTypeInfoList.add(reqInfo2);               
        return reqComponentTypeInfoList;
	}
}