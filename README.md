# lun[轮]

## 介绍

> 可执行jar

> 内置tomcat embed

> 基于jax-rs的rest最简路由

> 扩展druid实现jpa的NamedQuery注解#### 原理

## 使用说明

### maven

```
mvn help:system

mvn package -DskipTests -Ddockerfile.skip

mvn exec:exec -Dexec.executable="java" -Dexec.args="-jar target/lun-1.0-SNAPSHOT-bin.jar"
```


### jar

> java -jar *.jar，通过读取jar包内/META-INF/MANIFEST.MF，将jar包内的依赖包复制至./lib，类加载器会自动加载

```
java -jar  lun-1.0-SNAPSHOT-bin.jar
java -jar  lun-1.0-SNAPSHOT-bin.jar 9000
java -jar  lun-1.0-SNAPSHOT-bin.jar 9000 ssms.war
```

### h2
```
java -jar h2-1.4.199.jar -webAllowOthers
```

### docker

> 开启docker远程服务

```
com.spotify.docker.client.shaded.org.apache.http.conn.HttpHostConnectException

vi /etc/default/docker
DOCKER_OPTS="-H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375"
sudo service docker restart
curl http://192.168.1.10:2375/images/json

docker run -it -p 9000:9000 cike567/lun:1.0-SNAPSHOT #/bin/sh 
```

> 删除无效images

```
docker rmi -f $(docker images | grep "none" | awk '{print $3}')
```

### jax-rs
```
curl http://localhost:9000/application.json
```

### druid
```
curl http://localhost:9000/druid/index.html
```

### pom.xml配置

> maven-assembly-plugin 将依赖的jar打包至jar内lib下

> mvn package中执行assembly打包(按src.xml配置)

``` pom.xml
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
```

``` src.xml
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
```





