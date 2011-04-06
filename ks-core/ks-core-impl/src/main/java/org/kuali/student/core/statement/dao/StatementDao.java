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

package org.kuali.student.core.statement.dao;

import java.util.List;

import org.kuali.student.common.dao.CrudDao;
import org.kuali.student.common.dao.SearchableDao;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.core.statement.entity.RefStatementRelation;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.Statement;

public interface StatementDao extends CrudDao, SearchableDao {

    public List<ReqComponent> getReqComponents(List<String> reqComponentIdList);
    public List<Statement> getStatements(List<String> statementIdList);
    public List<Statement> getStatementsForStatementType(String statementTypeKey);
    public List<ReqComponent> getReqComponentsByType(String reqComponentTypeKey);
    public List<Statement> getStatementsForReqComponent(String reqComponentId);
    public List<RefStatementRelation> getRefStatementRelations(String refObjectTypeKey, String refObjectId);
    public Statement getParentStatement(String childId) throws DoesNotExistException;
	public List<Object[]> getStatementsWithDependencies(
			List<String> cluVersionIndIds, List<String> cluSetIds);
}
