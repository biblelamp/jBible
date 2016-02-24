/**
 * Book.java - Описание представления книги как редактируемого списка глав
 */
package jbible.data;

import java.util.*;

/**
 * Представление книги в виде редактируемого набора глав
 * @author Беляк Константин, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Book {

    /**
     * Номер книги
     */
    private int num;
	
    /**
     * Название книги
     */
    private String name;
    
    /**
     * Главы книги
     */
    private Vector chapters=new Vector();

    /**
     * Инициализирует пустую книгу, связывая её с номером и именем
     * @param num Номер книги (должен быть больше 0)
     * @param name Название книги
     */
    public Book(int num,String name){
    	this.num=num;
    	this.name=name;
    }

    /**
     * Возвращает номер книги
     * @return Номер книги
     */
    public int getNum(){
    	return num;
    }
    
    /**
     * Возвращает название книги
     * @return Название книги
     */
    public String getName(){
    	return name;
    }
    
    /**
     * Возвращает число глав в книге
     * @return Число глав в книге
     */
    public int getChaptersCount(){
        return chapters.size();
    }
    
    /**
     * Возвращает главу
     * @param num Номер главы (должен быть больше 0)
     * @return Глава
     */
    public Chapter getChapter(int num){
        if(num>0 && num<=chapters.size()){
            return (Chapter)chapters.elementAt(num-1);
        }else return null;
    }
    
    /**
     * Создаёт новую главу или возвращает существующую
     * @param num Номер главы (должен быть больше 0)
     * @return Глава
     */
    public Chapter createChapter(int num){
        if(num>0){
            if(num>chapters.size()){
                chapters.setSize(num);                
            }
            if(chapters.elementAt(num-1)==null) chapters.setElementAt(new Chapter(this,num),num-1);
            return (Chapter)chapters.elementAt(num-1);                 
        }else throw new IllegalArgumentException("Bad chapter index: book "+this.num+", chapter "+num);
    }
}
