# AusPost-JavaTestAutomation

Base java framework with data provider, with web app and message tech stack support

Clone the repo URL and do the below setup based on your requirements

*************************************************************************
For API, Please have the below pre-requisites
RestAssured
Fillo
Java 8
Maven 
TestNG
Set runApiTests = true in the config file

*************************************************************************
For Continuous test Solution, please have the below pre-requisites
Docker 2.3.0.2
Java 8
Maven
TestNG

If you want to run the containers
Set containerOn = true in the config file

If you want to run the tests remotely
Set browserType = Remote in the config file


*************************************************************************

To run SAP Tests, please have the below pre-requisites
Jacob 1.19
SAP GUI
Java 8
Maven
TestNG

Make sure you have jar and dll files under C:\Windows\System32
Below registry changes are required for 64bit environment users.
Add in the registry the entry DllSurrogate in the key HKCRWow6432NodeAppID{E2779C61-F87E-4038-98A0-1D9E71334706} without a value
i.e. HKEY_CLASSES_ROOT\Wow6432Node\AppID\{E2779C61-F87E-4038-98A0-1D9E71334706}

Enter SAP username and password in config file
SAPUserName = XXX
SAPPassword = XXX

*************************************************************************

For Smart object handling, please have the below pre-requisites

Pre-requisites - 
MangoDB 4.2
Java 8
Maven
TestNG

Start the Mango server to use the smartobj
Recommendation - use this only to those application where the element keeps changing frequently
