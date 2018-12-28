package cn.edu.sjtu.gchen08.javaee.web.validator;

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
 * Email检测器
 */

@FacesValidator("emailValidator")
public class EmailValidator implements Validator
{

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
            "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
            "(\\.[A-Za-z]{2,})$";
    private Pattern pattern;

    public EmailValidator()
    {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException
    {
        String email = o.toString();
        if (!email.equals(""))
        {
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches())
                throw new ValidatorException(new FacesMessage("invalid email", "Email格式不正确"));
        }
    }
}
