/**
 * 
 */
package org.kuali.student.brms.rulemanagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.kuali.student.brms.factfinder.dto.FactStructureInfo;

/**
 * @author zzraly
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class YieldValueFunctionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String yieldValueFunctionType; 
    
    @XmlElement(name = "factStructure")
    @XmlElementWrapper(name="factStructureList")
    List<FactStructureInfo> factStructureList = new ArrayList<FactStructureInfo>();

    /**
     * @return the yieldValueFunctionType
     */
    public String getYieldValueFunctionType() {        
        return yieldValueFunctionType;
    }

    /**
     * @param yieldValueFunctionType the yieldValueFunctionType to set
     */
    public void setYieldValueFunctionType(String yieldValueFunctionType) {
        this.yieldValueFunctionType = yieldValueFunctionType;
    }

    /**
     * @return the factStructureList
     */
    public List<FactStructureInfo> getFactStructureList() {
        return factStructureList;
    }

    /**
     * @param factStructureList the factStructureList to set
     */
    public void setFactStructureList(List<FactStructureInfo> factStructureList) {
        this.factStructureList = factStructureList;
    }

    
}
