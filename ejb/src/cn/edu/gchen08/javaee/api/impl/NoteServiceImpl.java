package cn.edu.gchen08.javaee.api.impl;

import cn.edu.sjtu.gchen08.javaee.api.NoteService;
import cn.edu.sjtu.gchen08.javaee.model.Note;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Gong on 2018/12/21
 * 笔记 无状态会话Bean
 */

@Stateless
public class NoteServiceImpl implements NoteService
{
    @PersistenceContext(unitName = "noteShareUnit")
    private EntityManager entityManager;

    @Override
    public Note save(Note note)
    {
        entityManager.persist(note);
        return note;
    }

    @Override
    public void delete(Note note)
    {
        if (!entityManager.contains(note))
            note = entityManager.merge(note);
        entityManager.remove(note);
    }

    @Override
    public void deleteById(Long noteId)
    {
        Note note = entityManager.find(Note.class, noteId);
        delete(note);
    }

    @Override
    public Note update(Note note)
    {
        entityManager.merge(note);
        return note;
    }

    @Override
    public Note findById(Long noteId)
    {
        return entityManager.find(Note.class, noteId);
    }

    @Override
    public List<Note> findAll()
    {
        return entityManager.createQuery("SELECT o FROM Note AS o", Note.class).getResultList();
    }

    @Override
    public List<Note> findAllByState(Byte state)
    {
        TypedQuery<Note> query = entityManager.createQuery("SELECT o FROM Note AS o WHERE o.state = :state ORDER BY o.publishTime DESC ", Note.class);
        query.setParameter("state", state);
        return query.getResultList();
    }

    @Override
    public List<Note> findByRangeAndState(int startRow, int size, Byte state)
    {
        TypedQuery<Note> query = entityManager.createQuery("SELECT o FROM Note As o WHERE o.state = :state ORDER BY o.publishTime DESC", Note.class);
        query.setParameter("state", state);
        query.setFirstResult(startRow);
        query.setMaxResults(size);
        return query.getResultList();
    }

}
