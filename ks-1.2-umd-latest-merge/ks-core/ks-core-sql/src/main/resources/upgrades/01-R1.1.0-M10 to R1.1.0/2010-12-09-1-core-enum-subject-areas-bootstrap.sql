update KSEM_ENUM_VAL_T set VAL = 'Arts and Humanities' where ID = 'SUBJAREA61'
/
update KSEM_ENUM_VAL_T set VAL = 'Biometrics' where ID = 'SUBJAREA63'
/
update KSEM_ENUM_VAL_T set VAL = 'Chemical Engineering' where ID = '11'
/
update KSEM_ENUM_VAL_T set VAL = 'Civil Engineering' where ID = '13'
/
update KSEM_ENUM_VAL_T set VAL = 'Mechanical Engineering' where ID = '25'
/
delete from KSEM_ENUM_VAL_T where ID = '6b84a916-92a6-4538-bf00-fafb7d92b22e'
/
update KSEM_ENUM_VAL_T set EFF_DT = to_date('2010-07-01', 'yyyy-mm-dd'), SORT_KEY = '74' where CD = 'CHEM'
/
delete from KSEM_ENUM_VAL_T where ID = 'bb2ee74e-7db5-46bc-a263-3fa7ae98d89b'
/
update KSEM_ENUM_VAL_T set EFF_DT = to_date('2010-07-01', 'yyyy-mm-dd'), SORT_KEY = '76' where CD = 'ENGL'
/
delete from KSEM_ENUM_VAL_T where ID = '79cc4865-fd18-44e0-997e-35df04afb4dc'
/
update KSEM_ENUM_VAL_T set EFF_DT = to_date('2010-07-01', 'yyyy-mm-dd'), SORT_KEY = '78', ABBREV_VAL = 'SOCY', CD = 'SOCY' where ID = '31'
/
