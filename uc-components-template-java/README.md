# UC-Components-Template-Java

### 代码规范
* 严格遵守JD EOS发布的JD编码规范- [JD-Java](http://doc.jd.com/base/eos-doc/system-rule/JD%E7%BC%96%E7%A0%81%E8%A7%84%E8%8C%83/Java/).
* 其中关于类, 函数, 接口, 枚举, 变量的注释直接查看代码. 

#### 静态代码检测
添加这三个文件在.git/hook/目录下:
- checkstyle-8.12-all.jar
- checkstyle.xml
- pre-commit

其中checkstyle.xml和pre-commit在check-style文件夹中，jar包地址如下
[checkStyle jar包下载地址](https://github.com/checkstyle/checkstyle/releases/download/checkstyle-8.12/checkstyle-8.12-all.jar)

并在.git/config文件最后添加：
```text
[checkstyle]
    jar = ./.git/hooks/checkstyle-8.12-all.jar
    checkfile = ./.git/hooks/checkstyle.xml
```
这样在commit时就会检测不合规范的代码

done~

### 依赖包管理 pom.xml
* 根据指定分类（可新增）, 填入相应的包依赖.

### 测试用例
* 每个API均需要功能测试用例, 以及必要的单元测试用例. 

### 代码结构
* 见 cataLog.txt.
