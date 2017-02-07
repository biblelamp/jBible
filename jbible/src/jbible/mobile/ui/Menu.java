/**
 * Menu.java - Описывает отображаемое на экране меню
 */

package jbible.mobile.ui;

import jbible.mobile.*;
import javax.microedition.lcdui.*;

/**
 * Отображаемое на экране меню
 * @author Konstantin K. Beliak, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class Menu {
    
    /**
     * Родительский экран
     */
    private Canvas owner;
    
    /**
     * Номер выбранного(активного) пункта меню
     */
    private int selIndex=0;
    
    /**
     * Список пунктов меню
     */
    private MenuItem[] items;
    
    /**
     * Отображается ли меню на экране
     */
    private boolean visible=false;
    
    /**
     * Высота элемента меню
     */
    private final int ITEM_HEIGHT=Font.getDefaultFont().getHeight()+Common.SETTINGS.getVerticalSpace();
    
    /**
     * Смещение по оси y начала меню
     */
    private int dy;
    
    /**
     * Высота меню
     */
    private int menuHeight;
    
    /**
     * Название меню
     */
    private String caption;
    
    /**
     * Создаёт новое меню
     * @param itemCount Число элементов
     * @param owner Родительский экран (на котором меню рисуется)
     * @param sel Выбранный пункт меню
     * @param caption Название меню (надпись заголовка)
     */
    public Menu(int itemCount,Canvas owner,int sel,String caption) {
        items=new MenuItem[itemCount];
        this.owner=owner;
        this.selIndex=sel;
        this.caption=caption;
        update();
    }

    /**
     * Обновляет меню в связи с возможным изменением размера экрана
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
     * Устанавливает элемент меню
     * @param item Элемент меню
     * @param index Номер элемента
     */
    public void setItem(MenuItem item, int index){
        items[index]=item;
        item.setWidth(owner.getWidth());
        item.setHeight(ITEM_HEIGHT);
        item.setY(dy+3*Common.SETTINGS.getVerticalSpace()+(ITEM_HEIGHT+Common.SETTINGS.getVerticalSpace())*(index+1));
        item.update();                
    }

    /**
     * Отображает меню на экране
     */
    public void show(){
        update();
        items[selIndex].setActive(true);
        visible=true;
        owner.repaint();
    }

    /**
     * Скрывает меню
     */
    public void hide(){
        visible=false;
        owner.repaint();
    }
    
    /**
     * Проверяет, является ли меню видимым в данный момент
     * @return true если меню отображено на экране, false в противном случае
     */
    public boolean isVisible(){
        return visible;
    }

    /**
     * Обработка действия пользователя (должна быть вызвана родительским экраном)
     * @param action Действие пользователя (полученное с помощью getGameAction)
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
     * Перекрывается для обработки действия FIRE
     * @param selIndex Номер элемента меню, активного в данный момент
     */
    public void fire(int selIndex){
    }

    /**
     * Отрисовка меню (должна быть вызвана родительским экраном)
     * @param g Контекст для отрисовки
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
     * Срабатывание выбора пункта меню
     */
    public void fire(){
        fire(selIndex);
    }

}