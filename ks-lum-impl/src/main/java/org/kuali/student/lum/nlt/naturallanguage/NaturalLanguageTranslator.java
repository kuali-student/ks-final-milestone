package org.kuali.student.lum.nlt.naturallanguage;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;

public interface NaturalLanguageTranslator {

	/**
	 * Sets the language translation.
	 * 
	 * @param language Language translation
	 */
	public void setLanguage(String language);
	
	/**
	 * Gets the translation language.
	 * 
	 * @return Language translation
	 */
	public String getLanguage();

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
	public String translateReqComponent(ReqComponent reqComponent, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param cluId Anchor CLU id
	 * @param statement Statement 
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU id does not exists
	 * @throws OperationFailedException
	 */
	public String translateStatement(String cluId, LuStatement statement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

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
	public NLTranslationNodeInfo translateToTree(String cluId, LuStatement statement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;
}