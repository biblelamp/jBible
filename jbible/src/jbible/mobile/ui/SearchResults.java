/*
 * SearchResults.java - ��������� ����������� ������
 */

package jbible.mobile.ui;

import java.util.*;
import javax.microedition.lcdui.*;
import jbible.data.*;
import jbible.mobile.*;

/**
 * ��������� ����������� ������
 * @author ����� ����������, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 * @author ������ ������, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 */
public class SearchResults extends List implements Runnable {

    /**
     * ����� ��� ���������� ������
     */
    private Thread th=new Thread(this);

    /**
     * ������� ���������� ������
     */
    private boolean finished=false;

    /**
     * ������ �������������� ��������� ������
     */
    private boolean stopSearch=false;

    /**
     * �������� ����� (����������� �����) � ������� ����� ������������ �����
     */
    private ChapterScreen owner;

    /**
     * ���� ������
     */
    private String key;

    /**
     * ������, � ������� ���������� ���������� ������ (��������)
     */
    private Vector list=new Vector();

    /**
     * ����� ����������� �� ������������ ����� ����������� ������
     */
    private int maxRes=100;

    /**
     * ��������� ������ ��� ����������� ������
     */
    private String titleNormal;

    /**
     * ��������� ������ ��� ����������� ������
     */
    private String searchWait;

    /**
     * ����� ����� - � ���� ����� ����������� ��������
     */
    private String searchWord;

    /**
     * �������� ����/������
     */
    private Command cmdCancel;

    /**
     * ����� � ������� ���������� �����
     */
    private int fromBookIndex;

    /**
     * ����� �� ������� ������������� �����
     */
    private int toBookIndex;

    /**
     * ������ ������� ���� � ������ ��������
     */
    private String lowerRULetter="��������������������������������";
    
    /**
     * ������ ������� ���� � ������� ��������
     */
    private String upperRULetter="�����Ũ��������������������������";
        
    /**
     * ��������� ��� ������� ������ � ������ �������
     */
     public String toLowerCase(String s){
         int foundIndex;
         String result="";
         for(int i=0;i<s.length();i++){
             if ((foundIndex=upperRULetter.indexOf(s.charAt(i)))!=-1){ // �����
                 result=result+lowerRULetter.charAt(foundIndex);
             }else{
                 result=result+s.charAt(i);
             }
         }
         return result;
     }
    
     /**
      * ��������� ��� ������� ������ � ������� �������
      */
     public String toUpperCase(String s){
         int foundIndex;
         String result="";
         for(int i=0;i<s.length();i++){
             if ((foundIndex=lowerRULetter.indexOf(s.charAt(i)))!=-1){ // �����
                 result=result+upperRULetter.charAt(foundIndex);
             }else{
                 result=result+s.charAt(i);
             }
         }
         return result;
     }
     
    /**
     * ������ ����� �����
     * @param owner �������� ����� (����������� �����) � ������� ����� ������������ �����
     * @param key ���� ������
     * @param startBookIndex ������ ����� � ������� �������� ����� (�� 0)
     * @param endBookIndex ������ ����� �� ������� ����������� ����� (�� 0)
     * @param maxRes ����������� �� ����� �����������
     */
    public SearchResults(ChapterScreen owner,String key,int startBookIndex,int endBookIndex,int maxRes){
        super(null,List.IMPLICIT);
        this.key=key;
        this.fromBookIndex=startBookIndex;
        this.toBookIndex=endBookIndex;
        this.maxRes=maxRes;
        update(owner);
        th.setPriority(Thread.MIN_PRIORITY);
        th.start();
    }

    /**
     * ��������� ������ �� ��������������� �������� �����, � ����������� ���� ���������
     * @param newScreen ��������������� � ������� �������� ����� (����� ����������� �����)
     */
    public void update(ChapterScreen newScreen){
        this.owner=newScreen;
        if(cmdCancel!=null) removeCommand(cmdCancel);
        //if(cmdNewSearch!=null) removeCommand(cmdNewSearch);
        cmdCancel=new Command(Common.getLocalizedString("search.cancel","Cancel"),Command.BACK,1);
        //cmdNewSearch=new Command(Common.getLocalizedString("search.new","New search"),Command.SCREEN,2);
        addCommand(cmdCancel);
        //addCommand(cmdNewSearch);
        titleNormal=Common.getLocalizedString("search.title","Search results");
        if(isFinished()) titleNormal=" ("+Common.getLocalizedString("search.total","Found")+" "+list.size()+")";
        searchWord=Common.getLocalizedString("search.wait","Searching..");
        searchWait=searchWord;
    }

