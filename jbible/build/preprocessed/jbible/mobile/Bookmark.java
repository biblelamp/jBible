/*
 * Bookmark.java - Представление закладки
 *
 */

package jbible.mobile;

import java.io.*;
import java.util.*;
import jbible.utils.*;

/**
 * Представление закладки
 * @author Konstantin K. Beliak, <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Bookmark {
    
    /**
     * Список закладок
     */
    private static Vector list;
    
    static{
        list=Storage.readBookmarks();
        if(list==null) list=new Vector();
    }
    
    /**
     * Сохраняет закладки в настройках
     */
    public static void save(){
        Storage.saveBookmarks(list);
    }

    /**
     * Создаёт новую закладку
     * @param book Номер книги (должен быть больше 0)
     * @param chapter Номер главы (должен быть больше 0)
     * @param verse Номер стиха (должен быть больше 0)
     */
    public Bookmark(int book, int chapter, int verse) {
        this(book,chapter,verse,null);
    }
    
    /**
     * Создаёт новую закладку
     * @param book Номер книги (должен быть больше 0)
     * @param chapter Номер главы (должен быть больше 0)
     * @param verse Номер стиха (должен быть больше 0)
     * @param text Текст из стиха (может быть равен null)
     */
    public Bookmark(int book,int chapter,int verse,String text){
        this.book=book;
        this.chapter=chapter;
        this.verse=verse;
        this.text=text;
    }
    
    /**
     * Создаёт новую закладку, получая её из записи хранилища
     * @param data Массив данных длиной 12 байт (3 x 4 байта - числа типа int: книга, глава, стих)
     * @throws java.io.IOException Выбрасывается в случае ошибки
     */
    public Bookmark(byte[] data) throws IOException {
       ByteArrayInputStream bis=new ByteArrayInputStream(data);
       DataInputStream dis=new DataInputStream(bis);
       this.book=dis.readInt();
       this.chapter=dis.readInt();
       this.verse=dis.readInt();       
       dis.close();
    }

    /**
     * Книга
     */
    private int book;

    /**
     * Глава
     */
    private int chapter;

    /**
     * Стих
     */
    private int verse;
    
    /**
     * Возвращает книгу
     * @return Книга
     */
    public int getBook() {
        return book;
    }

    /**
     * Устанавливает номер книги
     * @param book Номер книги
     */
    public void setBook(int book) {
        this.book = book;
    }

    /**
     * Получает номер главы
     * @return Номер главы
     */
    public int getChapter() {
        return chapter;
    }

    /**
     * Устанавливает номер главы
     * @param chapter Номер главы
     */
    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    /**
     * Получает номер стиха
     * @return Номер стиха
     */
    public int getVerse() {
        return verse;
    }

    /**
     * Устанавливает номер стиха
     * @param verse Номер стиха
     */
    public void setVerse(int verse) {
        this.verse = verse;
    }
    
    /**
     * Преобразует закладку в массив байт
     * @return Массив длиной 12 байт (3 x 4 байта - числа типа int: книга, глава, стих)
     */
    public byte[] toByteArray(){
        ByteArrayOutputStream os=new ByteArrayOutputStream(12);
        try{
            DataOutputStream dos=new DataOutputStream(os);
            dos.writeInt(book);
            dos.writeInt(chapter);
            dos.writeInt(verse);
            return os.toByteArray();
        }catch(IOException e){
            return null;
        }finally{
            try{
                os.close();
            }catch(IOException e){}    
        }        
    }
    
    /**
     * Сравнивает закладки
     * @param another Закладка, сравниваемая с this
     * @return true если another=this и false в противном случае
     */
    public boolean equals(Object another){
        return book==(((Bookmark)another).getBook()) && chapter==(((Bookmark)another).getChapter()) && verse==(((Bookmark)another).getVerse());
    }
    
    /**
     * Преобразует закладку в строку
     * @return Строковое представление закладки
     */
    public String toString(){
        return "Book ("+book+") "+chapter+":"+verse+" "+text; 
    }
    
    /**
     * Возвращает число закладок
     * @return Число закладок
     */
    public static int getBookmarkCount(){
        return list.size();
    }
    
    /**
     * Возвращает закладку по её индексу
     * @param index Индекс закладки (от 0)
     * @return Закладка
     */
    public static Bookmark getBookmark(int index){
        return (Bookmark)list.elementAt(index);
    }
    
    /**
     * Добавляет закладку в список
     * @param book Книга
     * @param chapter Глава
     * @param verse Стих
     */
    public static void addBookmark(int book, int chapter, int verse){
        Bookmark newB=new Bookmark(book,chapter,verse);
        if(list.indexOf(newB)==-1) list.addElement(newB);
    }
    
    /**
     * Удаляет закладку
     * @param index Индекс закладки (от 0)
     */
    public static void removeBookmark(int index){
        list.removeElementAt(index);
    }
    
    /**
     * Удаляет все закладки из списка
     */
    public static void removeAllBookmarks(){
        list.removeAllElements();
    }

    /**
     * Текст из стиха
     */
    private String text;

    /**
     * Возвращает текст из стиха, ассоциированный с закладкой
     * @return Текст из стиха, ассоциированный с закладкой
     */
    public String getText() {
        return text;
    }

    /**
     * Устанавливает текст из стиха, ассоциированный с закладкой
     * @param text Текст из стиха, ассоциированный с закладкой
     */
    public void setText(String text) {
        this.text = text;
    }
}