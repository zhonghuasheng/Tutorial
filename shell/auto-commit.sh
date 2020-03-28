#!/bin/sh
TODAY_PNG=$(date +%Y-%m-%d.png)
YESTERDAY_PNG=$(date -d last-day +%Y-%m-%d.png)

cd /root/luke/ci

rm -f $(date -d last-day +%Y-%m-%d.png)
wget http://assets.processon.com/chart_image/5c9867cde4b0afc7441ea764.png
mv 5c9867cde4b0afc7441ea764.png $TODAY_PNG
cd /root/luke/ci/Tutorial
git pull --all
rm -f $YESTERDAY_PNG
cp /root/luke/ci/$TODAY_PNG /root/luke/ci/Tutorial/
sed -i "s/$YESTERDAY_PNG/$TODAY_PNG/g" /root/luke/ci/Tutorial/README.md
git add .
git commit -m "Update the picture of skill stack $TODAY_PNG"
git push

auto-commit-musicstore-jsp.sh
#!/bin/sh
TODAY_PNG=musicstore-jsp-$(date +%Y-%m-%d.png)
YESTERDAY_PNG=musicstore-jsp-$(date -d last-day +%Y-%m-%d.png)

cd /root/luke/ci

rm -f $YESTERDAY_PNG
wget http://assets.processon.com/chart_image/5d030e3ce4b071ad5a23ac34.png
mv 5d030e3ce4b071ad5a23ac34.png $TODAY_PNG
cd /root/luke/ci/musicstore-jsp
git checkout develop
git pull --all
rm -f /root/luke/ci/musicstore-jsp/requirement/$YESTERDAY_PNG
cp /root/luke/ci/$TODAY_PNG /root/luke/ci/musicstore-jsp/requirement/
sed -i "s/$YESTERDAY_PNG/$TODAY_PNG/g" /root/luke/ci/musicstore-jsp/README.md
git add .
git commit -m "Update the music store requirement and architecture design $TODAY_PNG"
git fetch origin master:master
git rebase origin/master
git push origin develop


auto-commit-tutorial.sh
#!/bin/sh
TODAY_PNG=tutorial-$(date +%Y-%m-%d.png)
YESTERDAY_PNG=tutorial-$(date -d last-day +%Y-%m-%d.png)

echo 'delete yesterday png'
cd /root/luke/ci

rm -f $YESTERDAY_PNG

echo 'download today png'
wget http://assets.processon.com/chart_image/5c9867cde4b0afc7441ea764.png
mv 5c9867cde4b0afc7441ea764.png $TODAY_PNG

echo 'do commit'
cd /root/luke/ci/Tutorial
git pull --all
rm -f $YESTERDAY_PNG
cp /root/luke/ci/$TODAY_PNG /root/luke/ci/Tutorial/$TODAY_PNG
sed -i "s/$YESTERDAY_PNG/$TODAY_PNG/g" /root/luke/ci/Tutorial/README.md
git add -A
git commit -m "Update the picture of skill stack $TODAY_PNG"
git push

echo 'submit success'

38 3 * * * /root/luke/ci/auto-commit-tutorial.sh > /root/luke/ci/auto-commit-tutorial.log
10 3 * * * /root/luke/ci/auto-commit-musicstore-jsp.sh > /root/luke/ci/auto-commit-musicstore-jsp.log


# musicstore-jsp
server {
    listen       80;
    server_name  musicstore-jsp.justdo.fun;
   # root         /root/luke/ci/apache-tomcat-8.5.42/webapps/musicstore;
    location / {
        proxy_pass http://127.0.0.1:8018;
    }
}


sslocal -s ip -p remotepot -k pwd -m rc4-md5 -l localport -b 0.0.0.0 &

#!/bin/sh
nohup java -jar -Dserver.port=9999  /root/luke/ci/services/musicstore-1.0-SNAPSHOT.jar &
