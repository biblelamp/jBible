/**
 * Caption.java - ��������� ������������� �������
 */

package jbible.mobile.ui;

import java.util.*;
import javax.microedition.lcdui.*;
import jbible.utils.*;
import jbible.mobile.*;

/**
 * ������������� ������� � ������������ �������� �� ������
 * @author ���������� �����, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 * @author ������ ������, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 */
public class Caption {
    
    /**
     * ����� �������
     */
    String text;
    
    /**
     * ������ ����� �� ������� ������ �����
     */
    String[] lines;
    
    /**
     * ������ ������� �� ����
     */
    public static final int SPACE=Common.SETTINGS.getHorizontalSpace();
    
    /**
     * ������ ������������� �������
     * @param text ����� �������
     */
    public Caption(String text){
        this.text=text;
        lines=new String[1];
        lines[0]=text;
    }

    /**
     * ������ �����, �� ������� ������ �����
     */
    private Vector vect=new Vector();

    /**
     * ������� �����
     */
    private String curr="";

    /**
     * ������� ������ ������
     */
    private int currWidth=0;

    /**
     * ������������� ������-�����, ������ �� ������ ������ � ������� ������
     * @param gw ������ ������ = getWidth()
     * @param fnt �����
     */
    public void resize(int gw, Font fnt){
        int width=gw-Common.SETTINGS.getHorizontalSpace()*2;
        vect.removeAllElements();
        int i=0,j=0,p=0;
        while(j<text.length()){
            p=i;
            while(fnt.stringWidth(text.substring(i,p))<width){ // ���� ������ ��������� �� ��������� ��������
                j=p;
                p=text.indexOf(" ",j+1);
                if(p<0){ // ��������� ����� ������, ������ �� ������
                    j=text.length();
                    break;
                }else{
                    if(Common.SETTINGS.getVerseSplit()==1) { // ������ ���� ��������� � ����� ������
                        try{
                            if(i<j) // ��� �� � ������ ������
                               if(Integer.parseInt(text.substring(j+1,p))>1) // ������� ����� - �����>1
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
     * ���������� ������ ����� �������
     * @return ������ ����� �������
     */
    public String[] getLines(){
        return lines;
    }
}