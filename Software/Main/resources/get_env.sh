#!/bin/bash

if [ -f /etc/profile.d/modules.sh ]
then
. /etc/profile.d/modules.sh
fi

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
	echo "-h, --help                  show brief help"
	echo "-o, --output-file=FILE      specify the file to store output in"
	echo "-c, --cmd=COMMAND           specify the command to be executed"
	echo "-a, --args=CMD_ARGS         specify the command-line arguments for that command"
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
                    cmd=`echo $1 | sed -e 's/^[^=]*=//g;s/\"/\\\"/g'`
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
		cmdArgs=`echo $1 | sed -e 's/^[^=]*=//g;s/\"/\\\"/g'`
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
		datFile=`echo $1 | sed -e 's/^[^=]*=//g;s/\"/\\\"/g'`
		shift
		;;
	  *)
		echo "[ERROR] Unknown option: "$1
		usage
		exit 1
		;;
	esac
done

echo "{" > $datFile

echo "\"dateTime\":\""$datetime"\"," >> $datFile
echo "\"executable\":\""$cmd"\"," >> $datFile
echo "\"arguments\":\""$cmdArgs"\"," >> $datFile

## Get the runtime environment
echo "\"environment\":{" >> $datFile
printenv -0 | tr '\n' ';' | tr '\0' '\n' | sed -n '/BASH_FUNC_module()/!s/\(.*\)=\(.*\)/\t"\1":"\2",/p' >> $datFile
echo "	\"_pathway_env\":\""end"\"" >> $datFile
echo "	}," >> $datFile

## Get the currently loaded modules
echo "\"modules\":[" >> $datFile

if [ -n "$LOADEDMODULES" ]
then
# echo $LOADEDMODULES | tr ':' '\n' | sed 's/\(.*\)\/\(.*\)/\t"\1":"\2",/g' >> $datFile
  echo $LOADEDMODULES | tr ':' '\n' | sed 's/^\(.\+\)$/\t"\1",/g' >> $datFile
  echo '		"_pathway_modules_end"' >> $datFile
fi

echo "	]" >> $datFile

echo "}" >> $datFile
