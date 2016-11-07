Dundertext
==========

Web based subtitle editor

Build
-----

```bash
sbt "; fullOptJS; createLauncher"
nginx -c `pwd`/nginx.conf
./run.sh
chrome http://localhost:8000
```

License
-------
GNU General Public License GPLv3
