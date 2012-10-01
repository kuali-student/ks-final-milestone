-- Set all grade roster levels to 'Course' by default
update KSEN_LUI_ATTR set attr_value = 'kuali.lui.type.course.offering' where ATTR_KEY = 'kuali.attribute.grade.roster.level.type.key'
/

-- Set all final exam driver activity values to 'Lecture' for any format offerings with a Lecture
update KSEN_LUI_ATTR set ATTR_VALUE = 'kuali.lu.type.activity.Lecture' where id in (select lui_attr.ID from KSEN_LUI lui, KSEN_LUI_IDENT ident, KSEN_LUI_ATTR lui_attr where lui.id = ident.LUI_ID and ident.shrt_name like 'LC%' and lui.id = lui_attr.OWNER_ID and lui.LUI_TYPE = 'kuali.lui.type.course.format.offering' and lui_attr.ATTR_KEY='kuali.attribute.final.exam.level.type')
/

-- Set all final exam driver activity values to 'Seminar' for any seminar format offerings
update KSEN_LUI_ATTR set ATTR_VALUE = 'kuali.lu.type.activity.seminar' where id in (select lui_attr.ID from KSEN_LUI lui, KSEN_LUI_IDENT ident, KSEN_LUI_ATTR lui_attr where lui.id = ident.LUI_ID and ident.shrt_name = 'SM' and lui.id = lui_attr.OWNER_ID and lui.LUI_TYPE = 'kuali.lui.type.course.format.offering' and lui_attr.ATTR_KEY='kuali.attribute.final.exam.level.type')
/

-- Set all final exam driver activity values to 'Independent Study' for any independent study format offerings
update KSEN_LUI_ATTR set ATTR_VALUE = 'kuali.lu.type.activity.independentstudy' where id in (select lui_attr.ID from KSEN_LUI lui, KSEN_LUI_IDENT ident, KSEN_LUI_ATTR lui_attr where lui.id = ident.LUI_ID and ident.shrt_name = 'IS' and lui.id = lui_attr.OWNER_ID and lui.LUI_TYPE = 'kuali.lui.type.course.format.offering' and lui_attr.ATTR_KEY='kuali.attribute.final.exam.level.type')
/

-- Set all final exam driver activity values to 'Lab' for any lab format offerings
update KSEN_LUI_ATTR set ATTR_VALUE = 'kuali.lu.type.activity.Lab' where id in (select lui_attr.ID from KSEN_LUI lui, KSEN_LUI_IDENT ident, KSEN_LUI_ATTR lui_attr where lui.id = ident.LUI_ID and ident.shrt_name = 'LB' and lui.id = lui_attr.OWNER_ID and lui.LUI_TYPE = 'kuali.lui.type.course.format.offering' and lui_attr.ATTR_KEY='kuali.attribute.final.exam.level.type')
/

-- Set all final exam driver activity values to 'Conference' for any conference format offerings
update KSEN_LUI_ATTR set ATTR_VALUE = 'kuali.lu.type.activity.conference' where id in (select lui_attr.ID from KSEN_LUI lui, KSEN_LUI_IDENT ident, KSEN_LUI_ATTR lui_attr where lui.id = ident.LUI_ID and ident.shrt_name = 'CO' and lui.id = lui_attr.OWNER_ID and lui.LUI_TYPE = 'kuali.lui.type.course.format.offering' and lui_attr.ATTR_KEY='kuali.attribute.final.exam.level.type')
/

