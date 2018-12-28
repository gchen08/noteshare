package cn.edu.sjtu.gchen08.javaee.web.controller;

import cn.edu.sjtu.gchen08.javaee.api.NoteService;
import cn.edu.sjtu.gchen08.javaee.api.UserService;
import cn.edu.sjtu.gchen08.javaee.model.Note;
import cn.edu.sjtu.gchen08.javaee.model.User;
import cn.edu.sjtu.gchen08.javaee.web.common.Constant;
import cn.edu.sjtu.gchen08.javaee.web.common.Utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Gong on 2018/12/25
 * 笔记发布
 */

@ManagedBean(name = "postBean")
@SessionScoped
public class PostController implements Serializable
{
    private NoteService noteService;
    private UserService userService;

    private Note note;
    private User author;
    private String content;
    private String title;
    private Long noteId;
    private Byte state;


    public PostController()
    {
        try
        {
            InitialContext context = new InitialContext(); // 部署在同一个服务器上不需要设置properties
            noteService = (NoteService) context.lookup(Utils.getJndiName(NoteService.class));
            userService = (UserService) context.lookup(Utils.getJndiName(UserService.class));
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
        author = (User) Utils.getSession().getAttribute(Constant.CURRENT_USER);
    }

    /*
        发布
     */
    public String post(boolean saveAsDraft)
    {
        if (noteId == null)
            note = new Note();
        else
            author.deleteNote(note);
        note.setTitle(title);
        note.setContent(content);
        note.setPublishTime(new Date());
        note.setState(Byte.valueOf(saveAsDraft ? "0" : "1"));
        note.setAuthor(author);
        if (noteId == null)
            note = noteService.save(note);
        else
            note = noteService.update(note);
        author.addNote(note);
        updateUser();
        clearPost();
        return Constant.PAGE_USER_NOTE;
    }

    /*
        更新
     */
    public String update()
    {
        HttpServletRequest request = Utils.getRequest();
        noteId = Long.valueOf(request.getParameter(Constant.NOTE_ID));
        note = noteService.findById(noteId);
        title = note.getTitle();
        content = note.getContent();
        state = note.getState();
        return Constant.PAGE_POST;
    }

    /*
        删除
     */
    public String delete()
    {
        HttpServletRequest request = Utils.getRequest();
        noteId = Long.valueOf(request.getParameter(Constant.NOTE_ID));
        note = noteService.findById(noteId);
        noteService.delete(note);
        author.deleteNote(note);
        updateUser();
        clearPost();
        return Constant.PAGE_USER_NOTE;
    }

    /*
        更新用户信息
     */
    private void updateUser()
    {
        author = userService.update(author);
        Utils.getSession().setAttribute(Constant.CURRENT_USER, author);
    }

    public String clearPost()
    {
        content = "";
        title = "";
        noteId = null;
        state = 0;
        return Constant.PAGE_POST;
    }

    public Note getNote()
    {
        return note;
    }

    public void setNote(Note note)
    {
        this.note = note;
    }

    public Long getNoteId()
    {
        return noteId;
    }

    public void setNoteId(Long noteId)
    {
        this.noteId = noteId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Byte getState()
    {
        return state;
    }

    public void setState(Byte state)
    {
        this.state = state;
    }
}
