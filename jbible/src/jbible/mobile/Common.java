/*
 * Common.java - ����� ��� ������� ���������� ��������� � �������
 */

package jbible.mobile;

import java.io.*;
import java.util.*;
import jbible.mobile.ui.*;
import jbible.utils.*;

/**
 * ����� ��� ������� ���������� ��������� � �������
 * @author ���������� �����, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 * @author ������ ������, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 */
public class Common {

    /**
     * �������� ���������
     */
    public static final String APP_TITLE="jBible";

    /**
     * �������� ���������
     */
    public static final String APP_DESCRIPTION="Bible for mobile phones";

    /**
     * ������ ���������
     */
    public static final String APP_VERSION="1.3.15";

    /**
     * ���� ������
     */
    public static final String APP_DATE="24.12.2007";

    /**
     * ������ ������������� (�������� ���� ����, ���� �������� ��� ������� :)
     * ���������� ���������� ��� �������������. ��������! ��� ������ ������� �� ���������� ��
     * ������������ � ����� ������ ����������������. ������� ������� �� ����� �� ����� �������
     * ������� ����� � �������������������� ���������� ���� ��������� ��������� ������.
     * �������� ��� ������, � �� ������������� ������ ����� ���������� � ��������. �������� :-)
     */
    public static final String APP_DEVELOPERS_CONTACTINF="��������� ����� (smbadm@yandex.ru), ������ ������ (biblelamp@gmail.com), ����� ������ (roman_er@mail.ru), �������� ������ (webmaster@doposle.ru)";

    /**
     * �������� ���������
     */
    public static final String APP_VENDOR="���������� ������������ �������";

    /**
     * ���������� ���������� ���������
     */
    public static final String APP_VENDOR_CONTACTINF="http://rostovchurch.ru/";
    
    /**
     * ��������� ���������
     */
    public static final String APP_SUPPORT="8-918-557-2926";
    
    /**
     * ��������� ����������
     */
    public static Settings SETTINGS;
    
    /**
     * ������� � ������� �����������
     */
    public static final String LOCALIZATION_DIR="locale";

    /**
     * ���� ������ �������
     */
    public static final String LOCALES_LIST_FILE="locales";

    /**
     * ������ ��� ��������� �������
     */
    private static String[] localeNames;

    /**
     * ������ ������ ��������� �������
     */
    private static String[] localeFiles;
    
    /**
     * ����� ��������� ������
     */
    private static int localeNum=-1;

    /**
     * ������� ����������� �����
     */
    private static LocalizedStrings strings=null;

    /**
     * ������������� ����������� � ��������
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
            localeNames[0]="�������";
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
     * ������������������ ������
     * @return true � ������ ������ � false � ��������� ������
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
     * ������� � ����������� ��������
     */
    public static final String BIBLE_TEXT_DIR="bible";

    /**
     * ���� ������� ������ (�������� ����, ����� ���� � ������)
     */
    public static final String BIBLE_INDEX="index";

    /**
     * ������� � ��������
     */
    public static final String IMGS_DIR="imgs";

    /**
     * ������ ����������
     */
    public static final String ICON_FILE="/"+IMGS_DIR+"/jBible.png";

    /**
     * ���������� �������������� ������ ������
     * @param key ���� ������
     * @param alt �������������� ����� - ������������, ���� ���� �� ������
     * @return �������������� ������ ��� �������������� �����, ���� ���� �� ������
     */
    public static String getLocalizedString(String key,String alt){
        if(strings==null) return alt; else return strings.getString(key,alt);        
    }

    /**
     * ���������� ������ ��� �������
     * @return ������ ��� �������
     */
    public static String[] getLocaleNames(){
        return localeNames;
    }

    /**
     * ���������� ������ ��������� ������
     * @return ������ ��������� ������ (�� 0)
     */
    public static int getCurrentLocale(){
        return localeNum;
    } 
}