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

package org.kuali.student.core.statement.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.common.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.core.statement.dao.StatementDao;
import org.kuali.student.core.statement.entity.RefStatementRelation;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.Statement;

public class StatementDaoImpl extends AbstractSearchableCrudDaoImpl implements StatementDao {

	@PersistenceContext(unitName = "Statement")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	@Override
    public List<ReqComponent> getReqComponents(List<String> reqComponentIdList) {
        Query query = em.createNamedQuery("ReqComponent.getReqComponents");
        query.setParameter("reqComponentIdList", reqComponentIdList);
        @SuppressWarnings("unchecked")
        List<ReqComponent> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<Statement> getStatements(List<String> statementIdList) {
        Query query = em.createNamedQuery("Statement.getStatements");
        query.setParameter("statementIdList", statementIdList);
        @SuppressWarnings("unchecked")
        List<Statement> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<ReqComponent> getReqComponentsByType(String reqComponentTypeKey) {
        Query query = em.createNamedQuery("ReqComponent.getReqComponentsByType");
        query.setParameter("reqComponentTypeKey", reqComponentTypeKey);
        @SuppressWarnings("unchecked")
        List<ReqComponent> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<Statement> getStatementsForStatementType(String statementTypeKey) {
        Query query = em.createNamedQuery("Statement.getStatementsForStatementType");
        query.setParameter("statementTypeKey", statementTypeKey);
        @SuppressWarnings("unchecked")
        List<Statement> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<Statement> getStatementsForReqComponent(String reqComponentId) {
        Query query = em.createNamedQuery("Statement.getStatementsForReqComponent");
        query.setParameter("reqComponentId", reqComponentId);
        @SuppressWarnings("unchecked")
        List<Statement> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<RefStatementRelation> getRefStatementRelations(String refObjectTypeKey, String refObjectId) {
        Query query = em.createNamedQuery("RefStatementRelation.getRefStatementRelations");
        query.setParameter("refObjectTypeKey", refObjectTypeKey);
        query.setParameter("refObjectId", refObjectId);
        @SuppressWarnings("unchecked")
        List<RefStatementRelation> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public Statement getParentStatement(String childId) throws DoesNotExistException {
        Query query = em.createNamedQuery("Statement.getParentStatement");
        query.setParameter("childId", childId);
        try{
        	return (Statement)query.getSingleResult();
        }catch(NoResultException e){
        	throw new DoesNotExistException("No entity for key '" + childId + "' found",e);
        }
    }

	@Override
	public List<Statement> getStatementsWithDependencies(
			List<String> cluVersionIndIds, List<String> cluSetIds) {
        Query query = em.createNamedQuery("Statement.getStatementsWithDependencies");
        query.setParameter("cluVersionIndIds", cluVersionIndIds);
        query.setParameter("cluSetIds", cluSetIds);
        @SuppressWarnings("unchecked")
        List<Statement> resultList = query.getResultList();
        return resultList;
	}
}
