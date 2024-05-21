# oday
javafx编写的poc管理和漏洞扫描小工具
## 介绍

本工具是采用javafx编写，使用sqllite进行poc储存的poc管理和漏洞扫描集成化工具。可以可视化添加POC和指纹进行POC管理和漏洞扫描功能，包含POC管理、漏洞扫描、指纹识别、指纹库等模块。

## Oday1.4.2更新
#### 陆陆续续修了一些bug，更新了一些新功能，加了500多个常用OA和CMS的POC和100多个常见指纹
主要更新：

- 1.指纹库录入格式改变。
- 2.增加了JNDI注入漏洞使用远程ldap检测方法，使用https://github.com/r00tSe7en/JNDIMonitor在VPS进行默认端口的部署，设置里填写VPS的IP即可
- 3.增加了需要请求远程文件的漏洞检测方法，VPS用Python起一个文件服务，将地址填入到设置里即可。
- 4.其他更新不一一列举，大家多多使用鼠标右键点击试试。添加POC时点击感叹号图标有一些提示和例子。
- 5.修了一堆bug，记不得哪些了，不列举了。
## 1.3.4版本更新

**1.又又又修复了一些bug**

**2.增加了几个添加POC时使用的占位关键字,详情查看release里的说明**

## 1.3版本更新
**1.多次请求**
添加漏洞POC时可添加多个请求，以适应某些需要多次发包的POC；

**2.漏洞扫描逻辑优化**
将之前漏洞扫描的多种方式进行整合，简化逻辑。

**3.修复一些已知bug**

**4.移除了一些鸡肋的功能**

## 1.2版本更新
**1.加入web指纹模块**
现在可以添加web指纹，并在添加poc时选择；在扫描时会先识别指纹，再获取该指纹对应的poc进行扫描，提高扫描效率。

**2.加入漏洞利用功能**
文件上传等漏洞可以一键利用，节省抓包修改的操作步骤。

**3.修复已知bug**

**4.移除了某些鸡肋的功能**

## 1.0.3版本更新：
1.修复了某些情况下返回的header中字段名重复时丢失字段值的bug

2.调整了页面布局和部分样式

3.添加poc时可选择是否进行302跳转

4.双击poc可查看poc描述

## 部分功能使用说明
 
### POC管理
Poc管理模块包含poc的预览、增加、导入、导出、编辑、删除功能。
<img width="1000" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/562cf594-3ab3-41e1-812f-51aac717a82e">

CMS可选从指纹库拉取的CMS名或者自定义输入，漏洞名称、漏洞类型、漏洞描述均自定义填写。
<img width="800" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/7ac8ccc6-655c-4479-ad52-79433e6178ed">

右键添加请求包可添加编辑删除多个请求，以适应某些需要多次发包的POC；Headers填写请求时必须的Header头，一行填写一个；请求次序选择该请求包发送的顺序；

<img width="800" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/5fbeb8c9-1b7d-4955-9558-9514b357d76a">
<img width="700" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/62d0a54c-8c2e-45ab-8459-e877dccbe36b">

自定义变量可对该次请求的的结果进行处理，提取需要的字段保存为公共变量(直接用～代替需要截取的部分)，后续请求包带上{{变量名}}即可使用该变量；

<img width="500" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/18e3f7dc-bca7-4d73-82fe-7a881234d051">
<img width="700" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/566ff72c-ab37-4dc1-98bd-741d4e0f4c33">

在填写完所有请求包后，选择请求包中的某次请求作为二次验证的请求包：


提示：添加POC时使用的占位关键字：
1.使用HexDecode{{内容}}可在发包时将{{}}内的内容进行16进制数据解码;适配于反序列化等漏洞。如图：

<img width="750" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/9abbdffe-9e38-47ba-8146-a0ef22157199">

2.使用Base64Decode{{内容}}可在发包时将{{}}内的内容进行base64数据解码;适配于压缩包上传等漏洞。如图：

<img width="750" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/702398c2-dbd6-4cb5-b6d4-f41045fc1c07">

3.使用{{RequestUrl}}可在发包时替换成当前请求的url;适配于某些需要refer头或者请求路径中需要当前拼接请求URL的情况。如图：

<img width="750" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/6b771d48-7fd9-4ab4-a711-e788630f7b9c">

4.使用{{RemoteHttpLog}}可在发包时替换成设置里设置的httplog地址。适用于命令执行不回显的情况。如图：

<img width="750" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/103358b6-82b5-4526-8190-b0f0cc51a0e4">

#### 3.简易的httplog服务在压缩包内，vps上部署即可，使用方式：

<img width="742" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/e3f3d0a5-8e6c-437e-bfda-ac60124ed2fe">

再在设置里填你部署的vps的地址和端口

<img width="500" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/0ba9661d-b098-4142-849f-a2cd8d18460d">


### 漏洞扫描
左侧选择需要扫描的漏洞；勾选匹配指纹后，在漏洞扫描前先对URL进行指纹识别，识别到指纹的URL扫描对应指纹的POC和CMS名称为All的POC，未识别到指纹的URL进行全部POC扫描；右键扫描结果，可对结果进行二次验证；扫描结果可保存为CSV文件（macos打开该CSV文件中时，其中的文字符会乱码）。

<img width="1000" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/245ed34c-990e-4e55-99ad-61b5baafc54a">
<img width="900" alt="image" src="https://github.com/Janhsu/oday/assets/62692103/dae7e149-1db4-4f92-b913-2728a3cfda76">



## 使用场景

这个工具可以看作一个简单的漏洞扫描框架，需要扫描什么漏洞，就可以自己进行调试添加；调试好的poc可以导出分享给团队成员，也可以导入他人调试好的poc。它可以是oa漏洞扫描工具，也可以是框架漏洞扫描工具，也可以是默认弱口令扫描工具，这完全取决于添加的poc。

## 不足之处    

~目前还没有做指纹识别，是比较傻瓜式的全部遍历扫描，虽然使用了多线程，但是在poc数量较多且目标资产较多的情况下，扫描速度还是不尽如人意。且会增加误报概率。~
工具为本人初学安全开发写的一个练手的小工具，不足之处还请各位大佬轻喷。

## 感谢

本工具思路部分借鉴了[h4ckdepy](https://github.com/h4ckdepy)大佬的Un1kPoc工具,[f0ng](https://github.com/f0ng)大佬的[poc2jar](https://github.com/f0ng/poc2jar)工具，感谢各位大佬的无私分享。
欢迎各路大佬给工具提提建议，给我改进的动力，谢谢！

## 免责声明

本工具截图所进行的演示均在本地环境或授权情况下进行，且本工具不包含任何权限级别的漏洞利用poc，也不包含漏洞利用功能，仅作为企业或个人资产漏洞自查的安全建设工具。在使用本工具时，您应确保该行为符合当地的法律法规，并且已经取得了足够的授权，请勿对非授权目标进行访问。如您在使用本工具的过程中存在任何非法行为，您需自行承担相应后果，我们将不承担任何法律及连带责任。请勿将本项目技术或代码应用在恶意软件制作、软件著作权/知识产权盗取或不当牟利等非法用途中。
