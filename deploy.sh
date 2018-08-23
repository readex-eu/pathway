# abort on error
set -e

# configure directory names
live_dir=/home/www/periscope.in.tum.de/htdocs/pathway/eclipse
temp_dir=$live_dir.temp
backup_dir=$live_dir.backup.$(date --iso-8601)

# don't deploy these two files
rm -f Software/UpdateSite/artifacts.jar
rm -f Software/UpdateSite/content.jar

# set-up ssh agent; authentication is valid for only 20 sec
ssh-agent > /dev/null
ssh-add -t 20 ~/.ssh/id_rsa > /dev/null

# copy new update site to temp
ssh firbach@periscope.in.tum.de "rm -fr $temp_dir"
scp -r Software/UpdateSite firbach@periscope.in.tum.de:$temp_dir

# set permissions for files and folders
ssh firbach@periscope.in.tum.de "chmod --recursive 755 $temp_dir"
ssh firbach@periscope.in.tum.de "find $temp_dir -type f -print0 | xargs -0 chmod 644"

# backup current site, if there is no backup yet for this day
ssh firbach@periscope.in.tum.de "test ! -d $live_dir   ||   test -d $backup_dir   ||   mv $live_dir $backup_dir"

# deploy new site
ssh firbach@periscope.in.tum.de "rm -fr $live_dir   &&   mv $temp_dir $live_dir"
