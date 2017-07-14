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
