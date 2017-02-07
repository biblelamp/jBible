/*
 * SearchForm.java - ����� ��� ������� ���������� ������ 
 *
 */

package jbible.mobile.ui;

import jbible.data.*;
import jbible.mobile.*;
import javax.microedition.lcdui.*;

/**
 * ����� ��� ������� ���������� ������
 * @author ����� ����������, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public class SearchForm extends Form implements CommandListener {
    
    /**
     * ��������� ���� ��� ����� ����� ������
     */
    private TextField fieldKey=new TextField(Common.getLocalizedString("search.form.key","Key"),Common.SETTINGS.getLastSearchKey(),20,TextField.ANY);    
    
    /**
     * ��������� ���� ��� ����� ������������ ������� ������
     */
    private TextField fieldSize=new TextField(Common.getLocalizedString("search.form.size","Max results"),Common.SETTINGS.getMaxResCount()+"",3,TextField.NUMERIC);    
    
    /**
     * ������� ������ �����
     */
    private Command cmdOk=new Command(Common.getLocalizedString("search.form.ok","OK"),Command.OK,1);
    
    /**
     * ������� �������� �����
     */
    private Command cmdCancel=new Command(Common.getLocalizedString("search.form.cancel","Cancel"),Command.BACK,1);
    
    /**
     * �������� ����� (����������� ����), � ������� ����� ������������ �����
     */
    private ChapterScreen screen;
    
    /**
     * ���������� ����������� ������
     */
    private static SearchResults lastSearchRes;
    
    /**
     * ������� ������ ������ (�����)
     */
    private ChoiceGroup bookFrom;
    
    /**
     * ������� ���������� ������ (�����)
     */
    private ChoiceGroup bookTo;

    /**
     * ����� ��� ���������� ������ ����� ������ ������
     */
    private final String fromText=Common.getLocalizedString("search.bookfrom","Search start");
    
    /**
     * ����� ��� ���������� ������ ����� ���������� ������
     */
    private final String toText=Common.getLocalizedString("search.bookto","Search end");
    
    /**
     * ������ ����
     */
    private Book[] list;
    
    /**
     * ������ ����� ��� ������ ��������� ������
     * @param screen �������� ����� (����������� �����) � ������� ����� ������������ �����
     */
    public SearchForm(ChapterScreen screen) {
        super(Common.getLocalizedString("search.form.title","New search"));
        list=screen.getTextSource().getBibleContents();
        String[] strList=new String[list.length];
        for(int i=0;i<strList.length;i++){
            strList[i]=list[i].getName();
        }
        bookFrom=new ChoiceGroup(fromText,ChoiceGroup.POPUP,strList,null);
        bookTo=new ChoiceGroup(toText,ChoiceGroup.POPUP,strList,null);
        bookFrom.setSelectedIndex(Common.SETTINGS.getBookFrom(),true);
        bookTo.setSelectedIndex(Common.SETTINGS.getBookTo(),true);
        append(fieldKey);
        append(bookFrom);
        append(bookTo);
        append(fieldSize);
        addCommand(cmdOk);
        addCommand(cmdCancel);
        setCommandListener(this);
        this.screen=screen;
    }

    /**
     * ���������� ������
     * @param command �������
     * @param displayable ��������
     */
    public void commandAction(Command command, Displayable displayable) {
        if(command==cmdOk){
            String key=fieldKey.getString();
            if(key.length()<3){
                Alert a=new Alert(Common.getLocalizedString("input.error","Input error"),
                        Common.getLocalizedString("input.error.shortkey","Search key is too short"),
                        null,AlertType.ERROR);
                screen.getMidletInterface().showAlert(a,this);
                return;
            }
            String num=fieldSize.getString();
            int pageSize=0;
            try{
                pageSize=Integer.parseInt(num);
            }catch(NumberFormatException e){}
            if(pageSize<1){
                Alert a=new Alert(Common.getLocalizedString("input.error","Input error"),
                        Common.getLocalizedString("input.error.badpagesize","Result limit is too small"),
                        null,AlertType.ERROR);
                screen.getMidletInterface().showAlert(a,this);
                return;
            }
            int from=bookFrom.getSelectedIndex();
            int to=bookTo.getSelectedIndex();
            if(from>to){
                Alert a=new Alert(Common.getLocalizedString("input.error","Input error"),
                        Common.getLocalizedString("input.error.badrange","Bad search range"),
                        null,AlertType.ERROR);
                screen.getMidletInterface().showAlert(a,this);
                return;
            }
            Common.SETTINGS.setLastSearchKey(key);
            Common.SETTINGS.setBookFrom(from);
            Common.SETTINGS.setBookTo(to);
            Common.SETTINGS.setMaxResCount(pageSize);
            lastSearchRes=new SearchResults(screen,key,from,to,pageSize);
            lastSearchRes.show(screen);
        }
        if(command==cmdCancel){
            screen.getMidletInterface().show(screen);
        }
    }

    /**
     * ���������� ���������� ����������� ������. ���� �������� �� ����, ��������� ����� �������� ���������� ������ ������
     * @param newSearch ���������� ������ ����� �����
     * @param owner �������� ����� (����������� �����) � ������� ����� ������������ �����
     */
    public static void searchResults(boolean newSearch,ChapterScreen owner){
        if(newSearch || lastSearchRes==null){
            SearchForm f=new SearchForm(owner);
            f.screen.getMidletInterface().show(f);
        }else{
            lastSearchRes.update(owner);
            lastSearchRes.show(owner);
            //JBibleMidlet.show(lastSearchRes);
        }
    }
    
}
