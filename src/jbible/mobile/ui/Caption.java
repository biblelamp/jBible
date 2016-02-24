/**
 * Caption.java - Описывает многострочную надпись
 */

package jbible.mobile.ui;

import java.util.*;
import javax.microedition.lcdui.*;
import jbible.utils.*;
import jbible.mobile.*;

/**
 * Многострочная надпись с возможностью переноса по словам
 * @author Константин Беляк, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 * @author Сергей Ирюпин, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 */
public class Caption {
    
    /**
     * Текст надписи
     */
    String text;
    
    /**
     * Массив строк на которые разбит текст
     */
    String[] lines;
    
    /**
     * Размер отступа от краёв
     */
    public static final int SPACE=Common.SETTINGS.getHorizontalSpace();
    
    /**
     * Создаёт многострочную надпись
     * @param text Текст надписи
     */
    public Caption(String text){
        this.text=text;
        lines=new String[1];
        lines[0]=text;
    }

    /**
     * Список строк, на которые разбит текст
     */
    private Vector vect=new Vector();

    /**
     * Текущий текст
     */
    private String curr="";

    /**
     * Текущая ширина экрана
     */
    private int currWidth=0;

    /**
     * Переразбивает строку-главу, исходя из ширины экрана и размера шрифта
     * @param gw Ширина экрана = getWidth()
     * @param fnt Шрифт
     */
    public void resize(int gw, Font fnt){
        int width=gw-Common.SETTINGS.getHorizontalSpace()*2;
        vect.removeAllElements();
        int i=0,j=0,p=0;
        while(j<text.length()){
            p=i;
            while(fnt.stringWidth(text.substring(i,p))<width){ // пока ширина подстроки не превышает заданную
                j=p;
                p=text.indexOf(" ",j+1);
                if(p<0){ // последнее слово строки, пробел не найден
                    j=text.length();
                    break;
                }else{
                    if(Common.SETTINGS.getVerseSplit()==1) { // каждый стих выводится с новой строки
                        try{
                            if(i<j) // это не в начале строки
                               if(Integer.parseInt(text.substring(j+1,p))>1) // текущее слово - число>1
                                   break;
                        }catch(NumberFormatException e){}
                    }
                }
            }
            //System.out.println(text.substring(i,j));
            if(Common.RTL) vect.addElement(Common.strReverse(text.substring(i,j)));
            else vect.addElement(text.substring(i,j));
            i=j+1;
        }
        lines=new String[vect.size()];
        for(i=0;i<lines.length;i++) lines[i]=(String)vect.elementAt(i);
    }

    /**
     * Возвращает массив строк надписи
     * @return Массив строк надписи
     */
    public String[] getLines(){
        return lines;
    }
}