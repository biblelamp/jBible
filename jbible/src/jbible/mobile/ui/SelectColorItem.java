/*
 * SelectColorItem.java - компонент для выбора цвета
 *
 */

package jbible.mobile.ui;

import jbible.mobile.*;
import javax.microedition.lcdui.*;
import java.io.*;

/**
 * Компонент для выбора цвета
 * @author Беляк Константин, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class SelectColorItem extends CustomItem implements CommandListener, ItemCommandListener {
    
    /**
     * Комманда Принять цвет
     */
    private Command cmdOk=new Command(Common.getLocalizedString("settings.selectcolor.ok","OK"),Command.OK,0);
    
    /**
     * Комманда Отменить выбор цвета
     */
    private Command cmdCancel=new Command(Common.getLocalizedString("settings.selectcolor.cancel","Cancel"),Command.BACK,0);
    
    /**
     * Комманда Выбрать цвет
     */
    private Command cmdChange=new Command(Common.getLocalizedString("settings.changecolor","Change"),Command.ITEM,0);
    
    /**
     * Индекс текущего цвета (от 0)
     */
    private int curr=0;
    
    /**
     * Ширина одного элемента
     */
    private int W=96;
    
    /**
     * Высота одного элемента
     */
    private int H=Font.getDefaultFont().getHeight()+2*Common.SETTINGS.getVerticalSpace();
    
    /**
     * Основной экран (отображения главы) с которым будет ассоциирован поиск
     */
    private Form owner;
    
    /**
     * Массив цветов
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
     * Класс экрана для выбора цвета
     */
    private class ColorChooser extends Canvas {
        
        /**
         * Индекс выбраного цвета
         */
        private int sel=0;
        
        /**
         * Родительский элемент
         */
        private SelectColorItem owner;
        
        /**
         * Высота одного элемента
         */
        private int H=Font.getDefaultFont().getHeight()+2*Common.SETTINGS.getVerticalSpace();
        
        /**
         * Полная высота одного элемента (вместе с отступами)
         */
        private int H_FULL=H+Common.SETTINGS.getVerticalSpace();
        
        /**
         * Горизонтальный отступ
         */
        private int H_SPACE=Common.SETTINGS.getHorizontalSpace();
        
        /**
         * Индекс цвета, отображаемого на экране в данный момент в самой верхней позиции списка
         */
        private int top;
        
        /**
         * Число цветов (элементов списка, отображаемых одновременно на дисплее)
         */
        private int disp;
        
        /**
         * Слово Цвет
         */
        private String COLOR_WORD=Common.getLocalizedString("settings.color","Color");
        
        /**
         * Создаёт новый экран выбора цвета.
         * @param owner Родительский элемент
         */
        public ColorChooser(SelectColorItem owner){
            this.owner=owner;
           
            disp=getHeight()/H_FULL;            
        }
        
        /**
         * Обновляет (прокручивает) список при необходимости
         */
        public void updateTop(){
            if(sel<=top && top>0) top--;
            if(sel>=top+disp-1 && top+disp<COLORS.length) top++;
        }
        
        /**
         * Изменяет текущий выбранный цвет
         * @param newSel Новый выбранный цвет
         */
        public void setSelected(int newSel){
            sel=newSel;
            if(sel==0) top=0; else top=sel-1;            
            repaint();
        }
        
        /**
         * Возвращает выбранный цвет (индекс, от 0)
         * @return Выбранный цвет (индекс, от 0)
         */
        public int getSelected(){
            return sel;
        }
        
        /**
         * Перерисовка экрана
         * @param g Графический контекст
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
         * Обработчик повторного нажатия на клавишу
         * @param key Код нажатой клавиши
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
         * Обработчик нажатия на клавишу
         * @param key Код нажатой клавиши
         */
        protected void keyPressed(int key) {
            keyRepeated(key);
        }
    };

     
    /**
     * Интерфейс мидлета
     */
    private MidletInterface midlet;

    /**
     * Родительский элемент формы
     */
    private ColorChooser colorChooser=new ColorChooser(this);
    
    /**
     * Создаёт элемент формы длы выбора цвета
     * @param text Текст подписи
     * @param color Индекс текущего цвета (от 0)
     * @param midlet Интерфейс мидлета
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
     * Ассоциирует элемент с формой
     * @param f Форма
     */
    public void setOwner(Form f){
        this.owner=f;
    }
    
    /**
     * Получает индекс цвета в массиве цветов
     * @param color Цвет в виде 0xRRGGBB
     * @return Индекс цвета в массиве цветов или -1, если цвет не найден
     */
    public int getIndex(int color){
        for(int i=0;i<COLORS.length;i++)
            if(COLORS[i]==color) return i;
        return -1;
    }

    /**
     * Возвращает минимальную ширину элемента
     * @return Минимальная ширина элемента
     */
    protected int getMinContentWidth() {
        return W;
    }

    /**
     * Возвращает минимальную высоту элемента
     * @return Минимальная высота элемента
     */
    protected int getMinContentHeight() {
        return H;
    }

    /**
     * Возвращает предпочитаемую ширину элемента
     * @param w Предлагаемая ширина, если параметр больше 0
     * @return Предпочитаемая ширина элемента
     */
    protected int getPrefContentWidth(int w) {
        if(w>0) return w;
        return (owner==null)?W:owner.getWidth()-2*Common.SETTINGS.getHorizontalSpace();
    }

    /**
     * Возвращает предпочитаемую высоту элемента
     * @param h Предлагаемая высота, если параметр больше 0
     * @return Предпочитаемая высота элемента
     */
    protected int getPrefContentHeight(int h) {
        if(h>0 && h<2*H) return h;
        return H;
    }
   
    /**
     * Возвращает индекс выбранного цвета в массиве COLORS
     * @return Индекс выбранного цвета в массиве COLORS
     */
    public int getSelectedColor(){
        return COLORS[curr];
    }
    
    /**
     * Перерисовка элемента
     * @param g Графический контекст
     * @param w Ширина
     * @param h Высота
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
     * Обработчик команды Выбрать
     * @param command Команда
     * @param displayable Источник
     */
    public void commandAction(Command command, Displayable displayable) {
        if(command==cmdOk){
            curr=colorChooser.getSelected();
        }
        midlet.show(owner);
    }

    /**
     * Обработчик команды Изменить цвет
     * @param command Команда
     * @param item Элемент-источник
     */
    public void commandAction(Command command, Item item) {
        colorChooser.setSelected(curr);
        midlet.show(colorChooser);
    }
}
