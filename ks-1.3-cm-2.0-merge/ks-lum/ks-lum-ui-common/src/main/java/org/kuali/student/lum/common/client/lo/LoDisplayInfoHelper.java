package org.kuali.student.lum.common.client.lo;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.helper.PropertyEnum;

/**
 * @author Igor
 */
public class LoDisplayInfoHelper {
    private Data data;
    public enum Properties implements PropertyEnum
    {
        LO_INFO ("loInfo"),
        LO_DISPLAY_INFO_LIST ("loDisplayInfoList"),
        LO_CATEGORY_INFO_LIST ("loCategoryInfoList");

        private final String key;

        private Properties (final String key)
        {
            this.key = key;
        }

        @Override
        public String getKey ()
        {
            return this.key;
        }
    }

    public LoDisplayInfoHelper() {
        data = new Data();
    }

    public LoDisplayInfoHelper(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setLoInfo(Data loInfoData) {
        HelperUtil.setDataField(LoDisplayInfoHelper.Properties.LO_INFO, data, loInfoData);
    }

    public Data getLoInfo() {
        return HelperUtil.getDataField(LoDisplayInfoHelper.Properties.LO_INFO, data);
    }

    public void setDisplayInfoList(Data displayInfoListData) {
        HelperUtil.setDataField(LoDisplayInfoHelper.Properties.LO_DISPLAY_INFO_LIST, data, displayInfoListData);
    }

    public Data getDisplayInfoList() {
        return HelperUtil.getDataField(LoDisplayInfoHelper.Properties.LO_DISPLAY_INFO_LIST, data);
    }

    public void setCategoryInfoList(Data categoryInfoListData) {
        HelperUtil.setDataField(LoDisplayInfoHelper.Properties.LO_CATEGORY_INFO_LIST, data, categoryInfoListData);
    }

    public Data getCategoryInfoList() {
        return HelperUtil.getDataField(LoDisplayInfoHelper.Properties.LO_CATEGORY_INFO_LIST, data);
    }
}