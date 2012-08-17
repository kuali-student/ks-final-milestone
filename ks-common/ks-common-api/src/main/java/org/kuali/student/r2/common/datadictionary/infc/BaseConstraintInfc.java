/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.datadictionary.infc;

import org.kuali.rice.krad.datadictionary.validation.constraint.Constraint;

/**
 * A Base constriant.
 *
 * @author nwright
 */

public interface BaseConstraintInfc 
    extends Constraint {
	

    /**
     * LabelKey should be a single word key.  This key is used to find
     * a message to use for this constraint from available messages.
     * The key is also used for defining/retrieving validation method
     * names when applicable - as such this key MUST exist for valid
     * character constraints.
     *
     * @name Label Key
     */
    public String getLabelKey();

    /**
     * If this is true, the constraint should be applied on the client
     * side when the user interacts with a field - if this constraint
     * can be interpreted for client side use. Default is true.
     *
     * @name Is Apply Client Side
     */
    public Boolean getIsApplyClientSide();
}
