date /t
time /t

cd ..\..\ks-security
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dcxf-java2ws-plugin.execution.phase=process-classes -Dks.gwt.compile.phase=none clean install

cd ..\ks-core
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dcxf-java2ws-plugin.execution.phase=process-classes -Dks.gwt.compile.phase=none clean install

cd ..\ks-brms
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dcxf-java2ws-plugin.execution.phase=process-classes -Dks.gwt.compile.phase=none clean install

cd ..\ks-lum
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dcxf-java2ws-plugin.execution.phase=process-classes -Dks.gwt.compile.phase=none clean install

cd ..\ks-web\ks-embedded
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties clean install

cd ..\..\maven\resources

date /t
time /t
