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

/**
 * Type for events fired between controllers and views. UncheckedApplicationEvents are automatically propagated to the
 * topmost controller for handler registration and event dispatch. For events that do not automatically propagate to the
 * topmost controller, see ApplicationEvent
 * 
 * @author Kuali Student Team
 * @param <H>
 *            Handler type associated with the event
 */
public abstract class UncheckedApplicationEvent<H extends UncheckedApplicationEventHandler> extends ApplicationEvent<H> {

}
