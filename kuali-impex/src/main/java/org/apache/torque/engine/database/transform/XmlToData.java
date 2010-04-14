package org.apache.torque.engine.database.transform;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.torque.engine.database.model.Column;
import org.apache.torque.engine.database.model.Database;
import org.apache.torque.engine.database.model.Table;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A Class that is used to parse an input xml schema file and creates and AppData java structure.
 * 
 * @author <a href="mailto:leon@opticode.co.za">Leon Messerschmidt</a>
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:mpoeschl@marmot.at">Martin Poeschl</a>
 * @author <a href="mailto:fedor.karpelevitch@home.com">Fedor Karpelevitch</a>
 * @version $Id: XmlToData.java,v 1.1 2007-10-21 07:57:26 abyrne Exp $
 */
public class XmlToData extends DefaultHandler implements EntityResolver {
	private Database database;
	private List<DataRow> data;
	private String dataDTDResource;

	private static SAXParserFactory saxFactory;

	static {
		saxFactory = SAXParserFactory.newInstance();
		saxFactory.setValidating(true);
	}

	/**
	 * Default custructor
	 */
	public XmlToData(Database database, String dataDTDResource) {
		this.database = database;
		this.dataDTDResource = dataDTDResource;
	}

	/**
     *
     */
	public List<DataRow> parseFile(Resource resource) throws Exception {
		data = new ArrayList<DataRow>();

		SAXParser parser = saxFactory.newSAXParser();

		InputStream in = null;
		try {
			in = resource.getInputStream();
			InputSource is = new InputSource(in);
			is.setSystemId(ImpexDTDResolver.KS_EMBEDDED);
			parser.parse(is, this);
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(in);
		}
		return data;
	}

	/**
	 * Handles opening elements of the xml file.
	 */
	public void startElement(String uri, String localName, String rawName, Attributes attributes) throws SAXException {
		try {
			if (rawName.equals("dataset")) {
				// ignore <dataset> for now.
			} else {
				Table table = database.getTableByJavaName(rawName);

				if (table == null) {
					throw new SAXException("Table '" + rawName + "' unknown");
				}
				List<ColumnValue> columnValues = new ArrayList<ColumnValue>();
				for (int i = 0; i < attributes.getLength(); i++) {
					Column col = table.getColumnByJavaName(attributes.getQName(i));

					if (col == null) {
						throw new SAXException("Column " + attributes.getQName(i) + " in table " + rawName + " unknown.");
					}

					String value = attributes.getValue(i);
					columnValues.add(new ColumnValue(col, value));
				}
				data.add(new DataRow(table, columnValues));
			}
		} catch (Exception e) {
			throw new SAXException(e);
		}
	}

	/**
	 * called by the XML parser
	 * 
	 * @return an InputSource for the data file
	 */
	public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
		if (systemId.equals(ImpexDTDResolver.KS_EMBEDDED)) {
			ResourceLoader loader = new DefaultResourceLoader();
			Resource resource = loader.getResource(dataDTDResource);
			InputStream in = resource.getInputStream();
			return new InputSource(in);
		} else {
			return super.resolveEntity(publicId, systemId);
		}
	}

	/**
     *
     */
	public class DataRow {
		private Table table;
		private List<?> columnValues;

		public DataRow(Table table, List<?> columnValues) {
			this.table = table;
			this.columnValues = columnValues;
		}

		public Table getTable() {
			return table;
		}

		public List<?> getColumnValues() {
			return columnValues;
		}
	}

	/**
     *
     */
	public class ColumnValue {
		private Column col;
		private String val;

		public ColumnValue(Column col, String val) {
			this.col = col;
			this.val = val;
		}

		public Column getColumn() {
			return col;
		}

		public String getValue() {
			return val;
		}

		public String getEscapedValue() {
			StringBuffer sb = new StringBuffer();
			sb.append("'");
			sb.append(StringUtils.replace(val, "'", "''"));
			sb.append("'");
			return sb.toString();
		}
	}
}
