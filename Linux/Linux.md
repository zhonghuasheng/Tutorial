


















# Questions

## The difference among `su`, `su -`, `sudo`

1. Fist the command `su`, 'su -' can get `root` authority(权限) by enter password of root user.
2. While some user just want to do something that only root user can do,
   but root user doesn't want ot give pwd to them, so this command can just put root authority to them interim(临时).

   Following is the process:
   * check the user is in `etc/sudoers` list or not.
   * if in, use root authority to execute this command.
   * remove root authority from this user