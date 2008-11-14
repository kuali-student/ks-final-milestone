package org.kuali.student.enumeration.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.entity.EnumeratedValueEntity;
import org.kuali.student.enumeration.entity.EnumerationMetaEntity;

public interface EnumerationManagementDAO {

    public List<EnumerationMetaEntity> findEnumerationMetas();

    public EnumerationMetaEntity fetchEnumerationMeta(String enumerationKey);
    
    public EnumerationMetaEntity addEnumerationMeta(EnumerationMetaEntity entity);

    public void removeEnumerationMeta(EnumerationMetaEntity entity);
    
    public List<EnumeratedValueEntity> fetchEnumerationsWithContextAndDate(String enumerationKey, String enumContextKey, String contextValue, Date contextDate);
   
    public List<EnumeratedValueEntity> fetchEnumerationsWithContext(String enumerationKey, String enumContextKey, String contextValue);
   
    public List<EnumeratedValueEntity> fetchAllEnumerationsWithDate(String enumerationKey, Date contextDate);
    
    public List<EnumeratedValueEntity> fetchAllEnumerations(String enumerationKey);
    
    public EnumeratedValueEntity addEnumeratedValue(String enumerationKey, EnumeratedValueEntity value);

    public EnumeratedValueEntity updateEnumeratedValue(String enumerationKey, String code, EnumeratedValueEntity value);

    public void removeEnumeratedValue(String enumerationKey, String code);

    
}
