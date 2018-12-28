package cn.edu.gchen08.javaee.api.impl;

import cn.edu.sjtu.gchen08.javaee.api.UserService;
import cn.edu.sjtu.gchen08.javaee.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Gong on 2018/12/21
 * 用户 无状态会话Bean
 */

@Stateless
public class UserServiceImpl implements UserService
{
    @PersistenceContext(unitName = "noteShareUnit")
    private EntityManager entityManager;

    @Override
    public User save(User user)
    {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void delete(User user)
    {
        user.setLevel(Byte.valueOf("2"));
        entityManager.merge(user);
    }

    @Override
    public void deleteById(Long userId)
    {
        User user = entityManager.find(User.class, userId);
        delete(user);
    }

    @Override
    public User update(User user)
    {
        entityManager.merge(user);
        return user;
    }

    @Override
    public User findById(Long userId)
    {
        return entityManager.find(User.class, userId);
    }

    @Override
    public List<User> findAll()
    {
        return entityManager.createQuery("SELECT o FROM User AS o", User.class).getResultList();
    }

    @Override
    public User findByUsername(String userName)
    {
        TypedQuery<User> query = entityManager.createQuery("SELECT o FROM User AS o WHERE o.username = :username", User.class);
        query.setParameter("username", userName);
        List<User> resultList = query.getResultList();
        if (resultList.size() == 0) return null;
        else return resultList.get(0);
    }

    @Override
    public List<User> findByLevel(Byte level)
    {
        TypedQuery<User> query = entityManager.createQuery("SELECT o FROM User AS o WHERE o.level = :level", User.class);
        query.setParameter("level", level);
        return query.getResultList();
    }

}
