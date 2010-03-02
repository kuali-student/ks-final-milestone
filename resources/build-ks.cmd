date /t
time /t

cd ..\..\ks-security
svn update
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dks.gwt.compile.phase=none clean install

cd ..\ks-core
svn update
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dks.gwt.compile.phase=none clean install

cd ..\ks-brms
svn update
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dks.gwt.compile.phase=none clean install

cd ..\ks-lum
svn update
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dks.gwt.compile.phase=none clean install

cd ..\ks-web\ks-embedded
svn update
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties clean install

cd ..\..\maven\resources

date /t
time /t
