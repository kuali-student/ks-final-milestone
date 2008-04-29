package org.kuali.student.poc.learningunit.luipersonrelation.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;

public interface LuiPersonRelationDAO {

    public LuiPersonRelation createLuiPersonRelation(LuiPersonRelation luiPersonRelation);

    public boolean deleteLuiPersonRelation(String luiPersonRelationId);

    public LuiPersonRelation lookupLuiPersonRelation(String luiPersonRelationId);
    
    public List<LuiPersonRelation> findLuiPersonRelationsByPerson(String personId, String luiRelationType, String relationState);
    
    public List<LuiPersonRelation> findLuiPersonRelationsByLui(String luiId, String luiRelationType, String relationState);
    
    public List<LuiPersonRelation> findLuiPersonRelations(String personId, String luiId, String luiRelationType, String relationState);

    public LuiPersonRelation updateLuiPersonRelation(LuiPersonRelation luiPersonRelation);

    public List<String> searchForLuiPersonRelationIds(String luiId, String personId, String luiPersonRelationType, String relationState, Date effectiveStartDate, Date effectiveEndDate);

    public List<LuiPersonRelation> searchForLuiPersonRelations(String luiId, String personId, String luiPersonRelationType, String relationState, Date effectiveStartDate, Date effectiveEndDate);
}
