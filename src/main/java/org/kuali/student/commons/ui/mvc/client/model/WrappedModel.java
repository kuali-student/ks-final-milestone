package org.kuali.student.commons.ui.mvc.client.model;

import java.util.ArrayList;
import java.util.List;

public class WrappedModel<T extends Object> extends Model<WrappedModelObject<T>> {
    UniqueIdStrategy<T> strategy;

    public WrappedModel(UniqueIdStrategy<T> strategy) {
        super();
        this.strategy = strategy;
    }

    public void add(T object) {
        super.add(wrap(object));
    }

    public boolean contains(T object) {
        return super.contains(wrap(object));
    }

    public T getRaw(String uniqueId) {
        WrappedModelObject<T> result = super.get(uniqueId);
        if (result != null) {
            return result.getWrappedObject();
        } else {
            return null;
        }
    }

    public List<T> itemsRaw() {
        List<T> result = new ArrayList<T>(super.items().size());
        for (WrappedModelObject<T> wmo : super.items()) {
            result.add(wmo.wrappedObject);
        }
        return result;
    }

    public void remove(T object) {
        super.remove(wrap(object));
    }

    public void update(T object) {
        super.update(wrap(object));
    }
    
    private WrappedModelObject<T> wrap(T object) {
        return new WrappedModelObject<T>(object, strategy);
    }
    
}
