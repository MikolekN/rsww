#!/usr/bin/env bash

jar xf transport_service.jar BOOT-INF/classes/application.properties
#sed -i "s/POSTGRES_HOST/$POSTGRES_HOST/" BOOT-INF/classes/application.properties
#sed -i "s/POSTGRES_PORT/$POSTGRES_PORT/" BOOT-INF/classes/application.properties
#sed -i "s/POSTGRES_DATABASE/$POSTGRES_DATABASE/" BOOT-INF/classes/application.properties
#sed -i "s/POSTGRES_USER/$POSTGRES_USER/" BOOT-INF/classes/application.properties
#sed -i "s/POSTGRES_PWD/$POSTGRES_PWD/" BOOT-INF/classes/application.properties
sed -i "s/RABBIT_HOST/$RABBIT_HOST/" BOOT-INF/classes/application.properties
sed -i "s/RABBIT_USR/$RABBIT_USR/" BOOT-INF/classes/application.properties
sed -i "s/RABBIT_PWD/$RABBIT_PWD/" BOOT-INF/classes/application.properties
sed -i "s/RABBIT_PORT/$RABBIT_PORT/" BOOT-INF/classes/application.properties
jar uf transport_service.jar BOOT-INF/classes/application.properties
cat BOOT-INF/classes/application.properties
java -jar transport_service.jar