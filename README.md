### **Selenide + Allure + Remote host example (parametrized)**

_`- Run tests with:`_

for win
clean test -Dos=.exe -Dscript.exec=bat -Dhost=######

for mac
clean test -Dos=mac -Dscript.exec=sh -Dhost=######

for linux
clean test -Dos=linux -Dscript.exec=sh -Dhost=######

_`- Download allure_commandline and add *./bin in your $PATH`_

Uncomment in pom code for allure history if your run build without ci with Allure-plagin
