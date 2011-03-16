package org.kuali.student.lum.lu.ui.dependency.server;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.lu.ui.dependency.client.service.DependencyAnalysisRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DependencyAnalysisRpcServlet extends RemoteServiceServlet implements DependencyAnalysisRpcService{

	private static final long serialVersionUID = 1L;
	
	StatementService statementService;	

	@Override
	public String getRequirementComponentNL(String reqComponentId) {		
		return null;
	}

	@Override
	public List<String> getRequirmentComponentNL(List<String> reqComponentIds) throws Exception{		
		List<String> reqComponents = new ArrayList<String>();
		for (String reqComponentId:reqComponentIds){			
			String reqCompNL = statementService.getNaturalLanguageForReqComponent(reqComponentId, "KUALI.RULE", "en");
			reqComponents.add(reqCompNL);
		}
		
		return reqComponents;
	}

	public StatementService getStatementService() {
		return statementService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

}
