# mvn

#### 项目介绍
可执行jar

#### 原理
> maven-assembly-plugin 将依赖的jar打包至jar内lib下

> java -jar *.jar，通过读取jar包内/META-INF/MANIFEST.MF，将jar包内的依赖包复制至./lib，类加载器会自动加载

#### 配置

> pom.xml 

> mvn package中执行assembly打包(按src.xml配置)

~~~ xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-assembly-plugin</artifactId>
	<version>3.1.0</version>
	<configuration>
		<archive>
			<manifest>
				<mainClass>${project.groupId}.App</mainClass>
				<addClasspath>true</addClasspath>
				<classpathPrefix>${project.lib}</classpathPrefix>
			</manifest>
		</archive>
		<descriptors>
			<descriptor>src/main/assembly/src.xml</descriptor>
		</descriptors>
	</configuration>
	<executions>
		<execution>
			<phase>package</phase>
			<goals>
				<goal>single</goal>
			</goals>
		</execution>
	</executions>
</plugin>
~~~

> src.xml

~~~
<assembly
	xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0 http://maven.apache.org/xsd/assembly-1.1.0.xsd">
	<id>bin</id>
	<formats>
		<format>jar</format>
	</formats>
	<includeBaseDirectory>false</includeBaseDirectory>
	<dependencySets>
		<dependencySet>
			<unpack>false</unpack>
			<scope>runtime</scope>
			<excludes>
				<exclude>${project.groupId}:${project.artifactId}</exclude>
			</excludes>
			<outputDirectory>${project.lib}</outputDirectory>
		</dependencySet>
	</dependencySets>
	<fileSets>
		<fileSet>
			<directory>${project.build.directory}/classes</directory>
			<outputDirectory>/</outputDirectory>
		</fileSet>
		<fileSet>
			<directory>${project.build.directory}/classes</directory>
			<outputDirectory>/WEB-INF/classes</outputDirectory>
		</fileSet>
		<fileSet>
			<directory>/src</directory>
			<outputDirectory>/src</outputDirectory>
		</fileSet>
	</fileSets>
</assembly>
~~~

#### 使用说明

##### jar

~~~
mvn help:system

mvn archetype:generate -X -DarchetypeCatalog=local

mvn test

mvn package -DskipTests -Ddockerfile.skip

mvn exec:exec -Dexec.executable="java" -Dexec.args="-jar target/mvn-1.0-SNAPSHOT-bin.jar"
~~~

~~~
java -jar  mvn-1.0-SNAPSHOT-bin.jar
java -jar  mvn-1.0-SNAPSHOT-bin.jar 8000
java -jar  mvn-1.0-SNAPSHOT-bin.jar 8080 ssms.war
~~~

##### jax-rs
~~~
curl http://localhost:9000/application.json
~~~

#### druid
```
curl http://localhost:9000/druid/index.html
```

#### h2
```
java -jar h2-1.4.199.jar -webAllowOthers
```

#### docker

> 开启docker远程服务

```
com.spotify.docker.client.shaded.org.apache.http.conn.HttpHostConnectException

vi /etc/default/docker
DOCKER_OPTS="-H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375"
sudo service docker restart
curl http://192.168.1.10:2375/images/json

docker run -it -p 9000:9000 cike567/jaxrs:1.0-SNAPSHOT #/bin/sh 
```

```
docker rmi -f $(docker images | grep "none" | awk '{print $3}')
```

```
sudo add-apt-repository ppa:openjdk-r/ppa

sudo apt-get autoclean
sudo apt-get update
sudo apt-get upgrade -f

sudo apt-get install openjdk-8-jdk
sudo update-alternatives --config java

vim /etc/profile
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar 
export PATH=$PATH:$JAVA_HOME/bin
source /etc/profile

java -jar -Djava.ext.dirs=. mvn-1.0-SNAPSHOT-bin.jar

```

```
scp target/mvn-1.0-SNAPSHOT-bin.jar cike@120.77.144.97:~/workspace
ssh cike@120.77.144.97
```




