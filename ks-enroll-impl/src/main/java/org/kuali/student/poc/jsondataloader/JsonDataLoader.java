/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by Charles on 8/8/2014
 */
package org.kuali.student.poc.jsondataloader;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.poc.jsonparser.json.BaseJsonObject;
import org.kuali.student.poc.jsonparser.json.SimpleJsonMap;
import org.kuali.student.poc.jsonparser.parser.SimpleJsonParser;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * Main data loader
 *
 * @author Kuali Student Team
 */
public class JsonDataLoader {
    private String resourcePath;
    private JsonCourseOfferingDataLoader coLoader;
    private JsonLprDataLoader lprLoader;

    public JsonDataLoader(String resourcePath, LprService lprService, CourseOfferingService coService) {
        this.resourcePath = resourcePath;
        this.coLoader = new JsonCourseOfferingDataLoader();
        this.coLoader.setCourseOfferingService(coService);
        this.lprLoader = new JsonLprDataLoader();
        this.lprLoader.setLprService(lprService);
    }

    public void clearData() {
        this.coLoader.clearData();
        this.lprLoader.clearData();
    }

    public void loadData() {
        SimpleJsonParser parser = new SimpleJsonParser(resourcePath);
        ContextInfo contextInfo = new ContextInfo();
        try {
            for (BaseJsonObject jsonObject: parser) {
                SimpleJsonMap jsonMap = (SimpleJsonMap) jsonObject;
                String typeStr = jsonMap.getAsString("type");
                if (this.coLoader.acceptsType(typeStr)) {
                    coLoader.loadData(jsonMap, contextInfo);
                } else if (this.lprLoader.acceptsType(typeStr)) {
                    lprLoader.loadData(jsonMap, contextInfo);
                }
            }
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
    }
}
