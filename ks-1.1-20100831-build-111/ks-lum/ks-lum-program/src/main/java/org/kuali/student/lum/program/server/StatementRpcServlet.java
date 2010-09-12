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
import java.util.List;

import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.common.ui.client.widgets.rules.StatementVO;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;

import org.apache.log4j.Logger;

/**
 * @author Zdenek Zraly
 */
public class StatementRpcServlet extends BaseRpcGwtServletAbstract<LuService> implements StatementRpcService {

    final static Logger LOG = Logger.getLogger(StatementRpcServlet.class);
    
    private StatementService statementService;
    
    private static final long serialVersionUID = 822326113643828855L;

    @Override
    public List<StatementTypeInfo> getStatementTypesForStatementType(String statementTypeKey) throws Exception {
        List<String> statementTypeNames = statementService.getStatementTypesForStatementType(statementTypeKey);
        List<StatementTypeInfo> statementTypes = new ArrayList<StatementTypeInfo>();
        for (String statementTypeName : statementTypeNames) {
            statementTypes.add(statementService.getStatementType(statementTypeName));
        }
        return statementTypes;
    }

    //TODO do we need this?
    public String getNaturalLanguageForStatementVO(String cluId, StatementVO statementVO, String nlUsageTypeKey, String language) throws Exception {
        StatementTreeViewInfo statementTreeViewInfo = new StatementTreeViewInfo();
        
        // first translate StatementVO to StatementTreeViewInfo object
        String error = statementVO.composeStatementTreeViewInfo(statementVO, statementTreeViewInfo);
        if (error.isEmpty() == false) {
            throw new Exception(error + "cluId: " + cluId + ", usage: " + nlUsageTypeKey);
        }

        // cluId can't be empty
        if ((cluId != null) && cluId.isEmpty()) {
            cluId = null;
        }
        
        // then get natural language for the statement
        String nlStatement = "";
        try {
            nlStatement = statementService.translateStatementTreeViewToNL(statementTreeViewInfo, nlUsageTypeKey, language);
        } catch (Exception ex) {
            LOG.error(ex);
            throw new Exception("Unable to get natural language for clu: " + cluId + " and nlUsageTypeKey: " + nlUsageTypeKey);
        }
        
        return nlStatement;
    }
    
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String luStatementTypeKey) throws Exception {
                
        List<ReqComponentTypeInfo> reqComponentTypeInfoList = null;
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
          
    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }
}
