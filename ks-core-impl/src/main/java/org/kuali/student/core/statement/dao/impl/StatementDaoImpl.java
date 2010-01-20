package org.kuali.student.core.statement.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.statement.dao.StatementDao;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.Statement;

public class StatementDaoImpl extends AbstractSearchableCrudDaoImpl implements StatementDao {

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

}
