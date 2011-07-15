package org.kuali.student.lum.program.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import org.kuali.student.common.assembly.data.Data;

import java.util.HashMap;

/**
 * @author Igor
 */
public class ProgramRegistry {

    private static Data data;

    private static int row;

    private static Enum<?> section;

    private static HashMap<GwtEvent.Type, EventHandler> specializationHandlers = new HashMap<GwtEvent.Type, EventHandler>();

    private static boolean createNew = false;

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

    public static void addHandler(GwtEvent.Type<?> type, EventHandler handler) {
        specializationHandlers.put(type, handler);
    }

    public static HashMap<GwtEvent.Type, EventHandler> getSpecializationHandlers() {
        return specializationHandlers;
    }

    public static boolean isCreateNew() {
        return createNew;
    }

    public static void setCreateNew(boolean createNew) {
        ProgramRegistry.createNew = createNew;
    }
}
