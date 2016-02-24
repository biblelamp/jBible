/**
 * Chapter.java - Описание представления главы как редактируемого набора стихов
 */
package jbible.data;

import java.io.*;
import java.util.*;

/**
 * Представление главы в виде редактируемого набора стихов
 * @author Беляк Константин, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Chapter {
	
    /**
     * Книга, в состав которой входит глава
     */
    private Book book;
	
    /**
     * Номер главы
     */
    private int chapter;
    
    /**
     * Стихи
     */
    private Vector verses=new Vector();
    
    /**
     * Инициализирует представление главы 
     * @param book Книга, в состав которой входит глава
     * @param num Номер главы (должен быть больше 0)
     */
    public Chapter(Book book, int num) {
        this.book=book;
        this.chapter=num;        
    }
    
    /**
     * Возвращает число стихов в главе
     * @return Число стихов в главе
     */
    public int getVerseCount(){
        return verses.size();
    }
    
    /**
     * Возвращает текст стиха
     * @param num Номер стиха (должен быть больше 0)
     * @return Текст стиха
     */
    public String getVerse(int num){
        if(num>0 && num<=verses.size()){
            return (String)verses.elementAt(num-1);
        }else return null;        
    }
    
    /**
     * Удаляет все стихи
     */
    public void clear(){
        verses.removeAllElements();
    }
    
    /**
     * Возвращает номер главы
     * @return Номер главы
     */
    public int getNum(){
        return chapter;
    }
    
    /**
     * Создаёт стих
     * @param num Номер стиха (должен быть больше 0)
     * @param verse Текст стиха
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
     * Задаёт число стихов в главе
     * @param count Число стихов
     */
    public void setVerseCount(int count){
        if(count>=0){
            verses.setSize(count);
        }else throw new IllegalArgumentException("Bad verse count");
    }

}
