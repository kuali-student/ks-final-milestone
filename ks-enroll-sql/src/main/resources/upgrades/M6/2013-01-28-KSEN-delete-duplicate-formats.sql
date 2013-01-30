--String aggregation Function (needed to group formats by the activity types)
create or replace type vcArray as table of varchar2(4000)
/

create or replace type string_agg_type as object
    (
       data  vcArray,

     static function
          ODCIAggregateInitialize(sctx IN OUT string_agg_type )
          return number,

     member function
          ODCIAggregateIterate(self IN OUT string_agg_type ,
                               value IN varchar2 )
         return number,

     member function
          ODCIAggregateTerminate(self IN string_agg_type,
                                 returnValue OUT  varchar2,
                                 flags IN number)
          return number,

     member function
          ODCIAggregateMerge(self IN OUT string_agg_type,
                             ctx2 IN string_agg_type)
          return number
);
/

create or replace type body string_agg_type
   is

   static function ODCIAggregateInitialize(sctx IN OUT string_agg_type)
   return number
   is
   begin
       sctx := string_agg_type( vcArray() );
       return ODCIConst.Success;
   end;

   member function ODCIAggregateIterate(self IN OUT string_agg_type,
                                        value IN varchar2 )
   return number
   is
   begin
       data.extend;
       data(data.count) := value;
       return ODCIConst.Success;
   end;

   member function ODCIAggregateTerminate(self IN string_agg_type,
                                          returnValue OUT varchar2,
                                          flags IN number)
   return number
   is
       l_data varchar2(4000);
   begin
       for x in ( select column_value from TABLE(data) order by 1 )
       loop
               l_data := l_data || ',' || x.column_value;
       end loop;
       returnValue := ltrim(l_data,',');
       return ODCIConst.Success;
   end;

   member function ODCIAggregateMerge(self IN OUT string_agg_type,
                                      ctx2 IN string_agg_type)
   return number
   is
   begin -- not really tested ;)
       for i in 1 .. ctx2.data.count
       loop
               data.extend;
               data(data.count) := ctx2.data(i);
       end loop;
       return ODCIConst.Success;
   end;


   end;
/

CREATE or replace
   FUNCTION stragg(input varchar2 )
   RETURN varchar2
   PARALLEL_ENABLE AGGREGATE USING string_agg_type;
/


--Delete all the duplicated formats that don't have any LUIs attached to them
DELETE
    KSLU_CLUCLU_RELTN cr
WHERE
    RELATED_CLU_ID IN
    (
        SELECT
            fid
        FROM
            (
                SELECT
                    COUNT(fo.id) foc,
                    courses.cid,
                    courses.fid,
                    courses.activities,
                    ROW_NUMBER() over (partition BY courses.cid, activities ORDER BY activities,
                    COUNT( fo.id) DESC) r
                FROM
                    KSEN_LUI fo,
                    (
                        SELECT
                            cid,
                            fid,
                            stragg(atype) activities
                        FROM
                            (
                                SELECT
                                    c.id cid,
                                    f.id fid,
                                    a.LUTYPE_ID atype
                                FROM
                                    KSLU_CLU c,
                                    KSLU_CLU f,
                                    KSLU_CLU a,
                                    KSLU_CLUCLU_RELTN cfr,
                                    KSLU_CLUCLU_RELTN far
                                WHERE
                                    c.id = cfr.CLU_ID
                                AND f.id = cfr.RELATED_CLU_ID
                                AND f.id = far.clu_id
                                AND a.id = far.RELATED_CLU_ID
                                AND f.LUTYPE_ID='kuali.lu.type.CreditCourseFormatShell'
                                ORDER BY
                                    cid,
                                    fid,
                                    atype
                            )
                        GROUP BY
                            cid,
                            fid
                    )
                    courses
                WHERE
                    fo.CLU_ID(+)=courses.fid
                GROUP BY
                    courses.cid,
                    courses.fid,
                    courses.activities
            )
        WHERE
            r>1
        AND foc=0
    )
/

--This script should remove orphaned formats and their activities

--Delete orphaned activities
--Delete activity attributes
DELETE KSLU_CLU_ATTR WHERE KSLU_CLU_ATTR.OWNER IN (SELECT far.RELATED_CLU_ID FROM KSLU_CLUCLU_RELTN far where far.CLU_ID IN(SELECT orphanF.ID FROM KSLU_CLU orphanf WHERE orphanf.LUTYPE_ID='kuali.lu.type.CreditCourseFormatShell' AND NOT EXISTS (SELECT * FROM KSLU_CLUCLU_RELTN orphanfrel WHERE orphanfrel.RELATED_CLU_ID=orphanf.id)))
/
--Delete activity identifiers
DELETE KSLU_CLU_IDENT WHERE KSLU_CLU_IDENT.ID IN (SELECT a.OFFIC_CLU_ID FROM KSLU_CLUCLU_RELTN far, KSLU_CLU a WHERE far.RELATED_CLU_ID = a.ID and far.CLU_ID in(SELECT orphanF.ID FROM KSLU_CLU orphanf WHERE orphanf.LUTYPE_ID='kuali.lu.type.CreditCourseFormatShell' AND NOT EXISTS (SELECT * FROM KSLU_CLUCLU_RELTN orphanfrel WHERE orphanfrel.RELATED_CLU_ID=orphanf.id)))
/
--DELETE activity relation for orphaned formats
DELETE KSLU_CLUCLU_RELTN WHERE CLU_ID IN (SELECT orphanF.ID FROM KSLU_CLU orphanf WHERE orphanf.LUTYPE_ID='kuali.lu.type.CreditCourseFormatShell' AND NOT EXISTS (SELECT * FROM KSLU_CLUCLU_RELTN orphanfrel WHERE orphanfrel.RELATED_CLU_ID=orphanf.id))
/
--Delete activities that are orphaned
DELETE KSLU_CLU orphana WHERE LUTYPE_ID LIKE 'kuali.lu.type.activity.%' AND NOT EXISTS (SELECT * FROM KSLU_CLUCLU_RELTN orphanarel WHERE orphanarel.RELATED_CLU_ID=orphana.id)
/

--Delete orphaned Formats
--Delete format attributes
DELETE KSLU_CLU_ATTR WHERE KSLU_CLU_ATTR.OWNER IN (SELECT orphanF.ID FROM KSLU_CLU orphanf WHERE orphanf.LUTYPE_ID='kuali.lu.type.CreditCourseFormatShell' AND NOT EXISTS (SELECT * FROM KSLU_CLUCLU_RELTN orphanfrel WHERE orphanfrel.RELATED_CLU_ID=orphanf.id))
/
--Delete activity identifiers
DELETE KSLU_CLU_IDENT WHERE KSLU_CLU_IDENT.ID IN (SELECT orphanF.OFFIC_CLU_ID FROM KSLU_CLU orphanf WHERE orphanf.LUTYPE_ID='kuali.lu.type.CreditCourseFormatShell' AND NOT EXISTS (SELECT * FROM KSLU_CLUCLU_RELTN orphanfrel WHERE orphanfrel.RELATED_CLU_ID=orphanf.id))
/
--Delete activities that are orphaned
DELETE KSLU_CLU orphanf WHERE LUTYPE_ID = 'kuali.lu.type.CreditCourseFormatShell' AND NOT EXISTS (SELECT * FROM KSLU_CLUCLU_RELTN orphanfrel WHERE orphanfrel.RELATED_CLU_ID=orphanf.id)
/

