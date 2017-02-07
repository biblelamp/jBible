/*
 * LocalizedStrings.java - �����������
 */

package jbible.utils;

import java.io.*;
import java.util.*;

/**
 * ������� �������������� ����
 * @author ���������� K. �����, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru<a>
 * @author ������ �. ������, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 */
public class LocalizedStrings {

    /**
     * ���-������� ��� �������� ������ � �������������� �� ��������
     */
    private Hashtable table=new Hashtable();

    /**
     * ������ ����� ������� �����������, ����� � �� �������� ������ inputStream.  
     * <p>������ ����� ����������� �������� \n, � ������ ����� ������ <����>=<��������>, 
     * ��� ����� � �������� ������������� � 
     * <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/io/DataInput.html#modified-utf-8">
     * ����������������</a> ��������� UTF-8.
     * @param inputStream ����� ������� ������
     * @throws java.io.IOException ������������� � ������ ������ �����-������ ��� ��������� ������� ������
     */
    public LocalizedStrings(InputStream inputStream) throws IOException {
        DataInputStream dis = new DataInputStream(inputStream);
        String currLine;
        String key;
        String val;
        int lineNum = 0;
        do {
            try { 
                currLine = dis.readUTF().trim();
            } 
            catch (IOException e) { 
                currLine = "";
            }
            int sep = currLine.indexOf('=');
            if(sep != -1) {
                key = currLine.substring(0,sep);
                val = currLine.substring(sep+1);
                if(key.equals("")) throw new IOException("Bad data format: key \""+key+"\" is empty, line "+lineNum);
                if(val.equals("")) throw new IOException("Bad data format: value \""+val+"\" is empty, line "+lineNum);
                if(table.get(key) != null) throw new IOException("Bad data format: key \""+key+"\" is already used, line "+lineNum);
                table.put(key,val);
                lineNum++;
            }
        } while (!currLine.equals(""));
        dis.close();
    }

    /**
     * ���� � ������� �������������� ������� ������ �� �����
     * @param key ����
     * @param def �������� �� ���������
     * @return �������������� ������� ������ ���� ������ �� ���������, ���� ���� �� ������
     */
    public String getString(String key, String def) {
        String res=(String)table.get(key);
        return (res==null)?def:res;
    }

}