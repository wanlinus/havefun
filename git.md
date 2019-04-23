### git常用名命令

1. git仓库初始化

```bash
git init
```

2. 将文件添加到暂存区

```bash
git add <filename>
git add *
git add -i  #交互式天剑文件到暂存区
```

3. 提交

```bash
git commit -m "提交信息"

## 添加文件并提交 前提是文件之前已经add 
git commit -am "提交信息"
```

4. 提交改动

```bash
git remote add origin <server> #将本地仓库连接到远端仓库
git push origin master
```

5. 分支

```bash
git checkout -b feature_x #创建并切换到新分支
git checkout master  #切换到master分支 
git branch -d feature_x  #删除分支
```

6. 更新与合并

```bash
git pull  #更新本地仓库
git merge branch_exp  #将branch_exp合并到当前分支
```

7. 标签

```bash
git tag 1.0 1b2e1d63ff  #1.0是tag名字 1b2e1d63ff是git标记ID
```

8. log

```bash
git log  #查看git提交日志
git log --author=wanli  #查看wanli的提交记录
git log --pretty=oneline  #查看提交记录每条只占一行

## 通过 ASCII 艺术的树形结构来展示所有的分支, 每个分支都标示了他的名字和标签
git log --graph --oneline --decorate --all
```

9. 其他

```bash
git checkout -- <filename>  #撤销误操作

##丢弃你在本地的所有改动与提交，可以到服务器上获取最新的版本历史，并将你本地主分支指向它
git fetch origin
git reset --hard origin/master

##压缩前5次commit
git rebase -i HEAD~5
:%s/pick/s/gc
git push -f
```

10. 高级用法

```bash
# bugFix是从master拉取的分支, 现在master分支有更改需要将这些更改应用到bugFix上
git checkout master
git checkout -b bugFix
git checkout master
# 在master上做改动并提交
git checkout bugFix
git rebase master

# 强制修改分支位置
git branch -f master HEAD~3  #将master分支指向前三次提交的位置

#提交回退
git reset HEAD~2  #将当前分支回退两次提交 但是已经提交的记录还是存在
git revert HEAD  #将当前提交回退 在git树里面次提交和撤回前的提交一样

#提交复制
git cherry-pick C2 C4  #将两次提交的commit复制到当前(HEAD)分支下
```



