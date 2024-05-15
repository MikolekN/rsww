#!/usr/bin/env bash

jar xf user_service.jar BOOT-INF/classes/application.properties
sed -i "s/RABBIT_HOST/$RABBIT_HOST/" BOOT-INF/classes/application.properties
sed -i "s/RABBIT_USR/$RABBIT_USR/" BOOT-INF/classes/application.properties
sed -i "s/RABBIT_PWD/$RABBIT_PWD/" BOOT-INF/classes/application.properties
sed -i "s/RABBIT_PORT/$RABBIT_PORT/" BOOT-INF/classes/application.properties
jar uf user_service.jar BOOT-INF/classes/application.properties
cat BOOT-INF/classes/application.properties
java -jar user_service.jar