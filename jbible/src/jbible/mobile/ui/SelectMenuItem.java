/**
 * SelectMenuItem.java - ��������� ������� ���� ��� ������ �� ���������� ���������
 */

package jbible.mobile.ui;

import javax.microedition.lcdui.*;
import java.io.*;
import jbible.mobile.*;

/**
 * ������� ���� ��� ������ �� ���������� ���������
 * @author ���������� �����, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public abstract class SelectMenuItem extends MenuItem {

    /**
     * ����������� ���������
     */
    private int itemCount;

    /**
     * ����� ���������� ��������
     */
    private int selIndex;

    /**
     * ���������� �������� (����� ��������� � ��������������� ����)
     */
    private final int INSPACE=10;

    /**
     * ���������� y ������ ������ ����
     */
    private int centerY;

    /**
     * ������ ��������������
     */
    private int rectWidth;

    /**
     * ����� �������
     */
    private String text;

    /**
     * ������������ �����
     */
    private Canvas owner;

    /**
     * ������ ����� ������� ����
     * @param itemCount ����� ���������� ������ 
     * @param selIndex ����� ���������� ������
     * @param owner ������������ �����
     */
    public SelectMenuItem(int itemCount, int selIndex,Canvas owner) {
        this.itemCount=itemCount;
        this.selIndex=selIndex;
        this.owner=owner;
    }

    /**
     * ��������� ��������� ������ ���� � ����� � ���������� ����������� ���������� ������������� ������
     */
    public void update(){
        centerY=getY()+getHeight()/2;
        rectWidth=getWidth()-(2*Common.SETTINGS.getHorizontalSpace()+2*INSPACE/*+left.getWidth()+right.getWidth()*/);
        Font f=Font.getDefaultFont();
        text=getItemText(selIndex);
        StringBuffer buf=new StringBuffer(text);
        String append="";
        while(f.stringWidth(buf.toString()+append)>rectWidth-Common.SETTINGS.getHorizontalSpace()*2 && buf.length()>0){
            buf.deleteCharAt(buf.length()-1);
            append="..";
        }
        text=buf+append;
        owner.repaint();
    }

    /**
     * �������� ����� ���������� ������ (����� ������ ���� ���������� ��� ������� ������ ����)
     * @param index ����� ������ ����
     * @return ����� ���������� ������
     */
    public abstract String getItemText(int index);

    /**
     * ���������� ��� ������ ������
     * @param index ��������� �����
     */
    public abstract void select(int index);

    /**
     * ������������� ��������� �����
     * @param index ����� ������
     */
    public void setSelectedIndex(int index){
        selIndex=index;
        select(selIndex);
        update();
    }

    /**
     * ������������� ����� �������
     * @param count ����� �������
     */
    public void setItemCount(int count){
        this.itemCount=count;
        this.selIndex=0;
        select(selIndex);
        update();
    }

    /**
     * ��������� �������� ������������ (���������� ����)
     * @param action �������� ������������ (���������� � ������� getGameAction)
     */
    public void action(int action){
        switch(action){
            case Canvas.RIGHT:
                if(selIndex<itemCount-1) selIndex++; else selIndex=0;
                select(selIndex);
                update();
                break;
            case Canvas.LEFT:
                if(selIndex>0) selIndex--; else selIndex=itemCount-1;
                select(selIndex);
                update();
                break;                
        }
    }

    /**
     * ��������� ������ ����
     * @param g �������� ��� ���������
     */
    public void paint(Graphics g){
        g.setColor((isActive()?Common.SETTINGS.getBgTextColor():Common.SETTINGS.getBgTitleColor()));
        if(isActive()) g.fillRect(Common.SETTINGS.getHorizontalSpace()+/*left.getWidth()*/+INSPACE,getY()+1,rectWidth,getHeight()-1);
        g.setColor((isActive()?Common.SETTINGS.getFgTitleColor():Common.SETTINGS.getFgTextColor()));
        g.drawString(text,getWidth()/2,getY()+Common.SETTINGS.getVerticalSpace(),Graphics.TOP | Graphics.HCENTER);
        if(isActive())g.drawRect(Common.SETTINGS.getHorizontalSpace()+/*left.getWidth()*/+INSPACE,getY()+1,rectWidth,getHeight()-1);
    }

    /**
     * �������� ����� ���������� ������
     * @return ����� ���������� ������
     */
    public int getSelectedIndex() {
        return selIndex;
    }
}