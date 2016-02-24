/**
 * Chapter.java - �������� ������������� ����� ��� �������������� ������ ������
 */
package jbible.data;

import java.io.*;
import java.util.*;

/**
 * ������������� ����� � ���� �������������� ������ ������
 * @author ����� ����������, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Chapter {
	
    /**
     * �����, � ������ ������� ������ �����
     */
    private Book book;
	
    /**
     * ����� �����
     */
    private int chapter;
    
    /**
     * �����
     */
    private Vector verses=new Vector();
    
    /**
     * �������������� ������������� ����� 
     * @param book �����, � ������ ������� ������ �����
     * @param num ����� ����� (������ ���� ������ 0)
     */
    public Chapter(Book book, int num) {
        this.book=book;
        this.chapter=num;        
    }
    
    /**
     * ���������� ����� ������ � �����
     * @return ����� ������ � �����
     */
    public int getVerseCount(){
        return verses.size();
    }
    
    /**
     * ���������� ����� �����
     * @param num ����� ����� (������ ���� ������ 0)
     * @return ����� �����
     */
    public String getVerse(int num){
        if(num>0 && num<=verses.size()){
            return (String)verses.elementAt(num-1);
        }else return null;        
    }
    
    /**
     * ������� ��� �����
     */
    public void clear(){
        verses.removeAllElements();
    }
    
    /**
     * ���������� ����� �����
     * @return ����� �����
     */
    public int getNum(){
        return chapter;
    }
    
    /**
     * ������ ����
     * @param num ����� ����� (������ ���� ������ 0)
     * @param verse ����� �����
     */
    public void createVerse(int num, String verse){
        if(num>0){
            if(num>verses.size()){
                verses.setSize(num);
            }else if(verses.elementAt(num-1)!=null) throw new IllegalArgumentException("Verse index is already used: book "+book.getNum()+" ("+book.getName()+"), chapter "+chapter+", verse "+num+"\nOld text \""+verses.elementAt(num-1)+"\"\nNew text \""+verse+"\"");
            verses.setElementAt(verse,num-1);                 
        }else throw new IllegalArgumentException("Bad verse number: book "+book.getNum()+", chapter "+chapter+", verse "+num);
    }
    
    
    /**
     * ����� ����� ������ � �����
     * @param count ����� ������
     */
    public void setVerseCount(int count){
        if(count>=0){
            verses.setSize(count);
        }else throw new IllegalArgumentException("Bad verse count");
    }

}
