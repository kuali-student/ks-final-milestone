package org.kuali.maven.mojo.s3;

import org.apache.commons.lang.StringUtils;

public class HtmlUtils {

    int indent = 0;

    public String getIndentedContent(String content) {
        return getIndent() + content;
    }

    public String getIndent() {
        return StringUtils.repeat(" ", (indent + 1));
    }

    /**
     * Return an HTML ahref tag
     */
    public String getHref(String dest, String show) {
        return getIndent() + "<a href=\"" + dest + "\">" + show + "</a>";
    }

    /**
     * Return an HTML img tag
     */
    public String getImage(String image) {
        return getIndent() + "<img src=\"" + image + "\">";
    }

    public String openTag(Tag tag) {
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.repeat(" ", indent++));
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

    public String closeTag(Tag tag) {
        return StringUtils.repeat(" ", --indent) + "</" + tag.getName() + ">\n";
    }

    public String getTag(Tag tag, String content) {
        StringBuffer sb = new StringBuffer();
        sb.append(openTag(tag));
        sb.append(StringUtils.repeat(" ", (indent + 1)));
        sb.append(content);
        sb.append("\n");
        sb.append(closeTag(tag));
        return sb.toString();
    }
}
