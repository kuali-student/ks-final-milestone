package org.kuali.student.lum.lu.naturallanguage;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dao.LuDao;

public class NaturalLanguageTranslatorImpl implements NaturalLanguageTranslator {
	private LuDao luDao;
	private ReqComponentTranslator reqComponentTranslator = new ReqComponentTranslator();
	private StatementTranslator statementTranslator = new StatementTranslator();
	
	public NaturalLanguageTranslatorImpl() {
	}

	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
		this.reqComponentTranslator.setLuDao(this.luDao);
		this.statementTranslator.setLuDao(this.luDao);
	}

	public String translateReqComponent(String reqComponentId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return this.reqComponentTranslator.translate(reqComponentId, nlUsageTypeKey);
	}
	
	public String translateStatement(String statementId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return statementTranslator.translate(statementId, nlUsageTypeKey);
	}
}
