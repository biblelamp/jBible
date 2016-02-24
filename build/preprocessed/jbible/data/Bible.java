/**
 * Bible.java - �������� ������������� ������ ��� �������������� ������ ����
 */
package jbible.data;

import java.io.*;
import java.util.*;

/**
 * ������������� ������ � ���� �������������� ������ ����
 * @author ����� ����������, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Bible {
 
    /**
     * ����� �� �������
     */
    private Vector books=new Vector();
    
    /*
     * ������ ����� ��������� ������, � ������ ������� ����
     */
    /*public Bible() {
        
    }
    */
    
    /*
     * ������ ����� ��������� ������, ����� �������� ���� � ����� ���� � ������ 
     * �� ��� �� �������� ������. 
     * <p>������ � ������ ������ ���� ������������� �
     * <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/io/DataInput.html#modified-utf-8">
     * ����������������</a> ��������� UTF-8.
     * <p>C����� ���������� �������� \n. ������ ������ ����� ���:
     * <p>[����� �����]\t[�������� �����]\t[����� ����] 
     * @param inputStream ����� � �������� �������
     * @throws java.io.IOException ������������ � ������ ������ �����-������
     */
    /*public Bible(InputStream inputStream) throws IOException {
        DataInputStream dis=new DataInputStream(inputStream);
        int lineNum=0;
        while(dis.available()>0){
            String book=dis.readUTF();
            int firstTab=book.indexOf('\t');
            int lastTab=book.lastIndexOf('\t');
            if(firstTab==lastTab) throw new IOException("�������� ������ ������ ���� (�������� ������ ������), ������ "+lineNum);
            String numStr=book.substring(0,firstTab).trim();
            String chapCountStr=book.substring(lastTab).trim();
            String text=book.substring(firstTab+1,lastTab).trim();
            Integer num=new Integer(0);
            Integer chapCount=new Integer(0);
            try{
            	num=new Integer(Integer.parseInt(numStr));
            	chapCount=new Integer(Integer.parseInt(chapCountStr));
            }catch(NumberFormatException e){
            	throw new IOException("�������� ������ ������ ���� (�������� �������� ������), ������ "+lineNum);
            }
            if(num.intValue()<=0) throw new IOException("�������� ������ ������ ���� (�������� ����� �����), ������ "+lineNum);
            if(chapCount.intValue()<=0) throw new IOException("�������� ������ ������ ���� (�������� ����� ����), ������ "+lineNum);
            if(books.get(num)!=null) throw new IOException("�������� ������ ������ ���� (����� ����� ��� ������������), ������ "+lineNum);
            books.put(num,new Book(num,text,chapCount.intValue()));
            lineNum++;
        }
    }
    */
    
    /**
     * ������ ������������� ������������� ��������� ������, ������� ������ ���� �� ���������� ���������
     * @param src �������� ��� ��������� ���������� ������
     */
    public Bible(TextSource src) {
        Book[] list=src.getBibleContents();
        for(int i=0;i<list.length;i++){
            books.addElement(list[i]);
        }
    }
    
    /**
     * ���������� ����� ����
     * @return ���������� ����� ����
     */
    public int bookCount(){
        return books.size();
    }
    
    /**
     * ���������� ����� �� � ������
     * @param index ����� ����� (������ ���� ������ 0)
     * @return �����, ��� null ���� ����� �� ����������
     */
    public Book getBook(int index){
        if(index>0 && index<=books.size()){
            return (Book)books.elementAt(index-1);
        }else return null;
    }
    
}
