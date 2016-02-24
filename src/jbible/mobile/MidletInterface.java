/*
 * MidletInterface.java - интерфейс для доступа к мидлету из других пакетов 
 *
 */

package jbible.mobile;

import javax.microedition.lcdui.*;

/**
 * Интерфейс для доступа к мидлету из других пакетов 
 * @author Konstantin K. Beliak, <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public interface MidletInterface {
    
    /**
     * Завершает работу мидлета
     */
    public void quit();
    
    /**
     * Отображает на экране объект 
     * @param d Отображаемый объект(экран)
     */
    public void show(Displayable d);
 
    /**
     * Прекращает выполнение программы и выводит сообщение об ошибке
     * @param e Источник ошибки
     * @param msg Сообщение об ошибке
     */
    public void fatalError(Exception e,String msg);
 
    /**
     * Выводит сообщение
     * @param a Сообщение
     * @param next Объект для отображения после того, как сообщение будет прочитано пользователем
     */
    public void showAlert(Alert a,Displayable next);
 
    /**
     * Перегружает текущий экран с новыми настройками (такими как изменившаяся локаль и шрифт)
     */
    public void reloadScreens();
    
}