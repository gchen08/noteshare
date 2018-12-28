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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gong on 2018/12/24
 * 笔记页
 */

@ManagedBean(name = "noteBean")
@ViewScoped
public class NoteController
{
    private NoteService noteService;
    private CommentService commentService;

    private Note note;
    private Long noteId;
    private List<Comment> comments = new ArrayList<>();
    //访问控制
    private boolean canBeDisplayed = false;

    public NoteController()
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
        noteId = Long.valueOf(request.getParameter(Constant.NOTE_ID));
        note = noteService.findById(noteId);
        if (note != null)
            // 公开笔记
            if (note.getState().equals(Byte.valueOf("1")))
            {
                note.setViewCount(note.getViewCount() + 1);
                noteService.update(note);
                canBeDisplayed = true;
            }
            // 访问者为管理员或者作者
            else
            {
                User user = (User) Utils.getSession().getAttribute(Constant.CURRENT_USER);
                if (user.getLevel() == Byte.valueOf("0") ||
                        note.getAuthor().getUsername().equals(user.getUsername()))
                    canBeDisplayed = true;

            }
        if (canBeDisplayed)
        {
            comments = commentService.findByNote(note);
            Utils.getSession().setAttribute(Constant.RETURN_URL, Constant.PAGE_NOTE + "?id=" + noteId);
            canBeDisplayed = true;
        }
    }

    public Note getNote()
    {
        return note;
    }

    public void setNote(Note note)
    {
        this.note = note;
    }

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
    }

    public Long getNoteId()
    {
        return noteId;
    }

    public void setNoteId(Long noteId)
    {
        this.noteId = noteId;
    }

    public boolean isCanBeDisplayed()
    {
        return canBeDisplayed;
    }

    public void setCanBeDisplayed(boolean canBeDisplayed)
    {
        this.canBeDisplayed = canBeDisplayed;
    }
}
