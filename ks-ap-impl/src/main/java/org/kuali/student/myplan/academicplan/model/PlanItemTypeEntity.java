package org.kuali.student.myplan.academicplan.model;



import javax.persistence.Entity;
import javax.persistence.Table;

/**
 */
@Entity
@Table(name = "KSPL_LRNG_PLAN_ITEM_TYPE")
public class PlanItemTypeEntity extends TypeEntity<PlanItemAttributeEntity> {}
