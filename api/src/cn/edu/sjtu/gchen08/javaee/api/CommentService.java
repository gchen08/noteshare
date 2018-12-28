package cn.edu.sjtu.gchen08.javaee.api;

import cn.edu.sjtu.gchen08.javaee.model.Comment;
import cn.edu.sjtu.gchen08.javaee.model.Note;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Gong on 2018/12/21
 * 评论 远程借口
 */

@Remote
public interface CommentService
{
    Comment save(Comment comment);

    void delete(Comment comment);

    void deleteById(Long commentId);

    Comment update(Comment comment);

    Comment findById(Long commentId);

    List<Comment> findAll();

    List<Comment> findByNote(Note note);

    List<Comment> findByNoteId(Long noteId);
}
