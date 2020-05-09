### Linux下安装Postman
wget https://dl.pstmn.io/download/latest/linux64 -O chromecj.com-postman.tar.gz
sudo tar -xzf chromecj.com-postman.tar.gz -C /opt
rm chromecj.com-postman.tar.gz
sudo ln -s /opt/Postman/Postman /usr/bin/postman
启动时执行postman即可，当然也可以设置快捷启动方式

cat > ~/.local/share/applications/postman.desktop <<EOL
[Desktop Entry]
Encoding=UTF-8
Name=Postman
Exec=postman
Icon=/opt/Postman/resources/app/assets/icon.png
Terminal=false
Type=Application
Categories=Development;
EOL

不过我个人习惯用命令行启动，主要是为了装B，另外可以从terminal中看到一些参数