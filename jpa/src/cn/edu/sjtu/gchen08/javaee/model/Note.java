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
import java.util.Objects;

/**
 * Created by Gong on 2018/12/21
 * 笔记实体类
 */

@Entity
@Table(name = "t_note")
public class Note implements Serializable
{
    /*
        PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        笔记标题
     */
    private String title;

    /*
        笔记内容
     */
    @Lob
    private String content;

    /*
        发表时间 时间戳
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishTime;

    /*
        笔记状态
        0: 草稿
        1: 公开
     */
    private Byte state = 0;

    /*
        阅读数
     */
    private long viewCount = 0;

    /*
        作者
     */
    @ManyToOne
    private User author;

    /*
        格式化的发表日期
     */
    @Transient
    private String formattedPublishTime;

    /*
        摘要（首页） 不超过400字
     */
    @Transient
    private String longSummary;

    /*
        摘要（列表） 不超过100字
     */
    @Transient
    private String shortSummary;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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

    public Byte getState()
    {
        return state;
    }

    public void setState(Byte state)
    {
        this.state = state;
    }

    public long getViewCount()
    {
        return viewCount;
    }

    public void setViewCount(long vieCount)
    {
        this.viewCount = vieCount;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
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

    public String getLongSummary()
    {
        if (content.length() > 400)
            return content.substring(0, 400) + "...";
        else return content;
    }

    public void setLongSummary(String longSummary)
    {
        this.longSummary = longSummary;
    }

    public String getShortSummary()
    {
        if (content.length() > 100)
            return content.substring(0, 100) + "...";
        else return content;
    }

    public void setShortSummary(String shortSummary)
    {
        this.shortSummary = shortSummary;
    }

    @Override
    public String toString()
    {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishTime=" + publishTime +
                ", state=" + state +
                ", viewCount=" + viewCount +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return viewCount == note.viewCount &&
                Objects.equals(id, note.id) &&
                Objects.equals(title, note.title) &&
                Objects.equals(content, note.content) &&
                Objects.equals(publishTime, note.publishTime) &&
                Objects.equals(author, note.author);
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(id);
    }
}
