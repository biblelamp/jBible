/**
 * Bible.java - Описание представления Библии как редактируемого списка книг
 */
package jbible.data;

import java.io.*;
import java.util.*;

/**
 * Представление Библии в виде редактируемого набора книг
 * @author Беляк Константин, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Bible {
 
    /**
     * Книги по номерам
     */
    private Vector books=new Vector();
    
    /*
     * Создаёт новый экземпляр Библии, с пустым набором книг
     */
    /*public Bible() {
        
    }
    */
    
    /*
     * Создаёт новый экземпляр Библии, читая названия книг и число глав в каждой 
     * из них из входного потока. 
     * <p>Данные в потоке должны быть представленны в
     * <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/io/DataInput.html#modified-utf-8">
     * модифицированной</a> кодировке UTF-8.
     * <p>Cтроки отделяются символом \n. Каждая строка имеет вид:
     * <p>[Номер книги]\t[Название книги]\t[Число глав] 
     * @param inputStream Поток с входными данными
     * @throws java.io.IOException Выбрасыватся в случае ошибки ввода-вывода
     */
    /*public Bible(InputStream inputStream) throws IOException {
        DataInputStream dis=new DataInputStream(inputStream);
        int lineNum=0;
        while(dis.available()>0){
            String book=dis.readUTF();
            int firstTab=book.indexOf('\t');
            int lastTab=book.lastIndexOf('\t');
            if(firstTab==lastTab) throw new IOException("Неверный формат списка книг (неверный формат строки), строка "+lineNum);
            String numStr=book.substring(0,firstTab).trim();
            String chapCountStr=book.substring(lastTab).trim();
            String text=book.substring(firstTab+1,lastTab).trim();
            Integer num=new Integer(0);
            Integer chapCount=new Integer(0);
            try{
            	num=new Integer(Integer.parseInt(numStr));
            	chapCount=new Integer(Integer.parseInt(chapCountStr));
            }catch(NumberFormatException e){
            	throw new IOException("Неверный формат списка книг (неверный числовой формат), строка "+lineNum);
            }
            if(num.intValue()<=0) throw new IOException("Неверный формат списка книг (неверный номер книги), строка "+lineNum);
            if(chapCount.intValue()<=0) throw new IOException("Неверный формат списка книг (неверное число глав), строка "+lineNum);
            if(books.get(num)!=null) throw new IOException("Неверный формат списка книг (номер книги уже используется), строка "+lineNum);
            books.put(num,new Book(num,text,chapCount.intValue()));
            lineNum++;
        }
    }
    */
    
    /**
     * Создаёт редактируемое представление структуры Библии, получая список глав из указанного источника
     * @param src Источник для получения содержания Библии
     */
    public Bible(TextSource src) {
        Book[] list=src.getBibleContents();
        for(int i=0;i<list.length;i++){
            books.addElement(list[i]);
        }
    }
    
    /**
     * Возвращает число книг
     * @return Возвращает число книг
     */
    public int bookCount(){
        return books.size();
    }
    
    /**
     * Возвращает книгу по её номеру
     * @param index Номер книги (должен быть больше 0)
     * @return Книга, или null если книга не существует
     */
    public Book getBook(int index){
        if(index>0 && index<=books.size()){
            return (Book)books.elementAt(index-1);
        }else return null;
    }
    
}
