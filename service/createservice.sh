cp -a lib/systemd/system/siabe.service /lib/systemd/system/
-a cp -a etc/default/siabe /etc/default/
-a cp -a etc/init.d/siabe /etc/init.d/
-a cp -a etc/logrodate.d/siabe /etc/logrotate.d/
-a cp -a usr/bin/siabe /usr/bin/
-a cp -a ../target/SIABE-jar-with-dependencies.jar /usr/lib/siabe/

systemctl enable siabe.service
systemctl start siabe.service

