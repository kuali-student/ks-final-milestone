package org.kuali.student.core.statement.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.statement.dao.StatementDao;
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

}
