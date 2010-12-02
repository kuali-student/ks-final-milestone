package org.kuali.maven.mojo.s3;

import org.apache.commons.lang.StringUtils;

public class HtmlUtils {

    /**
     * Return an HTML ahref tag
     */
    public String getHref(String dest, String show) {
        return "<a href=\"" + dest + "\">" + show + "</a>";
    }

    /**
     * Return an HTML img tag
     */
    public String getImage(String image) {
        return "<img src=\"" + image + "\">";
    }

    public String getOpenTag(Tag tag) {
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.repeat(" ", tag.getIndent()));
        sb.append("<" + tag.getName());
        if (tag.getId() != null) {
            sb.append(" id=\"" + tag.getId() + '"');
        }
        if (tag.getClazz() != null) {
            sb.append(" class=\"" + tag.getClazz() + '"');
        }
        sb.append(">\n");
        return sb.toString();
    }

    public String getCloseTag(Tag tag) {
        return StringUtils.repeat(" ", tag.getIndent()) + "</" + tag.getName() + ">\n";
    }

    public String getTag(Tag tag, String content) {
        StringBuffer sb = new StringBuffer();
        sb.append(getOpenTag(tag));
        sb.append(StringUtils.repeat(" ", tag.getIndent() + 1));
        sb.append(content);
        sb.append("\n");
        sb.append(getCloseTag(tag));
        return sb.toString();
    }

}
