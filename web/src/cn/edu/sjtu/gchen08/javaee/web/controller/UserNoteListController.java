package cn.edu.sjtu.gchen08.javaee.web.controller;

import cn.edu.sjtu.gchen08.javaee.api.UserService;
import cn.edu.sjtu.gchen08.javaee.model.Note;
import cn.edu.sjtu.gchen08.javaee.model.User;
import cn.edu.sjtu.gchen08.javaee.web.common.Constant;
import cn.edu.sjtu.gchen08.javaee.web.common.Utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Gong on 2018/12/25
 * 用户笔记服务类
 */

@ManagedBean(name = "userNoteListBean")
@ViewScoped
public class UserNoteListController implements Serializable
{
    private UserService userService;
    private User user;
    private List<Note> noteList;

    private int totalRows;
    private int firstRow;
    private int rowsPerPage = Constant.NUM_OF_NOTES_PER_PAGE;
    private int totalPages;
    private int pageRange = Constant.DEFAULT_PAGE;
    private int[] pages;
    private int currentPage;

    public UserNoteListController()
    {
        try
        {
            InitialContext context = new InitialContext(); // 部署在同一个服务器上不需要设置properties
            userService = (UserService) context.lookup(Utils.getJndiName(UserService.class));
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
        user = (User) Utils.getSession().getAttribute(Constant.CURRENT_USER);
    }

    private void loadList()
    {
        List<Note> allNotes = new ArrayList<>(user.getNotes());
        allNotes.sort(Comparator.comparing(Note::getPublishTime).reversed());
        totalRows = allNotes.size();
        noteList = allNotes.subList(firstRow, Math.min(totalRows, firstRow + rowsPerPage));
        currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
        totalPages = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0 ? 1 : 0));
        int pagesLength = Math.max(pageRange, totalPages);
        pages = new int[pagesLength];
        int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages);
        for (int i = 0; i < pagesLength; i++)
            pages[i] = ++firstPage;
    }

    private void page(int firstRow)
    {
        this.firstRow = firstRow;
        loadList();
    }

    public void pageFirst()
    {
        page(0);
    }

    public void pageNext()
    {
        page(firstRow + rowsPerPage);
    }

    public void pagePrevious()
    {
        page(firstRow - rowsPerPage);
    }

    public void pageLast()
    {
        page(totalRows - (totalRows % rowsPerPage != 0 ? totalRows % rowsPerPage : rowsPerPage));
    }

    public List<Note> getNoteList()
    {
        if (noteList == null) loadList();
        return noteList;
    }

    public void setNoteList(List<Note> noteList)
    {
        this.noteList = noteList;
    }

    public int getTotalRows()
    {
        return totalRows;
    }

    public void setTotalRows(int totalRows)
    {
        this.totalRows = totalRows;
    }

    public int getFirstRow()
    {
        return firstRow;
    }

    public void setFirstRow(int firstRow)
    {
        this.firstRow = firstRow;
    }

    public int getRowsPerPage()
    {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage)
    {
        this.rowsPerPage = rowsPerPage;
    }

    public int getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }

    public int getPageRange()
    {
        return pageRange;
    }

    public void setPageRange(int pageRange)
    {
        this.pageRange = pageRange;
    }

    public int[] getPages()
    {
        return pages;
    }

    public void setPages(int[] pages)
    {
        this.pages = pages;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

}
