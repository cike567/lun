# nian[年]

## 1.介绍

> 节假日/工作日API

```
java -jar nian-1.0-SNAPSHOT-bin.jar 9000
或
docker run -it -p 9000:9000 lun/nian:1.0-SNAPSHOT #/bin/sh
或
mvn exec:exec -Dexec.executable="java" -Dexec.args="-jar nian-1.0-SNAPSHOT-bin.jar"
```

## 2.接口说明

### 2.1节假日接口

#### 2.1.1按年查询节假日列表
* 接口：GET　/holiday/year

> 查询当年所有的节假日 /holiday/year

> 查询某年所有的节假日 /holiday/year/2019 

#### 2.1.2按月查询节假日列表
* 接口：GET　/holiday/month 

> 查询当月所有的节假日 /holiday/month

> 查询某年某月的节假日 /holiday/month/2019/07

#### 2.1.3按周查询节假日列表
* 接口：GET　/holiday/week 

> 查询本周所有的节假日 /holiday/week

> 查询上周所有的节假日 /holiday/week/-1

> 查询下周所有的节假日 /holiday/week/1

#### 2.1.4按日查询节假日列表
* 接口： GET　/holiday/day 

> 查看当天是否是节假日，是则返回当天日期 /holiday/day

> 查看某天是否是节假日，是则返回该日期 /holiday/day/2019-06-26

> 查询多天的节假日 /holiday/day/2019-06-26,2019-07-26

> 查询日期范围内的节假日 /holiday/day/2019-06-26:2019-07-26,2018-10-04

### 2.2工作日接口

#### 2.2.1按年查询工作日列表
* 接口：GET　/weekday/year

> 查询当年所有的工作日 /weekday/year

> 查询某年所有的工作日 /weekday/year/2019 

#### 2.2.2按月查询工作日列表
* 接口：GET　/weekday/month 

> 查询当月所有的工作日 /weekday/month

> 查询某年某月的工作日 /weekday/month/2019/07

#### 2.2.3按周查询工作日列表
* 接口：GET　/weekday/week 

> 查询本周所有的工作日 /weekday/week

> 查询上周所有的工作日 /weekday/week/-1

> 查询下周所有的工作日 /weekday/week/1

#### 2.2.4按日查询工作日列表
* 接口： GET　/weekday/day 

> 查看当天是否是工作日，是则返回当天日期 /weekday/day

> 查看某天是否是工作日，是则返回该日期 /weekday/day/2019-06-26

> 查询多天的工作日 /weekday/day/2019-06-26,2019-07-26

> 查询日期范围内的工作日 /weekday/day/2019-06-26:2019-07-26,2018-10-04