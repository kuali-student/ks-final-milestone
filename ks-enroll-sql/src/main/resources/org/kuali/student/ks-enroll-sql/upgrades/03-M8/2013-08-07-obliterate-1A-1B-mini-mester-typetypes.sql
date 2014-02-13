-- reverse entries generated from: 2013-07-01-KSEN_ATP-minimesters.sql:
delete from KSEN_TYPETYPE_RELTN where ID in(
  'kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Mini-mester1A',
  'kuali.type.type.relation.type.allowed.kuali.atp.type.Mini-mester1A.kuali.milestone.type.group.instructional',
  'kuali.type.type.relation.type.allowed.kuali.atp.type.Mini-mester1A.kuali.milestone.type.group.registration',
  'kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Mini-mester1B',
  'kuali.type.type.relation.type.allowed.kuali.atp.type.Mini-mester1B.kuali.milestone.type.group.instructional',
  'kuali.type.type.relation.type.allowed.kuali.atp.type.Mini-mester1B.kuali.milestone.type.group.registration',
  'kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Mini-mester1A',
  'kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Mini-mester1B',
  'kuali.type.type.relation.type.contains.kuali.atp.type.Fall.kuali.atp.type.Mini-mester1A',
  'kuali.type.type.relation.type.contains.kuali.atp.type.Fall.kuali.atp.type.Mini-mester1B'
)
/