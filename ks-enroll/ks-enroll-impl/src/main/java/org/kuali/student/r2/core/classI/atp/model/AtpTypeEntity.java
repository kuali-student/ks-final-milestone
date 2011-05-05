package org.kuali.student.r2.core.classI.atp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.TypeEntity;

@Entity
@Table(name = "KSEN_ATP_TYPE")
public class AtpTypeEntity extends TypeEntity<AtpAttributeEntity>{

}
