#!/bin/sh
TODAY_PNG=$(date +%Y-%m-%d.png)
YESTERDAY_PNG=$(date -d last-day +%Y-%m-%d.png)

cd /home/luke

rm -f $(date -d last-day +%Y-%m-%d.png)
wget http://assets.processon.com/chart_image/5c9867cde4b0afc7441ea764.png
mv 5c9867cde4b0afc7441ea764.png $TODAY_PNG
cd /home/luke/Tutorial
git pull --all
rm -f $YESTERDAY_PNG
cp /home/luke/$TODAY_PNG /home/luke/Tutorial/
sed -i "s/$YESTERDAY_PNG/$TODAY_PNG/g" /home/luke/Tutorial/README.md
git add .
git commit -m "Update the picture of skill stack $TODAY_PNG"
git push