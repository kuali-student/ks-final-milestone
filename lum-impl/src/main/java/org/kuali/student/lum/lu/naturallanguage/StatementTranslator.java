package org.kuali.student.lum.lu.naturallanguage;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.LuStatement;

public class StatementTranslator extends AbstractTranslator<LuStatement> {
	private LuDao luDao;

	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
	}

	public String translate(String statementId, String nlUsageTypeKey) throws DoesNotExistException {
		return null;
	}

	public String translate(LuStatement luStatement, String nlUsageTypeKey) throws DoesNotExistException {
		return null;
	}
}
