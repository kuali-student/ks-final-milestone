package org.kuali.student.enrollment.class2.courseofferingset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import javax.persistence.UniqueConstraint;
import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSEN_SOC_ROR_OPTION", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"OPTION_ID", "ROR_ID"})})
public class SocRolloverResultOptionEntity {

    @Id
    @Column(name = "ID")
    private String id;
    @ManyToOne
    @JoinColumn(name = "ROR_ID")
    private SocRolloverResultEntity socRolloverResult;
    @Column(name = "OPTION_ID")
    private String optionId;

    public SocRolloverResultOptionEntity() {
    }

    public SocRolloverResultOptionEntity(String optionId, SocRolloverResultEntity socRolloverResult) {
        this.socRolloverResult = socRolloverResult;
        this.optionId = optionId;
    }

    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SocRolloverResultEntity getSocRolloverResult() {
        return socRolloverResult;
    }

    public void setSocRolloverResult(SocRolloverResultEntity socRolloverResult) {
        this.socRolloverResult = socRolloverResult;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }
}
