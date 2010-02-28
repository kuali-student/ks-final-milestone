/*
 * Copyright 2009 Johnson Consulting Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export;

/**
 * @author wilj
 *
 */
public class ExportFormatDefinition<T> {
	private final String label;
	private final String exporterClassName;
	private final ExportRequestBuilder<T> builder;

	/**
	 * @param label
	 * @param exporterClassName
	 * @param builder
	 */
	public ExportFormatDefinition(final String label, final String exporterClassName, final ExportRequestBuilder<T> builder) {
		super();
		this.label = label;
		this.exporterClassName = exporterClassName;
		this.builder = builder;
	}

	/**
	 * @return the builder
	 */
	public ExportRequestBuilder<T> getBuilder() {
		return builder;
	}

	/**
	 * @return the exporterClassName
	 */
	public String getExporterClassName() {
		return exporterClassName;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

}
