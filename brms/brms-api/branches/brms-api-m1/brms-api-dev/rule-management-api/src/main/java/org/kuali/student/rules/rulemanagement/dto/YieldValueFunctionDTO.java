/**
 * 
 */
package org.kuali.student.rules.rulemanagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.kuali.student.rules.factfinder.dto.FactStructureDTO;

/**
 * @author zzraly
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class YieldValueFunctionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String yieldValueFunctionType; 
    
    @XmlElement(name = "factStructure")
    @XmlElementWrapper(name="factStructureList")
    List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();

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
