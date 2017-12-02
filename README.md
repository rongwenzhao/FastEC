# FastEC
项目包含内容：
1、latte-core模块。
   里面有通用的核心内容
        a、delegate.bottom包中，基于底边拦按钮的应用框架的搭建。基于Fregmention的单activity。
           BaseBottomDelegate，BottomItemDelegate，应用只要继承该类，实现相应方法即可；
        b、app包中，基于全局线程安全的单例的Configurator的使用。在Application中初始化，在其他地方，通过Latte.getConfiguration(Enum key)获取使用；
        c、app包中，用户的管理，根据具体需求合理借鉴本项目的实现；
        d、web包中，是对于webView的封装。实现的是类似原生跳转效果的webFregment。每个访问url都被拦截跳转到新的fregment中；本地与js无缝交互；
        e、net包是对网络请求的封装。基于okhttp3与retrofit2的封装。
            可轻松实现get，post，上传，下载等一些列操作，还可以添加网络拦截器；
        f、ui包是通用的关于界面的通用的东西。
            包括全局相机，基于com.wang.avi.AVLoadingIndicatorView库的全局对话框；
        g、util包包下的子包：
            callback包下，是全局的callback接口管理部分，可实现全项目各模块任意部分的通信；
            dimen包下是获得设备宽高的工具类；
            file包下，是对文件的一些操作：
                包括获取问价后缀名，保存到本地，获取formate的文件名，获取assert目录下，获取raw目录下的文件，通知系统刷新系统相册，
                使照片展现出来等等操作；
             logger包下，是基于com.orhanobut.logger.Logger日志库的日志的使用，日志显示的效果非常好；
             storage包下是LatterPreference，基于preference的存储相关操作；
             timer包下，是倒计时使用的TimerTask以及回调方法；
        
        h、wechat包，是通过代码生成器生成微信登录，微信支付的模板基类以及回调，项目添加微信功能时可以借鉴；
        
        具体参见项目中代码。
   
