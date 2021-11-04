## Java文件的项目结构

* mywork1_lda
  * activity 软件包 存放activity组件
    * MainActiviy 主页面
    * MainActivity2 某个聊天的详细界面
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
    * playWhenSwitch 进入聊天界面时播放5秒音乐 被activity_mian2调用

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

  