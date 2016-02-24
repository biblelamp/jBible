/**
 * Book.java - �������� ������������� ����� ��� �������������� ������ ����
 */
package jbible.data;

import java.util.*;

/**
 * ������������� ����� � ���� �������������� ������ ����
 * @author ����� ����������, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Book {

    /**
     * ����� �����
     */
    private int num;
	
    /**
     * �������� �����
     */
    private String name;
    
    /**
     * ����� �����
     */
    private Vector chapters=new Vector();

    /**
     * �������������� ������ �����, �������� � � ������� � ������
     * @param num ����� ����� (������ ���� ������ 0)
     * @param name �������� �����
     */
    public Book(int num,String name){
    	this.num=num;
    	this.name=name;
    }

    /**
     * ���������� ����� �����
     * @return ����� �����
     */
    public int getNum(){
    	return num;
    }
    
    /**
     * ���������� �������� �����
     * @return �������� �����
     */
    public String getName(){
    	return name;
    }
    
    /**
     * ���������� ����� ���� � �����
     * @return ����� ���� � �����
     */
    public int getChaptersCount(){
        return chapters.size();
    }
    
    /**
     * ���������� �����
     * @param num ����� ����� (������ ���� ������ 0)
     * @return �����
     */
    public Chapter getChapter(int num){
        if(num>0 && num<=chapters.size()){
            return (Chapter)chapters.elementAt(num-1);
        }else return null;
    }
    
    /**
     * ������ ����� ����� ��� ���������� ������������
     * @param num ����� ����� (������ ���� ������ 0)
     * @return �����
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
