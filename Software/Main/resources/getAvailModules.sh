#!/bin/bash
. /etc/profile.d/modules.sh

datFile="/dev/stdout"

usage()
{
	echo "$0 [options]" 
       	echo "		stores the available modules in JSON format"
	echo " "
	echo "options:"
	echo "-h, --help                show brief help"
	echo "-o, --output-file=FILE    specify the file to store output in"
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

echo "[" >> $datFile

#module avail -t 2>&1 | sed -e '/^-/d' -e 's/\(.\+\)\/\(.*\)/\1":"\2/g' -e 's/^\(.\+\)$/"\1",/g' >> $datFile
module avail -t 2>&1 | sed -e '/^-/d'  -e 's/^\(.\+\)$/"\1",/g' >> $datFile
echo "\"_pathway_modules\":\""end"\"" >> $datFile

echo "]" >> $datFile

