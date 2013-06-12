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
    private List<CluInformation> clus;

    private boolean showClus = true;
    private boolean showTitle = true;

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

    public List<CluInformation> getClus() {
        return clus;
    }

    public void setClus(List<CluInformation> clus) {
        this.clus = clus;
    }

    public boolean getShowClus() {
        return showClus;
    }

    public void setShowClus(boolean showClus) {
        this.showClus = showClus;
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }


}
