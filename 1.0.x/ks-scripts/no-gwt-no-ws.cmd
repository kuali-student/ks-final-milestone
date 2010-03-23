cd ..
call mvn -o -Dmaven.test.skip=true -Pkuali-developer-properties -Dks.java2ws.phase=none -Dks.gwt.compile.phase=none install
cd ks-scripts
