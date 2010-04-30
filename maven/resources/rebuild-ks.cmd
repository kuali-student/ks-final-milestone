date /t
time /t

set CXF_PHASE=process-classes
set CXF_PHASE=none

cd ..\..\ks-security
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dcxf-java2ws-plugin.execution.phase=%CXF_PHASE% -Dks.gwt.compile.phase=none install

cd ..\ks-core
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dcxf-java2ws-plugin.execution.phase=%CXF_PHASE% -Dks.gwt.compile.phase=none install

cd ..\ks-brms
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dcxf-java2ws-plugin.execution.phase=%CXF_PHASE% -Dks.gwt.compile.phase=none install

cd ..\ks-lum
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dcxf-java2ws-plugin.execution.phase=%CXF_PHASE% -Dks.gwt.compile.phase=none install

cd ..\ks-web\ks-embedded
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties install

cd ..\..\maven\resources

date /t
time /t
