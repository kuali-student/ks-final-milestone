/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;


/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 * 
 * Usage:
 *  -m metadata-file.xml
 *  -o output-file.xml
 * 
 * @author Kuali Student Team
 */
public class MetaTransformer {
    private static final String META_TO_SPRING_XSL_FILE = "src/main/resources/meta-xml-to-spring.xml";
    
    private static final String SOURCE_XML          = "m";
    private static final String SOURCE_SPRING       = "s";
    private static final String OUTPUT_FILE_OPTION  = "o";
    private static final String SOURCE_CLASS        = "c";
    private static final String SOURCE_PACKAGE      = "p";
    
    private static final String DEFAULT_OUTPUT_FILE = "orch-dictionary.xml";
    private static final String DEFAULT_METAXML_FILE = "metadata.xml";
    
    private static Options options = null;  // Command line options
    private CommandLine cmd = null;         // Command Line arguments
        
    private String outputDir = "target";
    private String outputFile = DEFAULT_OUTPUT_FILE;
    private String inputFile = DEFAULT_METAXML_FILE;
    private String srcPackage = "org.kuali.student.lum.lu.assembly.data.client.refactorme.orch";
    private String srcClass = "CreditCourseMetadata";
    
    Logger log = Logger.getLogger(MetaTransformer.class);
    
    static{
        options = new Options();
        options.addOption(SOURCE_XML, true, "Metadata source xml file.");
        options.addOption(SOURCE_CLASS, true, "Metadata source class name");
        options.addOption(SOURCE_SPRING, true, "Orchestration dictionary source file");
        options.addOption(SOURCE_PACKAGE, true, "Metadata source package");
        options.addOption(OUTPUT_FILE_OPTION, true, "Dictionary output file (" + DEFAULT_OUTPUT_FILE + " by default)");
    }

    /**
     * @param args
     * @throws TransformerException
     */
    public static void main(String[] args) throws Exception {               
        MetaTransformer metaTransformer = new MetaTransformer();
        metaTransformer.loadArgs(args);
        
        metaTransformer.process();
    }

    private void loadArgs(String[] args){
        CommandLineParser parser = new PosixParser();
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Error parsing arguments");
            e.printStackTrace();
            System.exit(1);
        }
        
        if (cmd.hasOption(SOURCE_XML) && cmd.hasOption(SOURCE_CLASS)){
            System.err.println("Only one of the following is allowed: -m (spring source) or -c (class source).");
            System.exit(1);
        }

        if (cmd.hasOption(OUTPUT_FILE_OPTION)){
            outputFile = cmd.getOptionValue(OUTPUT_FILE_OPTION);
        }

        if (cmd.hasOption(SOURCE_XML)){            
            inputFile = cmd.getOptionValue(SOURCE_XML);
        }

        if (cmd.hasOption(SOURCE_PACKAGE)){
            srcPackage = cmd.getOptionValue(SOURCE_PACKAGE);
        }

