/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 9/17/13
 */
package org.kuali.student.poc.eventproc.handler.impl.helper;

import org.apache.log4j.Logger;
import org.kuali.student.poc.eventproc.KSEventProcessorImpl;
import org.kuali.student.poc.eventproc.handler.impl.ActivityOfferingDropADLsHandler;
import org.kuali.student.poc.eventproc.handler.impl.ActivityOfferingStateChangeHandler;
import org.kuali.student.poc.eventproc.handler.impl.CourseOfferingRecomputeStateHandler;
import org.kuali.student.poc.eventproc.handler.impl.FormatOfferingRecomputeStateHandler;
import org.kuali.student.poc.eventproc.handler.impl.RegGroupInvalidateStateHandler;
import org.kuali.student.poc.eventproc.handler.impl.RegGroupRecomputeStateHandler;

/**
 * Loads handlers into the EventProcessor
 *
 * @author Kuali Student Team
 */
public class KSHandlerLoader {
    public static final Logger LOGGER = Logger.getLogger(KSHandlerLoader.class);
    public static void loadHandlersIntoEventProcessor(KSEventProcessorImpl processor) {
        LOGGER.info("========== Loading handlers");
        ActivityOfferingStateChangeHandler aoHandler = new ActivityOfferingStateChangeHandler(processor);
        processor.registerHandler(aoHandler);
        FormatOfferingRecomputeStateHandler foHandler = new FormatOfferingRecomputeStateHandler(processor);
        processor.registerHandler(foHandler);
        CourseOfferingRecomputeStateHandler coHandler = new CourseOfferingRecomputeStateHandler(processor);
        processor.registerHandler(coHandler);
        RegGroupRecomputeStateHandler rgHandler = new RegGroupRecomputeStateHandler(processor);
        processor.registerHandler(rgHandler);
        RegGroupInvalidateStateHandler rgInvalidHandler = new RegGroupInvalidateStateHandler(processor);
        processor.registerHandler(rgInvalidHandler);
        ActivityOfferingDropADLsHandler dropHandler = new ActivityOfferingDropADLsHandler(processor);
        processor.registerHandler(dropHandler);
    }
}
