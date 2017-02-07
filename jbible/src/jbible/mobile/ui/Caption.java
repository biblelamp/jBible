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
     * ��������� ������ � ������ �����
     * @param str ������
     * @param width ������ ������
     * @param f �����
     * @param spaceSize ������ ������� �� ����
     */
    private void appendString(String str,int width,Font f,int spaceSize){
        //System.out.println("\""+str+"\""); // �������
        int newWidth=f.stringWidth(str); // ����� �����
        if(Common.SETTINGS.getVerseSplit()==1){ // ���� ���������� �� ������
            try{
                if (Integer.parseInt(str)>1)    // ��� ����� - ����� �����
                    currWidth = width;
            }catch(NumberFormatException e){}
        }
        if(newWidth+spaceSize+currWidth<width) { // ����� �� �������� < ������ ������
            if (curr=="") curr=str; else curr+=" "+str; // ��������� �����
            currWidth+=spaceSize+newWidth;       // ����������� ������� ������ ������
        }else{                                   // ����� �� ���������
            if(newWidth>=width){                 // ������� ������ ������ >= ������ ������
                int dSize=f.stringWidth("-");
                //System.out.println("Big string!"); // �������
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
     * �������� ������ ������� � �����
     * @param width ������ �������
     * @param font �����
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
     * ���������� ������ ����� �������
     * @return ������ ����� �������
     */
    public String[] getLines(){
        return lines;
    }
}