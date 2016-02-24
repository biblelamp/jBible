/**
 * SelectMenuItem.java - Описывает элемент меню для выбора из нескольких вариантов
 */

package jbible.mobile.ui;

import javax.microedition.lcdui.*;
import java.io.*;
import jbible.mobile.*;

/**
 * Элемент меню для выбора из нескольких вариантов
 * @author Константин Беляк, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public abstract class SelectMenuItem extends MenuItem {

    /**
     * Колличество элементов
     */
    private int itemCount;

    /**
     * Номер выбранного элемента
     */
    private int selIndex;

    /**
     * Внутреннее смещение (между стрелками и прямоугольником меню)
     */
    private final int INSPACE=10;

    /**
     * Координата y центра пункта меню
     */
    private int centerY;

    /**
     * Ширина прямоугольника
     */
    private int rectWidth;

    /**
     * Текст надписи
     */
    private String text;

    /**
     * Родительский экран
     */
    private Canvas owner;

    /**
     * Создаёт новый элемент меню
     * @param itemCount Число выбираемых пунтов 
     * @param selIndex Номер выбранного пункта
     * @param owner Родительский экран
     */
    public SelectMenuItem(int itemCount, int selIndex,Canvas owner) {
        this.itemCount=itemCount;
        this.selIndex=selIndex;
        this.owner=owner;
    }

    /**
     * Обновляет положение пункта меню в связи с возможными изменениями параметров родительского экрана
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
     * Получает текст выбранного пункта (метод должен быть реализован для каждого пункта меню)
     * @param index Номер пункта меню
     * @return Текст выбранного пункта
     */
    public abstract String getItemText(int index);

    /**
     * Вызывается при выборе пункта
     * @param index Выбранный пункт
     */
    public abstract void select(int index);

    /**
     * Устанавливает выбранный пункт
     * @param index Номер пункта
     */
    public void setSelectedIndex(int index){
        selIndex=index;
        select(selIndex);
        update();
    }

    /**
     * Устанавливает число пунктов
     * @param count Число пунктов
     */
    public void setItemCount(int count){
        this.itemCount=count;
        this.selIndex=0;
        select(selIndex);
        update();
    }

    /**
     * Обработка действия пользователя (вызывается меню)
     * @param action Действие пользователя (полученное с помощью getGameAction)
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
     * Отрисовка пункта меню
     * @param g Контекст для отрисовки
     */
    public void paint(Graphics g){
        g.setColor((isActive()?Common.SETTINGS.getBgTextColor():Common.SETTINGS.getBgTitleColor()));
        if(isActive()) g.fillRect(Common.SETTINGS.getHorizontalSpace()+/*left.getWidth()*/+INSPACE,getY()+1,rectWidth,getHeight()-1);
        g.setColor((isActive()?Common.SETTINGS.getFgTitleColor():Common.SETTINGS.getFgTextColor()));
        g.drawString(text,getWidth()/2,getY()+Common.SETTINGS.getVerticalSpace(),Graphics.TOP | Graphics.HCENTER);
        if(isActive())g.drawRect(Common.SETTINGS.getHorizontalSpace()+/*left.getWidth()*/+INSPACE,getY()+1,rectWidth,getHeight()-1);
    }

    /**
     * Получает номер выбранного пункта
     * @return Номер выбранного пункта
     */
    public int getSelectedIndex() {
        return selIndex;
    }
}