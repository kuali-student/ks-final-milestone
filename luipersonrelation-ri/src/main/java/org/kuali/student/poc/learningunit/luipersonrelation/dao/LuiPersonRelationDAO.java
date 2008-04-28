package org.kuali.student.poc.learningunit.luipersonrelation.dao;

import java.util.List;

import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;

public interface LuiPersonRelationDAO {

    public LuiPersonRelation createLuiPersonRelation(LuiPersonRelation luiPersonRelation);

    public boolean deleteLuiPersonRelation(String luiPersonRelationId);

    public LuiPersonRelation lookupLuiPersonRelation(String luiPersonRelationId);
    
    public List<LuiPersonRelation> findLuiPersonRelationsByPerson(String personId, String luiRelationType, String relationState);
    
    public List<LuiPersonRelation> findLuiPersonRelationsByLui(String luiId, String luiRelationType, String relationState);
    
    public List<LuiPersonRelation> findLuiPersonRelations(String personId, String luiId, String luiRelationType, String relationState);

    public LuiPersonRelation findLuiPersonRelation(String luiPersonRelationId);

    public LuiPersonRelation updateLuiPersonRelation(LuiPersonRelation luiPersonRelation);
}
