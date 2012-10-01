/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.state.service.impl;


import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.state.dto.LifecycleInfo;
import org.kuali.student.r2.common.state.dto.StateInfo;


/**
 *  This uses the State Service mock implementation to nail up some
 *  example data.
 */

public class StateServiceMockDataImpl 
    extends StateServiceMockImpl {

    public StateServiceMockDataImpl()
        throws Exception {

        addLifecycle("kuali.atp.process", "ATP Lifecycle", "Lifecycle process for Academic Time Periods.", "");
        addState("kuali.atp.process", "kuali.atp.state.Draft", "Draft", "Indicates that this ATP is tentative.");
        addState("kuali.atp.process", "kuali.atp.state.Official", "Official", "Indicates that this ATP has been established.");

        addLifecycle("kuali.milestone.process", "Milestone Lifecycle", "Lifecycle process for Milestones.", "");
        addState("kuali.milestone.process", "kuali.milestone.state.Draft", "Draft", "Indicates that this Milestone is tentative.");
        addState("kuali.milestone.process", "kuali.milestone.state.Official", "Official", "Indicates that this Milestone has been established.");        
    }

    protected void addLifecycle(String key, String name, String desc, String ref)
        throws Exception {

        LifecycleInfo lifecycle = new LifecycleInfo();
        lifecycle.setName(name);

        RichTextInfo rtDesc = new RichTextInfo();
        rtDesc.setPlain(desc);
        rtDesc.setFormatted(desc);
        lifecycle.setDescr(rtDesc);

        lifecycle.setRefObjectUri(ref);

        createLifecycle(key, lifecycle, new ContextInfo());
    }

    protected void addState(String lifecycleKey, String key, String name, String desc)
        throws Exception {

        StateInfo state = new StateInfo();
        state.setName(name);

        RichTextInfo rtDesc = new RichTextInfo();
        rtDesc.setPlain(desc);
        rtDesc.setFormatted(desc);
        state.setDescr(rtDesc);

        createState(lifecycleKey, key, state, new ContextInfo());
    }
}
