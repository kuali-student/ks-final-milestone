call mvn -N -DextraReleaseArguments=-N release:clean
call mvn --batch-mode -N -DextraReleaseArguments=-N release:prepare
call mvn --batch-mode -N -DextraReleaseArguments=-N release:perform
