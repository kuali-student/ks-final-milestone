--KSENROLL-15151
update KSEN_HOLD_ISSUE set NAME = 'Academic Probation / Dismissal'
where HOLD_CD = 'DISP02'
/
update KSEN_HOLD_ISSUE set NAME = 'Academically Ineligible'
where HOLD_CD = 'ACAD02'
/
update KSEN_HOLD_ISSUE set NAME = 'Administratively Ineligible'
where HOLD_CD = 'DISP03'
/
update KSEN_HOLD_ISSUE set NAME = 'Must choose degree / major'
where HOLD_CD = 'ACAD06'
/
update KSEN_HOLD_ISSUE set NAME = 'Financial Aid'
where HOLD_CD = 'FIN02'
/
update KSEN_HOLD_ISSUE set NAME = 'Financially Ineligible'
where HOLD_CD = 'FIN01'
/
update KSEN_HOLD_ISSUE set NAME = 'Fundamental Studies English'
where HOLD_CD = 'ACAD04'
/
update KSEN_HOLD_ISSUE set NAME = 'Fundamental Studies Math'
where HOLD_CD = 'ACAD05'
/
update KSEN_HOLD_ISSUE set NAME = 'International Student'
where HOLD_CD = 'ADMIS02'
/
update KSEN_HOLD_ISSUE set NAME = 'Judicially Ineligible'
where HOLD_CD = 'DISP04'
/
update KSEN_HOLD_ISSUE set NAME = 'Student last attended in ...'
where HOLD_CD = 'ACAD08'
/
update KSEN_HOLD_ISSUE set NAME = 'Mandatory Advising'
where HOLD_CD = 'ADV01'
/
update KSEN_HOLD_ISSUE set NAME = 'New student requires advising'
where HOLD_CD = 'ADMIS01'
/
update KSEN_HOLD_ISSUE set NAME = 'No record of immunization'
where HOLD_CD = 'MED01'
/
update KSEN_HOLD_ISSUE set NAME = 'Student has made too many registration transactions'
where HOLD_CD = 'ACAD07'
/
update KSEN_HOLD_ISSUE set NAME = 'Student athlete'
where HOLD_CD = 'ADV02'
/
update KSEN_HOLD_ISSUE set NAME = 'Term Only'
where HOLD_CD = 'ACAD09'
/
