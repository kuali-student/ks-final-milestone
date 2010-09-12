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
package org.kuali.student.common.ui.client.mvc;

import java.util.Date;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.ModelDefinition;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.assembly.client.Data.DataType;
import org.kuali.student.common.assembly.client.Data.DataValue;
import org.kuali.student.common.assembly.client.Data.Key;
import org.kuali.student.common.assembly.client.Data.Property;
import org.kuali.student.common.assembly.client.Data.Value;
import org.kuali.student.common.assembly.client.HasChangeCallbacks.ChangeCallback;
import org.kuali.student.common.assembly.client.HasChangeCallbacks.ChangeCallbackRegistration;
import org.kuali.student.common.assembly.client.HasChangeCallbacks.ChangeType;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent.Action;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author wilj
 *
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
	private HandlerManager handlers = new HandlerManager(this);
    private ChangeCallbackRegistration bridgeCallbackReg;

	private Data root;

	
	public DataModel() {
		// do nothing
	}

	public DataModel(final ModelDefinition definition, final Data root) {
		this.definition = definition;
		this.root = root;
	}


	public <T> T get(final QueryPath path) {
		return (T) root.query(path);
	}
	
    public <T> T get(final String path){
        return (T)get(QueryPath.parse(path));
    }
	
	/**
	 * @return the root
	 */
	public Data getRoot() {
		return root;
	}

	public <T> void query(final QueryPath path, final QueryCallback<T> callback) {
		queryRelative(root, path, callback);
	}

	public <T> void query(final String path, final QueryCallback<T> callback) {
		query(QueryPath.parse(path), callback);
	}

	@SuppressWarnings("unchecked")
	private <T> void queryRelative(final Data branch, final QueryPath path, final QueryCallback<T> callback) {
		Data d = branch;

		for (int i = 0; i < path.size(); i++) {
			final Key key = path.get(i);
			if (key.equals(Data.WILDCARD_KEY)) {
				final QueryPath relative = path.subPath(i + 1, path.size());
				for (final Property p : d) {
					// TODO this won't work with wildcarded leafnodes either
					if (p.getValueType().equals(Data.class)) {
						queryRelative((Data) p.getValue(), relative, callback);
					}
				}
			} else if (i < path.size() - 1) {
				d = d.get(key);
			} else {
				final QueryPath resultPath = d.getQueryPath();
				// TODO this won't work for leaf nodes that are wildcarded, e.g. myobj.mysubobj.*, probably need to enable leaf nodes to track their keys
				resultPath.add(key);
				callback.onResult(resultPath, (T) d.get(key));
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
	
	public void set(final QueryPath path, final Character value){
	    set(path, new Data.CharacterValue(value));
	}
	
	public void set(final QueryPath path, final Long value){
	    set(path, new Data.LongValue(value));
	}
	
	public void set(final QueryPath path, final Short value){
	    set(path, new Data.ShortValue(value));
	}
	
	public void set(final QueryPath path, final Double value){
	    set(path, new Data.DoubleValue(value));
	}
	
	public void set(final QueryPath path, final Float value){
	    set(path, new Data.FloatValue(value));
	}

	public void set(final QueryPath path, final Byte value){
	    set(path, new Data.ByteValue(value));
	}
	
	public void set(final QueryPath path, final Boolean value){	    
	    set(path, new Data.BooleanValue(value));
	}
	
	public void set(final QueryPath path, final Date value){
	    set(path, new Data.DateValue(value));
	}
	
	public void set(final QueryPath path, final Value value) {
		definition.ensurePath(root, path, value instanceof DataValue);
		final QueryPath q = path.subPath(0, path.size() - 1);
		final Data d = root.query(q);
		d.set(path.get(path.size() - 1), value);
	}
		
	public DataType getType(final QueryPath path){
	    return definition.getType(path);
	}

	/**
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
	public HandlerRegistration addModelChangeHandler(
			ModelChangeHandler handler) {
		return handlers.addHandler(ModelChangeEvent.TYPE, handler);
	}

	public ModelDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(ModelDefinition definition) {
		this.definition = definition;
	}
	
	
}
