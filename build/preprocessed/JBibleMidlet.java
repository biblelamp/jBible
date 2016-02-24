/**
 * JBibleMidlet.java - ��������� ������ jBible
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
 * ������ jBible - ��������� ������
 * @author Konstantin K. Beliak, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class JBibleMidlet extends MIDlet implements MidletInterface {
    
    /**
     * ������ �����������
     */
    public JBibleMidlet(){
        super();
    }
    
    /**
     * ������ �� ������� �����
     */
    private ChapterScreen screen;
    
    /**
     * ��������� �������
     */
    private BibleReader reader;
    
    /**
     * ����� �������, ����������� ����������
     * @throws javax.microedition.midlet.MIDletStateChangeException ������������� ��� �������� ������� �������
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
     * ����������� ������� ����� � ������ ����������� (������ ��� ������������ ������ � �����)
     */
    public void reloadScreens(){
        screen=new ChapterScreen(reader,screen.currentPage(),this);
        show(screen);
    }
    
    /**
     * ������������ ���������� (���� �� ������������)
     */
    protected void pauseApp() {
    }

    /**
     * ����������� ����������
     * @param b �� ������������
     */
    protected void destroyApp(boolean b) {
        screen.savePage();
        Storage.saveSettings(Common.SETTINGS);
        Bookmark.save();
        notifyDestroyed();
    }
    
    /**
     * ���������� �� ������� ������
     * @param d ������������ ������
     */
    public void show(Displayable d){
        Display.getDisplay(this).setCurrent(d);        
    }

    /**
     * ������� ���������
     * @param a ���������
     * @param next ������ ��� ����������� ����� ����, ��� ��������� ����� ��������� �������������
     */
    public void showAlert(Alert a,Displayable next){
        Display.getDisplay(this).setCurrent(a,next);
    }
    
    /**
     * ��������� ������ �������
     */
    public void quit(){
        destroyApp(true);
        
    }

    /**
     * ������� ����� � ���������� �� ������ � ��������� ���������� ���������
     * @param e ����������
     * @param msg �������������� ������ ��������� (���� �� ������������)
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
