package org.kuali.student.poc.learningunit.lu.dao;

import org.kuali.student.poc.learningunit.lu.entity.Atp;
import org.kuali.student.poc.learningunit.lu.entity.Clu;
import org.kuali.student.poc.learningunit.lu.entity.CluRelation;
import org.kuali.student.poc.learningunit.lu.entity.LuRelationType;
import org.kuali.student.poc.learningunit.lu.entity.LuType;
import org.kuali.student.poc.learningunit.lu.entity.Lui;
import org.kuali.student.poc.learningunit.lu.entity.LuiRelation;

public interface LuDao {
	public Clu createClu(Clu clu);
	public Atp createAtp(Atp atp);
	public LuType createLuType(LuType luType);
	public Lui createLui(Lui lui);
	public LuiRelation createLuiRelation(LuiRelation luiRelation);
	public LuRelationType createLuRelationType(LuRelationType luRelationType);
	public CluRelation createCluRelation(CluRelation cluRelation);
	
}
