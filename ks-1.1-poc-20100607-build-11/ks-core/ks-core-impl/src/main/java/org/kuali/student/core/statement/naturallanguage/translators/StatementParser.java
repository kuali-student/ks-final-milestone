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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.Statement;
import org.kuali.student.core.statement.naturallanguage.util.ReqComponentReference;

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
	 * <code>rootStatement</code> to generate the boolean expression. 
	 * No requirement components are included in this boolean expression.
	 * E.g. Boolean expression: 'S1 AND (S2 OR S3)'
	 * 
	 * @param rootStatement Starting (root) LU statement node
	 * @return A boolean expression of the LU statement tree
	 */
	public String getBooleanExpressionAsStatements(final Statement rootStatement) throws OperationFailedException {
		init();
		if(rootStatement.getChildren() == null || rootStatement.getChildren().isEmpty()) {
			return parseReqComponents(rootStatement, false);
		} else {
			traverseStatementTreeAndReduce(rootStatement, false);
		}
		return sb.toString();
	}

	/**
	 * Generates a reduced boolean expression (unneeded brackets removed) of 
	 * the LU statement tree with leaf requirement components.
	 * Traverses the LU statement and its children starting from the 
	 * <code>rootStatement</code> to generate the boolean expression. 
	 * No requirement components are included in this boolean expression.
	 * E.g. Boolean expression: 'R1 AND (R2 OR (R3 AND R4))'
	 * 
	 * @param rootStatement Starting (root) LU statement node
	 * @return A boolean expression of the LU statement tree
	 */
	public String getBooleanExpressionAsReqComponents(final Statement rootStatement) throws OperationFailedException {
		init();
		if(rootStatement.getChildren() == null || rootStatement.getChildren().isEmpty()) {
			return parseReqComponents(rootStatement, true);
		} else {
			traverseStatementTreeAndReduce(rootStatement, true);
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
	 * @param rootStatement Starting (root) LU statement node
	 * @return List of all requirement components
	 */
	public List<ReqComponentReference> getLeafReqComponents(final Statement rootStatement) throws OperationFailedException {
		init();
		this.reqComponentList = new ArrayList<ReqComponentReference>();
		if(rootStatement.getChildren() == null || rootStatement.getChildren().isEmpty()) {
			this.reqComponentList.addAll(getReqComponents(rootStatement.getRequiredComponents()));
		} else {
			traverseStatementTree(rootStatement);
		}
		return this.reqComponentList;
	}

	/**
	 * Traverses statement tree.
	 * 
	 * @param rootStatement Root LU statement
	 * @throws OperationFailedException
	 */
	private void traverseStatementTree(Statement rootStatement) throws OperationFailedException {
		for(Iterator<Statement> it = rootStatement.getChildren().iterator(); it.hasNext();) {
			Statement stmt = it.next();
			if (stmt.getChildren() == null || stmt.getChildren().isEmpty()) {
				this.reqComponentList.addAll(getReqComponents(stmt.getRequiredComponents()));
			} else {
				traverseStatementTree(stmt);
			}
		}
	}
	
	/**
	 * Gets custom requirement components.
	 * 
	 * @param list List of requirement components
	 * @return List of requirement components
	 * @throws OperationFailedException
	 */
	private List<ReqComponentReference> getReqComponents(List<ReqComponent > list) throws OperationFailedException {
		List<ReqComponentReference> newList = new ArrayList<ReqComponentReference>(list.size());
		for(ReqComponent reqComp : list) {
			newList.add(new ReqComponentReference(reqComp, getReqComponentReferenceId(reqComp)));
		}
		return newList;
	}

	/**
	 * Traverses statement tree.
	 * 
	 * @param rootStatement Root LU statement
	 * @param parseReqComponent if true parses requirement component
	 * @throws OperationFailedException
	 */
	private void traverseStatementTreeAndReduce(Statement rootStatement, boolean parseReqComponent) throws OperationFailedException {
		if(rootStatement.getChildren() != null && 
			(rootStatement.getOperator() == StatementOperatorTypeKey.OR || 
			(rootStatement.getParent() != null && rootStatement.getParent().getOperator() == StatementOperatorTypeKey.OR)) ) {
			this.sb.append("(");
		}
		for(Iterator<Statement> it = rootStatement.getChildren().iterator(); it.hasNext();) {
			Statement stmt = it.next();
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
		if(rootStatement.getChildren() != null && 
			(rootStatement.getOperator() == StatementOperatorTypeKey.OR || 
			(rootStatement.getParent() != null && rootStatement.getParent().getOperator() == StatementOperatorTypeKey.OR)) ) {
			this.sb.append(")");
		}
	}

	/**
	 * Parses requirement components for LU statement.
	 * 
	 * @param statement LU statement
	 * @param reduce If true, reduces unneeded brackets
	 * @return Parsed requirement components
	 * @throws OperationFailedException
	 */
	private String parseReqComponents(Statement statement, boolean reduce) throws OperationFailedException {
		if (statement.getRequiredComponents() == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		if(!reduce && statement.getRequiredComponents().size() > 1)
		{
			sb.append("(");
		}
		for(Iterator<ReqComponent> it = statement.getRequiredComponents().iterator(); it.hasNext(); ) {
			ReqComponent reqComponent = it.next();
			sb.append(getReqComponentReferenceId(reqComponent));
			if (it.hasNext()) {
				sb.append(" ");
				sb.append(getOperator(statement.getOperator()));
				sb.append(" ");
			}
		}
		if(!reduce && statement.getRequiredComponents().size() > 1)
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
	 * @param statement LU statement
	 * @return Statement reference identifier
	 * @throws OperationFailedException Statement id is null
	 */
	private String getStatementReferenceId(Statement statement) throws OperationFailedException {
		if(statement.getId() == null || statement.getId().isEmpty()) {
			throw new OperationFailedException("Statement id cannot be null");
		}
		
		if(this.idMap.containsKey(statement.getId())) {
			return this.idMap.get(statement.getId());
		}
		String id = STATEMENT_ID + this.idCounter++;
		this.idMap.put(statement.getId(), id);
		return id;
	}

	/**
	 * Gets the requirement component reference identifier.
	 * 
	 * @param reqComponent Requirement component
	 * @return requirement component reference identifier
	 * @throws OperationFailedException Requirement component id is null
	 */
	private String getReqComponentReferenceId(ReqComponent reqComponent) throws OperationFailedException {
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
