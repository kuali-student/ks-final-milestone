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

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
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
 * The data model for Kuali Student.  Data is stored in a map of maps and is accessed through a QueryPath.
 * 
 * @author Kuali Student Team
 * @see QueryPath
 */
/**
 * @author Kuali Student Team
 *
 */
@SuppressWarnings("unchecked")
public class DataModel implements Model {
    public interface QueryCallback<T> {
        void onResult(QueryPath path, T result);
    }

    private static final long serialVersionUID = 1L;

    private String modelName = "";
    private ModelDefinition definition;
    private DataModelValidator validator = new DataModelValidator();

    private HandlerManager handlers = new HandlerManager(this);
    private ChangeCallbackRegistration bridgeCallbackReg;

    private Data root;

    private String parentPath;    //Set this if DataModel's root element is nested in another data element.

    public DataModel() {
        // do nothing
    }

    public DataModel(String name) {
        this.modelName = name;
    }

    public DataModel(final ModelDefinition definition, final Data root) {
        this.definition = definition;
        this.root = root;
        validator.setDateParser((DateParser) GWT.create(ClientDateParser.class));
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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

    public void resetRoot() {
        root = null;
    }

    public void remove(final QueryPath path) {
        QueryPath parent = null;
        QueryPath leavePath = null;
        if (path != null && path.size() >= 2) {
            parent = path.subPath(0, path.size() - 1);
            leavePath = path.subPath(path.size() - 1, path.size());
            Object parentData = this.get(parent);
            if (parentData != null && parentData instanceof Data) {
                ((Data) parentData).remove(
                        new Data.StringKey(leavePath.toString()));
            }

        } else if (path != null) {
            root.remove(new Data.StringKey(path.toString()));
        }
    }

    /** 
     * @param path The path in the data model
     * @return A map containing the path/value pairs for all matching elements, or an empty
     * map if no matching values found.
     */
    public Map<QueryPath, Object> query(final QueryPath path) {
        Map<QueryPath, Object> result = new HashMap<QueryPath, Object>();
        queryRelative(root, path, result);
        return result;
    }

    /** 
     * @param path The path in the data model
     * @return A map containing the path/value pairs for all matching elements, or an empty
     * map if no matching values found.
     */
    public Map<QueryPath, Object> query(final String path) {
        return query(QueryPath.parse(path));
    }

    /**
     * @param branch The data branch on which to perform the query
     * @param path   The path string relative to the root of the branch
     * @param result A map containing all matching paths and the corresponding data value. For a non-wildcard path
     *               this should return a single entry in the map of the path supplied and the corresponding value. For
     *               a path containing wildcard, an entry in the map will exist for each matching path and their corresponding values.
     *               Note: A path ending in a wild card will not match the _runtimeData element.
     */
    private void queryRelative(final Data branch, final QueryPath path, Map<QueryPath, Object> result) {
        //Should this add the entire branch to the result when query path is an empty string?

        Data d = branch;

        for (int i = 0; i < path.size(); i++) {
            if (d == null) {
                // dead end
                break;
            }
            final Key key = path.get(i);
            if (key.equals(Data.WILDCARD_KEY)) {
                final QueryPath relative = path.subPath(i + 1, path.size());
                if (!relative.isEmpty()) {
                    //Handle remaining path after wildcard
                    for (final Property p : d) {
                        if (p.getValueType().equals(Data.class)) {
                            queryRelative((Data) p.getValue(), relative, result);
                        }
                    }
                } else {
                    //The wildcard is last element in path, so add all sub-elements to result
                    //The wildcard will not match a _runtimeData path
                    Set<Key> keys = d.keySet();
                    for (Key wildcardKey : keys) {
                        if (!("_runtimeData".equals(wildcardKey.get()))) {
                            QueryPath wildcardPath = path.subPath(0, path.size() - 1);
                            wildcardPath.add(wildcardKey);
                            result.put(wildcardPath, d.get(wildcardKey));
                        }
                    }
                }
                break; //No need to continue once we process wildcard
            } else if (i < path.size() - 1) {
                d = d.get(key);
            } else {
                final QueryPath resultPath = d.getQueryPath();
                resultPath.add(key);

                Object resultValue = d.get(key);

                //If query is against DataModel whose root element is child of another data object, 
                //need to strip of the parent path so result path is relative to root of child element
                if (parentPath != null) {
                    String relativePath = resultPath.toString();
                    if (relativePath.contains("/")) {
                        relativePath = relativePath.substring(parentPath.length());
                        result.put(QueryPath.parse(relativePath), resultValue);
                    }else{
                       result.put(resultPath, resultValue); 
                    }
                } else {
                    result.put(resultPath, resultValue);
                }
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
        if (path.size() > 1) {
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
     * Set the top level data for this DataModel
     * @param root the root to set
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


    public String getParentPath() {
        return parentPath;
    }


    /**
     * If the root element for this is a child of another data object, then the parent
     * path must be set to the path where this child data object can be found.
     *
     * @param parentPath
     */
    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    /**
     * Validates this data model against its ModelDefinition/Metadata and returns the result
     * to the callback
     * @param callback
     */
    public void validate(final Callback<List<ValidationResultInfo>> callback) {
        List<ValidationResultInfo> result = validator.validate(this);
        callback.exec(result);
    }

    /**
     * Validates this data model against the next state in its ModelDefinition and returns the result
     * to the callback
     * @param callback
     */
    public void validateNextState(final Callback<List<ValidationResultInfo>> callback) {
        List<ValidationResultInfo> result = validator.validateNextState(this);
        callback.exec(result);
    }

    /**
     * Validates a single field
     * @param fd
     * @param callback
     */
    public void validateField(FieldDescriptor fd, final Callback<List<ValidationResultInfo>> callback) {
        List<ValidationResultInfo> result = validator.validate(fd, this);
        callback.exec(result);
    }

    /**
     * Checks to see if data exists for the path passed in
     * @param sPath
     * @return
     */
    public boolean isValidPath(String sPath) {
        QueryPath path = QueryPath.parse(sPath);
        boolean result = false;
        Data root = this.getRoot();
        for (int i = 0; i < path.size(); i++) {
            Data.Key key = path.get(i);
            if (!root.containsKey(key)) {
                result = false;
                break;
            } else if (i < path.size() - 1) {
                root = (Data) root.get(key);
            } else {
                result = true;
                break;
            }
        }
        return result;
    }

}
