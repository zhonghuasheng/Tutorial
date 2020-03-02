## Git简介
* https://baike.baidu.com/item/Git/12647237

## Github帐号注册
* 注册地址： https://github.com

## 本地安装Git客户端
* 下载地址： https://git-scm.com/

## 配置本地Git信息
* 配置邮箱，邮箱是注册Github的邮箱 `git config --global user.email xxx@126.com`

* 配置用户名，建议使用字母而不是汉字 `git config --global user.name xxx`

* 查看配置 `git config --list`

## 在Github配置SSH-Key
* 参考文章 https://jingyan.baidu.com/article/dca1fa6f756777f1a44052e3.html

## 创建自己的代码仓库，并提交一个commit
* 参考文章 https://jingyan.baidu.com/article/8cdccae9269b1f315413cde2.html


### Create a new git branch from an old commit

* create a new branch and checkout it out

```shell
git checkout -b newbranch commitid
```

* create a new branch

```shell
git branch newbranch commitid
```

### Update authuor for commited commit

```shell
git commit --amend --author 'Hua Sheng' --email "xiaoyong6906@126.com"
```
### Add tag for a release branch
```shell
git tag -a V1.0.0 -m "Apr 1 release"
git push --tag
```

## Commit Message的格式
* 参考[Angular规范](https://docs.google.com/document/d/1QrDFcIiPjSLDn3EL15IJygNPiHORgU1_OOAqWjiDU5Y/edit#heading=h.greljkmo14y0)

### 格式
```
<type>(<scope>): <subject> ＃id
```
`eg`
```
feat(login): 添加了Login模块的后台验证逻辑　#11
```

### 格式说明

1. `type`用于说明commit的类别，属于必填字段，只允许使用下面７个标识
    ```
    * feat: 新功能(feature)
    * fix: 修补bug
    * docs:　文档(documentation)
    * style: 样式(不影响代码运行的变动)
    * refactor: 重构(既不是新增功能，也不是修补的bug的改动)
    * test:　增加测试
    * chore: 构建过程或辅助工具的变动
    ```
2. `scope`用于说明commit影响的范围，scope是可选的，可以是多个，中间用,隔开，取值只能从该项目的label中取功能模块的值
3. `subject`是commit的简单描述，建议使用精简的语言描述，最好不超过50个字符。另外，由于我们是内部项目，commit使用中文描述
4. `id`是任务的编号，因此每个commit的提交都必须有对应的标号，id加#是能够让Gitlab识别并绑定到现有的任务中。注意#之前和subject是有个空格的


### Issue:

* fatal: Refusing to fetch into current branch refs/heads/tutorial of non-bare repository
当你处于分支develop时，这个时候去fetch develop分支的代码的时候就会报这个错误，也就是说不可以在非bare的git仓库中通过fetch快进你的当前分支与远程同步。这个时候你要切换到其他分支，然后再去fetch非当前分支的代码。

### .gitignore can not work
> If you already have a file checked in, and you want to ignore it, Git will not ignore the file if you add a rule later. In those cases, you must untrack the file first, by running the following command in your termianl:
```
$ git rm --cached . 清楚当前stage
$ git add -A
$ git commit -m 'your message, eg update .gitignore'
```
.gitignore文件只是ignore没有被staged(cached)文件

### fatal: LF would be replaced by CRLF in .idea/workspace.xml
windows中的换行符为 CRLF，而在Linux下的换行符为LF，所以在执行add . 时出现提示
工作区的文件都应该用 CRLF 来换行。如果
改动文件时引入了 LF,提交改动时，git 会警告你哪些文件不是纯 CRLF 文件，但 git 不会擅自修改工作区的那些文件，而是对暂存区（我们对工作区的改动）进行修改。也因此，当我们进行 git add 的操作时，只要 git 发现改动的内容里有 LF 换行符，就还会出现这个警告。

```
rm -rf .git // 删除.git
git config –-global core.autocrlf false //禁用自动转换
git init //初始化git库
git add –all //提交所有修改到暂存区
```

或者设置workspace project .git目录下的conf文件
```
[Core]
...
autocrlf = false
```

## 解决github提交代码过慢的问题
* ubuntu下编辑/etc/ssh/ssh_config，windows下在git的安装目录中找，在ssh_config末尾append
```
Host github.com
User git
Hostname ssh.github.com
PreferredAuthentications publickey
IdentityFile ~/.ssh/id_rsa
Port 443
```

## 拉取指定branch代码到各自单独的文件夹
```shell
#!/bin/bash
#basePath是clone的master分支文件夹的外层文件夹
basePath="/xxx/"
branchs="branch1,branch2"
#要将$branchs分割开，先存储旧的分隔符
OLD_IFS="$IFS"

#设置分隔符
IFS=","

#如下会自动分隔
arr=($branchs)

#恢复原来的分隔符
IFS="$OLD_IFS"

#遍历数组
for branch in ${arr[@]}
  do
  #为每个branch创建一个文件夹
  cd $basePath
  rm -rf $branch
  echo "Delete $branch Done......"
  mkdir $branch

  #进入master folder并拉取$s分支的代码
  cd $basePath/master
  git checkout $branch
  git pull --all
  cp -r ./* $basePath/$branch

  echo "Clone $branch Done......"
done

#当前是master文件夹，最后切换到master分支
git checkout master
```