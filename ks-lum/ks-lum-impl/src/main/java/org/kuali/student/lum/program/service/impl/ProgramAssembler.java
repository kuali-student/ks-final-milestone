/**
 *
 */
package org.kuali.student.lum.program.service.impl;

import org.kuali.student.core.service.impl.BaseAssembler;
import org.kuali.student.lum.program.dao.ProgramDao;

/**
 * @author glindholm
 *
 */
public class ProgramAssembler extends BaseAssembler {

	private ProgramDao programDao;

	public void setProgramDao(ProgramDao dao) {
		this.programDao = dao;
	}


}
