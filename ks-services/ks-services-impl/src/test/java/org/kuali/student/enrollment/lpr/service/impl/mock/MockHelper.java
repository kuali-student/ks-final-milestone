 /*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.impl.mock;

import java.util.Date;

import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.MetaInfc;

/**
 * A helper class for the Mock implementation
 * @author nwright
 */
public class MockHelper {

    /**
     * Create a new MetaInfo with the create id and time as well as the update
     * date and time and version ind set
     * @param context
     * @return
     */
    public MetaInfo createMeta(ContextInfc context) {
        MetaInfo.Builder builder = new MetaInfo.Builder();
        Date now = new Date();
        builder.createId(context.getPrincipalId()).createTime(now).updateId(context.getPrincipalId());
        return builder.updateTime(now).versionInd("1").build();
    }

    /**
     * Updates the MetaInfo with new user id and date stamp
     * Also increments the verssion indicator
     * @param orig
     * @param context
     * @return new metainfo
     */
    public MetaInfo updateMeta(MetaInfc orig, ContextInfc context) {
        Date now = new Date();
        int oldVersionInd = Integer.parseInt(orig.getVersionInd());
        return new MetaInfo.Builder(orig).updateId(context.getPrincipalId()).updateTime(now).versionInd("" + (oldVersionInd + 1)).build();
    }
}

