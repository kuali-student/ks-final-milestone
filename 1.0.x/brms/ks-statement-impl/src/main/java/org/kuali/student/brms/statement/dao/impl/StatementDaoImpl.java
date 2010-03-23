package org.kuali.student.brms.statement.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.brms.statement.dao.StatementDao;
import org.kuali.student.brms.statement.entity.RefStatementRelation;
import org.kuali.student.brms.statement.entity.ReqComponent;
import org.kuali.student.brms.statement.entity.Statement;

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
    
}
