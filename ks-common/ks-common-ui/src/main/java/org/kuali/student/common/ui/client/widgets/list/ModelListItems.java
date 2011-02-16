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

package org.kuali.student.common.ui.client.widgets.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.kuali.student.common.dto.Idable;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.CollectionModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;

import com.google.gwt.event.shared.HandlerRegistration;

public abstract class ModelListItems<T extends Idable> implements ListItems{
    protected List<Callback<T>> addCallbacks = new ArrayList<Callback<T>>();
    protected List<Callback<T>> removeCallbacks = new ArrayList<Callback<T>>();
    protected List<Callback<T>> updateCallbacks = new ArrayList<Callback<T>>();
    protected List<Callback<T>> bulkUpdateCallbacks = new ArrayList<Callback<T>>();
    
    protected List<T> listItems = new ArrayList<T>();
    protected Comparator<T> listComparator = null;
    protected HandlerRegistration reg = null;
    
    protected void add(T item){
        listItems.add(item);
        reSort();
    }
        
    protected void update(T item){
        for(T i : listItems){
            if(i.getId().equals(item.getId())){
                listItems.remove(i);
                listItems.add(item);
                reSort();
                break;
            }
        }
    }
    
    protected void remove(T item){
        listItems.remove(item);
    }
    
    protected void reSort(){
        if(listComparator != null){
            Collections.sort(listItems, listComparator);
        }
    }
    
    public void setComparator(Comparator<T> comparator){
        listComparator = comparator;
        reSort();
        for (Callback<T> c : bulkUpdateCallbacks) {
            c.exec(null);
        }
    }
        
    public void setModel(CollectionModel<T> model){
        if(reg != null){
           reg.removeHandler();
        }
        reg = model.addModelChangeHandler(new ModelChangeHandler() {
            public void onModelChange(ModelChangeEvent evt) {
            	CollectionModelChangeEvent<T> event = (CollectionModelChangeEvent<T>) evt;
                switch (event.getAction()) {
                    case ADD:
                        ModelListItems.this.add(event.getValue());
                        for (Callback<T> c : addCallbacks) {
                            c.exec(event.getValue());
                        }
                        break;
                    case UPDATE:
                        ModelListItems.this.update(event.getValue());
                        for (Callback<T> c : updateCallbacks) {
                            c.exec(event.getValue());
                        }
                        break;
                    case REMOVE:
                        ModelListItems.this.remove(event.getValue());
                        for (Callback<T> c : removeCallbacks) {
                            c.exec(event.getValue());
                        }
                        break;
                }
            }
        });
        listItems.clear();
        listItems.addAll(model.getValues());
        reSort();
    }


    public void addOnAddCallback(Callback<T> callback) {
        addCallbacks.add(callback);
    }


    public void addOnRemoveCallback(Callback<T> callback) {
        removeCallbacks.add(callback);       
    }
    

    public void addOnUpdateCallback(Callback<T> callback) {
        updateCallbacks.add(callback);        
    }
    
    public void addOnBulkUpdateCallback(Callback<T> callback) {
        bulkUpdateCallbacks.add(callback);        
    }
    
    @Override
    public int getItemCount() {    
        return listItems.size();
    }

    @Override
    public List<String> getItemIds() {
        List<String> ids = new ArrayList<String>();
        for(T i: listItems){
            ids.add(i.getId());
        }
        return ids;
    }
}
