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

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StatementRpcServlet extends BaseRpcGwtServletAbstract<LuService> implements StatementRpcService {

    final static Logger LOG = Logger.getLogger(StatementRpcServlet.class);

    private StatementService statementService;

    private static final long serialVersionUID = 822326113643828855L;

    public List<StatementTypeInfo> getStatementTypesForStatementTypeForCourse(String statementTypeKey) throws Exception {


        String[] desiredSequenceEnrollmentElig = {"kuali.statement.type.course.academicReadiness.studentEligibilityPrereq",
                                                    "kuali.statement.type.course.academicReadiness.coreq",
                                                    "kuali.statement.type.course.recommendedPreparation",
                                                    "kuali.statement.type.course.academicReadiness.antireq"};

        String[] desiredSequenceCreditConstraints = {"kuali.statement.type.course.credit.restriction",
                                                        "kuali.statement.type.course.credit.repeatable"};

        List<StatementTypeInfo> statementTypesSorted = new ArrayList<StatementTypeInfo>();

        List<String> statementTypeNames = statementService.getStatementTypesForStatementType(statementTypeKey);

        for (String statementTypeName : statementTypeNames) {
            StatementTypeInfo stmtInfo = statementService.getStatementType(statementTypeName);

            statementTypesSorted.add(statementService.getStatementType(statementTypeName));

            //true if we found sub statement type
            List<String> subStmtInfos = stmtInfo.getAllowedStatementTypes();
            List<StatementTypeInfo> statementTypesOrig = new ArrayList<StatementTypeInfo>();
            if ((subStmtInfos != null) && !subStmtInfos.isEmpty()) {
                List<String> subStatementTypeNames = statementService.getStatementTypesForStatementType(statementTypeName);
                for (String subStatementTypeName : subStatementTypeNames) {
                    statementTypesOrig.add(statementService.getStatementType(subStatementTypeName));
                }
                if (statementTypeName.contains("kuali.statement.type.course.enrollmentEligibility")) {
                    for (String stmtType : desiredSequenceEnrollmentElig) {
                        Iterator<StatementTypeInfo> iter = statementTypesOrig.iterator();
                        while (iter.hasNext()) {
                            StatementTypeInfo stmtT = iter.next();
                            if (stmtT.getId().equals(stmtType)) {
                                statementTypesSorted.add(stmtT);
                                break;
                            }
                        }
                    }
                }
                if (statementTypeName.contains("kuali.statement.type.course.creditConstraints")) {
                    for (String stmtType : desiredSequenceCreditConstraints) {
                        Iterator<StatementTypeInfo> iter = statementTypesOrig.iterator();
                        while (iter.hasNext()) {
                            StatementTypeInfo stmtT = iter.next();
                            if (stmtT.getId().equals(stmtType)) {
                                statementTypesSorted.add(stmtT);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return statementTypesSorted;
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

    @Override
    public List<String> translateReqComponentToNLs(ReqComponentInfoUi reqComponentInfo, String[] nlUsageTypeKeys, String language) throws Exception {
    	List<String> nls = new ArrayList<String>(nlUsageTypeKeys.length);
    	for (String typeKey : nlUsageTypeKeys) {
    		nls.add(statementService.translateReqComponentToNL(reqComponentInfo, typeKey, language));
    	}
    	return nls;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }
}
