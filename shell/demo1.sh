#指定脚本解释器
#!/bin/sh
cd ~
mkdir shell_demo1
cd shell_demo1

for ((i=0; i<10; i++)); do
    touch test_$i.txt
done