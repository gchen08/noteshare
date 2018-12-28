package cn.edu.sjtu.gchen08.javaee.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Gong on 2018/12/21
 * 用户实体类
 */

@Entity
@Table(name = "t_user")
public class User implements Serializable
{
    /*
        PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        用户名 要求唯一
     */
    @Column(unique = true)
    private String username;

    /*
        密码 采用SHA256加密
     */
    private String password;

    /*
        电子邮箱
     */
    private String email;

    /*
        用户级别
        0: 管理员
        1: 普通用户
        2: 注销用户
     */
    private byte level = 1;

    /*
        发表的笔记
        以author为外键
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Note> notes = new ArrayList<>();

    /*
        发表的评论
        以author为外键
     */
    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();


    public void addNote(Note note)
    {
        if (!this.notes.contains(note))
            this.notes.add(note);
    }

    public void deleteNote(Note note)
    {
        this.notes.remove(note);
    }


    public void addComment(Comment comment)
    {
        if (!this.comments.contains(comment))
            this.comments.add(comment);
    }

    public void deleteComment(Comment comment)
    {
        this.comments.remove(comment);
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public byte getLevel()
    {
        return level;
    }

    public void setLevel(byte level)
    {
        this.level = level;
    }

    public List<Note> getNotes()
    {
        return notes;
    }

    public void setNotes(List<Note> notes)
    {
        this.notes = notes;
    }

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", level=" + level +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, username);
    }
}