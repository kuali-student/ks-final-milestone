/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.server.mvc.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;


public class DTOSerializationTest {
    @Test public void dtoTest(){
        
    }
    
    @Test public void basicDataTypeTest() {
       try {
            Person p = new Person();
            p.setName("asdf");
            p.setIntValue(1);
            p.setBooleanValue(true);
            p.setDoubleValue(3.0D);
            p.setFloatValue(3.0F);
            p.setLongValue(9L);
            HashMap map = new HashMap();
            map.put("2", "1");
            p.setMap(map);
            p.setArrayList(Arrays.asList("1234","asdf"));
            p.setCh('a');
            p.setDate(new Date());
            Department department = new Department();
            department.setName("CS");
            p.setDepartment(department);
         
            
            MapContext ctx = new MapContext();
            ModelDTO dto = ctx.fromBean(p);
            
            Person p2 = (Person)ctx.fromModelDTO(dto);
            Assert.assertEquals(p.getName(),p2.getName());
            Assert.assertEquals(p.getArrayList(),p2.getArrayList());
            Assert.assertEquals(p.getIntValue(),p2.getIntValue());
            Assert.assertEquals(p.isBooleanValue(),p2.isBooleanValue());
            Assert.assertEquals(p.getFloatValue(),p2.getFloatValue(),2);
            Assert.assertEquals(p.getDoubleValue(),p2.getDoubleValue(),2);
            Assert.assertEquals(p.getLongValue(),p2.getLongValue());
            Assert.assertEquals(p.getMap(),p2.getMap());
            Assert.assertEquals(p.getDate(),p2.getDate());            
            Assert.assertEquals(p.getCh(),p2.getCh());
            Assert.assertEquals(p.getDepartment().getName(),p2.getDepartment().getName());
        } catch (BeanMappingException e) {
            e.printStackTrace();
        }
    }
}

class Department{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
class Person{
    private String name ="";
    private List<String> bytes = new ArrayList<String>();
    private Department department;
    private int intValue;
    private double doubleValue;
    private float floatValue;
    private long longValue;
    private boolean booleanValue;
    private Map map ;
    
    private Date date;
    private char ch;
    
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public char getCh() {
        return ch;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<String> getArrayList() {
        return bytes;
    }

    public void setArrayList(List<String> bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
