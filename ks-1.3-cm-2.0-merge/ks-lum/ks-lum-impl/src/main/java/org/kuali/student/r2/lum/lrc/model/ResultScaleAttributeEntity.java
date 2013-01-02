package org.kuali.student.r2.lum.lrc.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LRC_RESULT_SCALE_ATTR")
public class ResultScaleAttributeEntity extends BaseAttributeEntity<ResultScaleEntity> {

	public ResultScaleAttributeEntity() {
		super();
	}

	public ResultScaleAttributeEntity(Attribute att, ResultScaleEntity owner) {
		super(att, owner);
	}


//
//    @Override
//    public boolean equals(Object obj) {
//        if (this.getId() == null) {
//            return super.equals(obj);
//        }
//        if (!(obj instanceof ResultScaleAttributeEntity)) {
//            return false;
//        }
//        ResultScaleAttributeEntity thatObj = (ResultScaleAttributeEntity) obj;
//        String thatId = thatObj.getId();
//        if (thatId == null) {
//            return super.equals(obj);
//        }
//        return thatId.equals(this.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        if (this.getId() == null) {
//            return super.hashCode();
//        }
//        return this.getId().hashCode();
//    }
}
