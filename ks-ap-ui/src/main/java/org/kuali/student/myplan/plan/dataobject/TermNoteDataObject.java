package org.kuali.student.myplan.plan.dataobject;

import java.util.Date;


public class TermNoteDataObject {
    private String id;
    private String termNote;
    private String atpId;
    private Date date;

    public TermNoteDataObject(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTermNote() {
        return termNote;
    }

    public void setTermNote(String termNote) {
        this.termNote = termNote;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTermNoteUI(){
        return getTermNote();
    }

}
