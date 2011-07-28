package org.kuali.student.enrollment.courseoffering.infc;

import java.util.List;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;

public interface COBasicAssembly {
	 public String getId();
	 public void setId(String id);
	 
	 public String getTypeKey();
	 public void setTypeKey(String typeKey);
	 
	 public String getStateKey();
	 public void setStateKey(String stateKey);
	 
	 public RichTextInfo getDescr();
	 public void setDescr(RichTextInfo descr); 
	 
	 public MetaInfo getMeta();
	 public void setMeta(MetaInfo metaInfo);
	 
	 public List<AttributeInfo> getAttributes();
	 public void setAttributes(List<AttributeInfo> attributes);
}
