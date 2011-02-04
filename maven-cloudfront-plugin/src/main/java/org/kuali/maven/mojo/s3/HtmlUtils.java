package org.kuali.maven.mojo.s3;

import org.apache.commons.lang.StringUtils;

/**
 * Utility methods for generating html
 */
public class HtmlUtils {

    int indent = 0;

    public String getIndentedContent(final String content) {
        return getIndent() + content;
    }

    public String getIndent() {
        return StringUtils.repeat(" ", indent);
    }

    /**
     * Return an HTML ahref tag
     */
    public String getHref(final String dest, final String show) {
        return getIndent() + "<a href=\"" + dest + "\">" + show + "</a>";
    }

    /**
     * Return an HTML img tag
     */
    public String getImage(final String image) {
        return getIndent() + "<img src=\"" + image + "\">";
    }

    public String openTag(final Tag tag) {
        StringBuffer sb = new StringBuffer();
        sb.append(getIndent());
        indent++;
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

    public String closeTag(final Tag tag) {
        indent--;
        return getIndent() + "</" + tag.getName() + ">\n";
    }

    public String getTag(final Tag tag, final String content) {
        StringBuffer sb = new StringBuffer();
        sb.append(openTag(tag));
        sb.append(getIndent());
        sb.append(content);
        sb.append("\n");
        sb.append(closeTag(tag));
        return sb.toString();
    }
}
