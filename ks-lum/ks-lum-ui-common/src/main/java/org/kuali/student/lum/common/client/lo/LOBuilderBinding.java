package org.kuali.student.lum.common.client.lo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;

/**
 * @author Igor
 */
public class LOBuilderBinding extends ModelWidgetBindingSupport<LOBuilder> {

    public static LOBuilderBinding INSTANCE = new LOBuilderBinding();

    /**
     * Gets a list of OutlineNode from LOBuilder.  Goes through the list one by one.
     * While going through the list the algorithm keeps track of the current parent of
     * a particular level.
     */
    @Override
    public void setModelValue(LOBuilder builder, DataModel model, String path) {
        Data losData = new Data();
        Map<Integer, Data> parentStore = new HashMap<Integer, Data>();
        int sequence = 0; // the ordering information of DisplayInfo
        List<OutlineNode<LOPicker>> value = stripOutEmptyInput(builder.getValue());
        if (value != null) {
            for (OutlineNode<LOPicker> node : value) {
                if (node.getIndentLevel() == 0) {
                    Data item = createLoDisplayInfoData(node, sequence);
                    parentStore.put(new Integer(0), item);
                    losData.add(item);
                } else {
                    Data item = createLoDisplayInfoData(node, sequence);
                    LoDisplayInfoHelper parentItemHelper = null;
                    parentStore.put(node.getIndentLevel(), item);
                    parentItemHelper = new LoDisplayInfoHelper(
                            parentStore.get(node.getIndentLevel() - 1));
                    parentItemHelper.getDisplayInfoList().add(item);
                }
                sequence++;
            }
        }
        model.set(QueryPath.parse(path), losData);
    }

    @Override
    public void setWidgetValue(LOBuilder builder, DataModel model, String path) {
        List<OutlineNode<LOPicker>> loOutlineNodes = new ArrayList<OutlineNode<LOPicker>>();

        // change the 'courseSpecificLOs' elements into a List of OutlineNode's
        QueryPath qPath = QueryPath.parse(path);

        Data data = null;
        if (model != null) {
            data = model.get(qPath);
        }

        dataToOutlineNodes(data, loOutlineNodes, 0);
        builder.setValue(loOutlineNodes);
    }

    private List<OutlineNode<LOPicker>> stripOutEmptyInput(List<OutlineNode<LOPicker>> input) {
        List<OutlineNode<LOPicker>> value = new ArrayList<OutlineNode<LOPicker>>();
        boolean allEmptyNodes = true;
        if (input != null) {
            for (OutlineNode<LOPicker> node : input) {
                String desc = node.getUserObject().getLOText();
                int indentLevel = node.getIndentLevel();
                List<LoCategoryInfo> categories = node.getUserObject().getLoCategories();
                if (desc != null && desc.trim().length() > 0 || indentLevel > 0 || categories != null && !categories.isEmpty()) {
                    allEmptyNodes = false;
                    value.add(node);
                }
            }
        }
        if (allEmptyNodes) {
            value = null;
        }
        return value;
    }

    private void dataToOutlineNodes(Data data, List<OutlineNode<LOPicker>> loOutlineNodes, int identLevel) {
        if (data != null) {
            LoDisplayInfoSortedSet sortedDisplayInfos = new LoDisplayInfoSortedSet();
            for (Data.Property property : data) {
                Data loDisplayInfoData = property.getValue();
                LoDisplayInfoHelper loDisplayInfoHelper = new LoDisplayInfoHelper(loDisplayInfoData);
                sortedDisplayInfos.add(loDisplayInfoHelper);
            }
            for (LoDisplayInfoHelper loDisplayInfoHelper : sortedDisplayInfos) {
                LOPicker picker = new LOPicker(LOBuilder.getMessageGroup(), LOBuilder.getType(), LOBuilder.getState(), LOBuilder.getRepoKey());
                
                LoInfoHelper loInfoHelper = new LoInfoHelper(loDisplayInfoHelper.getLoInfo());
                RichTextHelper descriptionHelper = new RichTextHelper(loInfoHelper.getDesc());
                picker.setLOText(descriptionHelper.getPlain());
                List<LoCategoryInfo> categories = getCategoryList(loDisplayInfoHelper);
                picker.setLOCategories(categories);
                picker.setMetaInfoData(loInfoHelper.getMetaInfo());
                OutlineNode<LOPicker> node = new OutlineNode<LOPicker>();

                node.setUserObject(picker);
                node.setOpaque(loInfoHelper.getId());
                node.setIndentLevel(identLevel);
                loOutlineNodes.add(node);
                // recurse
                dataToOutlineNodes(loDisplayInfoHelper.getDisplayInfoList(), loOutlineNodes, identLevel + 1);
            }
        }
    }

    private List<LoCategoryInfo> getCategoryList(LoDisplayInfoHelper loDisplayInfoHelper) {
        List<LoCategoryInfo> categoryInfos = new ArrayList<LoCategoryInfo>();
        Data categoriesData = loDisplayInfoHelper.getCategoryInfoList();

        if (null != categoriesData) {
            Iterator<Data.Property> itr = categoriesData.realPropertyIterator();

            while (itr.hasNext()) {
                Data.Property catProp = itr.next();
                Data catData = catProp.getValue();
                LoCategoryInfo catInfo = CategoryDataUtil.toLoCategoryInfo(catData);
                categoryInfos.add(catInfo);
            }
        }
        return categoryInfos;
    }

    private Data createLoDisplayInfoData(OutlineNode<LOPicker> node, int sequence) {
        Data result = null;
        LoDisplayInfoHelper loDisplayInfoDataHelper = new LoDisplayInfoHelper(new Data());
        LoInfoHelper loInfoHelper = new LoInfoHelper(new Data());
        // loInfo.id
        loInfoHelper.setId((String) node.getOpaque());
        // loInfo.desc
        RichTextHelper richTextHelper = new RichTextHelper();
        String loDesc = node.getUserObject().getLOText();
        richTextHelper.setFormatted(loDesc);
        richTextHelper.setPlain(loDesc);
        loInfoHelper.setDesc(richTextHelper.getData());
        // loInfo.name
        if (null == loInfoHelper.getName() || loInfoHelper.getName().length() == 0) {
            loInfoHelper.setName("SINGLE USE LO");
        }

        // loCategoryInfoList
        Data categoriesData = new Data();
        for (LoCategoryInfo cat : node.getUserObject().getLoCategories()) {
            categoriesData.add(CategoryDataUtil.toData(cat));
        }

        // loInfo.sequence
        loInfoHelper.setSequence(Integer.toString(sequence));

        // loInfo.metaInfo
        loInfoHelper.setMetaInfo(node.getUserObject().getMetaInfoData());

        loDisplayInfoDataHelper.setLoInfo(loInfoHelper.getData());
        loDisplayInfoDataHelper.setCategoryInfoList(categoriesData);
        result = loDisplayInfoDataHelper.getData();
        return result;
    }

}