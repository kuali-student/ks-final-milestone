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

package org.kuali.student.lum.program.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.apache.log4j.Logger;

public class StatementRpcServlet extends BaseRpcGwtServletAbstract<LuService> implements StatementRpcService {

    final static Logger LOG = Logger.getLogger(StatementRpcServlet.class);
    
    private StatementService statementService;
    private LuService luService;
    
    private static final long serialVersionUID = 822326113643828855L;

    public List<StatementTypeInfo> getStatementTypesForStatementTypeForCourse(String statementTypeKey) throws Exception {
    
        List<StatementTypeInfo> allStatementTypes = new ArrayList<StatementTypeInfo>();

        List<String> topStatementTypes = statementService.getStatementTypesForStatementType(statementTypeKey);

        // loop through top statement types like enrollment eligibility and credit constraints
        for (String topStatementType : topStatementTypes) {           
            allStatementTypes.add(statementService.getStatementType(topStatementType));
            List<String> subStatementTypeNames = statementService.getStatementTypesForStatementType(topStatementType);

            // loop through statement types belonging to the top statement types
            for (String subStatementTypeName : subStatementTypeNames) {
                allStatementTypes.add(statementService.getStatementType(subStatementTypeName));
            }
        }
        
        return allStatementTypes;
    }
    
    @Override
    public List<StatementTypeInfo> getStatementTypesForStatementType(String statementTypeKey) throws Exception {
        List<String> statementTypeNames = statementService.getStatementTypesForStatementType(statementTypeKey);
        List<StatementTypeInfo> statementTypes = new ArrayList<StatementTypeInfo>();
        for (String statementTypeName : statementTypeNames) {
            statementTypes.add(statementService.getStatementType(statementTypeName));
        }
        return statementTypes;
    }
    
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String luStatementTypeKey) throws Exception {

        List<ReqComponentTypeInfo> reqComponentTypeInfoList;
        try { 
            reqComponentTypeInfoList = statementService.getReqComponentTypesForStatementType(luStatementTypeKey);
        } catch (Exception ex) {
            LOG.error(ex);
            throw new Exception("Unable to find Requirement Component Types based on LU Statement Type Key:" + luStatementTypeKey, ex);
        }
        
        return reqComponentTypeInfoList;
    }

    @Override
    public String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language) throws Exception {
        return statementService.translateStatementTreeViewToNL(statementTreeViewInfo, nlUsageTypeKey, language);
    }

    @Override
    public String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language) throws Exception {
        return statementService.translateReqComponentToNL(reqComponentInfo, nlUsageTypeKey, language);
    }

    @Override
    public List<String> translateReqComponentToNLs(ReqComponentInfoUi reqComponentInfo, String[] nlUsageTypeKeys, String language) throws Exception {
    	List<String> nls = new ArrayList<String>(nlUsageTypeKeys.length);
    	for (String typeKey : nlUsageTypeKeys) {
    		nls.add(statementService.translateReqComponentToNL(reqComponentInfo, typeKey, language));
    	}
    	return nls;
    }

    @Override
    public CluInfo getClu(String cluId) throws Exception {
        return luService.getClu(cluId);
    }

    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId) throws Exception {
        return luService.getCurrentVersion(refObjectTypeURI, refObjectId);
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }
}
