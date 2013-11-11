package org.kuali.student.ap.audit.dto;

import org.kuali.student.ap.audit.infc.AuditProgram;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * PlanItem message structure
 *
 * @Author hemanthg
 * Date: 5/17/12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuditProgramInfo", propOrder = {"programId", "programTitle"})
public class AuditProgramInfo  implements AuditProgram, Serializable {

    @XmlAttribute
    private String programId;

    @XmlAttribute
    private String programTitle;

    @Override
    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    @Override
    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }
}
