# db-comm-srv
豆瓣置顶帖服务

## 使用场景:
早早发布的贴子迟迟没有得到回复,被不断更新的新帖不断打断? 最终石沉大海?

## 使用方法
这个小工具可以帮助自定义置顶时间周期-帮助评论置顶自己的帖子,置顶评论内容自定义,支持配置,使用仅需要指定douban绑定的手机号和密码,以及帖子的页面id既可使用;
<br>修改如下配置内容:
<br>`business.db.homepage.login.name`
<br>`business.db.homepage.login.password`
<br>`business.db.group.comment.commentUrl` 里面的`your_page_id`值.
<br>`business.db.group.comment`可以设置置顶帖追加评论的内容,这里默认设置为**"up"**,评论内容可自定义.

`your_page_id`值的获取方式:
![评论页id](img/coment-page-id.png)

#------分割线(2020.08.21更新)
本地服务运行可以正常置顶,
但发现将服务丢到腾讯云服务器上仿佛被限制了ip,
需要图形验证码才能够登录发布评论,add todo item...
