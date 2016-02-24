/**
 * JBibleMidlet.java - Описывает мидлет jBible
 */

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import java.io.*;
import jbible.data.*;
import jbible.mobile.*;
import jbible.mobile.ui.*;
import jbible.mobile.ui.SearchResults;
import jbible.utils.*;

/**
 * Мидлет jBible - мобильная Библия
 * @author Konstantin K. Beliak, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class JBibleMidlet extends MIDlet implements MidletInterface {
    
    /**
     * Пустой конструктор
     */
    public JBibleMidlet(){
        super();
    }
    
    /**
     * ссылка на текущий экран
     */
    private ChapterScreen screen;
    
    /**
     * Загрузчик текстов
     */
    private BibleReader reader;
    
    /**
     * Метод мидлета, запускающий приложение
     * @throws javax.microedition.midlet.MIDletStateChangeException Выбрасывается при неверном статусе мидлета
     */
    protected void startApp() throws MIDletStateChangeException {
        try{
            reader=new BibleReader();
            screen=new ChapterScreen(reader,null,this);   
            show(screen);
        }catch(Exception e){
            e.printStackTrace();
            fatalError(e,null);
        }
        
    }
    
    /**
     * Перегружает текущий экран с новыми настройками (такими как изменившаяся локаль и шрифт)
     */
    public void reloadScreens(){
        screen=new ChapterScreen(reader,screen.currentPage(),this);
        show(screen);
    }
    
    /**
     * Приостановка приложения (пока не используется)
     */
    protected void pauseApp() {
    }

    /**
     * Уничтожение приложения
     * @param b Не используется
     */
    protected void destroyApp(boolean b) {
        screen.savePage();
        Storage.saveSettings(Common.SETTINGS);
        Bookmark.save();
        notifyDestroyed();
    }
    
    /**
     * Отображает на дисплее объект
     * @param d Отображаемый объект
     */
    public void show(Displayable d){
        Display.getDisplay(this).setCurrent(d);        
    }

    /**
     * Выводит сообщение
     * @param a Сообщение
     * @param next Объект для отображения после того, как сообщение будет прочитано пользователем
     */
    public void showAlert(Alert a,Displayable next){
        Display.getDisplay(this).setCurrent(a,next);
    }
    
    /**
     * Завершает работу мидлета
     */
    public void quit(){
        destroyApp(true);
        
    }

    /**
     * Выводит зкран с сообщением об ошибке и завершает выполнение программы
     * @param e Исключение
     * @param msg Дополнительная строка сообщения (пока не используется)
     */
    public void fatalError(Exception e,String msg){
        Form f=new Form(Common.getLocalizedString("error.fatal.title","FATAL ERROR"));
        f.append(new StringItem(Common.getLocalizedString("error.class","Exception"),": "+e.getClass().getName()));
        f.append(new StringItem(Common.getLocalizedString("error.message","Message"),": "+e.getMessage()));
        show(f);
        f.addCommand(new Command(Common.getLocalizedString("error.fatal.done","Exit"),Command.EXIT,1));
        f.setCommandListener(
            new CommandListener() {
                public void commandAction(Command command, Displayable displayable) {
                    notifyDestroyed();
                }
            }
        );        
    }
    
}
