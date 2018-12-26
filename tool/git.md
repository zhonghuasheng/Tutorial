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