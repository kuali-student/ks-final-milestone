package org.kuali.student.lum.lu.naturallanguage.translators;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.naturallanguage.NaturalLanguageTranslator;

public class NaturalLanguageTranslatorImpl implements NaturalLanguageTranslator {
	private String language;
	private ReqComponentTranslator reqComponentTranslator;
	private StatementTranslator statementTranslator;
	
	public NaturalLanguageTranslatorImpl() {
	}

	public void setReqComponentTranslator(ReqComponentTranslator reqComponentTranslator) {
		this.reqComponentTranslator = reqComponentTranslator;
		setLanguage();
	}

	public void setStatementTranslator(StatementTranslator statementTranslator) {
		this.statementTranslator = statementTranslator;
		setLanguage();
	}
	
	/**
	 * Gets the translation language.
	 * 
	 * @return Language translation
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language to translate to.
	 * 
	 * @param language Language translation
	 */
	public void setLanguage(String language) {
		this.language = language;
		setLanguage();
	}
	
	private void setLanguage() {
		if(this.language != null) {
			if(this.reqComponentTranslator != null) {
				this.reqComponentTranslator.setLanguage(this.language);
			}
			if(this.statementTranslator != null) {
				this.statementTranslator.setLanguage(this.language);
			}
		}
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
	 * Translates a requirement component for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param reqComponent Requirement component to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language requirement translation
	 * @throws DoesNotExistException Requirement component id does not exists
	 * @throws OperationFailedException
	 */
	public String translateReqComponent(ReqComponent reqComponent, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return this.reqComponentTranslator.translate(reqComponent, nlUsageTypeKey);
	}
	
	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param cluId Anchor CLU id
	 * @param statementId Statement to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU id does not exists
	 * @throws OperationFailedException
	 */
	public String translateStatement(String cluId, String statementId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return this.statementTranslator.translate(cluId, statementId, nlUsageTypeKey);
	}

	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param cluId Anchor CLU id
	 * @param statement Statement to be translated 
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU does not exists
	 * @throws OperationFailedException Translation fails
	 */
	public String translateStatement(String cluId, LuStatement statement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return this.statementTranslator.translate(cluId, statement, nlUsageTypeKey);
	}

	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) into natural language tree structure.
	 * 
	 * @param cluId Clu anchor
	 * @param statementId Statement to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language root tree node
	 * @throws DoesNotExistException CLU or statement does not exist
	 * @throws OperationFailedException Translation fails
	 */
	public NLTranslationNodeInfo translateToTree(String cluId, String statementId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return this.statementTranslator.translateToTree(cluId, statementId, nlUsageTypeKey);
	}

	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) into natural language tree structure.
	 * 
	 * @param cluId Clu anchor
	 * @param statement Statement to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language root tree node
	 * @throws DoesNotExistException CLU or statement does not exist
	 * @throws OperationFailedException Translation fails
	 */
	public NLTranslationNodeInfo translateToTree(String cluId, LuStatement statement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return this.statementTranslator.translateToTree(cluId, statement, nlUsageTypeKey);
	}
}
