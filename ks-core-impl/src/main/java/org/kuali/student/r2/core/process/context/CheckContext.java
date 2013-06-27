package org.kuali.student.r2.core.process.context;

import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

public class CheckContext {
    
    private InstructionInfo instruction;    
    private CheckInfo check;

    public CheckInfo getCheck() {
        return check;
    }

    public void setCheck(CheckInfo check) {
        this.check = check;
    }

    public InstructionInfo getInstruction() {
        return instruction;
    }

    public void setInstruction(InstructionInfo instruction) {
        this.instruction = instruction;
    } 
    
}
