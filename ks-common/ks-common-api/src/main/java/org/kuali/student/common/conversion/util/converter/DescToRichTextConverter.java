package org.kuali.student.common.conversion.util.converter;

import org.dozer.DozerConverter;
import org.kuali.student.r2.common.dto.RichTextInfo;

public class DescToRichTextConverter extends DozerConverter<String, RichTextInfo> {

    public DescToRichTextConverter() {
        super(String.class, RichTextInfo.class);
    }

    @Override
    public RichTextInfo convertTo(String source, RichTextInfo destination) {
        RichTextInfo convertedRichText = new RichTextInfo();
        convertedRichText.setPlain(source);
        convertedRichText.setFormatted(source);
        return convertedRichText;
    }

    @Override
    public String convertFrom(RichTextInfo source, String destination) {
        return source.getPlain();
    }

}
