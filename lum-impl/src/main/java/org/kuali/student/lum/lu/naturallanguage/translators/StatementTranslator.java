package org.kuali.student.lum.lu.naturallanguage.translators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.brms.internal.common.runtime.BooleanMessage;
import org.kuali.student.brms.internal.common.runtime.MessageContainer;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanMessageImpl;
import org.kuali.student.brms.ruleexecution.runtime.drools.DroolsKnowledgeBase;
import org.kuali.student.brms.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.brms.ruleexecution.runtime.report.MessageBuilder;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.MessageBuilderImpl;
import org.kuali.student.common.util.VelocityTemplateEngine;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.LuStatementTypeHeaderTemplate;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.naturallanguage.util.CustomReqComponent;

public class StatementTranslator {
	private LuDao luDao;
	private StatementParser statementParser = new StatementParser("*", "+");
	private ReqComponentTranslator reqComponentTranslator;
	private SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();
    private final DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();
    private MessageBuilder messageBuilder;

    public StatementTranslator() {
		this.executor.setEnableStatisticsLogging(true);
		this.executor.setRuleBaseCache(this.ruleBase);
		this.messageBuilder = new MessageBuilderImpl(this.executor, "and", "or");
    }

	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
	}
	
	public void setReqComponentTranslator(ReqComponentTranslator reqComponentTranslator) {
		this.reqComponentTranslator = reqComponentTranslator;
	}

	public String translate(String cluId, String statementId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		LuStatement luStatement = this.luDao.fetch(LuStatement.class, statementId);
		String message = translate(cluId, luStatement, nlUsageTypeKey);
		return message;
	}

	public String translate(String cluId, LuStatement luStatement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		if(luStatement == null) {
			return null;
		}

		String booleanExpression = this.statementParser.getBooleanExpressionAsReqComponents(luStatement);
		List<CustomReqComponent> reqComponentList = this.statementParser.getLeafReqComponents(luStatement);
		String message = buildMessage(nlUsageTypeKey, booleanExpression, reqComponentList);
		String header = getHeader(luStatement, nlUsageTypeKey, cluId);
		
		return header + message;
	}

	public NLTranslationNodeInfo translateToTree(String cluId, String statementId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		LuStatement luStatement = this.luDao.fetch(LuStatement.class, statementId);
		return translateToTree(cluId, luStatement, nlUsageTypeKey);
	}

	public NLTranslationNodeInfo translateToTree(String cluId, LuStatement luStatement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		if(luStatement == null) {
			return null;
		}

		String booleanExpression = statementParser.getBooleanExpressionAsReqComponents(luStatement);
		String operator = (luStatement.getOperator() == null ? null : luStatement.getOperator().toString());
		String booleanId = statementParser.getIdMap().get(luStatement.getId());
		NLTranslationNodeInfo root = new NLTranslationNodeInfo(luStatement.getId(), booleanId, operator);
		root.setBooleanExpression(booleanExpression);

		createStatementTree(luStatement, root, nlUsageTypeKey);

		String translation = translate(cluId, luStatement, nlUsageTypeKey);
		root.setNLTranslation(translation);

		return root;
	}
	
	private String buildMessage(String nlUsageTypeKey, String booleanExpression, List<CustomReqComponent> reqComponentList) throws DoesNotExistException, OperationFailedException {
		MessageContainer messageContainer = new MessageContainer();
		for(CustomReqComponent reqComponent : reqComponentList) {
			String translation = this.reqComponentTranslator.translate(reqComponent.getReqComponent(), nlUsageTypeKey);
			BooleanMessage bm = new BooleanMessageImpl(reqComponent.getBooleanId(), true, translation);
			messageContainer.addMessage(bm);
		}
		String message = this.messageBuilder.buildMessage(booleanExpression, messageContainer);
		return message;
	}
	
	/**
	 * Gets header for root <code>luStatement</code>.
	 * 
	 * @param luStatement LU statement
	 * @param nlUsageTypeKey Natural language usuage type context key
	 * @param cluId Anchor CLU id
	 * @return Statement header
	 * @throws DoesNotExistException CLU or header template does not exist
	 */
	private String getHeader(LuStatement luStatement, String nlUsageTypeKey, String cluId) throws DoesNotExistException {
        if(cluId == null) {
        	return "";
        }
        
		Clu clu = this.luDao.fetch(Clu.class, cluId);
        String templateHeader = getHeaderTemplate(luStatement, nlUsageTypeKey);
		
        VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put("clu", clu);
        String s = templateEngine.evaluate(contextMap, templateHeader);
        
        return s;
	}
	
	/**
	 * Gets header template for root <code>luStatement</code>.
	 * 
	 * @param luStatement LU statement
	 * @param nlUsageTypeKey Natural language usuage type context key
	 * @return Header template
	 * @throws DoesNotExistException Header template does not exist
	 */
	private String getHeaderTemplate(LuStatement luStatement, String nlUsageTypeKey) throws DoesNotExistException {
		for(LuStatementTypeHeaderTemplate header : luStatement.getLuStatementType().getHeaders()) {
			if(header.getNlUsageTypeKey().equals(nlUsageTypeKey)) {
				return header.getTemplate();
			}
		}
        throw new DoesNotExistException("Natural language usage type key '" + nlUsageTypeKey + "' for statement type header not found");
	}

	/**
	 * Create a statement tree as <code>NLTranslationNodeInfo</code>.
	 * 
	 * @param luStatement LU statement
	 * @param rootNode Root node to translate to
	 * @param nlUsageTypeKey Natural language usuage type context key
	 * @throws DoesNotExistException
	 * @throws OperationFailedException
	 */
	private void createStatementTree(LuStatement luStatement, NLTranslationNodeInfo rootNode, String nlUsageTypeKey) 
		throws DoesNotExistException, OperationFailedException {
		if (luStatement.getChildren() == null || luStatement.getChildren().isEmpty()) {
			List<NLTranslationNodeInfo> children = getReqComponents(luStatement.getRequiredComponents(), nlUsageTypeKey);
			rootNode.setChildNodes(children);
			rootNode.setNLTranslation(getNLTranslation(children, rootNode.getOperator()));
			return;
		}

		for(Iterator<LuStatement> it = luStatement.getChildren().iterator(); it.hasNext();) {
			LuStatement stmt = it.next();
			String operator = (stmt.getOperator() == null ? null : stmt.getOperator().toString());
			String booleanId = statementParser.getIdMap().get(stmt.getId());
			NLTranslationNodeInfo node = new NLTranslationNodeInfo(stmt.getId(), booleanId, operator);
			node.setParent(rootNode);
			rootNode.addChildNode(node);
			if (stmt.getChildren() == null || stmt.getChildren().isEmpty()) {
				List<NLTranslationNodeInfo> children = getReqComponents(stmt.getRequiredComponents(), nlUsageTypeKey);
				node.setChildNodes(children);
				node.setNLTranslation(getNLTranslation(children, node.getOperator()));
			} else {
				createStatementTree(stmt, node, nlUsageTypeKey);
			}
		}
	}
	
	private String getNLTranslation(List<NLTranslationNodeInfo> children, String operator) {
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
	}

	private List<NLTranslationNodeInfo> getReqComponents(List<ReqComponent> reqComponentList, String nlUsageTypeKey) 
		throws DoesNotExistException, OperationFailedException {
		List<NLTranslationNodeInfo> list = new ArrayList<NLTranslationNodeInfo>(reqComponentList.size());
		for(ReqComponent reqComp : reqComponentList) {
			String translation = this.reqComponentTranslator.translate(reqComp, nlUsageTypeKey);
			String booleanId = statementParser.getIdMap().get(reqComp.getId());
			NLTranslationNodeInfo node = new NLTranslationNodeInfo(reqComp.getId(), booleanId, null);
			node.setNLTranslation(translation);
			list.add(node);
		}
		return list;
	}
}
