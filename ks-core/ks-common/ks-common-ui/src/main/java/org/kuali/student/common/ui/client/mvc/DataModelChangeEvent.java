/**
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

package org.kuali.student.common.ui.client.mvc;

import org.kuali.student.r1.common.assembly.data.QueryPath;

/**
 * Event that is fired when the model is changed.
 * 
 * @author Kuali Student Team
 * @param <T>
 */
@Deprecated
public class DataModelChangeEvent extends ModelChangeEvent {
    private final QueryPath path;
    
    /**
     * Constructs a new ModelChangeEvent with an action and a QueryPath
     * 
     * @param action
     * @param path the path that was changed
     */
    public DataModelChangeEvent(Action action, Model source, QueryPath path) {
        super(action, source);
        this.path = path;
    }

	public QueryPath getPath() {
		return path;
	}

}
