/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.impl;

import org.kuali.student.common.infc.*;
import org.kuali.student.enrollment.lpr.infc.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nwright
 */
public class CopierHelper {

    public MetaInfc makeCopy(MetaInfc orig) {
        if (orig == null) {
            return null;
        }
        MetaInfc copy = new MetaBean();
        BeanUtils.copyProperties(orig, copy);
        return copy;
    }

    public LuiPersonRelationTypeInfc makeCopy(LuiPersonRelationTypeInfc orig) {
        if (orig == null) {
            return null;
        }
        LuiPersonRelationTypeInfc copy = new LuiPersonRelationTypeBean();
        BeanUtils.copyProperties(orig, copy);
        copy.setAttributes(makeCopy(orig.getAttributes()));
        return copy;
    }

    public LuiPersonRelationStateInfc makeCopy(LuiPersonRelationStateInfc orig) {
        if (orig == null) {
            return null;
        }
        LuiPersonRelationStateInfc copy = new LuiPersonRelationStateBean();
        BeanUtils.copyProperties(orig, copy);
        copy.setAttributes(makeCopy(orig.getAttributes()));
        return copy;
    }

    public AttributeInfc makeCopy(AttributeInfc orig) {
        if (orig == null) {
            return null;
        }
        AttributeInfc copy = new AttributeBean();
        BeanUtils.copyProperties(orig, copy);
        return copy;
    }

    public List<AttributeInfc> makeCopy(List<AttributeInfc> orig) {
        if (orig == null) {
            return null;
        }
        List<AttributeInfc> copy = new ArrayList();
        for (AttributeInfc attr : orig) {
            copy.add(this.makeCopy(attr));
        }
        return copy;
    }

    public LuiPersonRelationInfc makeCopy(LuiPersonRelationInfc orig) {
        if (orig == null) {
            return null;
        }
        LuiPersonRelationInfc copy = new LuiPersonRelationBean();
        BeanUtils.copyProperties(orig, copy);
        copy.setAttributes(this.makeCopy(orig.getAttributes()));
        copy.setMetaInfo(this.makeCopy(orig.getMetaInfo()));
        return copy;
    }

    public MetaInfc createMeta(ContextInfc context) {
        MetaInfc meta = new MetaBean();
        Date now = new Date();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(now);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(now);
        meta.setVersionInd("1");
        return meta;
    }

    public MetaInfc updateMeta(MetaInfc orig, ContextInfc context) {
        MetaInfc meta = makeCopy(orig);
        Date now = new Date();
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(now);
        int oldVersionInd = Integer.parseInt(meta.getVersionInd());
        meta.setVersionInd("" + (oldVersionInd + 1));
        return meta;
    }
}
