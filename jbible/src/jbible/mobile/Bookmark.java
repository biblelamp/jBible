/*
 * Bookmark.java - ������������� ��������
 *
 */

package jbible.mobile;

import java.io.*;
import java.util.*;
import jbible.utils.*;

/**
 * ������������� ��������
 * @author Konstantin K. Beliak, <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Bookmark {
    
    /**
     * ������ ��������
     */
    private static Vector list;
    
    static{
        list=Storage.readBookmarks();
        if(list==null) list=new Vector();
    }
    
    /**
     * ��������� �������� � ����������
     */
    public static void save(){
        Storage.saveBookmarks(list);
    }

    /**
     * ������ ����� ��������
     * @param book ����� ����� (������ ���� ������ 0)
     * @param chapter ����� ����� (������ ���� ������ 0)
     * @param verse ����� ����� (������ ���� ������ 0)
     */
    public Bookmark(int book, int chapter, int verse) {
        this(book,chapter,verse,null);
    }
    
    /**
     * ������ ����� ��������
     * @param book ����� ����� (������ ���� ������ 0)
     * @param chapter ����� ����� (������ ���� ������ 0)
     * @param verse ����� ����� (������ ���� ������ 0)
     * @param text ����� �� ����� (����� ���� ����� null)
     */
    public Bookmark(int book,int chapter,int verse,String text){
        this.book=book;
        this.chapter=chapter;
        this.verse=verse;
        this.text=text;
    }
    
    /**
     * ������ ����� ��������, ������� � �� ������ ���������
     * @param data ������ ������ ������ 12 ���� (3 x 4 ����� - ����� ���� int: �����, �����, ����)
     * @throws java.io.IOException ������������� � ������ ������
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
     * �����
     */
    private int book;

    /**
     * �����
     */
    private int chapter;

    /**
     * ����
     */
    private int verse;
    
    /**
     * ���������� �����
     * @return �����
     */
    public int getBook() {
        return book;
    }

    /**
     * ������������� ����� �����
     * @param book ����� �����
     */
    public void setBook(int book) {
        this.book = book;
    }

    /**
     * �������� ����� �����
     * @return ����� �����
     */
    public int getChapter() {
        return chapter;
    }

    /**
     * ������������� ����� �����
     * @param chapter ����� �����
     */
    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    /**
     * �������� ����� �����
     * @return ����� �����
     */
    public int getVerse() {
        return verse;
    }

    /**
     * ������������� ����� �����
     * @param verse ����� �����
     */
    public void setVerse(int verse) {
        this.verse = verse;
    }
    
    /**
     * ����������� �������� � ������ ����
     * @return ������ ������ 12 ���� (3 x 4 ����� - ����� ���� int: �����, �����, ����)
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
     * ���������� ��������
     * @param another ��������, ������������ � this
     * @return true ���� another=this � false � ��������� ������
     */
    public boolean equals(Object another){
        return book==(((Bookmark)another).getBook()) && chapter==(((Bookmark)another).getChapter()) && verse==(((Bookmark)another).getVerse());
    }
    
    /**
     * ����������� �������� � ������
     * @return ��������� ������������� ��������
     */
    public String toString(){
        return "Book ("+book+") "+chapter+":"+verse+" "+text; 
    }
    
    /**
     * ���������� ����� ��������
     * @return ����� ��������
     */
    public static int getBookmarkCount(){
        return list.size();
    }
    
    /**
     * ���������� �������� �� � �������
     * @param index ������ �������� (�� 0)
     * @return ��������
     */
    public static Bookmark getBookmark(int index){
        return (Bookmark)list.elementAt(index);
    }
    
    /**
     * ��������� �������� � ������
     * @param book �����
     * @param chapter �����
     * @param verse ����
     */
    public static void addBookmark(int book, int chapter, int verse){
        Bookmark newB=new Bookmark(book,chapter,verse);
        if(list.indexOf(newB)==-1) list.addElement(newB);
    }
    
    /**
     * ������� ��������
     * @param index ������ �������� (�� 0)
     */
    public static void removeBookmark(int index){
        list.removeElementAt(index);
    }
    
    /**
     * ������� ��� �������� �� ������
     */
    public static void removeAllBookmarks(){
        list.removeAllElements();
    }

    /**
     * ����� �� �����
     */
    private String text;

    /**
     * ���������� ����� �� �����, ��������������� � ���������
     * @return ����� �� �����, ��������������� � ���������
     */
    public String getText() {
        return text;
    }

    /**
     * ������������� ����� �� �����, ��������������� � ���������
     * @param text ����� �� �����, ��������������� � ���������
     */
    public void setText(String text) {
        this.text = text;
    }
}