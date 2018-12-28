package cn.edu.sjtu.gchen08.javaee.web.validator;

import cn.edu.sjtu.gchen08.javaee.api.UserService;
import cn.edu.sjtu.gchen08.javaee.model.User;
import cn.edu.sjtu.gchen08.javaee.web.common.Constant;
import cn.edu.sjtu.gchen08.javaee.web.common.Utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by gchen08 on 2018/12/19.
 * 用户名检测器
 */

@FacesValidator("usernameValidator")
public class UsernameValidator implements Validator
{
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException
    {
        String username = o.toString();

        if (username.length() < Constant.USERNAME_MIN_LEN)
            throw new ValidatorException(new FacesMessage("invalid password", "用户名长度不能小于" + Constant.USERNAME_MIN_LEN));
        if (username.length() > Constant.USERNAME_MAX_LEN)
            throw new ValidatorException(new FacesMessage("invalid password", "用户名长度不能超过" + Constant.USERNAME_MAX_LEN));
        if (username.trim().contains(" "))
            throw new ValidatorException(new FacesMessage("invalid password", "用户名不得含有空格"));
        try
        {
            InitialContext context = new InitialContext();
            UserService userService = (UserService) context.lookup(Utils.getJndiName(UserService.class));

            User user = userService.findByUsername(username);
            if (user != null)
                throw new ValidatorException(new FacesMessage("invalid password", Constant.USERNAME_EXISTS));
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
    }
}
