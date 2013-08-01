Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1501','KS-KRMS-NL-USAGE-1005','a minimum cumulative GPA of $intValue','10040',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1502','KS-KRMS-NL-USAGE-1005','a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") total credits','10039',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1503','KS-KRMS-NL-USAGE-1005','$freeText','10031',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1504','KS-KRMS-NL-USAGE-1005','#if($intValue == 1 && $courseCluSet.getCluList().size() == 1) $courseCluSet.getCluSetAsCode() #{else} a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from $courseCluSet.getCluSetAsCode() #end','10019',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1505','KS-KRMS-NL-USAGE-1005','admitted to a program offered by  #if($org.getAttributes().get("umType") == "Academic Department")within $org.getLongName() department#{else}in $org.getLongName()#end','10065',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1506','KS-KRMS-NL-USAGE-1005','$courseClu.getOfficialIdentifier().getCode()','10017',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1507','KS-KRMS-NL-USAGE-1005','#if($courseCluSet.getCluList().size() == 1)$courseCluSet.getCluSetAsCode()#{else}$courseCluSet.getCluSetAsCode()#end','10018',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1508','KS-KRMS-NL-USAGE-1005','no more than $intValue $NLHelper.getProperGrammar($intValue, "course") from #if(($intValue == 1 &&$courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end','10053',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1509','KS-KRMS-NL-USAGE-1005','a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") from #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")$courseCluSet.getCluSetAsCode()#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end','10054',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1510','KS-KRMS-NL-USAGE-1005','admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") #if($programCluSet.getCluList().size() == 1)program#{else}programs#end','10052',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1511','KS-KRMS-NL-USAGE-1005','$courseClu.getOfficialIdentifier().getCode()','10032',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1512','KS-KRMS-NL-USAGE-1005','must not have earned a grade of $grade or higher in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end','10026',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1513','KS-KRMS-NL-USAGE-1005','a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")$courseCluSet.getCluSetAsCode()#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end with a minimum grade of $grade','10028',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1514','KS-KRMS-NL-USAGE-1005','any courses from #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange")#end#end','10020',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1515','KS-KRMS-NL-USAGE-1005','student must be in a class standing of $classStanding.name or greater','10067',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1516','KS-KRMS-NL-USAGE-1005','a minimum grade of $grade in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end','10027',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1517','KS-KRMS-NL-USAGE-1005','permission of #if($org.getAttributes().get("umType") == "Academic Department")$org.getLongName() department#{else}$org.getLongName()#end','10035',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1518','KS-KRMS-NL-USAGE-1005','permission of instructor','10034',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1519','KS-KRMS-NL-USAGE-1005','$courseClu.getOfficialIdentifier().getCode()','10030',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1520','KS-KRMS-NL-USAGE-1005','a minimum of &intValue $NLHelper.getProperGrammer($intValue, "course") from $courseCluSet.getCluSetAsCode()','10024',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1521','KS-KRMS-NL-USAGE-1005','#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange")#end#end','10023',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1522','KS-KRMS-NL-USAGE-1005','may be repeated for a maximum of $intValue credits','10029',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1523','KS-KRMS-NL-USAGE-1005','admitted to any program offered at the course campus location','10033',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1524','KS-KRMS-NL-USAGE-1005','not admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) program','10036',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1525','KS-KRMS-NL-USAGE-1005','a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") from courses in the $org.getLongName()','10055',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1526','KS-KRMS-NL-USAGE-1005','no more than $intValue $NLHelper.getProperGrammar($intValue, "credit") from $courseCluSet.getCluSetAsCode()','10021',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1527','KS-KRMS-NL-USAGE-1005','any credits from $courseCluSet.getCluSetAsCode()','10022',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1528','KS-KRMS-NL-USAGE-1005','a minimum GPA of $intValue in $courseCluSet.getCluSetAsCode()','10025',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1529','KS-KRMS-NL-USAGE-1005','must not have been admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) Program with a class standing of $classStanding.name','10064',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1530','KS-KRMS-NL-USAGE-1005','may not repeat $courseClu.getOfficialIdentifier().getCode()','10057',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1531','KS-KRMS-NL-USAGE-1005','student in <Program> can enrol in a maximum of <n> courses for the term','10046',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1532','KS-KRMS-NL-USAGE-1005','$courseClu.getOfficialIdentifier().getCode() between $term.name and $term2.name','10062',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1533','KS-KRMS-NL-USAGE-1005','student cannot add Activity Offering with <Activity Offering State>  of <state> ','10056',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1534','KS-KRMS-NL-USAGE-1005','must not be in a class standing of $classStanding.name','10069',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1535','KS-KRMS-NL-USAGE-1005','#if($courseCluSet.getCluList().size() == 1)may not repeat $courseCluSet.getCluSetAsCode()#{else}May not repeat any of $courseCluSet.getCluSetAsCode()#end','10058',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1536','KS-KRMS-NL-USAGE-1005','course has more than <n> minutes overlap with already enrolled course','10044',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1537','KS-KRMS-NL-USAGE-1005','$courseClu.getOfficialIdentifier().getCode() as of $term.name','10060',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1538','KS-KRMS-NL-USAGE-1005','must be concurrently enrolled in all courses from <courses>','10071',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1539','KS-KRMS-NL-USAGE-1005','students admitted to <campus> may take no more than <n  credits> at <campus> in <duration> <durationType>','10051',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1540','KS-KRMS-NL-USAGE-1005','students admitted to <campus> may take no more than <n> courses at <campus> in <duration> <durationType>','10050',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1541','KS-KRMS-NL-USAGE-1005','if student is <attribute> AND upon drop total credit hours would be less than <min credit hours>, prevent drop ','10043',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1542','KS-KRMS-NL-USAGE-1005','may not repeat $courseClu.getOfficialIdentifier().getCode() if repeated $intValue times ','10072',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1543','KS-KRMS-NL-USAGE-1005','$courseClu.getOfficialIdentifier().getCode() prior to $term.name','10061',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1544','KS-KRMS-NL-USAGE-1005','student must be in a class standing of $classStanding.name or less','10068',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1545','KS-KRMS-NL-USAGE-1005','course has less than <n> minutes between start time or end time with already enrolled course','10045',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1546','KS-KRMS-NL-USAGE-1005','student must be in a class standing of $classStanding.name','10066',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1547','KS-KRMS-NL-USAGE-1005','student is in an existing seat pool for the course with an available seat','10059',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1548','KS-KRMS-NL-USAGE-1005','if student is <attribute> AND upon drop total credit hours would be less than <min credit hours>, prevent drop ','10042',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1549','KS-KRMS-NL-USAGE-1005','must not have earned more than $intValue $NLHelper.getProperGrammar($intValue, "credit")','10047',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1550','KS-KRMS-NL-USAGE-1005','a minimum cumulative GPA of $intValue in $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()','10041',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1560','KS-KRMS-NL-USAGE-1005','Antirequisite:','10005',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1561','KS-KRMS-NL-USAGE-1005','Corequisite:','10006',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1562','KS-KRMS-NL-USAGE-1005','Recommended Preparation:','10008',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1563','KS-KRMS-NL-USAGE-1005','Student Eligibility & Prerequisite:','10010',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1564','KS-KRMS-NL-USAGE-1005','Repeatable for Credit:','10011',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1565','KS-KRMS-NL-USAGE-1005','Course that Restricts Credits:','10012',0)
/
Delete from KRMS_NL_TMPL_T where NL_TMPL_ID in ('KS-KRMS-NL-TMPL-1373', 'KS-KRMS-NL-TMPL-1368')
/