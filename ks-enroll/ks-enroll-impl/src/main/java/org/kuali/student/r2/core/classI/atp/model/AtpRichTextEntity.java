package org.kuali.student.r2.core.classI.atp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.RichTextEntity;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.core.classI.atp.dto.AtpInfo;

@Entity
@Table(name = "KSEN_RICH_TEXT_T")
public class AtpRichTextEntity extends RichTextEntity{

    public AtpRichTextEntity(){}
    
    public AtpRichTextEntity(RichText rt){
        this.setFormatted(rt.getFormatted());
        this.setPlain(rt.getPlain());
    }
    
    public RichTextInfo toDto() {
        RichTextInfo.Builder rtiBuilder = new RichTextInfo.Builder();
        rtiBuilder.setPlain(getPlain());
        rtiBuilder.setFormatted(getFormatted());
        RichTextInfo atpDesc = rtiBuilder.build();
        return atpDesc;
    }
}
