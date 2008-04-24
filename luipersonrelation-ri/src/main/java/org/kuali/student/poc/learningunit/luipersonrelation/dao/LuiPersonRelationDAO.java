package org.kuali.student.poc.learningunit.luipersonrelation.dao;

import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;

public interface LuiPersonRelationDAO {

    String createLuiPersonRelation(LuiPersonRelation luiPersonRelation);

    boolean deleteLuiPersonRelation(String luiPersonRelationId);

}
