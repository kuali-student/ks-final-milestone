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

package org.kuali.student.core.statement.naturallanguage.translators;

import java.util.List;
import java.util.Locale;

import org.kuali.student.common.messagebuilder.booleanmessage.BooleanMessage;
import org.kuali.student.common.messagebuilder.booleanmessage.MessageContainer;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanMessageImpl;
import org.kuali.student.core.statement.entity.Statement;
import org.kuali.student.core.statement.naturallanguage.util.ReqComponentReference;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class translates a LU (learning unit) statement into a specific 
 * natural language.  This class is not thread safe.
 */
public class StatementTranslator {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(StatementTranslator.class);

    private String language;
	private StatementParser statementParser = new StatementParser("*", "+");
	private ReqComponentTranslator reqComponentTranslator;
	private NaturalLanguageMessageBuilder messageBuilder;
//    private ContextRegistry<Context<StatementAnchor>> contextRegistry;
//    private TemplateTranslator templateTranslator = new TemplateTranslator();
    
	/**
	 * Constructs a new natural language translator in the 
	 * default language locale.
	 */
	public StatementTranslator() {
		this.language = Locale.getDefault().getLanguage();
    }

	/**
	 * Sets the translation language.
	 * 
	 * @param language Translation language
	 */
	public void setLanguage(final String language) {
		this.language = language;
		setLanguage();
	}

	/**
	 * Sets the translation language for the message builder and 
	 * requirement component translator.
	 */
	private void setLanguage() {
		if(this.language != null) {
			if(this.reqComponentTranslator != null) {
				this.reqComponentTranslator.setLanguage(this.language);
			}
		}
	}
	
    /**
     * Sets the template context registry.
     * 
     * @param contextRegistry Template context registry
     */
//    public void setContextRegistry(final ContextRegistry<Context<StatementAnchor>> contextRegistry) {
//    	this.contextRegistry = contextRegistry;
//    }

	/**
	 * Sets the requirement component translator.
	 * 
	 * @param reqComponentTranslator Requirement component translator
	 */
	public void setReqComponentTranslator(final ReqComponentTranslator reqComponentTranslator) {
		this.reqComponentTranslator = reqComponentTranslator;
		setLanguage();
	}

	/**
	 * Sets the message builder.
	 * 
	 * @param messageBuilder Message builder
	 */
    public void setMessageBuilder(final NaturalLanguageMessageBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
		setLanguage();
    }

	/**
	 * Translates a statement in the default language for a specific natural 
	 * language usuage type (context) into natural language.
	 * This method is not thread safe.
	 * 
	 * @param statement Statement
	 * @param nlUsageTypeKey Usuage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU or statement id does not exists
	 * @throws OperationFailedException Translation failed
	 */
	public String translate(final Statement statement, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return translate(this.language, statement, nlUsageTypeKey);
	}
	
