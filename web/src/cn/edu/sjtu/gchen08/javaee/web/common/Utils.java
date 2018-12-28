package cn.edu.sjtu.gchen08.javaee.web.common;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by gchen08 on 2018/12/19.
 * 工具类
 */

public class Utils
{

    /*
        JNDI名
     */
    public static String getJndiName(Class classObj)
    {
        return "java:global/" + Constant.MODULE_NAME + "/" +
                classObj.getSimpleName() + "Impl" + "!" +
                classObj.getName();
    }

    /*
        获得Session
     */
    public static HttpSession getSession()
    {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    /*
        获得Response
     */
    public static HttpServletResponse getResponse()
    {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    /*
        获得Request
     */
    public static HttpServletRequest getRequest()
    {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    /*
        SHA256加密
     */
    public static String getSHA256(String str)
    {
        String encodeStr = "";
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byteToHex(messageDigest.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byteToHex(byte[] bytes)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String hexStr = null;
        for (byte b : bytes)
        {
            hexStr = Integer.toHexString(b & 0xFF);
            if (hexStr.length() == 1) stringBuilder.append("0");
            stringBuilder.append(hexStr);
        }
        return stringBuilder.toString();
    }

}
