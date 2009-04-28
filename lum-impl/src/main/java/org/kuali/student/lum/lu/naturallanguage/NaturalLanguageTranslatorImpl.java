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

	/**
	 * Sets the LU DAO.
	 * 
	 * @param luDao LU DAO
	 */
	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
		this.reqComponentTranslator.setLuDao(this.luDao);
		this.statementTranslator.setLuDao(this.luDao);
	}

	/**
	 * Translates a requirement component for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param reqComponentId Requirement component to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language requirement translation
	 * @throws DoesNotExistException Requirement component id does not exists
	 * @throws OperationFailedException
	 */
	public String translateReqComponent(String reqComponentId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return this.reqComponentTranslator.translate(reqComponentId, nlUsageTypeKey);
	}
	
	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param cluId Anchor CLU id
	 * @param statementId Statement id
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU id does not exists
	 * @throws OperationFailedException
	 */
	public String translateStatement(String cluId, String statementId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return statementTranslator.translate(statementId, nlUsageTypeKey);
	}
}
