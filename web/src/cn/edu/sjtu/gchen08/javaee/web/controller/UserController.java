package cn.edu.sjtu.gchen08.javaee.web.controller;

import cn.edu.sjtu.gchen08.javaee.api.UserService;
import cn.edu.sjtu.gchen08.javaee.model.User;
import cn.edu.sjtu.gchen08.javaee.web.common.Constant;
import cn.edu.sjtu.gchen08.javaee.web.common.Utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;

/**
 * Created by Gong on 2018/12/21
 * 用户服务类
 */

@ManagedBean(name = "userBean")
@SessionScoped
public class UserController implements Serializable
{
    private UserService userService;

    private User user;
    private User currentUser;
    // 响应信息
    private String errorMsg;
    // 返回页面
    private String returnUrl;
    private String currentPassword;
    private String newPassword;
    private String newEmail;


    public UserController()
    {
        try
        {
            InitialContext context = new InitialContext(); // 部署在同一个服务器上不需要设置properties
            userService = (UserService) context.lookup(Utils.getJndiName(UserService.class));
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
        user = new User();
        currentUser = null;
    }

    /*
        注册
     */
    public String register()
    {
        user.setPassword(Utils.getSHA256(user.getPassword())); // 防止明文保存密码
        user.setLevel(Byte.valueOf("1"));
        userService.save(user);
        Utils.getSession().setAttribute(Constant.CURRENT_USER, user);
        returnUrl = (String) Utils.getSession().getAttribute(Constant.RETURN_URL);
        if (returnUrl != null && returnUrl.startsWith("note"))
        {
            Utils.getSession().setAttribute(Constant.RETURN_URL, null);
            return returnUrl + "&faces-redirect=true";
        }
        return Constant.PAGE_INDEX;
    }

    /*
        登陆
     */
    public String login()
    {
        User userGet = userService.findByUsername(user.getUsername());
        if (userGet == null)
        {
            errorMsg = Constant.USERNAME_DOES_NOT_EXIST;
            return Constant.PAGE_LOGIN;
        }
        if (!Utils.getSHA256(user.getPassword()).equals(userGet.getPassword()))
        {
            errorMsg = Constant.PASSWORD_INCORRECT;
            return Constant.PAGE_LOGIN;
        }
        user = userGet;
        currentUser = user;
        Utils.getSession().setAttribute(Constant.CURRENT_USER, user);
        errorMsg = "";
        returnUrl = (String) Utils.getSession().getAttribute(Constant.RETURN_URL);
        if (returnUrl != null && returnUrl.startsWith("note"))
        {
            Utils.getSession().setAttribute(Constant.RETURN_URL, null);
            return returnUrl + "&faces-redirect=true";
        }
        return Constant.PAGE_INDEX;
    }

    /*
        登出
     */
    public String logout()
    {
        currentUser = null;
        Utils.getSession().setAttribute(Constant.CURRENT_USER, null);
        returnUrl = (String) Utils.getSession().getAttribute(Constant.RETURN_URL);
        if (returnUrl != null && returnUrl.startsWith("note"))
        {
            Utils.getSession().setAttribute(Constant.RETURN_URL, null);
            return returnUrl + "&faces-redirect=true";
        }
        return Constant.PAGE_INDEX;
    }

    /*
        更新设置
     */
    public String update()
    {
        if (!Utils.getSHA256(currentPassword).equals(currentUser.getPassword()))
        {
            errorMsg = Constant.PASSWORD_INCORRECT;
            return Constant.PAGE_SETTINGS;
        }
        if (!newPassword.equals(""))
        {
            user = userService.findByUsername(currentUser.getUsername());
            user.setPassword(Utils.getSHA256(newPassword));
            if (!newEmail.equals(""))
                user.setEmail(newEmail);
            userService.update(user);
            currentUser = null;
            errorMsg = Constant.PASSWORD_CHANGED;
            return Constant.PAGE_SETTINGS;
        }
        if (!newEmail.equals(""))
        {
            user = userService.findByUsername(currentUser.getUsername());
            user.setEmail(newEmail);
            userService.update(user);
            currentUser = user;
            Utils.getSession().setAttribute(Constant.CURRENT_USER, user);
            errorMsg = Constant.EMAIL_CHANGED;
            return Constant.PAGE_SETTINGS;
        }
        errorMsg = "";
        return Constant.PAGE_SETTINGS;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getReturnUrl()
    {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl)
    {
        this.returnUrl = returnUrl;
    }

    public String getCurrentPassword()
    {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword)
    {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public String getNewEmail()
    {
        return newEmail;
    }

    public void setNewEmail(String newEmail)
    {
        this.newEmail = newEmail;
    }
}
