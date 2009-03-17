package org.kuali.student.lum.ui.requirements.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

/**
 * @author Zdenek Zraly
 */
public class RequirementsServiceImpl implements RequirementsService {

	LuService luService;

    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception {
                
        List<ReqComponentTypeInfo> reqComponentTypeInfoList = new ArrayList<ReqComponentTypeInfo>();
        try {
            /*
            List<LuStatementTypeInfo> stmtTypes = luService.getLuStatementTypes();
            System.out.println("printing statement types...");
            for (LuStatementTypeInfo stmtType : stmtTypes) {                   
                System.out.println("type: " + stmtType.getName());
            } */           
           System.out.println("In service...."); 
            
           reqComponentTypeInfoList = luService.getReqComponentTypesForLuStatementType(luStatementTypeKey);
           //reqComponentTypeInfoList = this.getReqComponentTypesForLuStatementTypeSTUB(luStatementTypeKey);
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to find Requirement Component Types based on LU Statement Type Key:" + luStatementTypeKey, ex);
        }
        
        return reqComponentTypeInfoList;
    }  
    
    
    /******************************************************************************************************************
     * 
     *                                                     GETTERS & SETTERS 
     *
     *******************************************************************************************************************/         
    

    public LuService getLuService() {
        return luService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
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