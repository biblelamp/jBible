/*
 * Storage.java - ���������� ������ � ���������� ������ MIDP
 */

package jbible.utils;

import java.io.*;
import java.util.*;
import javax.microedition.rms.*;
import jbible.mobile.*;
import jbible.mobile.ui.*;

/**
 * ���������� ������ � ���������� ������ MIDP
 * @author ���������� �. �����, <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Storage {

    /**
     * ��� MIDP-��������� ��������
     */
    private static final String BOOKMARKS_STORE_NAME="bookmarks";

    /**
     * ��� MIDP-��������� ��������
     */
    private static final String SETTINGS_STORE_NAME="settings";

    /**
     * ����� ��� ��������� �������� ����� �����
     */
    private static class BookmarkComparator implements RecordComparator {
        /**
         * ���������� ��� ��������
         * @param l ����� ��������
         * @param r ������ ��������
         * @return 0 ���� �������� ���������, -1 ���� ����� ������ ������ � 1 ���� ����� ������ ������
         */
        public int compare(byte[] l, byte[] r) {
            try{
                Bookmark left=new Bookmark(l);
                Bookmark right=new Bookmark(r);
                if(left.getBook()<right.getBook()) return PRECEDES;
                else if(left.getBook()>right.getBook()) return FOLLOWS;
                else{
                    if(left.getChapter()<right.getChapter()) return PRECEDES;
                    else if(left.getChapter()>right.getChapter()) return this.FOLLOWS;
                    else{
                        if(left.getVerse()<right.getVerse()) return PRECEDES;
                        else if(left.getVerse()>right.getVerse()) return FOLLOWS;
                        else return EQUIVALENT;
                    }
                }
            }catch(IOException e){
                return EQUIVALENT;
            }    
        }    
    }

    /**
     * ��������� �������� �� ���������
     * @return ������ ��������
     */
    public static Vector readBookmarks(){
        Vector res=new Vector();
        RecordStore rs=null;
        try{
            BookmarkComparator cmp=new BookmarkComparator();
            rs=RecordStore.openRecordStore(BOOKMARKS_STORE_NAME,false);
            RecordEnumeration recEnum=rs.enumerateRecords(null,cmp,false);
            while(recEnum.hasNextElement()){
                Bookmark newBookmark=new Bookmark(recEnum.nextRecord());
                res.addElement(newBookmark);
                System.out.println(newBookmark);
            }
            return res;
        }catch(RecordStoreNotFoundException e){
        }catch(Exception e){
            System.err.println("WARNING: Can't read bookmarks: "+e.getClass().getName()+" (with message "+e.getMessage()+")");
        }finally{
            try{
                rs.closeRecordStore();
            }catch(Exception e){}    
        }
        return null;
    }
    
    /**
     * ��������� ������ �������� � ���������
     * @param bookmarks ������ ��������
     */
    public static void saveBookmarks(Vector bookmarks) {
        RecordStore rs=null;
        try{
            rs=RecordStore.openRecordStore(BOOKMARKS_STORE_NAME,true);
            RecordEnumeration recEnum=rs.enumerateRecords(null,null,false);
            for(int i=0;i<bookmarks.size();i++){
                Bookmark b=(Bookmark)bookmarks.elementAt(i);
                byte[] rec=b.toByteArray();
                if(rec==null) throw new IOException("Error creating record from bookmark");
                if(recEnum.hasNextElement()){
                    int id=recEnum.nextRecordId();
                    rs.setRecord(id,rec,0,rec.length);
                }else rs.addRecord(rec,0,rec.length);
            }
            while(recEnum.hasNextElement()){
                int id=recEnum.nextRecordId();
                rs.deleteRecord(id);
            }
        }catch(Exception e){
            System.err.println("WARNING: Can't write bookmarks: "+e.getClass().getName()+" (with message "+e.getMessage()+")");
        }finally{
            try{
                rs.closeRecordStore();
            }catch(Exception e){}
        }        
    }
    
    /**
     * ������ ��������� �� ���������
     * @return ��������� ����������
     */
    public static Settings readSettings(){
        RecordStore rs=null;
        try{
            rs=RecordStore.openRecordStore(SETTINGS_STORE_NAME,false);
            RecordEnumeration recEnum=rs.enumerateRecords(null,null,false);
            byte[] data=recEnum.nextRecord();
            rs.closeRecordStore();
            return new Settings(data);
        }catch(RecordStoreNotFoundException e){
        }catch(Exception e){
            System.err.println("WARNING: Can't read settings: "+e.getClass().getName()+" (with message "+e.getMessage()+")");
        }finally{
            try{
                rs.closeRecordStore();
            }catch(Exception e){}
        }        
        return null;
    }

    /**
     * ��������� ��������� � ���������
     * @param s ���������
     */
    public static void saveSettings(Settings s){
        try{
            ByteArrayOutputStream os=new ByteArrayOutputStream();
            s.save(os);
            byte[] arr=os.toByteArray();
            RecordStore rs=RecordStore.openRecordStore(SETTINGS_STORE_NAME,true);
            if(rs.getNumRecords()>=1){
                RecordEnumeration recEnum=rs.enumerateRecords(null,null,false);
                int id=recEnum.nextRecordId();
                rs.setRecord(id,arr,0,arr.length);
            }else{
                rs.addRecord(arr,0,arr.length);
            }
            rs.closeRecordStore();
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("WARNING: Can't write settings: "+e.getClass().getName()+" (with message "+e.getMessage()+")");
        }
    }

    /* ���������� �������� ���������� � ��������� */
    /*public static void setVariable(RecordStore store,String name, String value) throws IOException,InvalidRecordIDException,RecordStoreException{
        RecordEnumeration records=store.enumerateRecords(null,null,false);
        while(records.hasNextElement()){
            int id=records.nextRecordId();
            byte[] rec=store.getRecord(id);
            ByteArrayInputStream bais=new ByteArrayInputStream(rec);
            DataInputStream is=new DataInputStream(bais);
            String recName=is.readUTF();
            if(recName.equals(name)) {
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                DataOutputStream os=new DataOutputStream(baos);
                os.writeUTF(name);
                os.writeUTF(value);
                store.setRecord(id, baos.toByteArray(), 0, baos.size());
                return;
            }
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        DataOutputStream os=new DataOutputStream(baos);
        os.writeUTF(name);
        os.writeUTF(value);
        store.addRecord(baos.toByteArray(), 0, baos.size());
    
    }*/
    
    /* �������� �������� ���������� �� ��������� */
    /*public static String getVariable(RecordStore store,String name) throws IOException,InvalidRecordIDException,RecordStoreException{
        RecordEnumeration records=store.enumerateRecords(null,null,false);
        while(records.hasNextElement()){
            byte[] rec=records.nextRecord();
            ByteArrayInputStream bais=new ByteArrayInputStream(rec);
            DataInputStream is=new DataInputStream(bais);
            String recName=is.readUTF();
            if(recName.equals(name)) return is.readUTF();
        }
        return null;
    }*/
    
    /* ������� ���������� �� ��������� */
    /*public static void removeVariable(RecordStore store,String name) throws IOException,InvalidRecordIDException,RecordStoreException{
        RecordEnumeration records=store.enumerateRecords(null,null,false);
        while(records.hasNextElement()){
            int id=records.nextRecordId();
            byte[] rec=store.getRecord(id);
            ByteArrayInputStream bais=new ByteArrayInputStream(rec);
            DataInputStream is=new DataInputStream(bais);
            String recName=is.readUTF();
            if(recName.equals(name)) {
                store.deleteRecord(id);
                break;
            }
        }
    }*/
    
}