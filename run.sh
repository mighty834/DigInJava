#!/bin/zsh
resultDir="./classes/"
notBuildKey="-nb"
hardBuildKey="-hb"

function compile {
    javac -classpath . -d $resultDir */**/*.java
}

function run {
    java -classpath $resultDir $*
}

function buildAssets {
    run aux_tools.Helpers buildAssets $resultDir 1 > /dev/null
}

function hardBuild {
    rm $resultDir -R -f 2 > /dev/null
    mkdir $resultDir
    compile
    buildAssets
}

if [ $# -gt 0 ]
then
    if [ $1 = $notBuildKey ]
    then
        shift
    elif [ $1 = $hardBuildKey ]
    then
        hardBuild
        shift
    else
        compile
        buildAssets
    fi

    if [ $# -gt 0 ]
    then
        run $*
    fi
else
    compile
    buildAssets
fi

