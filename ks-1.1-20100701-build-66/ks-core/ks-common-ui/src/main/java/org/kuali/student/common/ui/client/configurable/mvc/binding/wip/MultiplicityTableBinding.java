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
package org.kuali.student.common.ui.client.configurable.mvc.binding.wip;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.wip.MultiplicityTable;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data;

import java.util.Iterator;
import java.util.List;

/**
 *   
 *      !!!!!! WORK IN PROGRESS  !!!!!!
 *     
 *
 */
public class MultiplicityTableBinding extends ModelWidgetBindingSupport<MultiplicityTable> {

    @Override
    public void setModelValue(MultiplicityTable table, DataModel model, String path) {
        // shouldn't ever need this
    }

    @Override
    public void setWidgetValue(MultiplicityTable table, DataModel model, String path) {
        table.initTable();

        QueryPath qPath = QueryPath.parse(path);
        Data data = null;
        if (model != null) {
            data = model.get(qPath);
        }

        if (data != null) {
            Iterator<Data.Property> iter1 = data.realPropertyIterator();
            if (iter1.hasNext()) {
                table.buildHeaders();

                // iterate through data
                while (iter1.hasNext()) {
                    Data.Property prop = iter1.next();
                    Data rowData = prop.getValue();

                    // iterate through the fields defined for this table
                    for (Integer row  : table.getConfig().getFields().keySet()) {
                        List<FieldDescriptor> fields = table.getConfig().getFields().get(row);
                        for (FieldDescriptor fd : fields) {
                            QueryPath fieldPath = QueryPath.parse(fd.getFieldKey());
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
                                            String key = table.getConfig().getConcatenatedFields().get(fieldPath.toString());
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