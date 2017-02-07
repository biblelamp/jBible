/**
 * Menu.java - ��������� ������������ �� ������ ����
 */

package jbible.mobile.ui;

import jbible.mobile.*;
import javax.microedition.lcdui.*;

/**
 * ������������ �� ������ ����
 * @author Konstantin K. Beliak, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Menu {
    
    /**
     * ������������ �����
     */
    private Canvas owner;
    
    /**
     * ����� ����������(���������) ������ ����
     */
    private int selIndex=0;
    
    /**
     * ������ ������� ����
     */
    private MenuItem[] items;
    
    /**
     * ������������ �� ���� �� ������
     */
    private boolean visible=false;
    
    /**
     * ������ �������� ����
     */
    private final int ITEM_HEIGHT=Font.getDefaultFont().getHeight()+Common.SETTINGS.getVerticalSpace();
    
    /**
     * �������� �� ��� y ������ ����
     */
    private int dy;
    
    /**
     * ������ ����
     */
    private int menuHeight;
    
    /**
     * �������� ����
     */
    private String caption;
    
    /**
     * ������ ����� ����
     * @param itemCount ����� ���������
     * @param owner ������������ ����� (�� ������� ���� ��������)
     * @param sel ��������� ����� ����
     * @param caption �������� ���� (������� ���������)
     */
    public Menu(int itemCount,Canvas owner,int sel,String caption) {
        items=new MenuItem[itemCount];
        this.owner=owner;
        this.selIndex=sel;
        this.caption=caption;
        update();
    }

    /**
     * ��������� ���� � ����� � ��������� ���������� ������� ������
     */
    public void update(){
        menuHeight=ITEM_HEIGHT*(items.length)+Common.SETTINGS.getVerticalSpace()*(items.length+5);
        dy=(owner.getHeight()-menuHeight)/2;    
        for(int i=0;i<items.length;i++){
            MenuItem item=items[i];
            if(item!=null){
                item.setWidth(owner.getWidth());
                item.setHeight(ITEM_HEIGHT);
                item.setY(dy+3*Common.SETTINGS.getVerticalSpace()+(ITEM_HEIGHT+Common.SETTINGS.getVerticalSpace())*i);
                item.update();
            }
        }
    }

    /**
     * ������������� ������� ����
     * @param item ������� ����
     * @param index ����� ��������
     */
    public void setItem(MenuItem item, int index){
        items[index]=item;
        item.setWidth(owner.getWidth());
        item.setHeight(ITEM_HEIGHT);
        item.setY(dy+3*Common.SETTINGS.getVerticalSpace()+(ITEM_HEIGHT+Common.SETTINGS.getVerticalSpace())*(index+1));
        item.update();                
    }

    /**
     * ���������� ���� �� ������
     */
    public void show(){
        update();
        items[selIndex].setActive(true);
        visible=true;
        owner.repaint();
    }

    /**
     * �������� ����
     */
    public void hide(){
        visible=false;
        owner.repaint();
    }
    
    /**
     * ���������, �������� �� ���� ������� � ������ ������
     * @return true ���� ���� ���������� �� ������, false � ��������� ������
     */
    public boolean isVisible(){
        return visible;
    }

    /**
     * ��������� �������� ������������ (������ ���� ������� ������������ �������)
     * @param action �������� ������������ (���������� � ������� getGameAction)
     */
    public void action(int action){
        switch(action){
            case Canvas.DOWN:
                items[selIndex].setActive(false);
                if(selIndex<items.length-1) selIndex++; else selIndex=0;
                items[selIndex].setActive(true);
                owner.repaint();
                break;
            case Canvas.UP:
                items[selIndex].setActive(false);
                if(selIndex>0) selIndex--; else selIndex=items.length-1;
                items[selIndex].setActive(true);
                owner.repaint();
                break;
            case Canvas.FIRE:
                hide();                
                fire(selIndex);
                break;
            default:
                items[selIndex].action(action);
        }
    }

    /**
     * ������������� ��� ��������� �������� FIRE
     * @param selIndex ����� �������� ����, ��������� � ������ ������
     */
    public void fire(int selIndex){
    }

    /**
     * ��������� ���� (������ ���� ������� ������������ �������)
     * @param g �������� ��� ���������
     */
    public void paint(Graphics g){
        if(visible){ 
            /*int width=owner.getWidth();
            int height=owner.getHeight();
            int itemHeight=Font.getDefaultFont().getHeight()+2*Common.SETTINGS.getVerticalSpace();
            int menuHeight=itemHeight*items.length+Common.SETTINGS.getVerticalSpace()*(items.length-1);
            int dy=(height-menuHeight)/2;
            g.setColor(Common.SETTINGS.getBGTitleColor());
            g.fillRect(Common.SETTINGS.getHorizontalSpace(),dy-Common.SETTINGS.getVerticalSpace(),
              width-Common.SETTINGS.getHorizontalSpace()*2,menuHeight+2*Common.SETTINGS.getVerticalSpace());
            g.setColor(Common.SETTINGS.getFGTitleColor());
            g.drawRect(Common.SETTINGS.getHorizontalSpace(),dy-Common.SETTINGS.getVerticalSpace(),
              width-Common.SETTINGS.getHorizontalSpace()*2,menuHeight+2*Common.SETTINGS.getVerticalSpace());
            */
            g.setColor(Common.SETTINGS.getBgTitleColor());
            g.fillRect(Common.SETTINGS.getHorizontalSpace(),dy,owner.getWidth()-2*Common.SETTINGS.getHorizontalSpace(),menuHeight);
            g.setColor(Common.SETTINGS.getFgTitleColor());
            g.drawRect(Common.SETTINGS.getHorizontalSpace(),dy,owner.getWidth()-2*Common.SETTINGS.getHorizontalSpace(),menuHeight);
            for(int i=0;i<items.length;i++){
                items[i].paint(g);
            }
        }
    }

    /**
     * ������������ ������ ������ ����
     */
    public void fire(){
        fire(selIndex);
    }

}