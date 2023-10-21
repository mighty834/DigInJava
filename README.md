# DigInJava repository

This repository provide functionality for more convinient Java usage, and quickly learning of all Java possibilities.

## Repository structure
├── executions (main module which contains execution modules)  
│   ├── src (sources of main module)  
│   ├── ex_{number} (list of execution modules, user generated, and modify them)  
│   ├── ...  
│   ├── build.gradle  
├── auxtools  (module with common functionality which can be used from every execution module)  
│   ├── src  
│   │   ├── main  
│   │   │   ├── java  
│   │   │   ├── resources  
│   │   ├── test  
│   │   │   ├── java  
│   │   │   ├── resources  
│   ├── build.gradle  
├── buildSrc  (logic for automatisation project processes)  
│   ├── src  
│   │   ├── main  (languages for project's automatisation)
│   │   │   ├── groovy  
│   │   │   ├── java  
│   ├── build.gradle  
│   ├── settings.gradle  
├── gradle  
├── gradlew  
├── gradlew.bat  
├── README.md  (description of project)  
├── setting.gradle  
├── .gitattributes  
└── .gitignore

## How to use this repository

Use `run` script for Unix like systems or `run.bat` for Windows to start
any action in project. Both scripts are simple gradle wrapper scripts which
provide possibility to manage all possible project functionality.

### What do you need for use this repository

You need only JDK v. 11 or higher if you want using only base functional,
if you need possibility to create external dependencies for your java modules
like data base, kafka, redis etc and easily manage them from your `run` script,
you also need Docker (Engine v.24 or higher, Desktop v. 4 or higher).

### Check everithing

To check everything working good use this commands:  
`run cne Test`  
`run`

if compilation was successful, and you can see message `Hello "Test" java executable module!`
everything working good.

### All possible commands and args for run script

`run [number]` - execute last executable module of with given number  
`run cne {mcn} [type] [kw] [env] [dep] [desc]` - create new executable module with
given main class name, and with default or given type  
`run de {number}` - delete executable module with given number  
`run up {dependency name}` - up container with external dependency  
`run down {dependency name}` - down container with external dependency

#### run

Explanation of command work...