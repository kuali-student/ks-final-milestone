package org.kuali.student.lum.lu.assembly;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.AttributeInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoMetadata;

public class AttributesAssembler implements Assembler<Data, Map<String, String>>{

	@Override
	public Data assemble(Map<String, String> input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		Data data = new Data();
		
		for (Map.Entry<String, String> entry : input.entrySet()) {
			AttributeInfoHelper hlp = AttributeInfoHelper.wrap(new Data());
			hlp.setKey(entry.getKey());
			hlp.setValue(entry.getValue());
			data.add(hlp.getData());
		}
		return data;
	}

	@Override
	public Map<String, String> disassemble(Data input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		Map<String, String> result = new TreeMap<String, String>();
	
		return result;
	}

	@Override
	public Data get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(AttributesAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata(String type, String state) throws AssemblyException {
		return new RichTextInfoMetadata().getMetadata(type, state);
	}

	@Override
	public SaveResult<Data> save(Data input)
			throws AssemblyException {
		throw new UnsupportedOperationException(AttributesAssembler.class.getName() + " does not support persistence");
	}

	@Override
	public List<ValidationResultInfo> validate(Data input)
			throws AssemblyException {
		return null;
	}
	@Override
	public SearchResult search(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}
}
