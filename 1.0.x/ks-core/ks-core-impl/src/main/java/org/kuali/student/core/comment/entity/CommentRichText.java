package org.kuali.student.core.comment.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.core.entity.RichText;

@Entity
@Table(name = "KSCO_RICH_TEXT_T")
public class CommentRichText extends RichText {

}
