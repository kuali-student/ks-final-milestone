package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.List;

public interface HasSectionDeletion {
	public List<Section> getDeletedSections();
	public List<String> getDeletionParentKeys();
    /**
     * deletionParentKeys is optional and is only needed when you want to delete the
     * entire structure in addition to individual fields with in deleted sections.
     * 
     * @see SectionBinding#setModelValue(Section, org.kuali.student.common.ui.client.mvc.DataModel, String)
     */
	public void setDeletionParentKey(List<String> deletionParentKeys);
}
