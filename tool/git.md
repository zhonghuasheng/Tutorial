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


### Issue:

* fatal: Refusing to fetch into current branch refs/heads/tutorial of non-bare repository
当你处于分支develop时，这个时候去fetch develop分支的代码的时候就会报这个错误，也就是说不可以在非bare的git仓库中通过fetch快进你的当前分支与远程同步。这个时候你要切换到其他分支，然后再去fetch非当前分支的代码。

### .gitignore can not work
> If you already have a file checked in, and you want to ignore it, Git will not ignore the file if you add a rule later. In those cases, you must untrack the file first, by running the following command in your termianl:
```
$ git rm --cached
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
git config –global core.autocrlf false //禁用自动转换
git init //初始化git库
git add –all //提交所有修改到暂存区
```

或者设置workspace project .git目录下的conf文件
```
[Core]
...
autocrlf = false
```