package cn.edu.sjtu.gchen08.javaee.web.validator;

import cn.edu.sjtu.gchen08.javaee.web.common.Constant;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gchen08 on 2018/12/19.
 * 密码检测器
 */

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator
{

    private static final String PASSWORD_PATTERN = "\\S*(\\S*([a-zA-Z]\\S*[0-9])|([0-9]\\S*[a-zA-Z]))\\S*";
    private Pattern pattern;

    public PasswordValidator()
    {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException
    {

        String password = o.toString();
        if (!password.equals(""))
        {
            if (password.length() < Constant.PASSWORD_MIN_LEN)
                throw new ValidatorException(new FacesMessage("invalid password", "密码长度不能小于" + Constant.PASSWORD_MIN_LEN));
            if (password.length() > Constant.PASSWORD_MAX_LEN)
                throw new ValidatorException(new FacesMessage("invalid password", "密码长度不能超过" + Constant.PASSWORD_MAX_LEN));
            if (password.contains(" "))
                throw new ValidatorException(new FacesMessage("invalid password", "密码不得含有空格"));
            Matcher matcher = pattern.matcher(password);
            if (!matcher.matches())
                throw new ValidatorException(new FacesMessage("invalid password", "密码必须包含数字和字母"));
        }
    }
}
