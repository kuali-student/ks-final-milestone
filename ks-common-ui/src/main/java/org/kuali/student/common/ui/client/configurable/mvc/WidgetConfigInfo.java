package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Metadata.WriteAccess;

public class WidgetConfigInfo {
	public DataType type = null;
	public Integer maxLength = null;
	public WriteAccess access = null;
	public boolean isRichText = false;
	public boolean isMultiLine = false;
	public boolean isRepeating = false;
	public Metadata metadata = null;
	public LookupMetadata lookupMeta = null;
	public List<LookupMetadata> additionalLookups = null;
	public boolean canEdit = true;
	public boolean canUnmask = false;
	public boolean canView = true;
}
