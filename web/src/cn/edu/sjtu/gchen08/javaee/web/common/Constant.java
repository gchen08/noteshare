package cn.edu.sjtu.gchen08.javaee.web.common;

import java.util.Properties;

/**
 * Created by gchen08 on 2018/12/19.
 * 常用参数
 */

public class Constant
{

    /*
        管理员参数
     */
    public static final String ADMIN_NAME = "admin";
    public static final String ADMIN_PASSWORD = "admin";
    public static final String ADMIN_EMAIL = "gchen08@sjtu.edu.cn";

    /*
        EJB参数
     */
    public static final String MODULE_NAME = "noteshare_ejb";
    public static final Properties PROPERTIES = getProperties();

    /*
        页面设置参数
     */
    public static final int NUM_OF_NOTES_IN_INDEX = 3;          // 首页显示最新笔记数
    public static final int NUM_OF_NOTES_PER_PAGE = 5;          // 每页显示笔记数
    public static final int DEFAULT_PAGE = 1;                   // 默认页数
    public static final int USERNAME_MIN_LEN = 2;               // 用户名长度最小值
    public static final int USERNAME_MAX_LEN = 20;              // 用户名长度最大值
    public static final int PASSWORD_MIN_LEN = 8;               // 密码长度最小值
    public static final int PASSWORD_MAX_LEN = 30;              // 密码长度最大值

    /*
        Session parameter
     */
    public static final String CURRENT_USER = "currentUser";    // 当前用户
    public static final String NOTE_ID = "id";                  // 笔记id
    public static final String RETURN_URL = "returnUrl";        // 返回地址

    /*
        页面地址
     */
    public static final String PAGE_INDEX = "index";            // 首页
    public static final String PAGE_POST = "post";              // 撰写笔记
    public static final String PAGE_NOTE = "note";              // 笔记详情
    public static final String PAGE_NOTE_LIST = "notelist";     // 笔记列表
    public static final String PAGE_REGISTER = "register";      // 注册
    public static final String PAGE_LOGIN = "login";            // 登陆
    public static final String PAGE_USER_NOTE = "mynotes";      // 用户笔记列表
    public static final String PAGE_ABOUT = "about";            // 关于
    public static final String PAGE_SETTINGS = "settings";      // 账号设置

    /*
        提示信息
     */
    public static final String USERNAME_EXISTS = "用户名已存在！";
    public static final String USERNAME_DOES_NOT_EXIST = "用户名不存在，请重新输入！";
    public static final String PASSWORD_INCORRECT = "密码错误，请重新输入！";
    public static final String PASSWORD_CHANGED = "密码已修改，请重新登录！";
    public static final String EMAIL_CHANGED = "Email已经修改！";

    /*
        远程访问的properties
     */
    private static Properties getProperties()
    {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        properties.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        properties.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        return properties;
    }
}
