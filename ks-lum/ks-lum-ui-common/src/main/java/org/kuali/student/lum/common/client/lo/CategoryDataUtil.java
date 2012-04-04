package org.kuali.student.lum.common.client.lo;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Data.StringKey;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import java.util.Date;

public class CategoryDataUtil {
    
    public static Data toData(LoCategoryInfo loCategoryInfo) {
        LoCategoryInfoHelper catHelper = new LoCategoryInfoHelper();
        catHelper.setId(loCategoryInfo.getId());
        catHelper.setName(loCategoryInfo.getName());
        RichTextInfoHelper catRTHelper = RichTextInfoHelper.wrap(new Data());
        RichTextInfo catRT = loCategoryInfo.getDescr();
        if (null != catRT) {
            catRTHelper.setFormatted(loCategoryInfo.getDescr().getFormatted());
            catRTHelper.setPlain(loCategoryInfo.getDescr().getPlain());
        }
        catHelper.setDesc(catRTHelper.getData());
        catHelper.setLoRepository(loCategoryInfo.getLoRepositoryKey());
        catHelper.setEffectiveDate(loCategoryInfo.getEffectiveDate());
        catHelper.setExpirationDate(loCategoryInfo.getExpirationDate());
        /* TODO - doesn't work on the client; what to do?
        AttributesAssembler attAssembler = new AttributesAssembler();
        catHelper.setAttributes(attAssembler.assemble(cat.getAttributes()));
        */
        catHelper.setState(loCategoryInfo.getStateKey());
        catHelper.setType(loCategoryInfo.getTypeKey());

        MetaInfo mInfo = loCategoryInfo.getMetaInfo();
        if (mInfo != null) {
            MetaInfoHelper metaHelper = MetaInfoHelper.wrap(new Data());
            metaHelper.setCreateId(mInfo.getCreateId());
            metaHelper.setCreateTime(mInfo.getCreateTime());
            metaHelper.setUpdateId(mInfo.getUpdateId());
            metaHelper.setUpdateTime(mInfo.getUpdateTime());
            metaHelper.setVersionInd(mInfo.getVersionInd());
            catHelper.setMetaInfo(metaHelper.getData());
      }
       
 
        // TODO - LoCategoryInfoAssembler, w/ an assemble method so we can just do 
        // categoriesData.add(LoCategoryInfoAssembler.assemble(cat)) instead
        // of all the above
        return catHelper.getData();
    }
    
    public static LoCategoryInfo toLoCategoryInfo(Data categoryData) {
        LoCategoryInfo catInfo = null;
        if (categoryData != null) {
            LoCategoryInfoHelper catHelper = new LoCategoryInfoHelper(categoryData);
            catInfo = new LoCategoryInfo();
            catInfo.setId(catHelper.getId());
            RichTextHelper rtHelper = new RichTextHelper(catHelper.getDesc());
            // testing
            if (null != catHelper) {
                RichTextInfo descInfo = new RichTextInfo();
                descInfo.setFormatted(rtHelper.getFormatted());
                descInfo.setPlain(rtHelper.getPlain());
                catInfo.setDescr(descInfo);
            }
            catInfo.setEffectiveDate(catHelper.getEffectiveDate());
            catInfo.setExpirationDate(catHelper.getExpirationDate());
            catInfo.setLoRepositoryKey(catHelper.getLoRepository());
            // TODO - this should't be necessary when DOL pushed down into LOPicker
            // and its LOCategoryBuilder
            // catInfo.setAttributes(catHelper.getAttributes());
            catInfo.setName(catHelper.getName());
            catInfo.setStateKey(catHelper.getState());
            catInfo.setTypeKey(catHelper.getType());
            // TODO - LoCategoryInfoAssembler, w/ a disassemble method so we can just do 
            // categoriesData.add(LoCategoryInfoAssembler.disassemble(catData)) instead
            // of all the above

            MetaInfo metaInfo = new MetaInfo();
            MetaInfoHelper metaHelper = MetaInfoHelper.wrap(catHelper.getMetaInfo());

            if (metaHelper != null) {
	            metaInfo.setCreateId(metaHelper.getCreateId());
	            metaInfo.setCreateTime(metaHelper.getCreateTime());
	            metaInfo.setUpdateId(metaHelper.getUpdateId());
	            metaInfo.setUpdateTime(metaHelper.getUpdateTime());
	            metaInfo.setVersionInd(metaHelper.getVersionInd());
	            catInfo.setMetaInfo(metaInfo);
            }
        }
        return catInfo;
    }
    
}