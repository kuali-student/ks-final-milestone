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

package org.kuali.student.common.ui.client.widgets.list.testData;

import org.kuali.student.core.dto.Idable;

public class Color implements Idable{
    private String id;
    private String color;
    private String warmth;
    private String type;
    
    public Color(String id, String color, String warmth, String type) {
        super();
        this.color = color;
        this.warmth = warmth;
        this.id = id;
        this.type = type;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setWarmth(String warmth) {
        this.warmth = warmth;
    }
    public String getWarmth() {
        return warmth;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    
}
