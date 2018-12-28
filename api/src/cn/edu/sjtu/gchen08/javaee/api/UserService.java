package cn.edu.sjtu.gchen08.javaee.api;

import cn.edu.sjtu.gchen08.javaee.model.User;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Gong on 2018/12/21
 * 用户 远程接口
 */

@Remote
public interface UserService
{
    User save(User user);

    void delete(User user);

    void deleteById(Long userId);

    User update(User user);

    User findById(Long userId);

    List<User> findAll();

    User findByUsername(String userName);

    List<User> findByLevel(Byte level);
}
