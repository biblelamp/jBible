/*
 * SelectColorItem.java - ��������� ��� ������ �����
 *
 */

package jbible.mobile.ui;

import jbible.mobile.*;
import javax.microedition.lcdui.*;
import java.io.*;

/**
 * ��������� ��� ������ �����
 * @author ����� ����������, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class SelectColorItem extends CustomItem implements CommandListener, ItemCommandListener {
    
    /**
     * �������� ������� ����
     */
    private Command cmdOk=new Command(Common.getLocalizedString("settings.selectcolor.ok","OK"),Command.OK,0);
    
    /**
     * �������� �������� ����� �����
     */
    private Command cmdCancel=new Command(Common.getLocalizedString("settings.selectcolor.cancel","Cancel"),Command.BACK,0);
    
    /**
     * �������� ������� ����
     */
    private Command cmdChange=new Command(Common.getLocalizedString("settings.changecolor","Change"),Command.ITEM,0);
    
    /**
     * ������ �������� ����� (�� 0)
     */
    private int curr=0;
    
    /**
     * ������ ������ ��������
     */
    private int W=96;
    
    /**
     * ������ ������ ��������
     */
    private int H=Font.getDefaultFont().getHeight()+2*Common.SETTINGS.getVerticalSpace();
    
    /**
     * �������� ����� (����������� �����) � ������� ����� ������������ �����
     */
    private Form owner;
    
    /**
     * ������ ������
     */
    private static final int[] COLORS=new int[]{
        
        0xffffff,0xc0c0c0,0x333333,0x000000,
        0xffffcc,0xffff99,0xffcc33,0xffff00,
        0xffcccc,0xffcc99,0xffccff,0x990000,
        0xff6600,0xff0000,0xccffff,0x99ffff,
        0xccccff,0x6600cc,0x339999,0x663366,
        0x3366ff,0x000099,0x99ff99,0x009900,
        0x33ff33      
        
    };
    
    /**
     * ����� ������ ��� ������ �����
     */
    private class ColorChooser extends Canvas {
        
        /**
         * ������ ��������� �����
         */
        private int sel=0;
        
        /**
         * ������������ �������
         */
        private SelectColorItem owner;
        
        /**
         * ������ ������ ��������
         */
        private int H=Font.getDefaultFont().getHeight()+2*Common.SETTINGS.getVerticalSpace();
        
        /**
         * ������ ������ ������ �������� (������ � ���������)
         */
        private int H_FULL=H+Common.SETTINGS.getVerticalSpace();
        
        /**
         * �������������� ������
         */
        private int H_SPACE=Common.SETTINGS.getHorizontalSpace();
        
        /**
         * ������ �����, ������������� �� ������ � ������ ������ � ����� ������� ������� ������
         */
        private int top;
        
        /**
         * ����� ������ (��������� ������, ������������ ������������ �� �������)
         */
        private int disp;
        
        /**
         * ����� ����
         */
        private String COLOR_WORD=Common.getLocalizedString("settings.color","Color");
        
        /**
         * ������ ����� ����� ������ �����.
         * @param owner ������������ �������
         */
        public ColorChooser(SelectColorItem owner){
            this.owner=owner;
           
            disp=getHeight()/H_FULL;            
        }
        
        /**
         * ��������� (������������) ������ ��� �������������
         */
        public void updateTop(){
            if(sel<=top && top>0) top--;
            if(sel>=top+disp-1 && top+disp<COLORS.length) top++;
        }
        
        /**
         * �������� ������� ��������� ����
         * @param newSel ����� ��������� ����
         */
        public void setSelected(int newSel){
            sel=newSel;
            if(sel==0) top=0; else top=sel-1;            
            repaint();
        }
        
        /**
         * ���������� ��������� ���� (������, �� 0)
         * @return ��������� ���� (������, �� 0)
         */
        public int getSelected(){
            return sel;
        }
        
        /**
         * ����������� ������
         * @param g ����������� ��������
         */
        public void paint(Graphics g){
            g.setColor(0xFFFFFF);
            g.fillRect(0,0,getWidth(),getHeight());
            for(int i=0;i<=disp;i++){
                int col=top+i;
                if(col<COLORS.length){
                    g.setColor(COLORS[col]);
                    g.fillRect(H_SPACE,H_FULL*i,getWidth()-2*H_SPACE-1,H);
                    g.setColor(~COLORS[col]);
                    String name=COLOR_WORD+" "+(col+1);
                    g.drawString(name,getWidth()/2,H_FULL*i+(H-g.getFont().getHeight())/2,Graphics.HCENTER|Graphics.TOP);
                }
            }
            int selIndex=sel-top;
            g.setColor(~COLORS[sel]);
            g.drawRect(H_SPACE,H_FULL*selIndex,getWidth()-2*H_SPACE-1,H-1);
        }

        /**
         * ���������� ���������� ������� �� �������
         * @param key ��� ������� �������
         */
        protected void keyRepeated(int key) {
            int action=getGameAction(key);
            switch(action){
                case Canvas.UP:
                    if(sel>0) {
                        sel--;
                        updateTop();
                        repaint();
                    }
                    break;
                case Canvas.DOWN:
                    if(sel<COLORS.length-1){
                        sel++;
                        updateTop();
                        repaint();
                    }
                    break;
                case Canvas.FIRE:
                    owner.commandAction(owner.cmdOk,this);
                    break;
            }
        }
        
        /**
         * ���������� ������� �� �������
         * @param key ��� ������� �������
         */
        protected void keyPressed(int key) {
            keyRepeated(key);
        }
    };

     
    /**
     * ��������� �������
     */
    private MidletInterface midlet;

    /**
     * ������������ ������� �����
     */
    private ColorChooser colorChooser=new ColorChooser(this);
    
    /**
     * ������ ������� ����� ��� ������ �����
     * @param text ����� �������
     * @param color ������ �������� ����� (�� 0)
     * @param midlet ��������� �������
     */
    public SelectColorItem(String text,int color,MidletInterface midlet) {
        super(text);
        this.midlet=midlet;
        int index=getIndex(color);
        if(index!=-1) curr=index;
        addCommand(cmdChange);
        setDefaultCommand(cmdChange);
        setItemCommandListener(this);
        colorChooser.addCommand(cmdOk);
        colorChooser.addCommand(cmdCancel);
        colorChooser.setCommandListener(this);
    }
    
    /**
     * ����������� ������� � ������
     * @param f �����
     */
    public void setOwner(Form f){
        this.owner=f;
    }
    
    /**
     * �������� ������ ����� � ������� ������
     * @param color ���� � ���� 0xRRGGBB
     * @return ������ ����� � ������� ������ ��� -1, ���� ���� �� ������
     */
    public int getIndex(int color){
        for(int i=0;i<COLORS.length;i++)
            if(COLORS[i]==color) return i;
        return -1;
    }

    /**
     * ���������� ����������� ������ ��������
     * @return ����������� ������ ��������
     */
    protected int getMinContentWidth() {
        return W;
    }

    /**
     * ���������� ����������� ������ ��������
     * @return ����������� ������ ��������
     */
    protected int getMinContentHeight() {
        return H;
    }

    /**
     * ���������� �������������� ������ ��������
     * @param w ������������ ������, ���� �������� ������ 0
     * @return �������������� ������ ��������
     */
    protected int getPrefContentWidth(int w) {
        if(w>0) return w;
        return (owner==null)?W:owner.getWidth()-2*Common.SETTINGS.getHorizontalSpace();
    }

    /**
     * ���������� �������������� ������ ��������
     * @param h ������������ ������, ���� �������� ������ 0
     * @return �������������� ������ ��������
     */
    protected int getPrefContentHeight(int h) {
        if(h>0 && h<2*H) return h;
        return H;
    }
   
    /**
     * ���������� ������ ���������� ����� � ������� COLORS
     * @return ������ ���������� ����� � ������� COLORS
     */
    public int getSelectedColor(){
        return COLORS[curr];
    }
    
    /**
     * ����������� ��������
     * @param g ����������� ��������
     * @param w ������
     * @param h ������
     */
    protected void paint(Graphics g, int w, int h) {
        g.setColor(COLORS[curr]);
        g.fillRect(0,0,w,h);
        g.setColor(~COLORS[curr]);
        g.drawRect(0,0,w-1,h-1);
        String name=Common.getLocalizedString("settings.color","Color")+" "+(curr+1);
        g.drawString(name,w/2,(h-g.getFont().getHeight())/2,Graphics.HCENTER|Graphics.TOP);
    }

    /**
     * ���������� ������� �������
     * @param command �������
     * @param displayable ��������
     */
    public void commandAction(Command command, Displayable displayable) {
        if(command==cmdOk){
            curr=colorChooser.getSelected();
        }
        midlet.show(owner);
    }

    /**
     * ���������� ������� �������� ����
     * @param command �������
     * @param item �������-��������
     */
    public void commandAction(Command command, Item item) {
        colorChooser.setSelected(curr);
        midlet.show(colorChooser);
    }
}