	/**
	 * Translates a statement for a specific natural language usuage 
	 * type (context) into natural language.
	 * This method is not thread safe.
	 * 
	 * @param language Language translation
	 * @param statement Statement
	 * @param nlUsageTypeKey Usuage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU or statement id does not exists
	 * @throws OperationFailedException Translation failed
	 */
	public String translate(final String language, final Statement statement, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		if(statement == null) {
    		throw new DoesNotExistException("Statement cannot be null");
		}

		try {
			String booleanExpression = this.statementParser.getBooleanExpressionAsReqComponents(statement);
			List<ReqComponentReference> reqComponentList = this.statementParser.getLeafReqComponents(statement);
			String message = buildMessage(language, nlUsageTypeKey, booleanExpression, reqComponentList);
//		String header = "";
//		if(cluId != null && !cluId.isEmpty()) {
//			header = getHeader(statement, nlUsageTypeKey, cluId);
//		}
//		String header = getHeader(statement, nlUsageTypeKey);
//		
//		return header + message;
			return message;
		} catch (DoesNotExistException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (OperationFailedException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Translates a statement directly attached to a CLU for a specific natural 
	 * language usuage type (context) into natural language tree structure.
	 * 
	 * @param cluId Clu anchor
	 * @param luStatement LU statement
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language root tree node
	 * @throws DoesNotExistException CLU or statement does not exist
	 * @throws OperationFailedException Translation failed
	 */
/*	public NLTranslationNodeInfo translateToTree(final String cluId, final CustomLuStatementInfo luStatement, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		if(luStatement == null) {
//			return null;
    		throw new DoesNotExistException("LuStatement cannot be null");
		}

		String booleanExpression = statementParser.getBooleanExpressionAsReqComponents(luStatement);
		String operator = (luStatement.getOperator() == null ? null : luStatement.getOperator().toString());
		String booleanId = statementParser.getIdMap().get(luStatement.getId());
		NLTranslationNodeInfo rootNode = new NLTranslationNodeInfo(luStatement.getId(), booleanId, operator);
		rootNode.setBooleanExpression(booleanExpression);

		createStatementTree(luStatement, rootNode, nlUsageTypeKey);

		String translation = translate(cluId, luStatement, nlUsageTypeKey);
		rootNode.setNLTranslation(translation);

		return rootNode;
	}
	
	/**
	 * Builds the full translated message.
	 * 
	 * @param nlUsageTypeKey Usuage type key
	 * @param booleanExpression Boolean expression
	 * @param reqComponentList Requirement component list
	 * @return Translated message
	 * @throws DoesNotExistException Requirement component does not exist
	 * @throws OperationFailedException Translation failed
	 */
	private String buildMessage(String language, String nlUsageTypeKey, String booleanExpression, List<ReqComponentReference> reqComponentList) throws DoesNotExistException, OperationFailedException {
		MessageContainer messageContainer = new MessageContainer();
		for(ReqComponentReference reqComponent : reqComponentList) {
			String translation = this.reqComponentTranslator.translate(reqComponent.getReqComponent(), nlUsageTypeKey);
			BooleanMessage bm = new BooleanMessageImpl(reqComponent.getBooleanId(), true, translation);
			messageContainer.addMessage(bm);
		}
		
		String message = this.messageBuilder.buildMessage(language, booleanExpression, messageContainer);
		return message;
	}
	
	/**
	 * Gets header for root <code>luStatement</code>.
	 * 
	 * @param statement LU statement
	 * @param nlUsageTypeKey Natural language usuage type context key
	 * @return Statement header
	 * @throws DoesNotExistException CLU or header template does not exist
	 */
	/*private String getHeader(Statement statement, String nlUsageTypeKey) throws OperationFailedException, DoesNotExistException {
        String template = getHeaderTemplate(statement, nlUsageTypeKey);
		
        Map<String, Object> contextMap = buildContextMap(statement);
        String header = this.templateTranslator.translate(contextMap, template);
        
        return header;
	}*/

    /**
     * Builds a statement type context map.
     * 
	 * @param statement Statement
	 * @return Context map 
	 * @throws DoesNotExistException
	 */
	/*private Map<String, Object> buildContextMap(Statement statement) throws OperationFailedException, DoesNotExistException {
		RefStatementRelation refStmtRel = statement.getRefStatementRelations().get(0);
		if(refStmtRel == null) {
        	throw new DoesNotExistException("Reference statement relation not found for statement id: " + statement.getId());
		}

		StatementAnchor lua = new StatementAnchor(statement, refStmtRel.getRefObjectTypeKey(), refStmtRel.getRefObjectId());

		String statementTypeKey = statement.getStatementType().getId();
		Context<StatementAnchor> context = this.contextRegistry.get(statementTypeKey);
    	if(context == null) {
        	throw new DoesNotExistException("Header context not found in registry for statement type key: " + statementTypeKey);
    	}
		Map<String, Object> contextMap = context.createContextMap(lua);
    	
        return contextMap;
	}*/

	/**
	 * Gets header template for root <code>statement</code>.
	 * 
	 * @param statement LU statement
	 * @param nlUsageTypeKey Natural language usuage type context key
	 * @return Header template
	 * @throws DoesNotExistException Header template does not exist
	 */
	/*private String getHeaderTemplate(Statement statement, String nlUsageTypeKey) throws DoesNotExistException {
		for(StatementTypeHeaderTemplate header : statement.getStatementType().getStatementHeaders()) {
			if(header.getNlUsageTypeKey().equals(nlUsageTypeKey) && header.getLanguage().equals(this.language)) {
				return (header.getTemplate() == null ? "" : header.getTemplate());
			}
		}
        throw new DoesNotExistException("Natural language usage type key '" + nlUsageTypeKey + "'" +
        		" and language code '" + this.language + "' for statement type header not found");
	}*/

	/**
	 * Create a statement tree as <code>NLTranslationNodeInfo</code>.
	 * 
	 * @param luStatement LU statement
	 * @param rootNode Root node to translate to
	 * @param nlUsageTypeKey Natural language usuage type context key
	 * @throws DoesNotExistException Requirement component does not exist
	 * @throws OperationFailedException Translation failed
	 */
/*	private void createStatementTree(CustomLuStatementInfo luStatement, NLTranslationNodeInfo rootNode, String nlUsageTypeKey) 
		throws DoesNotExistException, OperationFailedException {
		if (luStatement.getChildren() == null || luStatement.getChildren().isEmpty()) {
			List<NLTranslationNodeInfo> children = getReqComponents(luStatement.getRequiredComponents(), nlUsageTypeKey);
			rootNode.setChildNodes(children);
			rootNode.setNLTranslation(getNLTranslation(children, rootNode.getOperator()));
			return;
		}

		for(Iterator<CustomLuStatementInfo> it = luStatement.getChildren().iterator(); it.hasNext();) {
			CustomLuStatementInfo stmt = it.next();
			String operator = (stmt.getOperator() == null ? null : stmt.getOperator().toString());
			String booleanId = statementParser.getIdMap().get(stmt.getId());
			NLTranslationNodeInfo node = new NLTranslationNodeInfo(stmt.getId(), booleanId, operator);
			node.setParentNode(rootNode);
			rootNode.addChildNode(node);
			if (stmt.getChildren() == null || stmt.getChildren().isEmpty()) {
				List<NLTranslationNodeInfo> children = getReqComponents(stmt.getRequiredComponents(), nlUsageTypeKey);
				node.setChildNodes(children);
				node.setNLTranslation(getNLTranslation(children, node.getOperator()));
			} else {
				createStatementTree(stmt, node, nlUsageTypeKey);
			}
		}
	}*/
	
	/**
	 * Gets the node's natural language translation.
	 * 
	 * @param children Nodes children
	 * @param operator Boolean operator
	 * @return Node's natural language translation
	 */
/*	private String getNLTranslation(List<NLTranslationNodeInfo> children, String operator) {
		StringBuilder sb = new StringBuilder();
		for(Iterator<NLTranslationNodeInfo> it = children.iterator(); it.hasNext();) {
			NLTranslationNodeInfo node = it.next();
			sb.append(node.getNLTranslation());
			if(it.hasNext()) {
				sb.append(" ");
				sb.append(operator.toLowerCase());
				sb.append(" ");
			}
		}
		return sb.toString();
	}*/

	/**
	 * Gets the requirement components as a list of translated nodes.
	 * 
	 * @param reqComponentList Requirement component list
	 * @param nlUsageTypeKey Usuage type key (context)
	 * @return List of translated nodes
	 * @throws DoesNotExistException Requirement component does not exist
	 * @throws OperationFailedException Translation fails
	 */
/*	private List<NLTranslationNodeInfo> getReqComponents(List<CustomReqComponentInfo> reqComponentList, String nlUsageTypeKey) 
		throws DoesNotExistException, OperationFailedException {
		List<NLTranslationNodeInfo> list = new ArrayList<NLTranslationNodeInfo>(reqComponentList.size());
		for(CustomReqComponentInfo reqComp : reqComponentList) {
			String translation = this.reqComponentTranslator.translate(reqComp, nlUsageTypeKey);
			String booleanId = statementParser.getIdMap().get(reqComp.getId());
			NLTranslationNodeInfo node = new NLTranslationNodeInfo(reqComp.getId(), booleanId, null);
			node.setNLTranslation(translation);
			list.add(node);
		}
		return list;
	}*/
}
