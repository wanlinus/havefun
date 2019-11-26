### git常用名命令

[toc]

​	注:  `[]`必填 

1. git仓库初始化

   ```bash
   git init
   git remote add origin [url] #关联远端仓库
   ```
   
2. 将文件添加到暂存区

   ```bash
   git add filename
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
  git branch #查看分支列表
  git branch -v #查看所有分支的最后一次操作
  git branch [branch_name] #创建分支
  git branch -b [branch_name] origin/[origin_branch_name] #创建远端分支到本地
  git branch -d [feature_x]  #删除分支
  git branch -D [branch_name] #强行删除分支
  git checkout -b [feature_x] #创建并切换到新分支
  git checkout master  #切换到master分支 
  ```

6. 暂存操作

  ```bash
   git stash #暂存当前修改
   git stash apply #恢复最近的一次暂存
   git stash pop #恢复暂存并删除暂存记录
   git stash list #查看暂存记录
   git stash frop [暂存名] #移除某次暂存
   git stash clear #清除暂存
  ```

7. 回退操作
  ```bash
  git reset --hard HEAD^ #回退到上个版本
  git reset --hard [commit_id] #回退到某个版本
  git checkout -- file #撤销修改的文件(如果文件夹到了暂存区, 则回退到暂存区)
  git reset HEAD file #撤回暂存区的文件修改到工作区
  ```

8. 更新与合并

  ```bash
  git pull  #更新本地仓库
  git merge branch_exp  #将branch_exp合并到当前分支
  ```

9. 标签

  ```bash
  git tag #列出所有标签列表
  git tag [标签名] #添加标签(默认当前版本)
  git tag [标签名] [commit_id]  #对某一提交记录打标签
  git tag -a [标签名] -m [描述] #创建新标签并增加备注
  git tag -d [标签名] #删除本地标签
  git show [标签名] #查看标签信息
  git push origin [标签名] # 推送标签到远端仓库
  git push origin --tags #推送所有标签到远端仓库
  git push origin :refs/tags/[标签名] #从远端仓库中删除标签
  ```

10. log

  ```bash
  git log  #查看git提交日志
  git log --author=wanli  #查看wanli的提交记录
  git log --pretty=oneline  #查看提交记录每条只占一行

  ## 通过 ASCII 艺术的树形结构来展示所有的分支, 每个分支都标示了他的名字和标签
  git log --graph --oneline --decorate --all
  ```

11. 其他

  ```bash
  ##丢弃你在本地的所有改动与提交，可以到服务器上获取最新的版本历史，并将你本地主分支指向它
  git fetch origin
  git reset --hard origin/master

  ##压缩前5次commit
  git rebase -i HEAD~5
  :%s/pick/s/gc
  git push -f
  ```

12. 高级用法

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

13.	撤销改变

  ```bash
  git checckout -- <filename>
  ```

14. 撤销使用add的文件,但是改变存在

    ```bash
    git reset HEAD
    ```
    
15.  提交到本地仓库的代码想撤销
  
    ```bash
    git reset --hard <版本号>
    # 或者
    git reset --hard HDAD^
    ```
    
    

