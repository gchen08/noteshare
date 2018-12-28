package test.cn.edu.gchen08.javaee.api.impl;

import cn.edu.sjtu.gchen08.javaee.api.UserService;
import cn.edu.sjtu.gchen08.javaee.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.InitialContext;
import java.util.Properties;

public class UserServiceImplTest
{
    private UserService userService;

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
        userService = (UserService) context.lookup("java:global/noteshare_ejb/UserServiceImpl!cn.edu.sjtu.gchen08.javaee.api.UserService");
    }

    @After
    public void after() throws Exception
    {
    }

    /**
     * Method: save(User user)
     */
    @Test
    public void testSave() throws Exception
    {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("test");
        user.setEmail("test@sjtu.edu.cn");
//        user.setLevel(Byte.valueOf("0"));
        user = userService.save(user);
        System.out.println("create user: " + user);
    }

    /**
     * Method: delete(User user)
     */
    @Test
    public void testDelete() throws Exception
    {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("test");
        user.setEmail("a@gmail.com");
        userService.save(user);

        System.out.println();

        user = userService.findByUsername("test");
        if (user != null)
        {
            System.out.println("delete user: " + user.getUsername());
            userService.delete(user);
        }
    }

    /**
     * Method: deleteById(Long userId)
     */
    @Test
    public void testDeleteById() throws Exception
    {
        String userName = "中文名";
        User user = userService.findByUsername(userName);
        userService.delete(user);
        if (userService.findByUsername(userName).getLevel() == Byte.valueOf("2"))
            System.out.println("delete user: " + userName);
        else
            System.out.println("delete failed");
    }

    /**
     * Method: update(User user)
     */
    @Test
    public void testUpdate() throws Exception
    {
        String userName = "中文名";
        User user = new User();
        user.setUsername(userName);
        user.setPassword("1234");
        user.setEmail("aaa@sjtu.edu.cn");
        userService.save(user);
        System.out.println("user saved: " + user);

        user = userService.findByUsername(userName);
        user.setEmail("bbb@sjtu.edu.cn");
        userService.update(user);

        System.out.println("user found: " + userService.findByUsername(userName));
    }

    /**
     * Method: findById(Long userId)
     */
    @Test
    public void testFindById() throws Exception
    {
        User user = userService.findById(Long.valueOf("100"));
        if (user != null)
            System.out.println(user);
        else
            System.out.println("Not found.");
    }

    /**
     * Method: findAll()
     */
    @Test
    public void testFindAll() throws Exception
    {
        System.out.println(userService.findAll());
    }

    /**
     * Method: findByUsername(String userName)
     */
    @Test
    public void testFindByUsername() throws Exception
    {
        String userName = "no_such_user";
        User user = userService.findByUsername(userName);
        if (user != null)
            System.out.println("found user: " + user);
        else
            System.out.print("user doesn't exist: " + userName);
    }

    /**
     *
     * Method: findByLevel(Byte level)
     *
     */
    @Test
    public void testFindByLevel() throws Exception {
        System.out.println(userService.findByLevel(Byte.valueOf("1")));
    }

} 
