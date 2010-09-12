package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;

/**
 * @author Igor
 */
public class ProgramWidgetFactory {

    public static TableSection createTableSection(SectionTitle sectionTitle) {
        TableSection section = new TableSection(sectionTitle);
        section.addStyleName("programTableSection");
        return section;
    }

    public static TableSection createTableSection() {
        return createTableSection(SectionTitle.generateEmptyTitle());
    }
}
