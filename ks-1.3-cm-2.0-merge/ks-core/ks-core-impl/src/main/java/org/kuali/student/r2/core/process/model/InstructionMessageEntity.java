package org.kuali.student.r2.core.process.model;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.RichTextEntity;
import org.kuali.student.r2.common.infc.RichText;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_INSTR_MESSAGE")
public class InstructionMessageEntity extends RichTextEntity {

    public InstructionMessageEntity() {

    }

    public InstructionMessageEntity(String plain, String formatted){
        this.setFormatted(formatted);
        this.setPlain(plain);
    }

    public InstructionMessageEntity(RichText rt) {
        if (null != rt) {
            this.setFormatted(rt.getFormatted());
            this.setPlain(rt.getPlain());
        }
    }

    public RichTextInfo toDto() {
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(getPlain());
        rti.setFormatted(getFormatted());
        return rti;
    }

}
