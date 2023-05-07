# oday
javafx编写的poc管理和漏洞扫描小工具
## 介绍

本工具是采用javafx编写，使用sqllite进行poc储存的poc管理和漏洞扫描集成化工具。主要功能是poc管理，并且采用多线程进行漏洞扫描,不具有漏洞利用功能。

## 1.0.3版本更新：
1.修复了某些情况下返回的header中字段名重复时丢失字段值的bug

2.调整了页面布局和部分样式

3.添加poc时可选择是否进行302跳转

4.双击poc可查看poc描述

## 模块

### POC管理
显示当前poc列表。
右键poc可以删除、编辑。也可以导出分享poc。
![image](https://user-images.githubusercontent.com/62692103/227689243-d284af30-af10-43e7-8599-daf3bf32136e.png)


### 增加POC

第一页填写poc介绍信息；
![image](https://user-images.githubusercontent.com/62692103/227689258-55ccec64-b741-41b2-9980-588d1ec56eb1.png)


第二页填写漏洞扫描时所使用的参数，注意选择合适的回显验证方式，目前提供5种方式，若选择两种组合验证，还需选择两者之间的组合关系；若为文件上传漏洞，可以勾选shell验证来对上传后的文件进行验证；
![image](https://user-images.githubusercontent.com/62692103/227689270-ef089fd0-5d66-4710-a5aa-cf49fd15ce94.png)

### 漏洞扫描

全部扫描即扫描当前所有漏洞,资产数量过多时需要以文件形式导入，否则会乱码。
![image](https://user-images.githubusercontent.com/62692103/227689629-0f1b75b3-92ee-43d9-a532-06e337c94c09.png)


单项扫描即扫描某个漏洞；
![image](https://user-images.githubusercontent.com/62692103/227689649-89f19445-1741-4b2b-bc74-69ffcf3afe73.png)


cms扫描即扫描某个cms的漏洞，这取决于添加poc时填入的cms名称；
![image](https://user-images.githubusercontent.com/62692103/227689672-9d432eed-ab05-4142-974d-0ce5768c6e83.png)



自定义扫描即自由选择本次扫描需要的漏洞进行扫描，双击添加进待扫描漏洞列表。
![image](https://user-images.githubusercontent.com/62692103/227689695-075d5b8e-db42-4d92-a5cf-a2f88b9c9289.png)

### 调用脚本

某些不方便添加poc参数的漏洞，可通过脚本形式进行调用，其实就是一个python脚本收集的功能，方便对脚本进行收藏管理。
![image](https://user-images.githubusercontent.com/62692103/227562630-cf1f9bce-3650-4600-b9c0-7cc9b596bbb2.png)


### web识别

为了快速发现web端口，对端口扫描的结果进行http，https的识别。结果可保存到文件。
![image](https://user-images.githubusercontent.com/62692103/227689779-6db690f3-6b41-4d39-a722-e17e02a2f069.png)

### Log记录
每次扫描都会在根目录下log文件夹内的log文件内写入记录；
![image](https://user-images.githubusercontent.com/62692103/227563320-97e9078f-04c0-4e8d-9d56-1a7fc4743a83.png)

## 使用场景

这个工具可以看作一个简单的漏洞扫描框架，需要扫描什么漏洞，就可以自己进行调试添加；调试好的poc可以导出分享给团队成员，也可以导入他人调试好的poc。它可以是oa漏洞扫描工具，也可以是框架漏洞扫描工具，也可以是默认弱口令扫描工具，这完全取决于添加的poc。

## 不足之处

目前还没有做指纹识别，是比较傻瓜式的全部遍历扫描，虽然使用了多线程，但是在poc数量较多且目标资产较多的情况下，扫描速度还是不尽如人意。且会增加误报概率。
工具为本人初学安全开发写的一个练手的小工具，不足之处还请各位大佬轻喷。

## 感谢

本工具思路部分借鉴了[h4ckdepy](https://github.com/h4ckdepy)大佬的Un1kPoc工具,[f0ng](https://github.com/f0ng)大佬的[poc2jar](https://github.com/f0ng/poc2jar)工具，感谢各位大佬的无私分享。
欢迎各路大佬给工具提提建议，给我改进的动力，谢谢！

## 免责声明

本工具截图所进行的演示均在本地环境或授权情况下进行，且本工具不包含任何权限级别的漏洞利用poc，也不包含漏洞利用功能，仅作为企业或个人资产漏洞自查的安全建设工具。在使用本工具时，您应确保该行为符合当地的法律法规，并且已经取得了足够的授权，请勿对非授权目标进行访问。如您在使用本工具的过程中存在任何非法行为，您需自行承担相应后果，我们将不承担任何法律及连带责任。请勿将本项目技术或代码应用在恶意软件制作、软件著作权/知识产权盗取或不当牟利等非法用途中。
