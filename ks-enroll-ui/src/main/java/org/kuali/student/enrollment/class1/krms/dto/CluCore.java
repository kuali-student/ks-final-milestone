package org.kuali.student.enrollment.class1.krms.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/06/07
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class CluCore implements Serializable, Comparable<CluCore> {

    private String code;
    private String shortName;
    private String credits;

    public CluCore(){
        super();
    }

    public CluCore(String code, String shortName, String credits){
        this.code = code;
        this.shortName = shortName;
        this.credits = credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int compareTo(CluCore clu) {
        return this.code.compareTo(clu.getCode());
    }

    public void clear() {
        this.code = null;
        this.shortName = null;
        this.credits = null;
    }

}
