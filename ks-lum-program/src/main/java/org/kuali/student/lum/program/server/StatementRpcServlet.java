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

import java.util.List;

import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.dto.StatementTypeInfo;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;

public class StatementRpcServlet extends BaseRpcGwtServletAbstract<CluService> implements StatementRpcService {

    private static final long serialVersionUID = 1L;
    private StatementRpcService statmentDataService;

    public void setStatmentDataService(StatementRpcService statmentDataService) {

        this.statmentDataService = statmentDataService;
    }

    @Override
    public List<StatementTypeInfo> getStatementTypesForStatementTypeForCourse(
            String statementTypeKey) throws Exception {
        try
        {
            return statmentDataService
                    .getStatementTypesForStatementTypeForCourse(statementTypeKey);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<StatementTypeInfo> getStatementTypesForStatementType(
            String statementTypeKey) throws Exception {
        try
        {
            return statmentDataService
                    .getStatementTypesForStatementType(statementTypeKey);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(
            String luStatementTypeKey) throws Exception {
        try
        {
            return statmentDataService
                    .getReqComponentTypesForStatementType(luStatementTypeKey);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String translateReqComponentToNL(ReqComponentInfo reqComponentInfo,
            String nlUsageTypeKey, String language) throws Exception {
        try
        {
            return statmentDataService.translateReqComponentToNL(reqComponentInfo,
                    nlUsageTypeKey, language);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String translateStatementTreeViewToNL(
            StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey,
            String language) throws Exception {
        try
        {
            return statmentDataService.translateStatementTreeViewToNL(
                    statementTreeViewInfo, nlUsageTypeKey, language);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> translateReqComponentToNLs(
            ReqComponentInfoUi reqComponentInfo, String[] nlUsageTypeKeys,
            String language) throws Exception {
        try
        {
            return statmentDataService.translateReqComponentToNLs(reqComponentInfo,
                    nlUsageTypeKeys, language);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public CluInfo getClu(String cluId) throws Exception {
        try
        {
            return statmentDataService.getClu(cluId);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI,
            String refObjectId) throws Exception {
        try
        {
            return statmentDataService.getCurrentVersion(refObjectTypeURI,
                    refObjectId);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
