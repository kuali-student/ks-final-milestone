package org.kuali.student.r2.core.classI.atp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.TypeEntity;

@Entity
@Table(name = "KSEN_ATPATP_RELTN_TYPE")
public class AtpAtpRelationTypeEntity extends TypeEntity<AtpAtpRelationAttributeEntity>{

}
