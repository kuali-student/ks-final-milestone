package org.kuali.student.r1.core.subjectcode.model;

import org.kuali.student.r2.common.entity.MetaEntity;

import javax.persistence.*;

@Entity
@Table(name = "KSSC_SUBJ_CD")
public class SubjectCode extends MetaEntity {

	private static final long serialVersionUID = 8238736199474085646L;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name="TYPE")
    private SubjectCodeType type;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "CD")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public SubjectCodeType getType() {
        return type;
    }

    public void setType(SubjectCodeType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
