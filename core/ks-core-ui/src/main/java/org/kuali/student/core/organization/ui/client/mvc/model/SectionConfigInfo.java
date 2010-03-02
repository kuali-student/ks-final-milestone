package org.kuali.student.core.organization.ui.client.mvc.model;

import java.io.Serializable;
import java.util.List;

public class SectionConfigInfo implements Serializable{
    private List<SectionViewInfo> sectionViewInfoList;
    
    public List<SectionViewInfo> getSectionViewInfoList(){
        return sectionViewInfoList;
    }
    
    public void setSectionViewInfoList(List<SectionViewInfo> sectionViewInfoList){
        this.sectionViewInfoList=sectionViewInfoList;
    }
       
}
