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

import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;

public class StatementRpcServlet extends BaseRpcGwtServletAbstract<LuService> implements StatementRpcService {

	private static final long serialVersionUID = 1L;
	private StatementRpcService statmentDataService;

	public void setStatmentDataService(StatementRpcService statmentDataService) {
		this.statmentDataService = statmentDataService;
	}

	@Override
	public List<StatementTypeInfo> getStatementTypesForStatementTypeForCourse(
			String statementTypeKey) throws Exception {
		return statmentDataService
				.getStatementTypesForStatementTypeForCourse(statementTypeKey);
	}

	@Override
	public List<StatementTypeInfo> getStatementTypesForStatementType(
			String statementTypeKey) throws Exception {
		return statmentDataService
				.getStatementTypesForStatementType(statementTypeKey);
	}

	@Override
	public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(
			String luStatementTypeKey) throws Exception {
		return statmentDataService
				.getReqComponentTypesForStatementType(luStatementTypeKey);
	}

	@Override
	public String translateReqComponentToNL(ReqComponentInfo reqComponentInfo,
			String nlUsageTypeKey, String language) throws Exception {
		return statmentDataService.translateReqComponentToNL(reqComponentInfo,
				nlUsageTypeKey, language);
	}

	@Override
	public String translateStatementTreeViewToNL(
			StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey,
			String language) throws Exception {
		return statmentDataService.translateStatementTreeViewToNL(
				statementTreeViewInfo, nlUsageTypeKey, language);
	}

	@Override
	public List<String> translateReqComponentToNLs(
			ReqComponentInfoUi reqComponentInfo, String[] nlUsageTypeKeys,
			String language) throws Exception {
		return statmentDataService.translateReqComponentToNLs(reqComponentInfo,
				nlUsageTypeKeys, language);
	}

	@Override
	public CluInfo getClu(String cluId) throws Exception {
		return statmentDataService.getClu(cluId);
	}

	@Override
	public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI,
			String refObjectId) throws Exception {
		return statmentDataService.getCurrentVersion(refObjectTypeURI,
				refObjectId);
	}

}
