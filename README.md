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
				<classpathPrefix>lib</classpathPrefix>
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
			<outputDirectory>/lib</outputDirectory>
		</dependencySet>
	</dependencySets>
	<fileSets>
		<fileSet>
			<directory>${project.build.directory}/classes</directory>
			<outputDirectory>/</outputDirectory>
		</fileSet>
		<fileSet>
			<directory>/src</directory>
			<outputDirectory>/src</outputDirectory>
		</fileSet>
	</fileSets>
</assembly>
~~~

#### 使用说明

~~~
mvn help:system

mvn archetype:generate -X -DarchetypeCatalog=local

mvn test

mvn package -Dmaven.test.skip=true  
~~~

~~~
java -jar  mvn-1.0-SNAPSHOT-bin.jar
~~~

~~~
java -jar  mvn-1.0-SNAPSHOT-bin.jar -w ssms.war
~~~