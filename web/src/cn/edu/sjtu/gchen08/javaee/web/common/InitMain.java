package cn.edu.sjtu.gchen08.javaee.web.common;

import cn.edu.sjtu.gchen08.javaee.api.NoteService;
import cn.edu.sjtu.gchen08.javaee.api.UserService;
import cn.edu.sjtu.gchen08.javaee.model.Note;
import cn.edu.sjtu.gchen08.javaee.model.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;

/**
 * Created by Gong on 2018/12/21
 * 初始化
 */

public class InitMain
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

            /*
                生成管理员账号
             */
            User admin = userService.findByUsername(Constant.ADMIN_NAME);
            if (admin != null)
                System.out.printf("管理员账号已存在, username:%s\n", Constant.ADMIN_NAME);
            else
                addAdmin();

            /*
                发布第一篇笔记
             */
            addInstruction();
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
    }

    private static void addInstruction()
    {
        User admin = userService.findByUsername(Constant.ADMIN_NAME);
        String title = "欢迎来到NoteShare！";
        String content = "NoteShare是一个分享知识与交流心得的社区。\n"
                + "在这里，你不仅可以查看他人分享的笔记，也可以分享自己的心得体会，与社区的大家一同交流进步！\n"
                + "Start to share your note!";
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setAuthor(admin);
        note.setPublishTime(new Date());
        note.setState(Byte.valueOf("1"));

        admin.addNote(note);
        userService.update(admin);
    }

    private static void addAdmin()
    {
        User user = new User();
        user.setUsername(Constant.ADMIN_NAME);
        user.setPassword(Utils.getSHA256(Constant.ADMIN_PASSWORD));
        user.setEmail(Constant.ADMIN_EMAIL);
        user.setLevel(Byte.valueOf("0"));
        userService.save(user);
        System.out.printf("管理员账号已设置, username:%s, password:%s\n", Constant.ADMIN_NAME, Constant.ADMIN_PASSWORD);
    }

}
