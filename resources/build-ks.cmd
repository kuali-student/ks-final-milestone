cd ..\..\ks-security-dev
call build-ks-module

cd ..\ks-core-dev
call build-ks-module

cd ..\brms-dev
call build-ks-module

cd ..\ks-lum-dev
call build-ks-module

cd ..\ks-web-dev\ks-embedded
call build-ks-module

cd ..\..\maven\resources
