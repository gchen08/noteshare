package cn.edu.sjtu.gchen08.javaee.api;

import cn.edu.sjtu.gchen08.javaee.model.Note;
import cn.edu.sjtu.gchen08.javaee.model.User;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Gong on 2018/12/21
 * 笔记 远程接口
 */

@Remote
public interface NoteService
{
    Note save(Note note);

    void delete(Note note);

    void deleteById(Long noteId);

    Note update(Note note);

    Note findById(Long noteId);

    List<Note> findAll();

    List<Note> findAllByState(Byte state);

    List<Note> findByRangeAndState(int startRow, int size, Byte state);
}
