/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * <p/>
 * http://www.osedu.org/licenses/ECL-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"                       
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * <p/>
 */
package org.kuali.student.common.ui.client.configurable.mvc.binding;

import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityFieldConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityTable;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.common.assembly.data.Data.DataType;

import com.google.gwt.core.client.GWT;

/**
 * This class handles transferring data between the model and the widgets 
 */
public class MultiplicityTableBinding extends ModelWidgetBindingSupport<MultiplicityTable> {


    /**
     * @see ModelWidgetBindingSupport#setModelValue(Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, String)
     */
    public void setModelValue(MultiplicityTable table, DataModel model, String path) {
        // Not required  - MultiplicityTable is read only
        GWT.log("Method setModelValue not implemented for MultiplicityTable");
    }


    /**
     * @see ModelWidgetBindingSupport#setWidgetValue(Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, String)
     */
    public void setWidgetValue(MultiplicityTable table, DataModel model, String path) {
        table.initTable();

        path = path.trim();
        if (path.startsWith(QueryPath.getPathSeparator())) {
            path = path.substring(QueryPath.getPathSeparator().length());
        }

        QueryPath qPath = QueryPath.parse(path);
        Data data = null;
        if (model != null) {
            data = model.get(qPath);
        }

        if (data != null) {
            Iterator<Data.Property> iter1 = data.iterator();
            if (iter1.hasNext()) {
                table.buildHeaders();

                // iterate through data
                while (iter1.hasNext()) {
                    Data.Property prop = iter1.next();
                    Object value = prop.getValue();

                    if (value instanceof String) {

                        Metadata metadata = model.getMetadata(qPath);
                        String dataValue = null;
                        if(metadata!=null&&metadata.getInitialLookup()!=null){
                            QueryPath translatedPath = QueryPath.concat("_runtimeData", prop.getKey().toString(), "id-translation");
                            dataValue = data.query(translatedPath);
                        }

                        if (dataValue == null)
                            dataValue = (String)value;
                        
                        table.addNextCell(dataValue);                        
                        table.nextRow();
                    }
                    else {
                        Data rowData = prop.getValue();

                        // iterate through the fields defined for this table
                        for (Integer row  : table.getConfig().getFields().keySet()) {
                            List<MultiplicityFieldConfiguration> fieldConfigs = table.getConfig().getFields().get(row);
                            for (MultiplicityFieldConfiguration fieldConfig : fieldConfigs) {

                                QueryPath fullPath = QueryPath.parse(fieldConfig.getFieldPath());
                                QueryPath fieldPath = translatePath(path, fullPath, model);                           

                                Object o = rowData.query(fieldPath);
                                if (o != null) {
                                    // multiple values required in the table cell, concatenate values
                                    // with comma separator
                                    if (o instanceof Data) {
                                        Data cellData = (Data) o;
                                        // iterate through the field keys to produce a comma delimited list
                                        // of values in a single cell of the table
                                        if (cellData != null && cellData.size() > 0) {
                                            StringBuilder sb = new StringBuilder();
                                            Iterator<Data.Property> iter = cellData.realPropertyIterator();
                                            while (iter.hasNext()) {
                                                Data.Property p = iter.next();
                                                Data d = p.getValue();
                                                String key = table.getConfig().getConcatenatedFields().get(fullPath.toString());
                                                sb.append(d.get(key)).append(", ");
                                            }
                                            sb.deleteCharAt(sb.lastIndexOf(", "));
                                            table.addNextCell((sb.toString()));
                                        } else {
                                            table.addEmptyCell();
                                        }
                                    }
                                    // a single value required in a single table cell
                                    else {
                                        table.addNextCell((o.toString()));
                                    }
                                } else {
                                    table.addEmptyCell();
                                }                        	
                            }
                            table.nextRow();
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * This method checks the meta data for an initial lookup for this field. If found, the field path
     * is translated to lookup the id_translation data in the _runtimeData structure in the model
     * 
     * @param path
     * @param fullPath
     * @param model
     * @return
     */
    private QueryPath translatePath(String path, QueryPath fullPath, DataModel model) {
        QueryPath parentPath = QueryPath.parse(path);
        int i = parentPath.size();

        Metadata metadata = model.getMetadata(fullPath);
        QueryPath fieldPath = null;

        if(metadata!=null&&metadata.getInitialLookup()!=null){
            if (metadata.getDataType().equals(DataType.STRING)) {
                QueryPath translatedPath = fullPath.subPath(0, fullPath.size()-1);
                translatedPath.add(new Data.StringKey("_runtimeData"));
                translatedPath.add(new Data.StringKey((String)fullPath.get(fullPath.size() - 1).get()));
                translatedPath.add(new Data.StringKey("id-translation"));
                fieldPath  =  translatedPath.subPath(i, translatedPath.size());
            }
            else {
                fieldPath =  fullPath.subPath(i, fullPath.size());
            }
        }
        else {
            fieldPath =  fullPath.subPath(i, fullPath.size());
        }

        if (fieldPath.get(0).toString().equals(QueryPath.getWildCard())) {
            fieldPath.remove(0);
        }
        return fieldPath;
    }
}