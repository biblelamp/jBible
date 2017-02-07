/*
 * BookmarksScreen.java - Экран для отображения закладок
 *
 */

package jbible.mobile.ui;

import javax.microedition.lcdui.*;
import jbible.data.*;
import jbible.mobile.*;

/**
 * Экран для отображения закладок
 * @author Konstantin K. Beliak, <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class BookmarksScreen extends List implements CommandListener {
    
    /**
     * Список книг
     */
    private Book[] books;
    
    /**
     * Основной экран (отображения главы)
     */
    private ChapterScreen chapterScreen; 
    
    /**
     * Комманда удаления закладки
     */
    private Command cmdRemove=new Command(Common.getLocalizedString("bookmarks.remove","Remove"),Command.SCREEN,2);
    /**
     * Комманда удаления всех закладок
     */
    private Command cmdRemoveAll=new Command(Common.getLocalizedString("bookmarks.removeall","Remove all"),Command.SCREEN,2);
    /**
     * Команда Отмена(Назад)
     */
    private Command cmdBack=new Command(Common.getLocalizedString("bookmarks.back","Back"),Command.BACK,2);

    /**
     * Интерфейс мидлета
     */
    private MidletInterface midlet;
            
    /**
     * Создаёт отображаемый список закладок
     * @param src Источник текстов
     * @param chapterScreen Основной экран (отображения главы)
     * @param midlet Интерфейс мидлета
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
     * Обновляет интерфейс у учётом изменившихся настроек (локаль)
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
     * Обработчик событий
     * @param command Комманда
     * @param displayable Объект - источник события
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
     * Создаёт меню
     */
    private void createMenu(){
        addCommand(cmdRemove);
        addCommand(cmdRemoveAll);
    }
    
    /**
     * Очищает меню
     */
    private void clearMenu(){
        removeCommand(cmdRemove);
        removeCommand(cmdRemoveAll);
    }
}
