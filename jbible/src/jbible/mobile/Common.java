/*
 * Common.java - Общие для классов приложения константы и функции
 */

package jbible.mobile;

import java.io.*;
import java.util.*;
import jbible.mobile.ui.*;
import jbible.utils.*;

/**
 * Общие для классов приложения константы и функции
 * @author Константин Беляк, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 * @author Сергей Ирюпин, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 */
public class Common {

    /**
     * Название программы
     */
    public static final String APP_TITLE="jBible";

    /**
     * Описание программы
     */
    public static final String APP_DESCRIPTION="Bible for mobile phones";

    /**
     * Версия программы
     */
    public static final String APP_VERSION="1.3.15";

    /**
     * Дата релиза
     */
    public static final String APP_DATE="24.12.2007";

    /**
     * Список разработчиков (допишите себя сюда, если изменили две строчки :)
     * Контактная информация для разработчиков. Внимание! Код никоим образом не претендует на
     * совершенство с точки зрения программирования. Поэтому просьба не слать по этому адресам
     * гневных писем о неудовлетворительной реализации либо неудачной объектной модели.
     * Исходный код открыт, в нём действительно многое нужно переписать и добавить. Дерзайте :-)
     */
    public static final String APP_DEVELOPERS_CONTACTINF="Костантин Беляк (smbadm@yandex.ru), Сергей Ирюпин (biblelamp@gmail.com), Роман Ершков (roman_er@mail.ru), Владимир Власов (webmaster@doposle.ru)";

    /**
     * Заказчик программы
     */
    public static final String APP_VENDOR="Ростовская христианская церковь";

    /**
     * Контактная информация заказчика
     */
    public static final String APP_VENDOR_CONTACTINF="http://rostovchurch.ru/";
    
    /**
     * Поддержка программы
     */
    public static final String APP_SUPPORT="8-918-557-2926";
    
    /**
     * Настройки приложения
     */
    public static Settings SETTINGS;
    
    /**
     * Каталог с файлами локализации
     */
    public static final String LOCALIZATION_DIR="locale";

    /**
     * Файл списка локалей
     */
    public static final String LOCALES_LIST_FILE="locales";

    /**
     * Список имён доступных локалей
     */
    private static String[] localeNames;

    /**
     * Список файлов доступных локалей
     */
    private static String[] localeFiles;
    
    /**
     * Номер выбранной локали
     */
    private static int localeNum=-1;

    /**
     * Таблица локализации строк
     */
    private static LocalizedStrings strings=null;

    /**
     * Инициализация локализации и настроек
     */
    static{
        SETTINGS=Storage.readSettings();
        if(SETTINGS==null) SETTINGS=new Settings();
        try{
            /*String localeListFile="/"+LOCALIZATION_DIR+"/"+LOCALES_LIST_FILE;
            InputStream is=localeListFile.getClass().getResourceAsStream(localeListFile);
            if(is==null) throw new IOException("locales list file not found: "+localeListFile);
            DataInputStream dis=new DataInputStream(is);
            Vector names=new Vector();
            Vector files=new Vector();
            while(dis.available()>0){
                String s=dis.readUTF();
                int split=s.indexOf('\t');
                if(split!=-1){
                    String file=s.substring(0,split);
                    String name=s.substring(split+1);
                    files.addElement(file);
                    names.addElement(name);                    
                }else throw new IOException("Bad locales list file format: "+localeListFile);
            }
            dis.close();
            if(files.size()>0){
                localeNames=new String[names.size()];
                localeFiles=new String[files.size()];
                for(int i=0;i<files.size();i++){
                    localeNames[i]=(String)names.elementAt(i);
                    localeFiles[i]=(String)files.elementAt(i);
                }
                String sysLocaleName=System.getProperty("microedition.locale");
                if(sysLocaleName!=null){
                    sysLocaleName=sysLocaleName.replace('-','_');
                    //System.out.println(sysLocaleName);
                    for(int i=0;i<localeFiles.length;i++){
                        String f=localeFiles[i];
                        if(f.equals(sysLocaleName)){
                            localeNum=i;
                            break;
                        }
                    }
                    if(localeNum==-1){
                        for(int i=0;i<localeFiles.length;i++){
                            String f=localeFiles[i];
                            if(f.startsWith(sysLocaleName+"_")){
                                localeNum=i;
                                break;
                            }                
                        }    
                    }
                }
                //if(localeNum==-1) localeNum=0;
                reloadLocale();
            }else throw new IOException("No locales found");
             */
            localeNames=new String[1];
            localeNames[0]="Русский";
            localeFiles=new String[1];
            localeFiles[0]="ru_RU";
            localeNum=0;
            reloadLocale();
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("WARNING: Localization error ("+e.getMessage()+")");
        }    
    }

    /**
     * Переинициализирует локаль
     * @return true в случае успеха и false в противном случае
     */
    public static boolean reloadLocale(){
        int newLocale=SETTINGS.getLocale();
        if(newLocale>=0 && newLocale<localeFiles.length) localeNum=newLocale;
        else {
            System.err.println("WARNING: Can't change locale.");
            localeNum=0;
        }
        try{
            String fileName="/"+LOCALIZATION_DIR+"/"+localeFiles[localeNum];
            InputStream is=fileName.getClass().getResourceAsStream(fileName);
            if(is==null) throw new IOException("Localization file not found: "+fileName);
            strings=new LocalizedStrings(is);
            SETTINGS.setLocale(localeNum);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Каталог с библейскими текстами
     */
    public static final String BIBLE_TEXT_DIR="bible";

    /**
     * Файл индекса Библии (названия книг, число глав и стихов)
     */
    public static final String BIBLE_INDEX="index";

    /**
     * Каталог с графикой
     */
    public static final String IMGS_DIR="imgs";

    /**
     * Иконка приложения
     */
    public static final String ICON_FILE="/"+IMGS_DIR+"/jBible.png";

    /**
     * Возвращает локализованную версию строки
     * @param key Ключ поиска
     * @param alt Альтернативный текст - возвращается, если ключ не найден
     * @return Локализованная строка или альтернативный текст, если ключ не найден
     */
    public static String getLocalizedString(String key,String alt){
        if(strings==null) return alt; else return strings.getString(key,alt);        
    }

    /**
     * Возвращает список имён локалей
     * @return Список имён локалей
     */
    public static String[] getLocaleNames(){
        return localeNames;
    }

    /**
     * Возвращает индекс выбранной локали
     * @return Индекс выбранной локали (от 0)
     */
    public static int getCurrentLocale(){
        return localeNum;
    } 
}