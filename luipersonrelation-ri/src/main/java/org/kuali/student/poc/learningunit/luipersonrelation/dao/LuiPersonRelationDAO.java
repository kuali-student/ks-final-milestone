package org.kuali.student.poc.learningunit.luipersonrelation.dao;

import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;

public interface LuiPersonRelationDAO {

    public LuiPersonRelation createLuiPersonRelation(LuiPersonRelation luiPersonRelation);

    public boolean deleteLuiPersonRelation(String luiPersonRelationId);

}
