/**
 * 
 */
package org.kuali.student.rules.rulesmanagement.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author zzraly
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class YieldValueFunctionDTO implements Serializable {

    @XmlElement
    private String yieldValueFunctionType; 
    
    @XmlElement(name = "factStructure")
    @XmlElementWrapper(name="factStructureList")
    List<FactStructureDTO> factStructureList;

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
    public List<FactStructureDTO> getFactStructureList() {
        return factStructureList;
    }

    /**
     * @param factStructureList the factStructureList to set
     */
    public void setFactStructureList(List<FactStructureDTO> factStructureList) {
        this.factStructureList = factStructureList;
    }

    
}
