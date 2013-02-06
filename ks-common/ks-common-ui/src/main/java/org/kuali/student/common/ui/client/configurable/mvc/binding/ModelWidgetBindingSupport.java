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

package org.kuali.student.common.ui.client.configurable.mvc.binding;

import java.util.Iterator;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.common.assembly.data.Data.Key;
import org.kuali.student.r1.common.assembly.data.Data.Property;
import org.kuali.student.r1.common.assembly.data.Data.StringKey;

/**
 * Exposes common functionality to binding classes.
 * 
 * @author Kuali Student Team
 */
public abstract class ModelWidgetBindingSupport<WidgetType> implements ModelWidgetBinding<WidgetType> {
    public static final StringKey RUNTIME_ROOT = new StringKey("_runtimeData");
    public static final StringKey DIRTY_PATH = new StringKey("dirty");

    /**
     * Flags the data at the provided path as dirty
     * 
     * @param model
     *            the DataModel containing the dirty data
     * @param qPath
     *            the path to the dirty data
     */
    protected void setDirtyFlag(DataModel model, QueryPath qPath) {
        QueryPath parent = qPath.subPath(0, qPath.size() - 1);
        QueryPath qPathDirty = new QueryPath();
        qPathDirty.addAll(parent);
        qPathDirty.add(RUNTIME_ROOT);
        qPathDirty.add(DIRTY_PATH);

        ensureDirtyFlagPath(model.getRoot(), qPathDirty);

        Data flags = model.get(qPathDirty);
        flags.set(qPath.get(qPath.size() - 1), Boolean.TRUE);
    }

    /**
     * Ensures that the path to the dirty flag exists
     * 
     * @param root
     *            the DataModel's root node
     * @param path
     *            the path to the dirty flag
     */
    protected void ensureDirtyFlagPath(Data root, QueryPath path) {
        Data current = root;
        for (int i = 0; i < path.size(); i++) {
            Key key = path.get(i);
            Data d = current.get(key);
            if (d == null) {
                d = new Data();
                current.set(key, d);
            }
            current = d;
        }
    }

    /**
     * Null checks, then compares parameters
     * 
     * @return true when either o1 and o2 are null, or when o1.equals(o2) returns true
     */
    protected static boolean nullsafeEquals(Object o1, Object o2) {
        return (o1 == null && o2 == null) || (o1 != null && o1.equals(o2));
    }

    /**
     * Performs initial comparison of two data hierarchies, then examines them recursively if necessary
     * 
     * @return true when all members of the provided hierarchies are equivalent
     */
    protected static boolean nullsafeEquals(Data o1, Data o2) {
        if (o1 == null && o2 == null) {
            return true;
        } else if (o1 != null && o2 != null && o1.size() != o2.size()) {
            return false;
        } else if (o1 != null && o2 != null && o1.size() == o2.size()) {
            return deepEquals(o1, o2);
        } else {
            return false;
        }
    }

    /**
     * Performs a recursive comparison of the two parameters.
     * 
     * @return true when all members of the provided hierarchies are equivalent
     */
    private static boolean deepEquals(Data o1, Data o2) {
        Iterator<Property> iter = o1.iterator();
        while (iter.hasNext()) {
            Property prop = iter.next();
            Key key = prop.getWrappedKey();
            Object value1 = prop.getValue();
            Object value2 = o2.get(key);

            if (value1 == null ^ value2 == null) {
                return false;
            } else if (value1 != null && value1 instanceof Data && value2 instanceof Data) {
                boolean recursiveResult = deepEquals((Data) value1, (Data) value2);
                if (!recursiveResult) {
                    return false;
                }
            } else if (value1 != value2 && !(value1.equals(value2))) {
                return false;
            }
        }
        return true;
    }
}
