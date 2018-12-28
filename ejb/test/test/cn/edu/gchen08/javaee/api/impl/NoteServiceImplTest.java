package test.cn.edu.gchen08.javaee.api.impl;

import cn.edu.sjtu.gchen08.javaee.api.NoteService;
import cn.edu.sjtu.gchen08.javaee.api.UserService;
import cn.edu.sjtu.gchen08.javaee.model.Note;
import cn.edu.sjtu.gchen08.javaee.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.InitialContext;
import java.util.Date;
import java.util.Properties;

public class NoteServiceImplTest
{

    private NoteService noteService;
    private UserService userService;
    private User author;

    @Before
    public void before() throws Exception
    {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        properties.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        /*
            CORBRA 端口为3700
         */
        properties.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        /*
            不在同一个服务器上需要指定properties
         */
        InitialContext context = new InitialContext(properties);
        noteService = (NoteService) context.lookup("java:global/noteshare_ejb/NoteServiceImpl!cn.edu.sjtu.gchen08.javaee.api.NoteService");
        userService = (UserService) context.lookup("java:global/noteshare_ejb/UserServiceImpl!cn.edu.sjtu.gchen08.javaee.api.UserService");
        author = userService.findByUsername("testUser");
    }

    @After
    public void after() throws Exception
    {
    }

    /**
     * Method: save(Note note)
     */
    @Test
    public void testSave() throws Exception
    {
        Note note = new Note();
        note.setState(Byte.valueOf("0"));
        note.setTitle("testTitle");
        note.setContent("testContent");
        note.setPublishTime(new Date());
        note.setAuthor(author);
        note = noteService.save(note);
        System.out.println("note saved: " + note);
    }

    /**
     * Method: delete(Note note)
     */
    @Test
    public void testDelete() throws Exception
    {
        Note note = new Note();
        note.setState(Byte.valueOf("1"));
        note.setTitle("testDelete");
        note.setContent("testContent");
        note.setPublishTime(new Date());
        note.setAuthor(author);
        note = noteService.save(note);
        System.out.println("note saved: " + note);

        noteService.delete(note);
        note = noteService.findById(note.getId());
        if (note.getState().equals(Byte.valueOf("3")))
            System.out.println("note deleted: " + note);
    }

    /**
     * Method: deleteById(Long NoteId)
     */
    @Test
    public void testDeleteById() throws Exception
    {
        Note note = new Note();
        note.setState(Byte.valueOf("1"));
        note.setTitle("another testTitle");
        note.setContent("testContent");
        note.setPublishTime(new Date());
        note.setAuthor(author);
        note = noteService.save(note);
        System.out.println("note saved:" + note);

        noteService.deleteById(note.getId());
        note = noteService.findById(note.getId());
        if (note.getState().equals(Byte.valueOf("3")))
            System.out.println("note deleted: " + note);
    }

    /**
     * Method: update(Note note)
     */
    @Test
    public void testUpdate() throws Exception
    {
        Note note = new Note();
        note.setState(Byte.valueOf("0"));
        note.setTitle("another testTitle");
        note.setContent("testContent");
        note.setPublishTime(new Date());
        note.setAuthor(author);
        note = noteService.save(note);
        System.out.println("note saved:" + note);

        Long id = note.getId();
        note = noteService.findById(id);
        note.setTitle("new title");
        note = noteService.update(note);
        System.out.println("note altered: " + note);
    }

    /**
     * Method: findById(Long NoteId)
     */
    @Test
    public void testFindById() throws Exception
    {
        Long id = Long.valueOf("2");
        Note note = noteService.findById(id);
        if (note == null)
            System.out.println("Not found: note of id " + id + "");
        else
            System.out.println(note);
    }

    /**
     * Method: findAll()
     */
    @Test
    public void testFindAll() throws Exception
    {
        System.out.println(noteService.findAll());
    }

    @Test
    public void testFindAllByState() throws Exception
    {
        System.out.println(noteService.findAllByState(Byte.valueOf("1")));
    }

    /**
     * Method: findByRangeAndState(int startRow, int size, Byte state)
     */
    @Test
    public void testFindByRangeAndState() throws Exception
    {
//TODO: Test goes here...
    }

}
