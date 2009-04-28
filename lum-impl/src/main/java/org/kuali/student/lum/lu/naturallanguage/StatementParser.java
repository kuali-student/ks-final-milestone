package org.kuali.student.lum.lu.naturallanguage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

public class StatementParser {
	private StringBuilder sb;
	private List<CustomReqComponent> reqComponentList;
	private String andOperator;
	private String orOperator;
	private int idCounter;
	private final static String STATEMENT_ID = "S";
	private final static String REC_COMPONENT_ID = "R";

	public StatementParser() {
		this(StatementOperatorTypeKey.AND.name(), StatementOperatorTypeKey.OR.name());
	}

	public StatementParser(String andOperator, String orOperator) {
		this.andOperator = andOperator;
		this.orOperator = orOperator;
	}
	
	private void init() {
		idCounter = 1;
	}

	/**
	 * Generates a reduced boolean expression (unneeded brackets removed) of 
	 * the LU statement tree.
	 * Traverses the LU statement and its children starting from the 
	 * <code>rootLuStatement</code> to generate the boolean expression. 
	 * No requirement components are included in this boolean expression.
	 * E.g. Boolean expression: 'S1 AND (S2 OR S3)'
	 * 
	 * @param rootLuStatement Starting (root) LU statement node
	 * @return A boolean expression of the LU statement tree
	 */
	public String getBooleanExpressionAsStatements(LuStatement rootLuStatement) {
		init();
		this.sb = new StringBuilder();
		if(rootLuStatement.getChildren() == null || rootLuStatement.getChildren().isEmpty()) {
			return parseReqComponent(rootLuStatement, false);
		} else {
			traverseStatementTreeAndReduce(rootLuStatement, false);
		}
		return sb.toString();
	}

	/**
	 * Generates a reduced boolean expression (unneeded brackets removed) of 
	 * the LU statement tree with leaf requirement components.
	 * Traverses the LU statement and its children starting from the 
	 * <code>rootLuStatement</code> to generate the boolean expression. 
	 * No requirement components are included in this boolean expression.
	 * E.g. Boolean expression: 'R1 AND (R2 OR (R3 AND R4))'
	 * 
	 * @param rootLuStatement Starting (root) LU statement node
	 * @return A boolean expression of the LU statement tree
	 */
	public String getBooleanExpressionAsReqComponents(LuStatement rootLuStatement) {
		init();
		this.sb = new StringBuilder();
		if(rootLuStatement.getChildren() == null || rootLuStatement.getChildren().isEmpty()) {
			return parseReqComponent(rootLuStatement, true);
		} else {
			traverseStatementTreeAndReduce(rootLuStatement, true);
		}
		return sb.toString();
	}
	
	/**
	 * Gets a list of all leaf requirement components.
	 * 
	 * @param rootLuStatement Starting (root) LU statement node
	 * @return List of all requirement components
	 */
	public List<CustomReqComponent> getLeafReqComponents(LuStatement rootLuStatement) {
		init();
		this.reqComponentList = new ArrayList<CustomReqComponent>();
		if(rootLuStatement.getChildren() == null || rootLuStatement.getChildren().isEmpty()) {
			this.reqComponentList.addAll(getCustomReqComponent(rootLuStatement.getRequiredComponents()));
		} else {
			traverseStatementTree(rootLuStatement);
		}
		return this.reqComponentList;
	}

	private void traverseStatementTree(LuStatement rootLuStatement) {
		for(Iterator<LuStatement> it = rootLuStatement.getChildren().iterator(); it.hasNext();) {
			LuStatement stmt = it.next();
			if (stmt.getChildren() == null || stmt.getChildren().isEmpty()) {
				this.reqComponentList.addAll(getCustomReqComponent(stmt.getRequiredComponents()));
			} else {
				traverseStatementTree(stmt);
			}
		}
	}
	
	private List<CustomReqComponent> getCustomReqComponent(List<ReqComponent> list) {
		List<CustomReqComponent> newList = new ArrayList<CustomReqComponent>(list.size());
		for(ReqComponent reqComp : list) {
			newList.add(new CustomReqComponent(reqComp, getBooleanReqComponentId()));
		}
		return newList;
	}
	
	private void traverseStatementTreeAndReduce(LuStatement rootLuStatement, boolean parseReqComponent) {
		if(rootLuStatement.getChildren() != null && 
			(rootLuStatement.getOperator() == StatementOperatorTypeKey.OR || 
			(rootLuStatement.getParent() != null && rootLuStatement.getParent().getOperator() == StatementOperatorTypeKey.OR)) ) {
			this.sb.append("(");
		}
		for(Iterator<LuStatement> it = rootLuStatement.getChildren().iterator(); it.hasNext();) {
			LuStatement stmt = it.next();
			if (stmt.getChildren() == null || stmt.getChildren().isEmpty()) {
				if (parseReqComponent) {
					this.sb.append(parseReqComponent(stmt, false));
				} else {
//					this.sb.append(StatementUtil.formatId(stmt.getId()));
					this.sb.append(getBooleanStatementId());
				}
			} else {
				traverseStatementTreeAndReduce(stmt, parseReqComponent);
			}

			if (it.hasNext() && stmt.getParent().getOperator() != null) {
				this.sb.append(" ");
				this.sb.append(getOperator(stmt.getParent().getOperator()));
				this.sb.append(" ");
			}
		}
		if(rootLuStatement.getChildren() != null && 
			(rootLuStatement.getOperator() == StatementOperatorTypeKey.OR || 
			(rootLuStatement.getParent() != null && rootLuStatement.getParent().getOperator() == StatementOperatorTypeKey.OR)) ) {
			this.sb.append(")");
		}
	}

	private String parseReqComponent(LuStatement luStatement, boolean reduce) {
		if (luStatement.getRequiredComponents() == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		if(!reduce && luStatement.getRequiredComponents().size() > 1)
		{
			sb.append("(");
		}
		for(Iterator<ReqComponent> it = luStatement.getRequiredComponents().iterator(); it.hasNext(); ) {
			ReqComponent reqComponent = it.next();
			sb.append(getBooleanReqComponentId());
			if (it.hasNext()) {
				sb.append(" ");
				sb.append(getOperator(luStatement.getOperator()));
				sb.append(" ");
			}
		}
		if(!reduce && luStatement.getRequiredComponents().size() > 1)
		{
			sb.append(")");
		}
		return sb.toString();
	}
	
	private String getOperator(StatementOperatorTypeKey operator) {
		switch(operator) {
			case AND:
				return this.andOperator;
			case OR:
				return this.orOperator;
			default:
//				throw new OperationFailedException("Invalid statement operator: "+luStatement.getOperator().name());
		}
		return null;
	}

	private String getBooleanStatementId() {
		return STATEMENT_ID + this.idCounter++;
	}

	private String getBooleanReqComponentId() {
		return REC_COMPONENT_ID + this.idCounter++;
	}
}
