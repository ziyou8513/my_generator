# my_generator

#### 介绍
基于ssm框架项目
一键生成dao&service层
省去自己编写dao和service层基本代码，使开发人员更加专注于业务逻辑

#### 软件架构
src  
 ├─templates	文件模板  
 └─util			工具类  


#### 安装教程

1.  jdk1.8

#### 使用说明

1.  下载release包
2.  修改config.properties里的参数
3.  运行run-my-generator.bat

#### 注意事项
1.	前置条件，先用mybatis生成mapper
2.	生成的代码中包含一些TODO需要自己补全代码（比如查询条件，主键生成逻辑）
3.  不适用于使用联合主键的数据表
4.  此工具只可用于项目创建初期生成dao层和service层，如果是已存在的代码，将会被覆盖

#### 参与贡献

1.  Fork 本仓库
2.  新建dbg_xxx分支
3.  提交代码
4.  新建 Pull Request