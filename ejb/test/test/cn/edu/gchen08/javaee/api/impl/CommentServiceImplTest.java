package test.cn.edu.gchen08.javaee.api.impl;

import cn.edu.sjtu.gchen08.javaee.api.CommentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.InitialContext;
import java.util.Properties;

public class CommentServiceImplTest
{

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
        CommentService commentService = (CommentService) context.lookup("java:global/noteshare_ejb/CommentServiceImpl!cn.edu.sjtu.gchen08.javaee.api.CommentService");
    }

    @After
    public void after() throws Exception
    {
    }

    /**
     * Method: save(Comment comment)
     */
    @Test
    public void testSave() throws Exception
    {
//TODO: Test goes here... 
    }

    /**
     * Method: delete(Comment comment)
     */
    @Test
    public void testDelete() throws Exception
    {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteById(Long commentId)
     */
    @Test
    public void testDeleteById() throws Exception
    {
//TODO: Test goes here... 
    }

    /**
     * Method: update(Comment comment)
     */
    @Test
    public void testUpdate() throws Exception
    {
//TODO: Test goes here... 
    }

    /**
     * Method: findById(Long commmentId)
     */
    @Test
    public void testFindById() throws Exception
    {
//TODO: Test goes here... 
    }

    /**
     * Method: findAll()
     */
    @Test
    public void testFindAll() throws Exception
    {
//TODO: Test goes here... 
    }


} 
