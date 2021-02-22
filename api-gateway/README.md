# API Gateway.

### 代码规范
* 严格遵守JD EOS发布的JD编码规范- [JD-Java](http://doc.jd.com/base/eos-doc/system-rule/JD%E7%BC%96%E7%A0%81%E8%A7%84%E8%8C%83/Java/).
* 其中关于类, 函数, 接口, 枚举, 变量的注释直接查看代码, 可在Idea-File-Settings-Editor File and Code Templates-Class中进行如下配置

```bash
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")
/**
 * Write description here.
 *
 * @author ${USER}
 * @date ${DATE} ${TIME}
 */
public class ${NAME} {
}
```
* 并在 Idea-File-Settings-Live Templates-userDefine 配置快捷键, Template text 如下
```bash
/** 
 * Write description here.
 *
 * @author $author$
 * @date  $date$ $time$  
 */
```

#### 代码检测
在README.md的同级路径下执行下面命令
```bash
tee .git/hooks/pre-commit > /dev/null <<EOF
#!/bin/bash
mvn clean test -e
if [ \$? -ne 0 ]; then
    exit 1
fi
exit 0
EOF
```

### 依赖包管理 pom.xml
* 根据指定分类（可新增）, 填入相应的包依赖.

### 测试用例
* 每个API均需要功能测试用例, 以及必要的单元测试用例. 
