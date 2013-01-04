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

import org.kuali.student.r2.common.util.date.DateFormatters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class just holds fields for the collection properties in KitchenSinkForm
 *
 * @author Kuali Student Team
 */
public class KitchenSinkFormCollection1 {

    private Boolean selected;
    private Integer id;
    private String name;
    private String description;
    private Date date;
    private List<KitchenSinkFormCollection2> list1;
    private List<KitchenSinkFormCollection2> list3;

    public KitchenSinkFormCollection1() {
    }

    public KitchenSinkFormCollection1(KitchenSinkFormCollection1 collection) {
        this.selected = collection.getSelected();
        this.id = collection.getId();
        this.name = collection.getName();
        this.description = collection.getDescription();
        this.date = collection.getDate();
    }

    public KitchenSinkFormCollection1(String name, String description, String dateString) {
        this.id = ++count;
        this.name = name;
        this.description = description;
        try {
            this.date = DateFormatters.DEFAULT_DATE_FORMATTER.parse(dateString);
        } catch (IllegalArgumentException ex) {
            this.date = new Date();
        }
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
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

    public List<KitchenSinkFormCollection2> getList1() {
        return list1;
    }

    public void setList1(List<KitchenSinkFormCollection2> list1) {
        this.list1 = list1;
    }

    public List<KitchenSinkFormCollection2> getList3() {
        return list3;
    }

    public void setList3(List<KitchenSinkFormCollection2> list3) {
        this.list3 = list3;
    }

    // --- STATIC METHODS ---

    private static Integer count = 0;

    public static Integer assignId() {
        return ++count;
    }

    public static List<KitchenSinkFormCollection1> clone(List<KitchenSinkFormCollection1> collection1List) {
        List<KitchenSinkFormCollection1> clonedList = new ArrayList<KitchenSinkFormCollection1>(collection1List.size());
        for (KitchenSinkFormCollection1 collection : collection1List) {
            clonedList.add(new KitchenSinkFormCollection1(collection));
        }
        return clonedList;
    }
}
