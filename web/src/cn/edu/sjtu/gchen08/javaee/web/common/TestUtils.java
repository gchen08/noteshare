package cn.edu.sjtu.gchen08.javaee.web.common;

import cn.edu.sjtu.gchen08.javaee.api.NoteService;
import cn.edu.sjtu.gchen08.javaee.api.UserService;
import cn.edu.sjtu.gchen08.javaee.model.Note;
import cn.edu.sjtu.gchen08.javaee.model.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;

/**
 * Created by Gong on 2018/12/24
 * 生成测试数据
 */

public class TestUtils
{

    private static UserService userService;
    private static NoteService noteService;

    public static void main(String[] args)
    {

        try
        {
            InitialContext context = new InitialContext(Constant.PROPERTIES);
            userService = (UserService) context.lookup(Utils.getJndiName(UserService.class));
            noteService = (NoteService) context.lookup(Utils.getJndiName(NoteService.class));
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
        genTestData();
    }

    private static void genTestData()
    {
        /*
            添加测试用户
         */
        User user;
        user = userService.findByUsername("gchen08");
        if (user == null)
        {
            user = new User();
            user.setUsername("gchen08");
            user.setPassword(Utils.getSHA256("gchen08"));
            user.setEmail("gchen08@sjtu.edu.cn");
            user.setLevel(Byte.valueOf("1"));
            user = userService.save(user);
        }

//        String title = "testTitle";
//        String content = "testContent";
//        addNote(title, content, user);
    }

    private static void addNote(String title, String content, User user)
    {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setState(Byte.valueOf("1"));
        note.setPublishTime(new Date());
        note.setAuthor(user);

        user.addNote(note);
        userService.update(user);
    }
}
