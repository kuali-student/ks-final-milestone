package org.kuali.student.brms.statement.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.brms.statement.entity.ReqComponent;
import org.kuali.student.brms.statement.entity.Statement;

public interface StatementDao extends CrudDao, SearchableDao {

    public List<ReqComponent> getReqComponents(List<String> reqComponentIdList);
    public List<Statement> getStatements(List<String> statementIdList);
    public List<Statement> getStatementsForStatementType(String statementTypeKey);
    public List<ReqComponent> getReqComponentsByType(String reqComponentTypeKey);
}
