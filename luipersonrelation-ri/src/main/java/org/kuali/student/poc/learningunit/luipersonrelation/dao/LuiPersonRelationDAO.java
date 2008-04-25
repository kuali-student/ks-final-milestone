package org.kuali.student.poc.learningunit.luipersonrelation.dao;

import java.util.List;

import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;

public interface LuiPersonRelationDAO {

    public LuiPersonRelation createLuiPersonRelation(LuiPersonRelation luiPersonRelation);

    public boolean deleteLuiPersonRelation(String luiPersonRelationId);

    public LuiPersonRelation lookupLuiPersonRelation(String luiPersonRelationId);
    
    public List<LuiPersonRelation> findLuiPersonRelationByPerson(String personId, String luiRelationType);
    
    public List<LuiPersonRelation> findLuiPersonRelationByLui(String luiId, String luiRelationType);
}
