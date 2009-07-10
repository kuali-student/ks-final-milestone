package org.kuali.student.lum.nlt.naturallanguage.translators;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.LuStatementType;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.lu.entity.ReqComponentType;

public class StatementTestUtil {
	private LuDao luDao;

	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
	}

	public LuStatement createStatement(LuStatement luStatement) {
		return this.luDao.create(luStatement);
	}
	
	public LuStatement createStatement(String statementTypeId) throws DoesNotExistException {
		LuStatement stmt = new LuStatement();
		LuStatementType type = this.luDao.fetch(LuStatementType.class, statementTypeId);
		stmt.setLuStatementType(type);
		return stmt;
	}

	public ReqComponent createReqComponent(String reqComponentTypeId, List<ReqComponentField> fieldList) throws DoesNotExistException {
		return createReqComponent(reqComponentTypeId, fieldList, false);
	}

	public ReqComponent createReqComponent(String reqComponentTypeId, List<ReqComponentField> fieldList, boolean persist) throws DoesNotExistException {
		ReqComponentType reqCompType = this.luDao.fetch(ReqComponentType.class, reqComponentTypeId);
		
		ReqComponent reqComponent = new ReqComponent();
		reqComponent.setRequiredComponentType(reqCompType);
		reqComponent.setReqCompField(fieldList);
		if(persist) {
			reqComponent = this.luDao.create(reqComponent);
		}

		return reqComponent;
    }

    public List<ReqComponentField> createReqComponentFields(String expectedValue, String operator, String cluSetId) {
    	return createReqComponentFields(expectedValue, operator, "reqCompFieldType.cluSet", cluSetId);
    }
    
    public List<ReqComponentField> createReqComponentFields(String expectedValue, String operator, String reqCompFieldType, String id) {
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setKey("reqCompFieldType.requiredCount");
		field1.setValue(expectedValue);
		field1.prePersist();
		fieldList.add(field1);
		//luDao.create(field1);
		
		ReqComponentField field2 = new ReqComponentField();
		field2.setKey("reqCompFieldType.operator");
		field2.setValue(operator);
		field2.prePersist();
		fieldList.add(field2);
		//luDao.create(field2);
		
		ReqComponentField field3 = new ReqComponentField();
		field3.setKey(reqCompFieldType);
		field3.setValue(id);
		field3.prePersist();
		fieldList.add(field3);
		//luDao.create(field3);
		
		//reqComponent.setReqCompField(fieldList);
		//reqComponent = this.luDao.update(reqComponent);
		return fieldList;
    }
}
