/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.ui.server.screenreport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.server.screenreport.jasper.JasperScreenReportProcessorImpl;

public class TestScreenReport {

    private Data dataMap;
    
    private List<ExportElement> dataList; 

    @Before
    public void setup() {

        dataMap = new Data();

        dataMap.set("key1", "value1");
        Data subData = new Data();
        subData.set("firstName", "Jon");
        subData.set("ssn", "123456789");
        dataMap.set("key2", subData);

        Data subData2 = new Data();
        subData2.set("firstName", "who");
        subData2.set("ssn", "123456789");

        Data subData2a = new Data();
        subData2a.set("third", "value");
        subData2.set("level", subData2a);
        dataMap.set("key3", subData2);

        Data subData3 = new Data();
        subData3.set("firstName", "Me");
        subData3.set("ssn", "123456789");
        dataMap.set("key4", subData3);

        dataMap.set("key5", "value5");
        
        dataList = new ArrayList<ExportElement>();
        
        ExportElement element1 = new ExportElement();
        element1.setFieldLabel("First label");
        element1.setFieldValue("First value");
        dataList.add(element1);
        
        ExportElement element2 = new ExportElement();
        element2.setFieldLabel("Second label");
        element2.setFieldValue("Second value");
        dataList.add(element2);
        
        ExportElement element3 = new ExportElement();
        element3.setFieldLabel("Third label");
        element3.setFieldValue("Third value");
        dataList.add(element3);
        
        List<ExportElement> subElements = new ArrayList<ExportElement>();
        ExportElement subElement1 = new ExportElement();
        subElement1.setFieldLabel("First sublabel");
        subElement1.setFieldValue("First subvalue");
        subElements.add(subElement1);
        
        ExportElement subElement2 = new ExportElement();
        subElement2.setFieldLabel("Second sublabel");
        subElement2.setFieldValue("Second subvalue");
        subElements.add(subElement2);
        
        ExportElement element4 = new ExportElement();
        element4.setFieldLabel("Subset label");
        element4.setSubset(subElements);
        dataList.add(element4);

    }

    @Test
    public void testScreenReport() {

        ScreenReportProcessor processor = new JasperScreenReportProcessorImpl();

        byte[] bytes = processor.createPdf(dataMap, "base.template");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataMap.pdf");
        Assert.assertTrue(bytes.length > 0);

        bytes = processor.createDoc(dataMap, "base.template");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataMap.doc");
        Assert.assertTrue(bytes.length > 0);
        
        bytes = processor.createXls(dataMap, "base.template");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataMap.xls");
        Assert.assertTrue(bytes.length > 0);
        
        bytes = processor.createPdf(dataList, "base.template");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataList.pdf");
        Assert.assertTrue(bytes.length > 0);

        bytes = processor.createDoc(dataList, "base.template");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataList.doc");
        Assert.assertTrue(bytes.length > 0);
        
        bytes = processor.createXls(dataList, "base.template");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataList.xls");
        Assert.assertTrue(bytes.length > 0);
        
    }

    private void printToFile(byte[] bytes, String fileName) {
        OutputStream out;
        try {
            out = new FileOutputStream(fileName);
            out.write(bytes);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
