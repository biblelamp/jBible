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
     * Добавляет строку к списку строк
     * @param str Строка
     * @param width Ширина экрана
     * @param f Шрифт
     * @param spaceSize Размер отступа от краёв
     */
    private void appendString(String str,int width,Font f,int spaceSize){
        //System.out.println("\""+str+"\""); // отладка
        int newWidth=f.stringWidth(str); // длина слова
        if(Common.SETTINGS.getVerseSplit()==1){ // надо переносить по стихам
            try{
                if (Integer.parseInt(str)>1)    // это цифра - номер стиха
                    currWidth = width;
            }catch(NumberFormatException e){}
        }
        if(newWidth+spaceSize+currWidth<width) { // слово со пробелом < ширины экрана
            if (curr=="") curr=str; else curr+=" "+str; // добавляем слово
            currWidth+=spaceSize+newWidth;       // увеличиваем текушую ширину экрана
        }else{                                   // слово не умещается
            if(newWidth>=width){                 // текущая ширина экрана >= ширины экрана
                int dSize=f.stringWidth("-");
                //System.out.println("Big string!"); // отладка
                int i=1;
                while(i<=str.length()){
                    String s=str.substring(0,i);
                    int w=f.stringWidth(s);
                    if(currWidth+spaceSize+w+dSize>=width) break;
                    i++;
                }
                String left=str.substring(0,i-1);
                if(!left.equals("")) left+="-";
                String right=str.substring(i-1);
                vect.addElement(curr+" "+left);
                curr="";
                currWidth=0;
                appendString(right,width,f,spaceSize);
            }else{
                vect.addElement(curr);
                curr=str;           
                currWidth=newWidth;
            }
        }
    }

    /**
     * Изменяет ширину надписи и шрифт
     * @param width Ширина надписи
     * @param font Шрифт
     */
    public void resize(int width, Font font){
        int spaceSize=font.stringWidth(" ");
        int maxWidth=width-Common.SETTINGS.getHorizontalSpace()*2;
        vect.removeAllElements();
        int curr=0, next=0;
        int i=0;
  loop: while(i<text.length()){
            int j=i;
            while(j<text.length()){
                if(text.charAt(j)==' '){
                    String s=text.substring(curr,j);
                    appendString(s,maxWidth,font,spaceSize);
                    curr=j+1;
                    i=curr;
                    continue loop;
                }
                j++;
            }
            String s=text.substring(curr);
            appendString(s,maxWidth,font,spaceSize);
            break;
        }
        vect.addElement(this.curr);
        //if(curr)
        /*StringTokenizer st=new StringTokenizer(text);
        String buf=st.nextToken();
        String curr="";
        int maxTextWidth=width-2*SPACE;
        while(buf!=null){
            curr=buf;
            do{
                buf=st.nextToken();
                if(buf==null) break;
                if(font.stringWidth(curr+buf)<maxTextWidth) curr+=buf; else break;
            }while(true);
            vect.addElement(curr);
        }
        */
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