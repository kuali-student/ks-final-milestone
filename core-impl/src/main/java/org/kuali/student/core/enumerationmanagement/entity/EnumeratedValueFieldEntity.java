package org.kuali.student.core.enumerationmanagement.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name="ENUM_VAL_FLD_ENT")
public class EnumeratedValueFieldEntity {
    @Id
    @Column(name="ID")
    String id;
    @Column(name="DATA_TYPE")
    String dataType;
    @Column(name="FLD_KEY")
    String fieldKey;
    @Column(name="MIN_LGTH")
    int minLength;
    @Column(name="MAX_LGTH")
    int maxLength;
    @Column(name="MIN_OCCRS")
    int minOccurs;
    @Column(name="MAX_OCCRS")
    int maxOccurs;
    @Column(name="MIN_VAL")
    String minValue;
    @Column(name="MAX_VAL")
    String maxValue;
    @Column(name="VALID_CHARS")
    String validChars;
    @Column(name="INVLD_CHARS")
    String invalidChars;
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="ENUM_META_ENT_ID")
    EnumerationMetaEntity enumerationMetaEntity = new EnumerationMetaEntity(); 

    /**
     * AutoGenerate the id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String key) {
        this.fieldKey = key;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinOccurs() {
        return minOccurs;
    }

    public void setMinOccurs(int minOccurs) {
        this.minOccurs = minOccurs;
    }

    public int getMaxOccurs() {
        return maxOccurs;
    }

    public void setMaxOccurs(int maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    public String getValidChars() {
        return validChars;
    }

    public void setValidChars(String validChars) {
        this.validChars = validChars;
    }

    public String getInvalidChars() {
        return invalidChars;
    }

    public void setInvalidChars(String invalidChars) {
        this.invalidChars = invalidChars;
    }
    public EnumerationMetaEntity getEnumerationMetaEntity() {
        return enumerationMetaEntity;
    }
    public void setEnumerationMetaEntity(EnumerationMetaEntity enumerationMetaEntity) {
        this.enumerationMetaEntity = enumerationMetaEntity;
    }
    
    public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public Map<String, Object> toMap(){
    	Map<String, Object> fieldDescMap = new HashMap<String, Object>();
    	fieldDescMap.put("maxOccurs", maxOccurs);
    	fieldDescMap.put("maxValue", maxValue);
    	fieldDescMap.put("maxLength", maxLength);
    	fieldDescMap.put("minOccurs", minOccurs);
    	fieldDescMap.put("minValue", minValue);
    	fieldDescMap.put("minLength", minLength);
    	fieldDescMap.put("validChars", validChars);
    	fieldDescMap.put("invalidChars", invalidChars);
    	fieldDescMap.put("dataType", dataType);
    	return fieldDescMap;
    }

}
