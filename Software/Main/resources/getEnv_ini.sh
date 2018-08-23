#!/bin/bash

cmd=""
cmdArgs=""
datFile="/dev/stdout"
datetime=$(date "+%d.%m.%Y %H:%M:%S")

usage()
{
	echo "$0 [options]" 
       	echo "		attempts to get the running environment"
	echo " "
	echo "options:"
	echo "-h, --help                show brief help"
	echo "-o, --output-file=FILE    specify the file to store output in"
	echo "-c, --cmd=COMMAND        specify the command to be executed"
	echo "-a, --args=CMD_ARGS       specify the command-line arguments for that command"
}

if test $# -lt 1; then
   usage
   exit 1
fi

#Parse arguments
while test $# -gt 0; do
	#echo "Processing argument: "$1
	case "$1" in
	  -h|--help)
		usage
		exit 0
		;;
          -c)
	        shift
        	if test $# -gt 0; then
		     cmd=$1
		else
		     echo "no command specified"
		     exit 1
		fi
		shift
		;;
	  --cmd*)
                    cmd=`echo $1 | sed -e 's/^[^=]*=//g'`
                shift
                ;;

	  -a)
		shift
		if test $# -gt 0; then
		    cmdArgs=$1
		else
		    echo "no command-line args specified"
		    exit 1
		fi
		shift
		;;
	  --args*)
		cmdArgs=`echo $1 | sed -e 's/^[^=]*=//g'`
		shift
		;;
	  -o)
		shift
		if test $# -gt 0; then
		    datFile=$1
	    	else
		    echo "no output file specified"
		    exit 1
		fi
		shift
		;;
	  --output-file*)
		datFile=`echo $1 | sed -e 's/^[^=]*=//g'`
		shift
		;;
	  *)
		echo "[ERROR] Unknown option: "$1
		usage
		exit 1
		;;
	esac
done

echo "; ************* Experiment Start *************"  >> $datFile
echo " " >> $datFile

echo "[Experiment]" >> $datFile
echo "; Experiment Meta Info" >> $datFile
echo "DateTime = \""$datetime"\"" >> $datFile
echo "Executable = \""$cmd"\"" >> $datFile
echo "Arguments = \""$cmdArgs"\"" >> $datFile
echo " " >> $datFile

## Get the runtime environment
echo "[Environment]" >> $datFile
echo "; Runtime environment" >> $datFile
printenv >> $datFile

echo " " >> $datFile

## Get the currently loaded modules
echo "[Modules]" >> $datFile
if [ -n "$LOADEDMODULES" ]; then
  echo "; Currently loaded modules" >> $datFile
  echo $LOADEDMODULES | tr ":" "\n" >> $datFile
else
  echo "; Modules environment is not installed or configured" >> $datFile
fi

echo " " >> $datFile
echo "; ************* Experiment End *************"  >> $datFile

