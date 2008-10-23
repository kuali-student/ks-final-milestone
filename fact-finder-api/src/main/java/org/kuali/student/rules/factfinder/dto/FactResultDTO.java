package org.kuali.student.rules.factfinder.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * This class contains the result of a fetchFact call 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FactResultDTO implements Serializable{

    @XmlElement
    FactResultTypeInfoDTO factResultTypeInfo;

    @XmlElement
    List<HashMap<String, String>> resultList;

    /**
     * @return the factResultTypeInfo
     */
    public FactResultTypeInfoDTO getFactResultTypeInfo() {
        return factResultTypeInfo;
    }

    /**
     * @param factResultTypeInfo the factResultTypeInfo to set
     */
    public void setFactResultTypeInfo(FactResultTypeInfoDTO factResultTypeInfo) {
        this.factResultTypeInfo = factResultTypeInfo;
    }

    /**
     * @return the resultList
     */
    public List<HashMap<String, String>> getResultList() {
        return resultList;
    }

    /**
     * @param resultList the resultList to set
     */
    public void setResultList(List<HashMap<String, String>> resultList) {
        this.resultList = resultList;
    }
}
