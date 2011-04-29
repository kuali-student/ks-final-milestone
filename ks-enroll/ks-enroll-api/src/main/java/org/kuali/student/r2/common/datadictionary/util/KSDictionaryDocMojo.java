/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.datadictionary.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
//import org.apache.maven.plugin.AbstractMojo;
//import org.apache.maven.plugin.MojoExecutionException;

/**
 * Mojo for generating a formatted view of the data dictionary
 * @goal ksdictionarydoc
 * @requiresDependencyResolution
 */
public class KSDictionaryDocMojo 
//  extends AbstractMojo
{

    /**
     * @parameter
     **/
    private List<String> inputFiles;
    /**
     * @parameter expression="${htmlDirectory}" default-value="${project.build.directory}/site/services/dictionarydocs"
     */
    private File htmlDirectory;
    /**
     * @parameter
     **/
    private String projectUrl;

    public void setHtmlDirectory(File htmlDirectory) {
        this.htmlDirectory = htmlDirectory;
    }

    public void setInputFiles(List<String> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

//    @Override
    public void execute()
//      throws MojoExecutionException
    {
        //System.out.println ("Writing java class: " + fileName + " to " + dir.getAbsolutePath ());
        if (!htmlDirectory.exists()) {
            if (!htmlDirectory.mkdirs()) {
//                throw new MojoExecutionException("Could not create directory "
                throw new IllegalArgumentException("Could not create directory "
                        + this.htmlDirectory.getPath());
            }
        }

        List<String> outputFiles = new ArrayList<String>(this.inputFiles.size());
        for (String dictFileName : this.inputFiles) {
            String outputFileName = replaceXmlWithHtml(dictFileName);
            String fullOutputFileName = this.htmlDirectory.getAbsolutePath() + "/" + outputFileName;
            outputFiles.add(outputFileName);
            DictionaryTesterHelper tester = new DictionaryTesterHelper(fullOutputFileName, this.projectUrl, dictFileName);
            List errors = tester.doTest();
            if (errors == null) {
                continue;
            }
            if (errors.isEmpty()) {
                continue;
            }
//            throw new MojoExecutionException("Errors validating dictionary file "
            throw new IllegalArgumentException ("Errors validating dictionary file "
                    + dictFileName + "\n" + this.formatAsString(errors));
        }

        // write out the index file
        String indexFileName = this.htmlDirectory.getPath() + "/" + "index.html";
        File indexFile = new File(indexFileName);
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(indexFile, false);
        } catch (FileNotFoundException ex) {
//            throw new MojoExecutionException(indexFileName, ex);
             throw new IllegalArgumentException (indexFileName, ex);
        }
        PrintStream out = new PrintStream(outputStream);
        DictionaryFormatter.writeHeader(out, "Data Dictionary Index");
        out.println("<h1>Data Dictionary Index</h1>");
        out.println("HTML Formatted views of:");
        out.println("<ul>");
        for (String outputFileName : outputFiles) {
            String text = outputFileName.substring(0, outputFileName.length() - ".html".length()) + ".xml";
            out.println("<li><a href=\"" + outputFileName + "\">" + text + "</a>");
        }
        out.println("</ul>");
        DictionaryFormatter.writeFooter(out);
    }

    private String replaceXmlWithHtml(String name) {
        // strip off .xml
        if (name.endsWith(".xml")) {
            name = name.substring(0, name.length() - ".xml".length());
        }
        name = name + ".html";
        int i = name.lastIndexOf("/");
        if (i != -1) {
            name = name.substring(i + 1);
        }
        return name;
    }

    private String formatAsString(List<String> errors) {
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (String error : errors) {
            i++;
            builder.append(i + ". " + error + "\n");
        }
        return builder.toString();
    }
}
