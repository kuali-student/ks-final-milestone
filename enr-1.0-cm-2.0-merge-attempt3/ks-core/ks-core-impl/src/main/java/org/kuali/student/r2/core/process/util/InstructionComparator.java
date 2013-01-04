package org.kuali.student.r2.core.process.util;

import org.kuali.student.r2.core.process.dto.InstructionInfo;

import java.util.Comparator;

public class InstructionComparator implements Comparator<InstructionInfo> {

    @Override
    public int compare(InstructionInfo arg0, InstructionInfo arg1) {
        
        if (arg0.getPosition() > arg1.getPosition())
            return 1;
        else if (arg0.getPosition() < arg1.getPosition())
            return -1;

        return 0;

    }

}
