package cn.edu.sjtu.gchen08.javaee.web.controller;

import cn.edu.sjtu.gchen08.javaee.api.NoteService;
import cn.edu.sjtu.gchen08.javaee.model.Note;
import cn.edu.sjtu.gchen08.javaee.web.common.Constant;
import cn.edu.sjtu.gchen08.javaee.web.common.Utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Gong on 2018/12/23
 * 笔记列表
 */

@ManagedBean(name = "noteListBean")
@ViewScoped
public class NoteListController implements Serializable
{
    private NoteService noteService;

    private List<Note> noteList;
    // 首页笔记列表
    private List<Note> topNoteList;

    private int totalRows;
    private int firstRow;
    private int rowsPerPage = Constant.NUM_OF_NOTES_PER_PAGE;
    private int totalPages = Constant.DEFAULT_PAGE;
    private int pageRange;
    private int[] pages;
    private int currentPage;

    public NoteListController()
    {
        try
        {
            InitialContext context = new InitialContext(); // 部署在同一个服务器上不需要设置properties
            noteService = (NoteService) context.lookup(Utils.getJndiName(NoteService.class));
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
    }

    private void loadTopList()
    {
        topNoteList = noteService.findByRangeAndState(0, Constant.NUM_OF_NOTES_IN_INDEX, Byte.valueOf("1"));
    }

    private void loadList()
    {
        noteList = noteService.findByRangeAndState(firstRow, rowsPerPage, Byte.valueOf("1"));
        totalRows = noteService.findAllByState(Byte.valueOf("1")).size();
        currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
        // 无笔记情况
        if (totalRows == 0)
            currentPage = 0;
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

    public List<Note> getTopNoteList()
    {
        if (topNoteList == null) loadTopList();
        return topNoteList;
    }

    public void setTopNoteList(List<Note> topNoteList)
    {
        this.topNoteList = topNoteList;
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
