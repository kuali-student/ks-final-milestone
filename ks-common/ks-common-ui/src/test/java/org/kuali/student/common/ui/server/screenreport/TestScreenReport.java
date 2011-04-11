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

        String value = null;
        dataMap.set("key6", value);

        dataMap.set("key7", "This is a very long description. The impact of public policies on the black community and the role of the policy process in affecting the social, economic and political well-being of minorities. Particular attention given to the post-1960 to present era.");

        //Adding lots of extra values to test the page numbers.
        dataMap.set("key8", "simple value");
        dataMap.set("key9", "simple value");
        dataMap.set("key10", "simple value");
        dataMap.set("key11", "simple value");
        dataMap.set("key12", "simple value");
        dataMap.set("key13", "simple value");
        dataMap.set("key14", "simple value");
        dataMap.set("key15", "simple value");
        dataMap.set("key16", "simple value");
        dataMap.set("key17", "simple value");
        dataMap.set("key18", "simple value");
        dataMap.set("key19", "simple value");
        dataMap.set("key20", "simple value");
        
        dataList = new ArrayList<ExportElement>();
        
        ExportElement element0 = new ExportElement();
        element0.setFieldLabel("My label");
        element0.setFieldValue("Proposal");
        element0.setFieldValue2("Original Course");
        dataList.add(element0);

        ExportElement element1 = new ExportElement();
        element1.setFieldLabel("First label");
        element1.setFieldValue("First value");
        dataList.add(element1);

        ExportElement element2 = new ExportElement();
        element2.setSectionName("Section 1");
        element2.setFieldLabel("Second label");
        element2.setFieldValue("Second value");
        element2.setMandatory(true);
        dataList.add(element2);

        ExportElement element3 = new ExportElement();
        element3.setSectionName("Section 1");
        element3.setFieldLabel("Third label");
        element3.setFieldValue("Third value");
        element3.setFieldValue2("Third value O");
        dataList.add(element3);

        List<ExportElement> subElements = new ArrayList<ExportElement>();
        ExportElement subElement1 = new ExportElement();
        subElement1.setSectionName("Subsection");
        subElement1.setFieldLabel("First sublabel");
        subElement1.setFieldValue("First subvalue");
        subElement1.setFieldValue2("First subvalue O");
        subElements.add(subElement1);

        ExportElement subElement2 = new ExportElement();
        subElement1.setSectionName("Subsection");
        subElement2.setFieldLabel("Second sublabel");
        subElement2.setFieldValue("Second subvalue");
        subElement2.setFieldValue2("Second subvalue");
        subElements.add(subElement2);
        
        List<ExportElement> subsubElements = new ArrayList<ExportElement>();
        ExportElement subsubElement1 = new ExportElement();
        subsubElement1.setSectionName("Subsection");
        subsubElement1.setFieldLabel("First sublabel");
        subsubElement1.setFieldValue("First subvalue");
        subsubElement1.setFieldValue2("First subvalue O");
        subsubElements.add(subsubElement1);
        
        ExportElement subelement3 = new ExportElement();
        subelement3.setSectionName("Subsection");
        subelement3.setFieldLabel("Subsubset label");
        subelement3.setSubset(subsubElements);
        subElements.add(subelement3);

        ExportElement element4 = new ExportElement();
        element4.setSectionName("Section 1");
        element4.setFieldLabel("Subset label");
        element4.setSubset(subElements);
        dataList.add(element4);
        
        ExportElement element5 = new ExportElement();
        element5.setSectionName("Section 2");
        element5.setFieldLabel("5th label");
        element5.setFieldValue("5th value");
        element5.setFieldValue2("5th value");
        dataList.add(element5);
        
        ExportElement element6 = new ExportElement();
        element6.setSectionName("Section 2");
        element6.setFieldLabel("6th label");
        element6.setFieldValue("6th value");
        element6.setFieldValue2("6th value");
        element6.setMandatory(true);
        dataList.add(element6);

    }

    @Test
    public void testScreenReport() {

        ScreenReportProcessor processor = new JasperScreenReportProcessorImpl();

        byte[] bytes = processor.createPdf(dataMap, "base.template", "Course Information");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataMap.pdf");
        Assert.assertTrue(bytes.length > 0);

        bytes = processor.createDoc(dataMap, "base.template", "Course Information");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataMap.doc");
        Assert.assertTrue(bytes.length > 0);

        bytes = processor.createXls(dataMap, "base.template", "Course Information");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataMap.xls");
        Assert.assertTrue(bytes.length > 0);

        bytes = processor.createPdf(dataList, "base.template", "Course Information");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataList.pdf");
        Assert.assertTrue(bytes.length > 0);
        
        bytes = processor.createPdf(dataList, "proposal.template", "Course Information");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataList2.pdf");
        Assert.assertTrue(bytes.length > 0);

        bytes = processor.createDoc(dataList, "base.template", "Course Information");
        Assert.assertNotNull(bytes);
        printToFile(bytes, "c:\\dataList.doc");
        Assert.assertTrue(bytes.length > 0);

        bytes = processor.createXls(dataList, "base.template", "Course Information");
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
