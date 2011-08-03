package org.kuali.student.enrollment.class1.lpr.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpMilestoneRelationAttributeEntity;


@Entity
@Table(name = "KSLP_LPRROSTER_LUI_RELTN")
public class LprRosterLuiRelationEntity extends MetaEntity { 

    @ManyToOne
    @JoinColumn(name="LPRROSTER_ID")
    private LprRosterEntity lprRoster;
    
    @ManyToOne
    @JoinColumn(name="LUI_ID")
    private LuiEntity lui;
    
    @ManyToOne
    @JoinColumn(name="LRL_RELTN_TYPE_ID")
    private LprRosterTypeEntity lprRosterType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "LPRROSTER_STATE_ID")
    private LprRosterStateEntity lprRosterState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<AtpMilestoneRelationAttributeEntity> attributes;
    

}

