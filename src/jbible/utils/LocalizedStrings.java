/*
 * LocalizedStrings.java - Локализация
 */

package jbible.utils;

import java.io.*;
import java.util.*;

/**
 * Таблица локализованных сток
 * @author Константин K. Беляк, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru<a>
 * @author Сергей В. Ирюпин, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 */
public class LocalizedStrings {

    /**
     * Хэш-таблица для хранения ключей и сопоставленных им значений
     */
    private Hashtable table=new Hashtable();

    /**
     * Создаёт новую таблицу локализации, читая её из входного потока inputStream.  
     * <p>Строки файла разделяются символом \n, и должны иметь формат <ключ>=<значение>, 
     * где ключи и значения представленны в 
     * <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/io/DataInput.html#modified-utf-8">
     * модифицированной</a> кодировке UTF-8.
     * @param inputStream Поток входных данных
     * @throws java.io.IOException Выбрасывается в случае ошибки ввода-вывода или неверного формата данных
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
     * Ищет в таблице локализованный вариант строки по ключу
     * @param key Ключ
     * @param def Значение по умолчанию
     * @return Локализованный вариант строки либо строка по умолчанию, если ключ не найден
     */
    public String getString(String key, String def) {
        String res=(String)table.get(key);
        return (res==null)?def:res;
    }

}