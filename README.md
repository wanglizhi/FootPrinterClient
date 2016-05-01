![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/img/logo.png)

## Footprinter 客户端

> 最近在整理一些之前的项目到github上来，Footprinter应该是继EL之后，我们组的又一力作吧，用Java Swing 加RMI能做成这样着实不易。项目整理后真的是满满的回忆，一方面自己看着很充实的感觉，大学的成长就在其中，另一方面，项目还是可以拿得出手去的，大家欣赏欣赏=。= 
>
> 感谢曾经的队友，永远的Firework团队！

#### Footprinter项目简介

**需求：**

 我是一个爱旅行的人;
 我是一个计划强迫症患者;
 我可以一个人,也想搭伴;
 我要记录我所有的足迹,以便老去后的回忆;
 我想看外面的世界;
 我想看别人的生活,他们的囧途;
 我要一个旅行者的系统																				**备注** 团队使用螺旋模型,高质量的完成一个系统的设计与开发工作

**功能：**

- 注册用户、登陆
- 指定旅行计划、查看他人的旅行计划、收藏他人的旅行计划
- 查看城市地图、城市介绍、旅行景点餐饮娱乐信息
- 写旅行记录、查看别人的旅行记录、评价别人的旅行记录、对景点评价

**项目截图（没错，相信你的眼睛，它就是Java Swing写的。由于换电脑的缘故，数据库没有保存，项目不能跑，截图是某一次提交的文档里的，有些数据是mock的，最终项目的功能是都实现了的）**

本身项目采用BS的话可能会更简单（BS难度降低会减分），但是为了将Java的CS模式贯彻到底，采用基于RMI的分层模式，更好的螺旋模型的使用，每次迭代及相应的文档。

经历了EL的洗礼，对于Java Swing可谓熟练，各种panel上直接draw图片和文字，实现了很多网页上的特效，beauty-eye效果很赞。

**登陆界面：背景图片和Logo精心制作**

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/login.png)

**主界面**

Tab、图标、位置都是精心设计的，风格还不错呢

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/home.png)

**个人空间界面**

我的地图意思是根据计划的位置，生成一个个点，连在一块形成`足迹`，辛苦选易当时研究谷歌地图的嵌入和使用

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/userInfo.png)

地图可以缩放的

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/map.png)

**景点界面（这个界面是我写的，注意，鼠标移动到图片上会有动画效果上滑出景点说明）**

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/landmark.png)

**城市介绍（这个节目也是我写的，红心可以点赞，南京下面的那两张图是一组图，仿照网页效果循环往左滑动，amazing）**

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/city.png)

**查看记录（根据时间条动态显示，回形针的样式很赞）**

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/record.png)

新建记录，根据输入动态显示地图

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/newRecord.png)

一个写记录的编辑器（辛苦zhoukai），可以上传图片、音频、视频的哦！Jpanel上绘制出的效果。文章的存储是采用xml的格式。

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/createRecord.png)

**计划（晨富任性地用swing搞出一个可以拖拽的计划日历编辑器）**

界面操作都是采用拖拽的方式实现，amazing！滑块可以动态移动、交换，景点可以直接拖动到计划任务上生成计划。

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/newPlan.png)

![](https://raw.githubusercontent.com/wanglizhi/FootPrinterClient/master/shotcut/planDetail.png)



**说明** 为减小体积，代码中的lib包和一些资源文件有删除。

