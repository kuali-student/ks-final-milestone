package org.kuali.student.lum.program.client;

import org.kuali.student.core.assembly.data.Data;

/**
 * @author Igor
 */
public class ProgramRegistry {

    private static Data data;

    private static int row;

    private static Enum<?> section;

    public static Data getData() {
        return data;
    }

    public static void setData(Data data) {
        ProgramRegistry.data = data;
    }

    public static int getRow() {
        return row;
    }

    public static void setRow(int row) {
        ProgramRegistry.row = row;
    }

    public static Enum<?> getSection() {
        return section;
    }

    public static void setSection(Enum<?> section) {
        ProgramRegistry.section = section;
    }
}
