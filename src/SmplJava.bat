@echo off

title SmplJava

javac -cp C:\Users\andre\Desktop\COMP3652\SMPL-interpreter-master\src\java-cup-11b-runtime.jar;. C:\Users\andre\Desktop\COMP3652\SMPL-interpreter-master\src\smpl\sys\*.java


java -cp C:\Users\andre\Desktop\COMP3652\SMPL-interpreter-master\src\java-cup-11b-runtime.jar;. smpl.sys.Repl NT.smpl