/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by bobhurt on 8/14/12
 */
package org.kuali.student.enrollment.kitchensink;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KitchenSinkFormCollection1 {

    private Integer id;
    private String name;
    private String description;
    private Date date;
    private static Integer count = 0;

    public KitchenSinkFormCollection1() { }

    public KitchenSinkFormCollection1(String name, String description, String dateString) {
        this.id = ++count;
        this.name = name;
        this.description = description;
        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        }
        catch (ParseException ex) {
            this.date = new Date();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
