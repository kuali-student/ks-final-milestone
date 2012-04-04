/*
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.program.dto.assembly;

import java.util.List;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.MetaInfo;

/**
 * R1 implementation Compatibility
 * @author nwright
 * @deprecated1
 */
@Deprecated
public interface ProgramCommonAssembly {

    public List<AttributeInfo> getAttributes();

    public void setAttributes(List<AttributeInfo> attributes);

    public MetaInfo getMeta();

    public void setMeta(MetaInfo meta);

    public String getTypeKey();

    public void setTypeKey(String type);

    public String getStateKey();

    public void setStateKey(String state);

    public String getId();

    public void setId(String id);
}
