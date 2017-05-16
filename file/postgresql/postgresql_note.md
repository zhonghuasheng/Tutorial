### How to install Postgresql in Linux

* Go to page https://yum.postgresql.org/9.4/redhat/rhel-6-x86_64/
* URL may find by `https://yum.postgresql.org/version`, like `https://yum.postgresql.org/9.4/`
* Find rpm url about
    `postgresql94-libs-9.4.7-1PGDG.rhel7.x86_64.rpm`
    `postgresql94-9.4.7-1PGDG.rhel7.x86_64.rpm`
    `postgresql94-contrib-9.4.7-1PGDG.rhel7.x86_64.rpm`
    `postgresql94-server-9.4.7-1PGDG.rhel7.x86_64.rpm`
* wget http://yum.postgresql.org/9.4/redhat/rhel-7-x86_64/postgresql94-libs-9.4.7-1PGDG.rhel7.x86_64.rpm
  wget http://yum.postgresql.org/9.4/redhat/rhel-7-x86_64/postgresql94-9.4.7-1PGDG.rhel7.x86_64.rpm
  wget http://yum.postgresql.org/9.4/redhat/rhel-7-x86_64/postgresql94-contrib-9.4.7-1PGDG.rhel7.x86_64.rpm
  wget http://yum.postgresql.org/9.4/redhat/rhel-7-x86_64/postgresql94-server-9.4.7-1PGDG.rhel7.x86_64.rpm
* yum install -y postgresql94-libs-9.4.7-1PGDG.rhel7.x86_64.rpm
  yum install -y postgresql94-9.4.7-1PGDG.rhel7.x86_64.rpm
  yum install -y postgresql94-contrib-9.4.7-1PGDG.rhel7.x86_64.rpm
  yum install -y postgresql94-server-9.4.7-1PGDG.rhel7.x86_64.rpm
* execute `service postgresql-9.4 initdb`
* execute `service postgresql-9.4 start`
