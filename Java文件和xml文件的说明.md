## Java文件的项目结构

* mywork1_lda
  * activity 软件包 存放activity组件
    * MainActiviy 主页面
    * MainActivity2 某个聊天的详细界面
    * LogActivity 登录页面
    * EnrolActivity 注册界面
    * FindPassActivity 找回密码的界面
    * changePassActivity 更改密码的界面
    * BaiduMapActivity 调用百度地图的界面
    * BingPaperActivity 显示必应壁纸的界面
  * fragment 软件包 存放fragment类，四个fragment切换实现微信界面中间内容的切换
    * FragmentContact 消息界面，存放一个RecyclerView
    * FragmentContact 联系人页面，存放一个RecyclerView
    * FragmentExplore 探索页面
    * FragmentMe 我的页面
  * adapter 软件包 存放adapter和继承于RecyclerView的类
    * AdapterOfMessAndCon 绑定item_message.xml和FragmentMessage/Contact中的recyclerView
    * AdapterOfMessage 绑定聊天详细界面的RecyclerView和message_item1.xml和message_item2.xml
    * SwipeRecyclerView 继承于RecyclerView 为了实现下拉刷新、左划删除和点击的效果，在fragment_message.xml中使用
  * service
    * playWhenSwitch 进入聊天界面时播放5秒音乐 在MyReceiver中被调用和关闭
  * receiver
    * MyReceiver 点击进入具体连天页面时向MianActivity2和playWhenSwitch发出广播调用两者
  * DAO 存放和数据库相关的类
    * logDAO 登录所使用的DAO
    * MySQLiteOpenHelper
  * other
    * OperateOfAccount 登录和登录状态所使用的类，目前尚未完善，还没有实现未登录可使用的功能
  * otherSDK 存放第三方SDK
    * BaiduMap 百度地图调用

## layout文件夹下文件说明

​	原本打算在各文件夹下新建文件夹分类存放资源的，但是查找资料后发现建的文件夹无法在android视图下查看，遂放弃这种做法，直接在本文档中说明各文件的作用

* activity_main.xml 主页面

  * 顶栏引用与top.xml
  * 中间为FrameLayout，用于承接fragment，例fragmengt_message.xml等，一共四个
  * 底栏引用botton.xml
* activity_main2.xml 聊天的详细界面

  * 顶栏引用 message_top.xml
  * 中间是RecyclerView，承接message_item1.xml（对方消息）和message_item2.xml（机房消息）
  * 底栏引用message_botton.xml
* fragment_message contact explore me 四个fragment用于填充activity_main.xml中的FrameLayout
* activity_change_pass.xml 密码更改界面
* activity_enrol.xml 密码注册界面
* activity_find_pass.xml 找回密码
* activity_log.xml 登录界面
* message_top.xml和message_botton.xml是activity_main2.xml的顶栏和低栏
* message_item1.xml 和 message_item2.xml分别表示聊天的对方和自己的消息
* activity_baidu.map.xml 调用百度地图的界面
* activity_bing_paper.xml 必应壁纸的界面