/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.nlt.naturallanguage.translators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomLuStatementInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.ReqComponentReference;

/**
 * This class parses a LU (learning unit) statement statement to generate 
 * the boolean expression either as requirement components (e.g. R1 and R2) 
 * or statements (e.g. S1 and S2).
 */
public class StatementParser {
	private StringBuilder sb;
	private Map<String,String> idMap;
	private List<ReqComponentReference> reqComponentList;
	private String andOperator;
	private String orOperator;
	private int idCounter;
	private final static String STATEMENT_ID = "S";
	private final static String REC_COMPONENT_ID = "R";

	/**
	 * Constructs a new statement parser with AND and OR statement 
	 * operator type keys.
	 */
	public StatementParser() {
		this(StatementOperatorTypeKey.AND.name(), StatementOperatorTypeKey.OR.name());
	}

	/**
	 * Constructs a new statement parser.
	 * 
	 * @param andOperator And operator
	 * @param orOperator Or operator
	 */
	public StatementParser(final String andOperator, final String orOperator) {
		this.andOperator = andOperator.toLowerCase();
		this.orOperator = orOperator.toLowerCase();
	}

	/**
	 * Initialize boolean identifiers.
	 */
	private void init() {
		idCounter = 1;
		this.idMap = new HashMap<String, String>();
		this.sb = new StringBuilder();
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
	public String getBooleanExpressionAsStatements(final CustomLuStatementInfo rootLuStatement) throws OperationFailedException {
		init();
		if(rootLuStatement.getChildren() == null || rootLuStatement.getChildren().isEmpty()) {
			return parseReqComponents(rootLuStatement, false);
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
	public String getBooleanExpressionAsReqComponents(final CustomLuStatementInfo rootLuStatement) throws OperationFailedException {
		init();
		if(rootLuStatement.getChildren() == null || rootLuStatement.getChildren().isEmpty()) {
			return parseReqComponents(rootLuStatement, true);
		} else {
			traverseStatementTreeAndReduce(rootLuStatement, true);
		}
		return sb.toString();
	}
	
	/**
	 * Gets boolean identifier map.
	 * 
	 * @return Boolean identifier map
	 */
	public Map<String, String> getIdMap() {
		return this.idMap;
	}
	
	/**
	 * Gets a list of all leaf requirement components.
	 * 
	 * @param rootLuStatement Starting (root) LU statement node
	 * @return List of all requirement components
	 */
	public List<ReqComponentReference> getLeafReqComponents(final CustomLuStatementInfo rootLuStatement) throws OperationFailedException {
		init();
		this.reqComponentList = new ArrayList<ReqComponentReference>();
		if(rootLuStatement.getChildren() == null || rootLuStatement.getChildren().isEmpty()) {
			this.reqComponentList.addAll(getCustomReqComponents(rootLuStatement.getRequiredComponents()));
		} else {
			traverseStatementTree(rootLuStatement);
		}
		return this.reqComponentList;
	}

	/**
	 * Traverses statement tree.
	 * 
	 * @param rootLuStatement Root LU statement
	 * @throws OperationFailedException
	 */
	private void traverseStatementTree(CustomLuStatementInfo rootLuStatement) throws OperationFailedException {
		for(Iterator<CustomLuStatementInfo> it = rootLuStatement.getChildren().iterator(); it.hasNext();) {
			CustomLuStatementInfo stmt = it.next();
			if (stmt.getChildren() == null || stmt.getChildren().isEmpty()) {
				this.reqComponentList.addAll(getCustomReqComponents(stmt.getRequiredComponents()));
			} else {
				traverseStatementTree(stmt);
			}
		}
	}
	
	/**
	 * Gets custom requirement components.
	 * 
	 * @param list List of requirement components
	 * @return List of custom requirement components
	 * @throws OperationFailedException
	 */
	private List<ReqComponentReference> getCustomReqComponents(List<CustomReqComponentInfo> list) throws OperationFailedException {
		List<ReqComponentReference> newList = new ArrayList<ReqComponentReference>(list.size());
		for(CustomReqComponentInfo reqComp : list) {
			newList.add(new ReqComponentReference(reqComp, getReqComponentReferenceId(reqComp)));
		}
		return newList;
	}

	/**
	 * Traverses statement tree.
	 * 
	 * @param rootLuStatement Root LU statement
	 * @param parseReqComponent if true parses requirement component
	 * @throws OperationFailedException
	 */
	private void traverseStatementTreeAndReduce(CustomLuStatementInfo rootLuStatement, boolean parseReqComponent) throws OperationFailedException {
		if(rootLuStatement.getChildren() != null && 
			(rootLuStatement.getOperator() == StatementOperatorTypeKey.OR || 
			(rootLuStatement.getParent() != null && rootLuStatement.getParent().getOperator() == StatementOperatorTypeKey.OR)) ) {
			this.sb.append("(");
		}
		for(Iterator<CustomLuStatementInfo> it = rootLuStatement.getChildren().iterator(); it.hasNext();) {
			CustomLuStatementInfo stmt = it.next();
			if (stmt.getChildren() == null || stmt.getChildren().isEmpty()) {
				if (parseReqComponent) {
					this.sb.append(parseReqComponents(stmt, false));
				} else {
//					this.sb.append(StatementUtil.formatId(stmt.getId()));
					this.sb.append(getStatementReferenceId(stmt));
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

	/**
	 * Parses requirement components for LU statement.
	 * 
	 * @param luStatement LU statement
	 * @param reduce If true, reduces unneeded brackets
	 * @return Parsed requirement components
	 * @throws OperationFailedException
	 */
	private String parseReqComponents(CustomLuStatementInfo luStatement, boolean reduce) throws OperationFailedException {
		if (luStatement.getRequiredComponents() == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		if(!reduce && luStatement.getRequiredComponents().size() > 1)
		{
			sb.append("(");
		}
		for(Iterator<CustomReqComponentInfo> it = luStatement.getRequiredComponents().iterator(); it.hasNext(); ) {
			CustomReqComponentInfo reqComponent = it.next();
			sb.append(getReqComponentReferenceId(reqComponent));
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
	
	/**
	 * Gets the statement operator type key translation.
	 * 
	 * @param operator Statement operator type key 
	 * @return Operator translation
	 * @throws OperationFailedException Invalid statement operator
	 */
	private String getOperator(StatementOperatorTypeKey operator) throws OperationFailedException {
		switch(operator) {
			case AND:
				return this.andOperator;
			case OR:
				return this.orOperator;
			default:
				throw new OperationFailedException("Invalid statement operator: "+operator);
		}
	}

	/**
	 * Gets the statement reference identifier.
	 * 
	 * @param luStatement LU statement
	 * @return Statement reference identifier
	 * @throws OperationFailedException Statement id is null
	 */
	private String getStatementReferenceId(CustomLuStatementInfo luStatement) throws OperationFailedException {
		if(luStatement.getId() == null || luStatement.getId().isEmpty()) {
			throw new OperationFailedException("Statement id cannot be null");
		}
		
		if(this.idMap.containsKey(luStatement.getId())) {
			return this.idMap.get(luStatement.getId());
		}
		String id = STATEMENT_ID + this.idCounter++;
		this.idMap.put(luStatement.getId(), id);
		return id;
	}

	/**
	 * Gets the requirement component reference identifier.
	 * 
	 * @param reqComponent Requirement component
	 * @return requirement component reference identifier
	 * @throws OperationFailedException Requirement component id is null
	 */
	private String getReqComponentReferenceId(CustomReqComponentInfo reqComponent) throws OperationFailedException {
		if(reqComponent.getId() == null || reqComponent.getId().isEmpty()) {
			throw new OperationFailedException("Requirement component id cannot be null");
		}
		
		if(this.idMap.containsKey(reqComponent.getId())) {
			return this.idMap.get(reqComponent.getId());
		}
		String id = REC_COMPONENT_ID + this.idCounter++;
		this.idMap.put(reqComponent.getId(), id);
		return id;
	}
}
