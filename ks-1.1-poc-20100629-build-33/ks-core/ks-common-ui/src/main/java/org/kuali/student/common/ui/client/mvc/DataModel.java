/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

/**
 * 
 */
package org.kuali.student.common.ui.client.mvc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.ui.client.mvc.ModelChangeEvent.Action;
import org.kuali.student.common.ui.client.validator.ClientDateParser;
import org.kuali.student.common.ui.client.validator.DataModelValidator;
import org.kuali.student.common.validator.DateParser;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.ModelDefinition;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.assembly.data.Data.Key;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.assembly.data.HasChangeCallbacks.ChangeCallback;
import org.kuali.student.core.assembly.data.HasChangeCallbacks.ChangeCallbackRegistration;
import org.kuali.student.core.assembly.data.HasChangeCallbacks.ChangeType;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author wilj
 */
@SuppressWarnings("unchecked")
public class DataModel implements Model {
    public interface QueryCallback<T> {
        void onResult(QueryPath path, T result);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ModelDefinition definition;
    private DataModelValidator validator = new DataModelValidator();

    private HandlerManager handlers = new HandlerManager(this);
    private ChangeCallbackRegistration bridgeCallbackReg;

    private Data root;

    public DataModel() {
    // do nothing
    }

    public DataModel(final ModelDefinition definition, final Data root) {
        this.definition = definition;
        this.root = root;
        validator.setDateParser((DateParser) GWT.create(ClientDateParser.class));
    }

    public <T> T get(final QueryPath path) {
        return (T) root.query(path);
    }

    public <T> T get(final String path) {
        return (T) get(QueryPath.parse(path));
    }

    /**
     * @return the root
     */
    public Data getRoot() {
        return root;
    }

    public Map<QueryPath, Object> query(final QueryPath path) {
        Map<QueryPath, Object> result = new HashMap<QueryPath, Object>();
        queryRelative(root, path, result);
        return result;
    }

    public Map<QueryPath, Object> query(final String path) {
        return query(QueryPath.parse(path));
    }

    private void queryRelative(final Data branch, final QueryPath path, Map<QueryPath, Object> result) {
        Data d = branch;

        for (int i = 0; i < path.size(); i++) {
            if (d == null) {
                // dead end
                break;
            }
            final Key key = path.get(i);
            if (key.equals(Data.WILDCARD_KEY)) {
                final QueryPath relative = path.subPath(i + 1, path.size());
                if (!relative.isEmpty()){
	                for (final Property p : d) {
	                    // TODO this won't work with wildcarded leafnodes either
	                    if (p.getValueType().equals(Data.class)) {
	                        queryRelative((Data) p.getValue(), relative, result);
	                    }
	                }
                } else {
                	//The wildcard is last element in path, so add all sub-elements to result
                	Set<Key> keys = d.keySet();
                	for (Key wildcardKey:keys){
                		QueryPath wildcardPath = path.subPath(0, path.size()-1);
                		wildcardPath.add(wildcardKey);
                		result.put(wildcardPath, d.get(wildcardKey));
                	}
                }
            } else if (i < path.size() - 1) {
                d = d.get(key);
            } else {
                final QueryPath resultPath = d.getQueryPath();
                // TODO this won't work for leaf nodes that are wildcarded, e.g. myobj.mysubobj.*, probably need to enable
                // leaf nodes to track their keys
                resultPath.add(key);
                Object resultValue = d.get(key);
                result.put(resultPath, resultValue);
            }
        }
    }

    public void set(final QueryPath path, final Data value) {
        set(path, new Data.DataValue(value));
    }

    public void set(final QueryPath path, final Integer value) {
        set(path, new Data.IntegerValue(value));
    }

    public void set(final QueryPath path, final String value) {
        set(path, new Data.StringValue(value));
    }

    public void set(final QueryPath path, final Long value) {
        set(path, new Data.LongValue(value));
    }

    public void set(final QueryPath path, final Short value) {
        set(path, new Data.ShortValue(value));
    }

    public void set(final QueryPath path, final Double value) {
        set(path, new Data.DoubleValue(value));
    }

    public void set(final QueryPath path, final Float value) {
        set(path, new Data.FloatValue(value));
    }

    public void set(final QueryPath path, final Boolean value) {
        set(path, new Data.BooleanValue(value));
    }

    public void set(final QueryPath path, final Date value) {
        set(path, new Data.DateValue(value));
    }

    public void set(final QueryPath path, final Value value) {
        definition.ensurePath(root, path, value instanceof DataValue);
        if (path.size() > 1){
        	final QueryPath q = path.subPath(0, path.size() - 1);
        	final Data d = root.query(q);
            d.set(path.get(path.size() - 1), value);
        } else {
        	root.set(path.get(0), value);
        }
    }

    public DataType getType(final QueryPath path) {
        return definition.getType(path);
    }

    public Metadata getMetadata(final QueryPath path) {
        return definition.getMetadata(path);
    }

    /**
     * @param root
     *            the root to set
     */
    public void setRoot(final Data root) {
        if (bridgeCallbackReg != null) {
            bridgeCallbackReg.remove();
        }
        this.root = root;
        bridgeCallbackReg = root.addChangeCallback(new ChangeCallback() {
            @Override
            public void onChange(ChangeType type, QueryPath path) {
                Action action = null;
                if (type == ChangeType.ADD) {
                    action = Action.ADD;
                } else if (type == ChangeType.REMOVE) {
                    action = Action.REMOVE;
                } else if (type == ChangeType.UPDATE) {
                    action = Action.UPDATE;
                }
                handlers.fireEvent(new DataModelChangeEvent(action, DataModel.this, path));
            }
        });
        handlers.fireEvent(new DataModelChangeEvent(Action.RELOAD, this, new QueryPath()));
    }

    @Override
    public HandlerRegistration addModelChangeHandler(ModelChangeHandler handler) {
        return handlers.addHandler(ModelChangeEvent.TYPE, handler);
    }

    public ModelDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(ModelDefinition definition) {
        this.definition = definition;
    }

    public void validate(final Callback<List<ValidationResultInfo>> callback) {
        List<ValidationResultInfo> result = validator.validate(this);
        callback.exec(result);
    }
}
