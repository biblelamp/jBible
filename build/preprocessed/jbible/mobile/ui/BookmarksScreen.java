/*
 * BookmarksScreen.java - ����� ��� ����������� ��������
 *
 */

package jbible.mobile.ui;

import javax.microedition.lcdui.*;
import jbible.data.*;
import jbible.mobile.*;

/**
 * ����� ��� ����������� ��������
 * @author Konstantin K. Beliak, <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class BookmarksScreen extends List implements CommandListener {
    
    /**
     * ������ ����
     */
    private Book[] books;
    
    /**
     * �������� ����� (����������� �����)
     */
    private ChapterScreen chapterScreen; 
    
    /**
     * �������� �������� ��������
     */
    private Command cmdRemove=new Command(Common.getLocalizedString("bookmarks.remove","Remove"),Command.SCREEN,2);
    /**
     * �������� �������� ���� ��������
     */
    private Command cmdRemoveAll=new Command(Common.getLocalizedString("bookmarks.removeall","Remove all"),Command.SCREEN,2);
    /**
     * ������� ������(�����)
     */
    private Command cmdBack=new Command(Common.getLocalizedString("bookmarks.back","Back"),Command.BACK,2);

    /**
     * ��������� �������
     */
    private MidletInterface midlet;
            
    /**
     * ������ ������������ ������ ��������
     * @param src �������� �������
     * @param chapterScreen �������� ����� (����������� �����)
     * @param midlet ��������� �������
     */
    public BookmarksScreen(TextSource src,ChapterScreen chapterScreen,MidletInterface midlet) {
        super(Common.getLocalizedString("bookmarks.screen","Bookmarks"),Choice.IMPLICIT);
        this.midlet=midlet;
        books=src.getBibleContents();
        this.chapterScreen=chapterScreen;
        setCommandListener(this);
        addCommand(cmdBack);
        update();
    }
    
    /**
     * ��������� ��������� � ������ ������������ �������� (������)
     */
    public void update(){
        deleteAll();
        for(int i=0;i<Bookmark.getBookmarkCount();i++){
            Bookmark b=Bookmark.getBookmark(i);
            append(books[b.getBook()-1].getName()+" "+b.getChapter()+":"+b.getVerse(),null);
        }
        if(this.getSelectedIndex()!=-1) createMenu(); else clearMenu();
    }

    /**
     * ���������� �������
     * @param command ��������
     * @param displayable ������ - �������� �������
     */
    public void commandAction(Command command, Displayable displayable) {
        if(command==List.SELECT_COMMAND){
            int index=getSelectedIndex();
            if(index!=-1){
                Bookmark selected=Bookmark.getBookmark(index);
                midlet.show(chapterScreen);
                chapterScreen.load(selected);
            }
        }
        if(command==cmdBack){
            clearMenu();
            midlet.show(chapterScreen);
        }
        if(command==cmdRemove){
            int index=getSelectedIndex();
            if(index!=-1){
                Bookmark.removeBookmark(index);
                update();
            }
        }
        if(command==cmdRemoveAll){
            Bookmark.removeAllBookmarks();
            update();
        }        
    }
    
    /**
     * ������ ����
     */
    private void createMenu(){
        addCommand(cmdRemove);
        addCommand(cmdRemoveAll);
    }
    
    /**
     * ������� ����
     */
    private void clearMenu(){
        removeCommand(cmdRemove);
        removeCommand(cmdRemoveAll);
    }
}
