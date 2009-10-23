package org.kuali.student.schemaFiller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AppDom {

	public static final String DOC_XML_FILE_NAME = "C:/workspace/ks-core/doc/jel.xml";
	public static final String SCHEMA_FILE_NAME = "C:/workspace/ks-core/doc/schema-description.xml";

	public static void main(String[] args) {
		HashMap<String, Object> tableMap = new HashMap<String, Object>();
		// HashMap<String, String> fieldJava = new HashMap<String, String>();
		AppDom appDom = new AppDom();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();

		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(
					DOC_XML_FILE_NAME));

			document.getChildNodes();
			Element element = document.getDocumentElement();

			NodeList nodes = element.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node instanceof Element) {
					// a child element to process
					Element child = (Element) node;
					String name = child.getNodeName();
					if (name.equals("jelclass")) {
						if (child.getAttribute("package").matches(
								"(?i).*.entity.*")) {
							Table table = new Table();
							Table metaEntity = new Table();
							metaEntity = appDom.addMetaInfo();
							table.setMetaInfo(metaEntity);
							if (child.getAttribute("superclassfulltype")
									.matches("(?i).*.entity.MetaEntity")) {
								System.out.println("MetaEntity found "
										+ child.getAttribute("type"));
							}
							HashMap<String, String> fieldJava = new HashMap<String, String>();
							String java12 = child.getAttribute("type");
							table.setjavaTableName(java12);
							System.out.println("package: "
									+ child.getAttribute("package") + " Type: "
									+ child.getAttribute("type"));
							NodeList nodes12 = child
									.getElementsByTagName("annotation");
							for (int k = 0; k < nodes12.getLength(); k++) {
								Node node12 = nodes12.item(k);
								Element child12 = (Element) node12;
								String name12 = child12.getAttribute("name");
								if (name12.equals("Table")) {
									String value12 = child12
											.getAttribute("value");
									System.out.println("name12: " + name12);
									System.out.println("value12: " + value12);
									table.setdbTableName(value12);
									tableMap.put(value12, table);
								}

							}
							NodeList nodes13 = child
									.getElementsByTagName("field");
							for (int k = 0; k < nodes13.getLength(); k++) {
								Node node13 = nodes13.item(k);
								Element child13 = (Element) node13;
								// String javaField =
								// child13.getAttribute("name");
								NodeList nodes131 = child13
										.getElementsByTagName("annotation");
								for (int l = 0; l < nodes131.getLength(); l++) {
									Node node131 = nodes131.item(l);
									Element child131 = (Element) node131;
									String tag = child131.getAttribute("name");
									if (tag.equals("Column")
											|| tag.equals("JoinColumn")) {
										String javaField = child13
												.getAttribute("name");
										String tagValue = child131
												.getAttribute("value");
										fieldJava.put(tagValue, javaField);
									}
								}
							}
							table.setfields(fieldJava);
							appDom.addDescription(document, table);
						}
					}
				}
			}

			// DocumentBuilder builder = builderFactory.newDocumentBuilder();
			appDom.formatSchema(tableMap);

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void formatSchema(HashMap<String, Object> tableMap) {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();

		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer tFormer = tFactory.newTransformer();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(
					SCHEMA_FILE_NAME));

			document.getChildNodes();
			Element element = document.getDocumentElement();

			NodeList nodes = element.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node instanceof Element) {
					// a child element to process
					Element child = (Element) node;
					String name = child.getNodeName();
					if (name.equals("table")) {
						String tableName = child.getAttribute("name");
						Table tableDetails = new Table();
						tableDetails = (Table) tableMap.get(tableName);
						if (tableDetails != null) {
							String tableJavaName = tableDetails
									.getjavaTableName();
							child.setAttribute("javaName", tableJavaName);
							String tableDescription = tableDetails
									.gettableDescription();
							child.setAttribute("description", tableDescription);
							for (int j = 0; j < tableDetails.getfields().size(); j++) {
								HashMap<String, String> fields = new HashMap<String, String>();
								Table metaInfo = tableDetails.getMetaInfo();
								HashMap<String, String> fieldsDescription = tableDetails
										.getfieldDescription();
								fields = tableDetails.getfields();
								NodeList nodes12 = child
										.getElementsByTagName("column");
								for (int l = 0; l < nodes12.getLength(); l++) {
									Node node12 = nodes12.item(l);

									if (node instanceof Element) {
										// a child element to process
										Element child12 = (Element) node12;
										// if (child12.getNodeName().equals(
										// "column")) {
										String schColName = child12
												.getAttribute("name");
										String schColJavaName = fields
												.get(schColName);
										if (schColJavaName != null) {
											child12.setAttribute("javaName",
													schColJavaName);
											child12
													.setAttribute(
															"description",
															fieldsDescription
																	.get(schColJavaName));
										} else {
											if (metaInfo != null) {
												HashMap<String, String> metaFields = new HashMap<String, String>();
												HashMap<String, String> metaFieldsDescription = metaInfo.getfieldDescription();
												metaFields = metaInfo
														.getfields();
												schColJavaName = metaFields
														.get(schColName);
												if (schColJavaName != null) {
													child12.setAttribute(
															"javaName",
															schColJavaName);
													child12
															.setAttribute(
																	"description",
																	metaFieldsDescription
																			.get(schColName));
												}
											}
										}

										// }

									}
								}
							}
						}
					}
				}
			}
			// File file = new File(filename);
			// Result result = new StreamResult(file);
			document.normalize();
			Source source = new DOMSource(document);
			Result dest = new StreamResult(new FileOutputStream(
					SCHEMA_FILE_NAME));
			tFormer.transform(source, dest);
			System.out.println();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addDescription(Document document, Table tableDetails) {
		String tableJavaName = tableDetails.getjavaTableName();

		String tableDTOName = tableJavaName.concat("Info");
		System.out.println("DTO Name: " + tableDTOName);
		document.getChildNodes();
		Element element = document.getDocumentElement();

		NodeList nodes = element.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node instanceof Element) {
				// a child element to process
				Element child = (Element) node;
				String name = child.getNodeName();
				if (name.equals("jelclass")) {
					if (child.getAttribute("type").equals(tableDTOName)) {
						NodeList nodes12 = child
								.getElementsByTagName("comment");

						Node node12 = nodes12.item(0);
						if (node12 != null) {
							Element child12 = (Element) node12;
							Node node121 = child12.getFirstChild();
							Node node122 = node121.getNextSibling();
							String tableDescription = node122.getTextContent();
							tableDetails.settableDescription(tableDescription);
						}
						// System.out.println("I will extract this DTO--> "+
						// tableDTOName);
						NodeList nodes13 = child.getElementsByTagName("method");
						HashMap<String, String> fields = tableDetails
								.getfields();
						Set fieldClmn = fields.keySet();
						Iterator ite = fieldClmn.iterator();
						HashMap<String, String> fieldsDescrp = new HashMap<String, String>();
						for (int j = 0; j < fieldClmn.size(); j++) {

							String fieldClmnName = (String) ite.next();
							String fieldName = fields.get(fieldClmnName);
							String fieldGetter = "get" + fieldName;
							for (int k = 0; k < nodes13.getLength(); k++) {
								Node node13 = nodes13.item(k);

								if (node13 instanceof Element) {
									// a child element to process
									Element child13 = (Element) node13;
									if (child13.getAttribute("name")
											.compareToIgnoreCase(fieldGetter) == 0) {
										// System.out.println("I will extract this field--> "+
										// fieldName);
										NodeList nodes131 = child13
												.getElementsByTagName("comment");
										Node node131 = nodes131.item(0);
										if (node131 != null) {
											Element child131 = (Element) node131;
											Node node1311 = child131
													.getFirstChild();
											Node node1312 = node1311
													.getNextSibling();
											String fieldDescription = node1312
													.getTextContent();
											System.out
													.println("I will extract this field--> "
															+ fieldName
															+ " Description--> "
															+ fieldDescription);
											fieldsDescrp.put(fieldName,
													fieldDescription);
										}

									}
								}
							}
							tableDetails.setfieldDescription(fieldsDescrp);
						}

					}
				}
			}
		}
	}

	public Table addMetaInfo() {
		Table tableDetails = new Table();
		HashMap<String, String> fields = new HashMap<String, String>();
		HashMap<String, String> fieldDescription = new HashMap<String, String>();
		fields.put("CREATEID", "createId");
		fields.put("CREATETIME", "createTime");
		fields.put("UPDATEID", "updateId");
		fields.put("UPDATETIME", "updateTime");
		fields.put("VERSIONIND", "versionInd");
		fieldDescription
				.put(
						"CREATEID",
						"The principal who created the thing being described with this meta information");
		fieldDescription
				.put(
						"CREATETIME",
						"The date and time the thing being described with this meta information was last updated");
		fieldDescription
				.put(
						"UPDATEID",
						"The principal who last updated the thing being described with this meta information");
		fieldDescription
				.put(
						"UPDATETIME",
						"The date and time the thing being described with this meta information was last updated");
		fieldDescription
				.put(
						"VERSIONIND",
						"An indicator of the version of the thing being described with this meta information. This is set by the service implementation and will be used to determine conflicts in updates");
		tableDetails.setfields(fields);
		tableDetails.setfieldDescription(fieldDescription);

		return tableDetails;

	}
}
