package org.kuali.student.poc.learningunit.lu.dao;

import org.kuali.student.poc.learningunit.lu.entity.Atp;
import org.kuali.student.poc.learningunit.lu.entity.Clu;
import org.kuali.student.poc.learningunit.lu.entity.CluRelation;
import org.kuali.student.poc.learningunit.lu.entity.LuRelationType;
import org.kuali.student.poc.learningunit.lu.entity.LuType;
import org.kuali.student.poc.learningunit.lu.entity.Lui;
import org.kuali.student.poc.learningunit.lu.entity.LuiRelation;

public interface LuDao {
	public String createClu(Clu clu);
	public String createAtp(Atp atp);
	public String createLuType(LuType luType);
	public String createLui(Lui lui);
	public String createLuiRelation(LuiRelation luiRelation);
	public String createLuRelationType(LuRelationType luRelationType);
	public String createCluRelation(CluRelation cluRelation);
}
