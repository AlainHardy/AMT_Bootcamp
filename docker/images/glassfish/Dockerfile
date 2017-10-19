FROM oracle/glassfish:4.1.2

MAINTAINER Alain Hardy <alain.hardy@heig-vd.ch>

ADD drivers/mysql-connector-java-5.1.39-bin.jar /glassfish4/glassfish/lib/

RUN echo "AS_ADMIN_PASSWORD=glassfish" > /tmp/gfpw.txt && \
asadmin --user admin --passwordfile=/tmp/gfpw.txt create-domain domainAMT && \
asadmin start-domain domainAMT  && \
asadmin --user admin --passwordfile=/tmp/gfpw.txt enable-secure-admin && \
asadmin --interactive=false --user admin --passwordfile=/tmp/gfpw.txt create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=adminpw:DatabaseName=bootcamp_db:ServerName=db:port=3306 pool_bootcamp && \
asadmin --interactive=false --user admin --passwordfile=/tmp/gfpw.txt create-jdbc-resource --connectionpoolid pool_bootcamp jdbc/bootcamp

RUN ./glassfish4/bin/asadmin stop-domain domainAMT

ADD /war/Bootcamp-1.0-SNAPSHOT.war /glassfish4/glassfish/domains/domainAMT/autodeploy

CMD ["asadmin", "start-domain", "-v", "domainAMT"]
