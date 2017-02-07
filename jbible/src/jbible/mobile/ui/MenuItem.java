/**
 * MenuItem.java - Описывает элемент меню
 */

package jbible.mobile.ui;

import javax.microedition.lcdui.*;
import java.io.*;
import jbible.mobile.*;

/**
 * Элемент меню
 * @author Konstantin K. Beliak, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public abstract class MenuItem {
    
    /**
     * Признак активности пункта меню
     */
    private boolean active;
    
    /**
     * Ширина родительского экрана
     */
    private int width;
    
    /**
     * Высота родительского экрана
     */
    private int height;
    
    /**
     * Смещение пункта меню по оси y
     */
    private int y;
    
    /*public MenuItem(int width, int height, int y){
        this.setWidth(width);
        this.setHeight(height);
        this.setY(y);
    }*/
    
    /**
     * Обработка действия пользователя
     * @param action Действие пользователя (полученное с помощью getGameAction)
     */
    public void action(int action){
    
    }
    
    /**
     * Отрисовка пункта меню (должна вызываться меню)
     * @param g Контекст для отрисовки
     */
    public abstract void paint(Graphics g);

    /**
     * Проверяет, является ли пункт меню активным в данный момент
     * @return true если пункт меню активен и false в противном случае
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Делает пункт меню активным или неактивным
     * @param active true если пункт меню должен стать активным и false в противном случае
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Возвращает ширину родительского экрана
     * @return Ширина родительского экрана
     */
    public int getWidth() {
        return width;
    }

    /**
     * Возвращает смещение пункта меню по оси y
     * @return Смещение пункта меню по оси y
     */
    public int getY() {
        return y;
    }

    /**
     * Возвращает высоту пункта меню
     * @return ВЫсота пункта меню
     */
    public int getHeight() {
        return height;
    }

    /**
     * Устанавливает ширину родительского экрана
     * @param width Ширина родительского экрана
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Устанавливает высоту пункта меню
     * @param height Высота пункта меню
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Устанавливает смещение пункта меню по оси y
     * @param y Смещение пункта меню по оси y
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Обновляет параметры меню в связи с возможным изменением размеров экрана
     */
    public void update(){
    }
    
}