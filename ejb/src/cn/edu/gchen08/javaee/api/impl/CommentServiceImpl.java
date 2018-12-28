package cn.edu.gchen08.javaee.api.impl;

import cn.edu.sjtu.gchen08.javaee.api.CommentService;
import cn.edu.sjtu.gchen08.javaee.model.Comment;
import cn.edu.sjtu.gchen08.javaee.model.Note;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Gong on 2018/12/21
 * 评论 无状态会话Bean
 */

@Stateless
public class CommentServiceImpl implements CommentService
{
    @PersistenceContext(unitName = "noteShareUnit")
    private EntityManager entityManager;

    @Override
    public Comment save(Comment comment)
    {
        entityManager.persist(comment);
        return comment;
    }

    @Override
    public void delete(Comment comment)
    {
        entityManager.remove(comment);
    }

    @Override
    public void deleteById(Long commentId)
    {
        Comment comment = entityManager.find(Comment.class, commentId);
        delete(comment);
    }

    @Override
    public Comment update(Comment comment)
    {
        entityManager.merge(comment);
        return comment;
    }

    @Override
    public Comment findById(Long commentId)
    {
        return entityManager.find(Comment.class, commentId);
    }

    @Override
    public List<Comment> findAll()
    {
        return entityManager.createQuery("SELECT o FROM Comment AS o", Comment.class).getResultList();
    }

    @Override
    public List<Comment> findByNote(Note note)
    {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT o FROM Comment as o WHERE o.note = :note ORDER BY o.publishTime DESC", Comment.class);
        query.setParameter("note", note);
        return query.getResultList();
    }

    @Override
    public List<Comment> findByNoteId(Long noteId)
    {
        Note note = entityManager.find(Note.class, noteId);
        return findByNote(note);
    }
}