        if (cmd.hasOption(SOURCE_CLASS) && ! cmd.hasOption(SOURCE_XML)){
            srcClass = cmd.getOptionValue(SOURCE_CLASS);
            if (!cmd.hasOption(SOURCE_PACKAGE)){
                System.err.println("A source package (-p) must be provided for source class : " + srcClass);
                System.exit(1);                
            }
        }
    }
    
    private void process() throws Exception{
        if (cmd.hasOption(SOURCE_SPRING)){
            loadMetadataSpringXML(srcClass, cmd.getOptionValue(SOURCE_SPRING));
            return;
        }
        
        if (cmd.hasOption(SOURCE_CLASS) || (!cmd.hasOption(SOURCE_XML) && !cmd.hasOption(SOURCE_CLASS))){
            if (cmd.hasOption(SOURCE_CLASS)){
                Metadata metadata = getMetadata(srcPackage + "." + srcClass);    
                System.out.println("Extracting metadata xml from " + srcClass);
                generateMetadataXML(metadata, DEFAULT_METAXML_FILE);
                generateSpringXML(srcClass);
            } else {
                Class<?>[] classes = getClasses(srcPackage);
                for (Class<?> c:classes){
                    Metadata metadata = getMetadata(c.getName());    
                    
                    if (metadata != null){
                        System.out.println("Extracting metadata xml from " + c.getName());
                        generateMetadataXML(metadata, DEFAULT_METAXML_FILE);
                        generateSpringXML(c.getSimpleName());
                    }
                }
            }
            
        } else {            
            generateSpringXML("OrgProposal");
        }

        //loadMetadataXML();        
    }
   
    
    private Metadata getMetadata(String className) throws Exception{
        Class<?> clazz = Class.forName(className);
        Object metadataHelper = clazz.newInstance();
        
        Method getMetadata = clazz.getMethod("getMetadata", String.class, String.class);
        
        Metadata metadata = null;
        
        try {
            metadata = (Metadata)getMetadata.invoke(metadataHelper, null, "draft");
        } catch (InvocationTargetException e) {
            System.err.println("Stack overflow in getMetadata for : " + className);
        }
        //Metadata metadata = new CreditCourseProposalMetadata().getMetadata(null, "draft");
        
        return metadata;
    }
    
    public void generateSpringXML(String objectClass) throws Exception{
        File xmlFile;
        if (inputFile.equals(DEFAULT_METAXML_FILE)){
            xmlFile = new File(outputDir, inputFile);
        } else {
            xmlFile = new File(inputFile);
        }
        File xsltFile = new File(META_TO_SPRING_XSL_FILE);

        // JAXB reads data using the Source interface
        Source xmlSource = new StreamSource(xmlFile);
        Source xsltSource = new StreamSource(xsltFile);

        TransformerFactory transFact = net.sf.saxon.TransformerFactoryImpl.newInstance();
        Transformer trans = transFact.newTransformer(xsltSource);

        String dataObjectName = objectClass;
        String targetFile = outputFile;
        if (objectClass != null){
            int metaBegin = objectClass.indexOf("Metadata");
            if (metaBegin > 0){
                dataObjectName = objectClass.substring(0, metaBegin);
            }
            
            trans.setParameter("dataObjectName", dataObjectName);
            targetFile = dataObjectName + "_" + targetFile;
        }
        

        Result result = new StreamResult(new File(outputDir + "/classes", targetFile));
        Result result2 = new StreamResult(new File(outputDir, targetFile));
        
                

        //trans.transform(xmlSource, new StreamResult(System.out));
        trans.transform(xmlSource, result);
        trans.transform(xmlSource, result2);
        
        loadMetadataSpringXML(dataObjectName, targetFile);
    }
    
    /** 
     * This method outputs an xml file from metadata helper classes, to be consumed by
     * the xsl transformer to convert to a spring bean based file.
     * 
     * @throws Exception
     */
    public void generateMetadataXML(Metadata metadata, String targetFile) throws Exception {
        JAXBContext context;

        try {            
            context = JAXBContext.newInstance(Metadata.class);
            Result result = new StreamResult(new File(outputDir, targetFile));
            
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            m.marshal(metadata, result);
            //m.marshal(metadata, System.out);
                        
        } catch (JAXBException e) {
            e.printStackTrace();
        }        
    }
    
    /** 
     * This outputs a metadata xml file generated from loading the spring bean based version
     * to check for inconsistencies from the conversion.
     * 
     * @throws Exception
     */
    public void loadMetadataSpringXML(String dataObjectName, String targetFile) throws Exception{
        MetadataServiceImpl metadataService = new MetadataServiceImpl(targetFile);
        
        Metadata metadata = metadataService.getMetadata(dataObjectName, "default", "default");
        
        generateMetadataXML(metadata, dataObjectName + "_metadata.xml");
        
    }
     
    public void loadMetadataXML() throws Exception{
        Metadata metadata;
        
        Unmarshaller um;
        JAXBContext context;
        context = JAXBContext.newInstance(Metadata.class);
        um = context.createUnmarshaller();
        InputStream is = new FileInputStream(new File("src/main/resources/org-metadata.xml" )); 
        metadata = (Metadata) um.unmarshal(is);
    }
    
    /* The following code snippets were taken from http://snippets.dzone.com/posts/show/4831 */
    
    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class<?>[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith("Metadata.class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    
}
