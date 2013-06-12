package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.student.enrollment.class1.krms.dto.CluCore;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/06/07
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class CluGroup implements Serializable {

    private String title;
    private Boolean showClus;
    private List<CluInformation> clus;

    public CluGroup(){
        super();
    }

    public CluGroup(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getShowClus() {
        return showClus;
    }

    public void setShowClus(Boolean showClus) {
        this.showClus = showClus;
    }

    public List<CluInformation> getClus() {
        return clus;
    }

    public void setClus(List<CluInformation> clus) {
        this.clus = clus;
    }
}
