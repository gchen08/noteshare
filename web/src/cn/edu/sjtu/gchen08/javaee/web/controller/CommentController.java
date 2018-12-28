package cn.edu.sjtu.gchen08.javaee.web.controller;

import cn.edu.sjtu.gchen08.javaee.api.CommentService;
import cn.edu.sjtu.gchen08.javaee.api.NoteService;
import cn.edu.sjtu.gchen08.javaee.model.Comment;
import cn.edu.sjtu.gchen08.javaee.model.Note;
import cn.edu.sjtu.gchen08.javaee.model.User;
import cn.edu.sjtu.gchen08.javaee.web.common.Constant;
import cn.edu.sjtu.gchen08.javaee.web.common.Utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Gong on 2018/12/26
 * 评论
 */

@ManagedBean(name = "commentBean")
@ViewScoped
public class CommentController
{
    private NoteService noteService;
    private CommentService commentService;

    private Note note;
    private User author;
    private String content;

    public CommentController()
    {
        try
        {
            InitialContext context = new InitialContext(); // 部署在同一个服务器上不需要设置properties
            noteService = (NoteService) context.lookup(Utils.getJndiName(NoteService.class));
            commentService = (CommentService) context.lookup(Utils.getJndiName(CommentService.class));
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
        HttpServletRequest request = Utils.getRequest();
        Long noteId = Long.valueOf(request.getParameter(Constant.NOTE_ID));
        note = noteService.findById(noteId);
        author = (User) Utils.getSession().getAttribute(Constant.CURRENT_USER);
    }

    /*
        发布评论
     */
    public String post()
    {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setNote(note);
        comment.setPublishTime(new Date());
        commentService.save(comment);
        return Constant.PAGE_NOTE + "?id=" + note.getId() + "&faces-redirect=true"; // 返回对应的note页面
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
