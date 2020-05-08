#!/bin/zsh
resultDir="./classes/"
notBuildKey="-nb"
hardBuildKey="-hb"
moreInfoKey="-mi"
compileOnlyKey="-co"
exNumPrefix="--"

entryPointName=""
argsString=""

function compile {
    javac -classpath . -d $resultDir ./aux_tools/**/*.java

    if [ $# -gt 0 ]
    then
        javac -classpath .:$resultDir -d $resultDir ./exercises/ex_$*/*.java
        entryPointName=exercises.${$(basename $(find ./exercises/ex_$*/Main*.java))%.*}
    else
        javac -classpath .:$resultDir -d $resultDir ./exercises/ex_$(ls exercises/ | wc -l)/**/*.java
        entryPointName=exercises.${$(basename $(find ./exercises/ex_$(ls exercises/ | wc -l)/Main*.java))%.*}
    fi
}

function compileWithInfo {
    javac -Xlint -classpath . -d $resultDir */**/*.java
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
    elif [ $1 = $moreInfoKey ]
    then
        compileWithInfo
        buildAssets
        shift
    elif [ $1 = $compileOnlyKey ]
    then
        compile
        buildAssets
        shift
    elif [ ${1:0:2} = $exNumPrefix ]
    then
        compile ${1:2}
        buildAssets
        
        for val in $@
            do
                if [[ $val == $1 ]]
            then
                continue;
            else
                argsString="$argsString$val "
            fi
        done

        args=($(echo $argsString | tr ";" " "));


        run $entryPointName $args
    else
        compile
        buildAssets
    fi

    if [ $# -gt 0 ] && [ ${1:0:2} != $exNumPrefix ]
    then
        run $*
    fi
else
    compile
    buildAssets
    run $entryPointName
fi

