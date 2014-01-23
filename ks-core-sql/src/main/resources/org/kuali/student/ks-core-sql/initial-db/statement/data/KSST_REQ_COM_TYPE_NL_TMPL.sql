TRUNCATE TABLE KSST_REQ_COM_TYPE_NL_TMPL DROP STORAGE
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.completed.none','#if($courseCluSet.getCluList().size() == 1)Must not have successfully completed $courseCluSet.getCluSetAsCode()#{else}Must not have successfully completed any courses from $courseCluSet.getCluSetAsCode()#end',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1001','en','KUALI.RULE','kuali.reqComponent.type.program.programset.completed.nof','Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "program") from $programCluSet.getCluSetAsLongName() $NLHelper.getProperGrammar($programCluSet.getCluList().size(), "program")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1002','en','KUALI.RULE','kuali.reqComponent.type.program.programset.notcompleted.nof','Must not have successfully completed #if($programCluSet.getCluList().size() > 1)any of #end$programCluSet.getCluSetAsLongName() $NLHelper.getProperGrammar($programCluSet.getCluList().size(), "program")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1003','en','KUALI.RULE','kuali.reqComponent.type.program.programset.completed.all','Must have successfully completed #if($programCluSet.getCluList().size() != 1)all of #end$programCluSet.getCluSetAsLongName() $NLHelper.getProperGrammar($programCluSet.getCluList().size(), "program")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1004','en','KUALI.RULE','kuali.reqComponent.type.program.programset.coursecompleted.nof','Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from $programCluSet.getCluSetAsLongName() $NLHelper.getProperGrammar($programCluSet.getCluList().size(), "program")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1005','en','KUALI.RULE','kuali.reqComponent.type.program.admitted.credits','Must be admitted to program prior to earning $intValue $NLHelper.getProperGrammar($intValue, "credit")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1006','en','KUALI.RULE','kuali.reqComponent.type.program.credits.min','Must have earned a minimum of $intValue total $NLHelper.getProperGrammar($intValue, "credit")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1007','en','KUALI.RULE','kuali.reqComponent.type.program.credits.max','Must not have earned more than $intValue $NLHelper.getProperGrammar($intValue, "credit")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1008','en','KUALI.RULE','kuali.reqComponent.type.program.completion.duration','Must not exceed $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase() without completing program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1009','en','KUALI.RULE','kuali.reqComponent.type.program.candidate.status.duration','Must attain candidate status within $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase() after program entry term',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('101','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.completed.none','#if($courseCluSet.getCluList().size() == 1)Must not have successfully completed#{else}Must not have successfully completed any courses from#end',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1010','en','KUALI.RULE','kuali.reqComponent.type.program.completion.duration.afterentry','Must complete program within $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase() after program entry term',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1011','en','KUALI.RULE','kuali.reqComponent.type.program.residence.credits.final','$programClu.getOfficialIdentifier().getLongName() students must take their final $intValue $NLHelper.getProperGrammar($intValue, "credit") in residence',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1012','en','KUALI.RULE','kuali.reqComponent.type.program.enrolled.credits.final','$programClu.getOfficialIdentifier().getLongName() students must be enrolled in their graduation major for the final $intValue $NLHelper.getProperGrammar($intValue, "credit") taken',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1013','en','KUALI.RULE','kuali.reqComponent.type.program.minors.nof','Must earn no more than $intValue $NLHelper.getProperGrammar($intValue, "minor") as part of a program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1014','en','KUALI.RULE','kuali.reqComponent.type.program.minor.admitted.classstanding','Must be admitted to a minor program only if they have junior or senior class standing',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1015','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.completed.max','Must have successfully completed no more than $intValue $NLHelper.getProperGrammar($intValue, "course") from $courseCluSet.getCluSetAsCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1016','en','KUALI.RULE','kuali.reqComponent.type.program.cumulative.gpa.min','Must have earned a minimum cumulative GPA of $gpa',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1017','en','KUALI.RULE','kuali.reqComponent.type.program.duration.gpa.min','Must have earned a minimum $durationType.getName().toLowerCase() GPA of $gpa',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('102','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.completed.all','#if($courseCluSet.getCluList().size() == 1)Must have successfully completed#{else}Must have successfully completed all courses from#end',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('103','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.completed.nof','#if($intValue == 1 && $courseCluSet.getCluList().size() == 1)Must have successfully completed#{else}Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from#end',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('104','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.enrolled.nof','Must be concurrently enrolled in a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('105','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.credits.completed.nof','Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") from',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('106','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.credits.completed.none','Must not have successfully completed any credits from',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('107','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.credits.completed.max','Must successfully complete no more than $intValue $NLHelper.getProperGrammar($intValue, "credit") from',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('108','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.gpa.min','Must have earned a minimum GPA of $gpa in',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('109','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.enrolled.all','#if($courseCluSet.getCluList().size() == 1)Must be concurrently enrolled in#{else}Must be concurrently enrolled in all courses from#end',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1101','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.programset.completed.nof','Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "program") from $programCluSet.getCluSetAsLongName() $NLHelper.getProperGrammar($programCluSet.getCluList().size(), "program")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1102','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.programset.notcompleted.nof','Must not have successfully completed #if($programCluSet.getCluList().size() > 1)any of #end$programCluSet.getCluSetAsLongName() $NLHelper.getProperGrammar($programCluSet.getCluList().size(), "program")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1103','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.programset.completed.all','Must have successfully completed #if($programCluSet.getCluList().size() != 1)all of #end$programCluSet.getCluSetAsLongName() $NLHelper.getProperGrammar($programCluSet.getCluList().size(), "program")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1104','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.programset.coursecompleted.nof','Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from $programCluSet.getCluSetAsLongName() $NLHelper.getProperGrammar($programCluSet.getCluList().size(), "program")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1105','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.admitted.credits','Must be admitted to program prior to earning $intValue $NLHelper.getProperGrammar($intValue, "credit")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1106','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.credits.min','Must have earned a minimum of $intValue total $NLHelper.getProperGrammar($intValue, "credit")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1107','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.credits.max','Must not have earned more than $intValue $NLHelper.getProperGrammar($intValue, "credit")',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1108','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.completion.duration','Must not exceed $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase() without completing program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1109','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.candidate.status.duration','Must attain candidate status within $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase() after program entry term',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1110','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.completion.duration.afterentry','Must complete program within $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase() after program entry term',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1111','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.residence.credits.final','$programClu.getOfficialIdentifier().getLongName() students must take their final $intValue $NLHelper.getProperGrammar($intValue, "credit") in residence',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1112','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.enrolled.credits.final','$programClu.getOfficialIdentifier().getLongName() students must be enrolled in their graduation major for the final $intValue $NLHelper.getProperGrammar($intValue, "credit") taken',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1113','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.minors.nof','Must earn no more than $intValue $NLHelper.getProperGrammar($intValue, "minor") as part of a program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1114','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.minor.admitted.classstanding','Must be admitted to a minor program only if they have junior or senior class standing',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1115','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.completed.max','Must have successfully completed no more than $intValue $NLHelper.getProperGrammar($intValue, "course") from ',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1116','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.cumulative.gpa.min','Must have earned a minimum cumulative GPA of $gpa',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('1117','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.duration.gpa.min','Must have earned a minimum $durationType.getName().toLowerCase() GPA of $gpa',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('113','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.grade.min','Must have earned a minimum grade of #if($gradeType.getId().equals("kuali.result.scale.grade.letter") || $gradeType.getId().equals("kuali.result.scale.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade in',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('114','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.grade.max','Must not have earned a maximum grade of #if($gradeType.getId().equals("kuali.result.scale.grade.letter") || $gradeType.getId().equals("kuali.result.scale.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade or higher in',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('115','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.permission.org.required','Permission of $org.getLongName() required',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('116','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.permission.instructor.required','Permission of instructor required',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('117','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.test.score.min','Must have achieved a minimum score of $fields.get(''kuali.reqComponent.field.type.test.score'') on $testCluSet.getCluSetAsLongName()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('118','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.test.score.max','Must have achieved a score no higher than $fields.get(''kuali.reqComponent.field.type.test.score'') on $testCluSet.getCluSetAsLongName()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('119','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.nof.grade.min','Must successfully complete a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") with a minimum grade of #if($gradeType.getId().equals("kuali.result.scale.grade.letter") || $gradeType.getId().equals("kuali.result.scale.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade from',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('120','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.program.admitted.org.duration','Students admitted to $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) may take no more than $intValue $NLHelper.getProperGrammar($intValue, "course") in the $org.getLongName() in $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('121','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.program.notadmitted.org.duration','Students not admitted to $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) may take no more than $intValue $NLHelper.getProperGrammar($intValue, "course") in the $org.getLongName() in $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('122','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.org.program.admitted','Must be admitted to any program offered at the course campus location',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('123','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.program.notadmitted','Must not have been admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('124','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.credits.repeat.max','May be repeated for a maximum of $intValue credits',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('125','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.org.credits.completed.min','Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") from courses in the $org.getLongName()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('126','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.program.admitted','Must have been admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('127','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.completed','Must have successfully completed $courseClu.getOfficialIdentifier().getCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('128','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.enrolled','Must be concurrently enrolled in $courseClu.getOfficialIdentifier().getCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('129','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.notcompleted','Must not have successfully completed $courseClu.getOfficialIdentifier().getCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('13','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.grade.min','Must have earned a minimum grade of #if($gradeType.getId().equals("kuali.result.scale.grade.letter") || $gradeType.getId().equals("kuali.result.scale.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade in $courseCluSet.getCluSetAsCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('14','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.grade.max','Must not have earned a maximum grade of #if($gradeType.getId().equals("kuali.result.scale.grade.letter") || $gradeType.getId().equals("kuali.result.scale.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade or higher in $courseCluSet.getCluSetAsCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('15','en','KUALI.RULE','kuali.reqComponent.type.course.permission.org.required','Permission of $org.getLongName() required',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('16','en','KUALI.RULE','kuali.reqComponent.type.course.permission.instructor.required','Permission of instructor required',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('17','en','KUALI.RULE','kuali.reqComponent.type.course.test.score.min','Must have achieved a minimum score of $fields.get(''kuali.reqComponent.field.type.test.score'') on $testCluSet.getCluSetAsLongName()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('18','en','KUALI.RULE','kuali.reqComponent.type.course.test.score.max','Must have achieved a score no higher than $fields.get(''kuali.reqComponent.field.type.test.score'') on $testCluSet.getCluSetAsLongName()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('19','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.nof.grade.min','Must successfully complete a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") with a minimum grade of #if($gradeType.getId().equals("kuali.result.scale.grade.letter") || $gradeType.getId().equals("kuali.result.scale.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade from $courseCluSet.getCluSetAsCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.completed.all','#if($courseCluSet.getCluList().size() == 1)Must have successfully completed $courseCluSet.getCluSetAsCode()#{else}Must have successfully completed all courses from $courseCluSet.getCluSetAsCode()#end',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('20','en','KUALI.RULE','kuali.reqComponent.type.course.program.admitted.org.duration','Students admitted to $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) may take no more than $intValue $NLHelper.getProperGrammar($intValue, "course") in the $org.getLongName() in $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2001','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.programset.completed.nof','Must have successfully completed a minimum of 1 program from (Geology or  Sociology) programs',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2002','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.programset.notcompleted.nof','Must not have successfully completed any of (Geology or Sociology) programs',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2003','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.programset.completed.all','Must have successfully completed all of (Sociology and CORE Advanced Studies)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2004','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.programset.coursecompleted.nof','Must have successfully completed a minimum of 14 courses from ( Sociology and CORE Advanced Studies) programs',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2005','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.admitted.credits','Must be admitted to program prior to earning 60 credits',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2006','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.credits.min','Must have earned a minimum of 120 total credits',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2007','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.credits.max','Must not have earned more than 130 total credits',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2008','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.completion.duration','Must not exceed 10 semesters without completing program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2009','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.candidate.status.duration','Must attain candidate status within 3 semesters after program entry term',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('201','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.completed.none','Must not have successfully completed any courses from (MATH111, 140, 220, or STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2010','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.completion.duration.afterentry','Must complete program within 10 semesters after program entry term',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2011','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.residence.credits.final','Undergraduate students must take their final 30 credits in residence',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2012','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.enrolled.credits.final','Undergraduate students must be enrolled in their graduation major for the final 15 credits taken',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2013','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.minors.nof','Must earn no more than 2 minors as part of a program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2014','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.minor.admitted.classstanding','Must be admitted to a minor program only if they have junior or senior class standing',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2015','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.completed.max','Must have successfully completed no more than 2 courses from (MATH111, 140, 220, and STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2016','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.cumulative.gpa.min','Must have earned a minimum cumulative GPA of 2.5',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('2017','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.duration.gpa.min','Must have earned a minimum semester GPA of 3.0',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('202','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.completed.all','Must have successfully completed all courses from (MATH111, 140, 220, and STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('203','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.completed.nof','Must have successfully completed a minimum of 1 course from (MATH111, 140, 220, or STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('204','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.enrolled.nof','Must be concurrently enrolled in a minimum of 1 course from (MATH111, 140, 220, or STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('205','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.credits.completed.nof','Must have earned a minimum of 3 credits from (MATH111, 140, 220, or STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('206','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.credits.completed.none','No credits may be used from (Developmental Math courses (MATH001, 002, 003, 010, 011, 013 or 015)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('207','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.credits.completed.max','Must not have earned more than 6 credits from (MATH111, 140, 220, and STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('208','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.gpa.min','Must have earned a minimum GPA of 2.00 in (MATH111, 140, 220, and  STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('209','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.enrolled.all','Must be concurrently enrolled in all courses from (MATH111, 140, 220, and STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('21','en','KUALI.RULE','kuali.reqComponent.type.course.program.notadmitted.org.duration','Students not admitted to $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) may take no more than $intValue $NLHelper.getProperGrammar($intValue, "course") in the $org.getLongName() in $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('213','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.grade.min','Must have earned a minimum grade of letter B in (MATH111, 140, 220, and STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('214','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.grade.max','Must not have earned a maximum grade of letter C or higher in (MATH111, 140, 220, and STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('215','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.permission.org.required','Permission of English Department Required',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('216','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.permission.instructor.required','Permission of instructor required',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('217','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.test.score.min','Must have achieved a minimum score of 600 on SAT Critical Reading Exam',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('218','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.test.score.max','Must have achieved a score no higher than 5 on AP Japanese',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('219','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.nof.grade.min','Must have successfully completed a minimum of 1 course with a minimum grade of letter B from (MATH111, 140, 220, or STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('22','en','KUALI.RULE','kuali.reqComponent.type.course.org.program.admitted','Must be admitted to any program offered at the course campus location',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('220','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.program.admitted.org.duration','Students admitted to Sociology may take no more than 2 courses in the Math department in 1 year',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('221','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.program.notadmitted.org.duration','Students not admitted to Sociology may take no more than 3 courses in the Computer Science Dept department in 1 year',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('222','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.org.program.admitted','Must be admitted to any program offered at the course campus location',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('223','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.program.notadmitted','Must not have been admitted to the Sociology program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('224','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.credits.repeat.max','May be repeated for a maximum of $intValue credits',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('225','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.org.credits.completed.min','Must have successfully completed a minimum of 3 credits from courses in the Math department',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('226','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.program.admitted','Must have been admitted to the Sociology program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('227','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.completed','Must have successfully completed MATH111',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('228','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.enrolled','Must be concurrently enrolled in MATH111',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('229','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.notcompleted','Must not have successfully completed MATH111',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('23','en','KUALI.RULE','kuali.reqComponent.type.course.program.notadmitted','Must not have been admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('24','en','KUALI.RULE','kuali.reqComponent.type.course.credits.repeat.max','May be repeated for a maximum of $intValue credits',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('25','en','KUALI.RULE','kuali.reqComponent.type.course.org.credits.completed.min','Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") from courses in the $org.getLongName()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('26','en','KUALI.RULE','kuali.reqComponent.type.course.program.admitted','Must have been admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) program',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('27','en','KUALI.RULE','kuali.reqComponent.type.course.completed','Must have successfully completed $courseClu.getOfficialIdentifier().getCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('28','en','KUALI.RULE','kuali.reqComponent.type.course.enrolled','Must be concurrently enrolled in $courseClu.getOfficialIdentifier().getCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('29','en','KUALI.RULE','kuali.reqComponent.type.course.notcompleted','Must not have successfully completed $courseClu.getOfficialIdentifier().getCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.completed.nof','#if($intValue == 1 && $courseCluSet.getCluList().size() == 1)Must have successfully completed $courseCluSet.getCluSetAsCode()#{else}Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from $courseCluSet.getCluSetAsCode()#end',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3001','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.programset.completed.nof','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Programs> from <reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=Programs>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3002','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.programset.notcompleted.nof','<reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=Programs>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3003','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.programset.completed.all','<reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=Programs>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3004','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.programset.coursecompleted.nof','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Courses> from <reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=Programs>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3005','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.admitted.credits','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3006','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.credits.min','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Credits>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3007','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.credits.max','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Maximum Number of Credits>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3008','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.completion.duration','<reqCompFieldType=kuali.reqComponent.field.type.duration;reqCompFieldLabel=Duration Count> of type <reqCompFieldType=kuali.reqComponent.field.type.durationType.id;reqCompFieldLabel=Duration Type>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3009','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.candidate.status.duration','<reqCompFieldType=kuali.reqComponent.field.type.duration;reqCompFieldLabel=Duration Count> of type <reqCompFieldType=kuali.reqComponent.field.type.durationType.id;reqCompFieldLabel=Duration Type>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('301','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.completed.none','<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3010','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.completion.duration.afterentry','<reqCompFieldType=kuali.reqComponent.field.type.duration;reqCompFieldLabel=Duration Count> of type <reqCompFieldType=kuali.reqComponent.field.type.durationType.id;reqCompFieldLabel=Duration Type>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3011','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.residence.credits.final','<reqCompFieldType=kuali.reqComponent.field.type.program.clu.id;reqCompFieldLabel=Program> for final credits of <reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3012','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.enrolled.credits.final','<reqCompFieldType=kuali.reqComponent.field.type.program.clu.id;reqCompFieldLabel=Program> for final credits of <reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3013','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.minors.nof','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minors>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,VER_NBR)
  VALUES ('3014','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.minor.admitted.classstanding',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3015','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.completed.max','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Courses> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3016','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.cumulative.gpa.min','<reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('3017','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.duration.gpa.min','<reqCompFieldType=kuali.reqComponent.field.type.durationType.id;reqCompFieldLabel=Duration Type> <reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('302','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.completed.all','<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('303','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.completed.nof','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Courses> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('304','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.enrolled.nof','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Courses> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('305','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.credits.completed.nof','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('306','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.credits.completed.none','<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('307','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.credits.completed.max','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('308','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.gpa.min','<reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('309','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.enrolled.all','<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('313','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.grade.min','<reqCompFieldType=kuali.reqComponent.field.type.gradeType.id;reqCompFieldLabel=Grade Type> of <reqCompFieldType=kuali.reqComponent.field.type.grade.id;reqCompFieldLabel=Grade> in <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('314','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.grade.max','<reqCompFieldType=kuali.reqComponent.field.type.gradeType.id;reqCompFieldLabel=Grade Type> of <reqCompFieldType=kuali.reqComponent.field.type.grade.id;reqCompFieldLabel=Grade> in <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('315','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.permission.org.required','<reqCompFieldType=kuali.reqComponent.field.type.org.id;reqCompFieldLabel=Organization>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('316','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.permission.instructor.required','<reqCompFieldType=kuali.reqComponent.field.type.person.id;reqCompFieldLabel=Instructor>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('317','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.test.score.min','<reqCompFieldType=kuali.reqComponent.field.type.test.score;reqCompFieldLabel=Test Score> from <reqCompFieldType=kuali.reqComponent.field.type.test.cluSet.id;reqCompFieldLabel=Tests>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('318','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.test.score.max','<reqCompFieldType=kuali.reqComponent.field.type.test.score;reqCompFieldLabel=Test Score> from <reqCompFieldType=kuali.reqComponent.field.type.test.cluSet.id;reqCompFieldLabel=Tests>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('319','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.nof.grade.min','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Courses> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses> with <reqCompFieldType=kuali.reqComponent.field.type.gradeType.id;reqCompFieldLabel=Grade Type> of <reqCompFieldType=kuali.reqComponent.field.type.grade.id;reqCompFieldLabel=Grade>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('320','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.program.admitted.org.duration','<reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=Program(s)> no more than <reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Courses> in <reqCompFieldType=kuali.reqComponent.field.type.org.id;reqCompFieldLabel=Department> in <reqCompFieldType=kuali.reqComponent.field.type.duration;reqCompFieldLabel=Duration Count> of type <reqCompFieldType=kuali.reqComponent.field.type.durationType.id;reqCompFieldLabel=Duration Type>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('321','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.program.notadmitted.org.duration','<reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=Program(s)> no more than <reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Courses> in <reqCompFieldType=kuali.reqComponent.field.type.org.id;reqCompFieldLabel=Department> in <reqCompFieldType=kuali.reqComponent.field.type.duration;reqCompFieldLabel=Duration Count> of type <reqCompFieldType=kuali.reqComponent.field.type.durationType.id;reqCompFieldLabel=Duration Type>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,VER_NBR)
  VALUES ('322','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.org.program.admitted',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('323','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.program.notadmitted','<reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=Program(s)>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('324','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.credits.repeat.max','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('325','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.org.credits.completed.min','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits> from courses in <reqCompFieldType=kuali.reqComponent.field.type.org.id;reqCompFieldLabel=Department>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('326','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.program.admitted','<reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=Program(s)>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('327','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.completed','<reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=Course>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('328','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.enrolled','<reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=Course>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('329','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.notcompleted','<reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=Course>',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('4','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.enrolled.nof','Must be concurrently enrolled in a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from $courseCluSet.getCluSetAsCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4001','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.cumulative.gpa.min','Must have earned a minimum cumulative GPA of 2.5')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4002','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.credits.min','Must have earned a minimum of 120 total credits')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4003','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.freeform.text','Students must be proficient in Excel')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4004','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.credits.advisor','Must have 35 credits from courses approved by advisor')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4005','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.courses.advisor','Must have 6 courses from courses approved by advisor')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4006','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.credits.theme','Student must successfully complete 35 credits in music theory or analysis')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4007','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.program.courses.theme','Student must successfully complete 9 courses in music theory or analysis')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4101','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.cumulative.gpa.min','<reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4102','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.credits.min','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Credits>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4103','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.freeform.text','<reqCompFieldType=kuali.reqComponent.field.type.value.freeform.text;reqCompFieldLabel=Text>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4104','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.credits.advisor','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Credits Approved By Advisor>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4105','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.courses.advisor','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Courses Approved By Advisor>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4106','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.credits.theme','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Credits> in <reqCompFieldType=kuali.reqComponent.field.type.value.freeform.text;reqCompFieldLabel=Theme>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4107','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.program.courses.theme','<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Courses> in <reqCompFieldType=kuali.reqComponent.field.type.value.freeform.text;reqCompFieldLabel=Theme>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4201','en','KUALI.RULE.CATALOG','kuali.reqComponent.type.course.cumulative.gpa.min','Must have earned a minimum cumulative GPA of $gpa')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4202','en','KUALI.RULE.CATALOG','kuali.reqComponent.type.course.credits.min','Must have earned a minimum of $intValue total $NLHelper.getProperGrammar($intValue, "credit")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4203','en','KUALI.RULE.CATALOG','kuali.reqComponent.type.course.freeform.text','$fields.get("kuali.reqComponent.field.type.value.freeform.text")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4204','en','KUALI.RULE.CATALOG','kuali.reqComponent.type.program.credits.advisor','Must have $intValue $NLHelper.getProperGrammar($intValue, "credit") from courses approved by advisor')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4205','en','KUALI.RULE.CATALOG','kuali.reqComponent.type.program.courses.advisor','Must have $intValue $NLHelper.getProperGrammar($intValue, "course") from courses approved by advisor')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4206','en','KUALI.RULE.CATALOG','kuali.reqComponent.type.program.credits.theme','Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "credit") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4207','en','KUALI.RULE.CATALOG','kuali.reqComponent.type.program.courses.theme','Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4301','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.cumulative.gpa.min','Must have earned a minimum cumulative GPA of $gpa')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4302','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.credits.min','Must have earned a minimum of $intValue total $NLHelper.getProperGrammar($intValue, "credit")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4303','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.freeform.text','$fields.get("kuali.reqComponent.field.type.value.freeform.text")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4304','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.credits.advisor','Must have $intValue $NLHelper.getProperGrammar($intValue, "credit") from courses approved by advisor')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4305','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.courses.advisor','Must have $intValue $NLHelper.getProperGrammar($intValue, "course") from courses approved by advisor')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4306','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.credits.theme','Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "credit") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4307','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.program.courses.theme','Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4401','en','KUALI.RULE','kuali.reqComponent.type.course.cumulative.gpa.min','Must have earned a minimum cumulative GPA of $gpa')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4402','en','KUALI.RULE','kuali.reqComponent.type.course.credits.min','Must have earned a minimum of $intValue total $NLHelper.getProperGrammar($intValue, "credit")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4403','en','KUALI.RULE','kuali.reqComponent.type.course.freeform.text','$fields.get("kuali.reqComponent.field.type.value.freeform.text")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4404','en','KUALI.RULE','kuali.reqComponent.type.program.credits.advisor','Must have $intValue $NLHelper.getProperGrammar($intValue, "credit") from courses approved by advisor')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4405','en','KUALI.RULE','kuali.reqComponent.type.program.courses.advisor','Must have $intValue $NLHelper.getProperGrammar($intValue, "course") from courses approved by advisor')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4406','en','KUALI.RULE','kuali.reqComponent.type.program.credits.theme','Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "credit") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4407','en','KUALI.RULE','kuali.reqComponent.type.program.courses.theme','Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('5','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.credits.completed.nof','Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") from $courseCluSet.getCluSetAsCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('6','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.credits.completed.none','Must not have successfully completed any credits from $courseCluSet.getCluSetAsCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('7','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.credits.completed.max','Must successfully complete no more than $intValue $NLHelper.getProperGrammar($intValue, "credit") from $courseCluSet.getCluSetAsCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('8','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.gpa.min','Must have earned a minimum GPA of $gpa in $courseCluSet.getCluSetAsCode()',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('9','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.enrolled.all','#if($courseCluSet.getCluList().size() == 1)Must be concurrently enrolled in $courseCluSet.getCluSetAsCode()#{else}Must be concurrently enrolled in all courses from $courseCluSet.getCluSetAsCode()#end',0)
/
