@echo off

title SmplRunner
call SmplJflex.bat

echo ...................................

echo ...................................

echo . Jflex complete

echo . Running Cup

echo ...................................

echo ...................................

TIMEOUT 3

call SmplCup.bat

echo ...................................

echo ...................................

echo . Cup complete

echo . Running Java Smpl 

echo ...................................

echo ...................................

TIMEOUT 3

call SmplJava.bat


