# Arch Linux

1. 更新系统时间

   ```
   timedatectl set-ntp true
   ```

2. 编辑镜像站(或者在[这里](https://www.archlinux.org/mirrorlist/) 生成自定义地区的镜像列表)

   ```
   vim /etc/pacman.d/mirrorlist
   ```

3. 建立分区系统

   ```bash
   parted /dev/sda
   (parted) mklabel gpt
   (parted) mkpart ESP fat32 0% 513M # EFI
   (parted) set 1 boot on
   (parted) mkpart primary ext4 513M 20.5G # /
   (parted) mkpart primary linux-swap 20.5G 24.5G # swap
   (parted) mkpart primary ext4 24.5G 100% # /home
   ```

   ```bash
   #fdisk使用说明
   fdisk /dev/sda
   g #create a new empty GPT partition table
   n # add a new partition
   #cfdisk 使用说明
   cfdisk /dev/sda
   ```

   ```bash
   #格式化分区
   mkfs.fat -F32 /dev/sda1
   mkswap /dev/sda3
   swapon /dev/sda3
   mkfs.ext4 /dev/sda2
   mkfs.ext4 /dev/sda4
   ```

   

4. 挂载分区

   ```
   mount /dev/sda2 /mnt
   mkdir -p /mnt/boot/EFI
   mount /dev/sda1 /mnt/boot/EFI
   mkdir /mnt/home
   mount /dev/sda4 /mnt/home
   ```

5. 安装镜像

   ```
   pacstrap -i /mnt base linux linux-firmware base-devel vim git zsh dhcpcd
   ```

6. 配置fstab

   ```
   genfstab -U /mnt >> /mnt/etc/fstab
   ```

7. 切换到新系统

   ```bash
   arch-chroot /mnt
   ```

8. 设置时区

   ```
   ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
   hwclock --systohc
   ```

9. 本地化(没有vim `pacman -S vim`)

   ```bash
   vim /etc/locale.gen # 取消注释
   	en_US.UTF-8 UTF-8
   	zh_CN.UTF-8 UTF-8
   locale-gen
   echo "LANG=en_US.UTF-8" >> /etc/locale.conf
   echo "wanli-arch" >> /etc/hostname
   vim /etc/hosts
   	127.0.0.1 	localhost
   	::1			localhost
   	127.0.0.1	wanli-arch.localdomain wanli-arch
   ```

10. 设置密码

    ```bash
    passwd
    ```

11. 添加普通用户

    ```bash
    useradd -m -s /bin/zsh wanli
    # -m, --create-home 创建用户的家目录
    # -g, --gid GROUP 	添加到指定组， 如果不指定新建一个和用户名一样的组
    # -s, --shell SHELL	新用户登录的默认shell
    passwd wanli #为新添加的用户修改密码
    
    #将新用户添加到sudo(系统要安装sudo`pacman -S sudo`或者`pacman -S base-devel`)
    #临时使用vim`EDITOR=vim visudo`
    visudo
    	# 找到root ALL=(ALL) ALL 在下面添加
    	wanli ALL=(ALL) ALL
    ```

12. 应该安装Microcode

    ```bash
    pacman -S intel-ucode # intel cpu
    pacman -S amd-ucode  # amd cpu
    ```

13. 安装Grub

    ```bash
    pacman -S grub efibootmgr
    grub-install --target=x86_64-efi --efi-directory=/boot/EFI/ --bootloader-id=arch_grub --recheck
    grub-mkconfig -o /boot/grub/grub.cfg
    ```

14. 重启

    ```
    Ctrl+D
    umount -R /mnt
    reboot
    ```

15. 联网

    ```bash
    ip addr # 查看网卡
    ip link set ens33 up
    dhcpcd
    systemctl enable dhcpcd
    ```

16. 安装Deepin

    ```bash
    pacman -S deepin deepin-extra networkmanager
    systemctl enable NetworkManager
    systemctl start NetworkManager
    
    pacman -S adobe-source-han-serif-cn-fonts 	#思源宋体
    pacman -S adobe-source-han-sans-cn-fonts	#思源黑体
    ```

17. 修改lightdm

18. ```
    sudo vim /etc/lightdm/lightdm.conf
    	[Seat:*]
    	greeter-session=lightdm-deepin-greeter
    ```

19. 添加X服务器自启动

    ```
    vim ~/.xinitrc
    	exec startdde
    ```

20. 启动lightdm

    ```bash
    systemctl enable lightdm
    systemctl start lightdm
    ```

21. 常用软件

    ```bash
    pacman -S jdk11-openjdk
    ```

    

22. d

23. d

24. d

25. d

26. d

