package org.kuali.student.lum.lu.naturallanguage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.brms.internal.common.runtime.BooleanMessage;
import org.kuali.student.brms.internal.common.runtime.MessageContainer;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanMessageImpl;
import org.kuali.student.brms.ruleexecution.runtime.drools.DroolsKnowledgeBase;
import org.kuali.student.brms.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.brms.ruleexecution.runtime.report.MessageBuilder;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.MessageBuilderImpl;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.ReqComponent;

public class StatementTranslator extends AbstractTranslator<LuStatement> {
	private LuDao luDao;
	private StatementParser statementParser = new StatementParser("*", "+");
	private ReqComponentTranslator reqComponentTranslator = new ReqComponentTranslator();
	private SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();
    private final DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();
    private MessageBuilder messageBuilder;

    public StatementTranslator() {
		this.executor.setEnableStatisticsLogging(true);
		this.executor.setRuleBaseCache(this.ruleBase);
		this.messageBuilder = new MessageBuilderImpl(this.executor);
    }

	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
		this.reqComponentTranslator.setLuDao(this.luDao);
	}

	public String translate(String statementId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		LuStatement luStatement = this.luDao.fetch(LuStatement.class, statementId);
		String message = translate(luStatement, nlUsageTypeKey);
		return message;
	}

	public String translate(LuStatement luStatement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		String booleanExpression = statementParser.getBooleanExpressionAsReqComponents(luStatement);

		List<CustomReqComponent> reqComponentList = statementParser.getLeafReqComponents(luStatement);

		MessageContainer messageContainer = new MessageContainer();

		for(CustomReqComponent reqComponent : reqComponentList) {
			String translation = this.reqComponentTranslator.translate(reqComponent.getReqComponent(), nlUsageTypeKey);
			BooleanMessage bm = new BooleanMessageImpl(reqComponent.getBooleanId(), true, translation);
			messageContainer.addMessage(bm);
		}
		
		String message = this.messageBuilder.buildMessage(booleanExpression, messageContainer);

		return message;
	}

	public NLTranslationNode translateAsTree(LuStatement luStatement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		String booleanExpression = statementParser.getBooleanExpressionAsReqComponents(luStatement);
		traverseStatementTree(luStatement, nlUsageTypeKey);
		NLTranslationNode root = new NLTranslationNode(Boolean.TRUE, booleanExpression);
		root.addChildNode(this.tempNode);
		return tempNode;
	}
	
	//private List<NLTranslationNode> nodes = new ArrayList<NLTranslationNode>();
	private NLTranslationNode tempNode = null;
		
	private void traverseStatementTree(LuStatement rootLuStatement, String nlUsageTypeKey) 
		throws DoesNotExistException, OperationFailedException {
		for(Iterator<LuStatement> it = rootLuStatement.getChildren().iterator(); it.hasNext();) {
			LuStatement stmt = it.next();
			if (stmt.getChildren() == null || stmt.getChildren().isEmpty()) {
//				NLTranslationNode node = new NLTranslationNode(stmt.getId(), null);
//				List<NLTranslationNode> children = getReqComponents(stmt.getRequiredComponents(), nlUsageTypeKey);
//				node.setChildNodes(children);
//				this.tempNode = node;
			} else {
				traverseStatementTree(stmt, nlUsageTypeKey);
//				NLTranslationNode node = new NLTranslationNode(stmt.getId(), null);
//				node.addChildNode(this.tempNode);
//				this.tempNode = node;
			}
		}
	}
	
	private List<NLTranslationNode> getReqComponents(List<ReqComponent> reqComponentList, String nlUsageTypeKey) 
		throws DoesNotExistException, OperationFailedException {
		List<NLTranslationNode> list = new ArrayList<NLTranslationNode>(reqComponentList.size());
		for(ReqComponent reqComp : reqComponentList) {
			String translation = this.reqComponentTranslator.translate(reqComp, nlUsageTypeKey);
			NLTranslationNode node = new NLTranslationNode(reqComp.getId(), translation);
			list.add(node);
		}
		return list;
	}
}