    /**
     * �� ��������� ����! ��������� ������. ������������� ��� ������� � ����� ������.
     */
    public void run(){
        searchWait=searchWord+" (0%)";
        setTitle(searchWait);
        try{
            TextSource src=owner.getTextSource();
            Book[] books=src.getBibleContents();
            int len=toBookIndex-fromBookIndex+1;
            String keyLo=toLowerCase(key); // � ������ �������
            String key1Up=toUpperCase(key.substring(0,1))+keyLo.substring(1); // 1 ����� - ���������
            for(int book=fromBookIndex;book<=toBookIndex;book++){
                if(stopSearch) break;
                Book currBook=books[book];
                Chapter[] chapters=src.getBookContents(currBook.getNum());
                for(int chapter=0;chapter<chapters.length;chapter++){
                    if(stopSearch) break;
                    Chapter currChapter=chapters[chapter];
                    String[] verses=src.getChapterVerses(currBook.getNum(),currChapter.getNum());
                    for(int verse=0;verse<verses.length;verse++){
                        if(stopSearch) break;
                        String s=verses[verse];
                        int fromIndex=0;
                        int foundIndex;
                        int foundIndex1Up;
                        while(((foundIndex=s.indexOf(keyLo,fromIndex))!=-1)|((foundIndex1Up=s.indexOf(key1Up,fromIndex))!=-1)){
                            if (foundIndex<0){
                                foundIndex=foundIndex1Up;
                            }
                            int start=s.lastIndexOf(' ',foundIndex-1);
                            start=s.lastIndexOf(' ',start-1)+1;
                            int end=s.indexOf(' ',foundIndex+1);
                            if(end!=-1) end=s.indexOf(' ',end+1);
                            if(end==-1) end=s.length();
                            String str=s.substring(start,end);
                            if(end<s.length()) str=str+"...";
                            if(start>0) str="..."+str;
                            Bookmark b=new Bookmark(currBook.getNum(),currChapter.getNum(),verse+1,str);
                            list.addElement(b);
                            append(books[b.getBook()-1].getName()+" "+b.getChapter()+":"+b.getVerse()+" "+b.getText(),null);
                            fromIndex=foundIndex+1;
                            if(list.size()>=maxRes) stopSearch=true;
                        }
                    }
                }
                searchWait=searchWord+" ("+(100*(book-fromBookIndex+1))/len+"%)";
                setTitle(searchWait);
            }
        }catch(Exception e){
            owner.getMidletInterface().fatalError(e,"Search error");
        }
        finished=true;
        titleNormal=" ("+Common.getLocalizedString("search.total","Found")+" "+list.size()+")";
        setTitle(titleNormal);
    }

    /**
     * ��������� ����� �� ������� ��� ����������
     * @return true ���� ����� �������� � false � ��������� ������
     */
    public boolean isFinished(){
        return finished;
    }

    /**
     * ���������� ���������� ������ �� ������
     * @param next ��������������� �������� ����� (����� ����������� �����)
     */
    public void show(Displayable next){
        final Displayable nextDisp=next;
        final List screen=this;
        this.addCommand(cmdCancel);
        setCommandListener(new CommandListener(){
            public void commandAction(Command command, Displayable displayable) {
                if(command==List.SELECT_COMMAND){
                    int index=screen.getSelectedIndex();
                    Bookmark b=(Bookmark)list.elementAt(index);
                    //System.out.println("Selected: "+b); // �������
                    stop();
                    owner.load(b);
                    owner.getMidletInterface().show(owner);                     
                }else 
                    if(command==cmdCancel){
                        stop();
                        owner.getMidletInterface().show(nextDisp);
                    }
            }            
        });
        owner.getMidletInterface().show(this);
    }

    /**
     * ��������� �����
     */
    public void stop(){
        stopSearch=true;
        while(!isFinished()){
            try{
                Thread.sleep(250);
            }catch(InterruptedException e){}
        }
    }
}