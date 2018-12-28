package cn.edu.sjtu.gchen08.javaee.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gong on 2018/12/21
 * 评论实体类
 */

@Entity
@Table(name = "t_comment")
public class Comment implements Serializable
{
    /*
        PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /*
        评论内容
     */
    @Lob
    private String content;

    /*
        评论时间 时间戳
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishTime;

    /*
        对应作者
     */
    @ManyToOne
    private User author;

    /*
        对应笔记
     */
    @ManyToOne
    private Note note;

    /*
        格式化的发表日期
    */
    @Transient
    private String formattedPublishTime;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }

    public Note getNote()
    {
        return note;
    }

    public void setNote(Note note)
    {
        this.note = note;
    }

    public String getFormattedPublishTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(publishTime);
    }

    public void setFormattedPublishTime(String formattedPublishTime)
    {
        this.formattedPublishTime = formattedPublishTime;
    }

    @Override
    public String toString()
    {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", publishTime=" + publishTime +
                ", author=" + author.getUsername() +
                ", note=" + note.getTitle() +
                '}';
    }
}
