/**
 * Settings.java - ��������� ��������� ���������� ����������
 */

package jbible.mobile.ui;

import java.io.*;
import javax.microedition.lcdui.*;
import jbible.mobile.*;

/**
 * ��������� ���������� ����������
 * @author ���������� �����, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 * @author ������ ������, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 * @author �������� ������, e-mail: <a href="mailto:webmaster@doposle.ru">webmaster@doposle.ru</a>
 */
public class Settings {
    
    /**
     * ��������� ������������� �����/����
     */
    private Bookmark lastPage=new Bookmark(1,1,1);
    
    /**
     * �����
     */
    private Font font=Font.getDefaultFont();
    
    /**
     * ������� �������� �� �����
     */
    private int verseSplit=0;
    
    /**
     * ���� ���� ������
     */
    private int bgTextColor=0xffffcc;
    
    /**
     * ���� ������
     */
    private int fgTextColor=0x333333;
    
    /**
     * ���� ���� ���������
     */
    private int bgTitleColor=0xc0c0c0;//0xffcc33;
    
    /**
     * ���� ���� ���������
     */
    private int fgTitleColor=0x000000;//0x990000;
    
    /**
     * ������
     */
    private int locale=-1;
         
    /**
     * ���� ���������� ������
     */
    private String lastSearchKey="";

    /**
     * ����� �����, � ������� ���������� �����
     */
    private int bookFrom=0;
    
    /**
     * ����� �����, �� ������� ������������� �����
     */
    private int bookTo=0;
    
    /**
     * ����������� �� ������������ ����� ����������� ������
     */
    private int resCount=100;

    /**
     * ������ ����� ��� �������� �������� �� ���������� �� ���������
     */
    public Settings(){
    }
    
    /**
     * ������ ����� ��� �������� �������� �� ����������, ������� �������� �� ������� ����
     * @param data ������ ���� (������ ��������� � �����������)
     * @throws java.io.IOException ������������� ��� �������� ������� ������ ��� ��� ������ �����-������
     */
    public Settings(byte[] data) throws IOException {
        ByteArrayInputStream is=new ByteArrayInputStream(data);
        load(is);
    }
    
    /**
     * ��������� ��������� �� ������ �����
     * @param stream ����� �����
     * @throws java.io.IOException ������������� ��� �������� ������� ������ ��� ��� ������ �����-������
     */
    public void load(InputStream stream) throws IOException {
        DataInputStream dis=new DataInputStream(stream);
        int fontSize=dis.readInt();
        int fontStyle=dis.readInt();
        setFont(Font.getFont(Font.FACE_PROPORTIONAL,fontStyle,fontSize));
        setVerseSplit(dis.readInt());
        setBgTextColor(dis.readInt());
        setFgTextColor(dis.readInt());
        setBgTitleColor(dis.readInt());
        setFgTitleColor(dis.readInt());
        setLocale(dis.readInt());
        byte[] bookmark=new byte[12];
        dis.read(bookmark);
        setLastPage(new Bookmark(bookmark));
        setLastSearchKey(dis.readUTF());
        setBookFrom(dis.readInt());
        setBookTo(dis.readInt());
        setMaxResCount(dis.readInt());
    }
    
    /**
     * ��������� ��������� � �������� ������
     * @param stream ����� ������
     * @throws java.io.IOException ������������� ��� ������ �����-������
     */
    public void save(OutputStream stream) throws IOException{
        DataOutputStream dos=new DataOutputStream(stream);
        dos.writeInt(getFont().getSize());
        dos.writeInt(getFont().getStyle());
        dos.writeInt(getVerseSplit());
        dos.writeInt(getBgTextColor());
        dos.writeInt(getFgTextColor());
        dos.writeInt(getBgTitleColor());
        dos.writeInt(getFgTitleColor());
        dos.writeInt(getLocale());
        dos.write(getLastPage().toByteArray());
        dos.writeUTF(lastSearchKey);
        dos.writeInt(bookFrom);
        dos.writeInt(bookTo);
        dos.writeInt(resCount);
    }
    
    /**
     * ���������� ������� �����
     * @return ������� �����
     */
    public Font getFont(){
        return font;
    }
    
    /**
     * ���������� ������� ���� ����� ���� ������
     * @return ������� ���� ���� ������
     */
    public int getBgTextColor(){
        return bgTextColor;
    }

    /**
     * ���������� ������� ���� ������
     * @return ���� ������
     */
    public int getFgTextColor(){
        return fgTextColor;
    }
    
    /**
     * ���������� ������� ������ ��������������� ������� ��� ��������� ����������
     * @return ������� ������ ��������������� ������� ��� ��������� ����������
     */
    public int getHorizontalSpace(){
        return 2;
    }

    /**
     * ���������� ������� ������ ������������� ������� ��� ��������� ����������
     * @return ���������� ������� ������ ������������� ������� ��� ��������� ����������
     */
    public int getVerticalSpace(){
        return 2;
    }
    
    /**
     * ���������� ������� ���� ������ ���������
     * @return ������� ���� ������ ���������
     */
    public int getFgTitleColor(){
        return fgTitleColor;
    }

    /**
     * ���������� ������� ���� ���� ���������
     * @return ������� ���� ���� ���������
     */
    public int getBgTitleColor(){
        return bgTitleColor;
    }
    
    /**
     * �������� ��������� ���������� �����/����
     * @return ��������, ����������� �� ��������� ���������� �����/����
     */
    public Bookmark getLastPage(){
        return lastPage;
    }

    /**
     * ������������� ��������� ���������� �����/����
     * @param lastPage ��������� ���������� �����/����
     */
    public void setLastPage(Bookmark lastPage) {
        this.lastPage = lastPage;
    }

    /**
     * ������������� �����
     * @param font �����
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * ������������� ���� ����
     * @param bgTextColor ���� ����
     */
    public void setBgTextColor(int bgTextColor) {
        this.bgTextColor = bgTextColor;
    }

    /**
     * ������������� ���� ������
     * @param fgTextColor ���� ������
     */
    public void setFgTextColor(int fgTextColor) {
        this.fgTextColor = fgTextColor;
    }

    /**
     * ������������� ���� ���� ���������
     * @param bgTitleColor ���� ���� ���������
     */
    public void setBgTitleColor(int bgTitleColor) {
        this.bgTitleColor = bgTitleColor;
    }

    /**
     * ������������� ���� ������ ���������
     * @param fgTitleColor ���� ������ ���������
     */
    public void setFgTitleColor(int fgTitleColor) {
        this.fgTitleColor = fgTitleColor;
    }

    /**
     * ���������� ������ ������� ������
     * @return ������ ������� ������ (�� 0)
     */
    public int getLocale() {
        return locale;
    }

    /**
     * ������������� ������ ������� ������
     * @param locale ����� ������ ������ (�� 0)
     */
    public void setLocale(int locale) {
        this.locale = locale;
    }
    
    /**
     * ���������� ����� ���������
     * @param back ����� � �������� ��������� ��������� ����� ���������� ���������
     */
    public void showEditScreen(final ChapterScreen back){
        Form f=new Form(Common.getLocalizedString("settings.title","Settings")){
            protected void keyPressed(int key){
                System.out.println("key="+key);
            }
        };
        
        final SelectColorItem itemTextBg=new SelectColorItem(Common.getLocalizedString("settings.textbg","Text background"),Common.SETTINGS.getBgTextColor(),back.getMidletInterface());
        itemTextBg.setOwner(f);
        final SelectColorItem itemTextFg=new SelectColorItem(Common.getLocalizedString("settings.textfg","Text foreground"),Common.SETTINGS.getFgTextColor(),back.getMidletInterface());
        itemTextFg.setOwner(f);
        final SelectColorItem itemTitleBg=new SelectColorItem(Common.getLocalizedString("settings.titlebg","Title background"),Common.SETTINGS.getBgTitleColor(),back.getMidletInterface());
        itemTitleBg.setOwner(f);
        final SelectColorItem itemTitleFg=new SelectColorItem(Common.getLocalizedString("settings.titlefg","Title foreground"),Common.SETTINGS.getFgTitleColor(),back.getMidletInterface());
        itemTitleFg.setOwner(f);
        
        String[] fontSizes=new String[]{Common.getLocalizedString("settings.fontsmall","Small"),Common.getLocalizedString("settings.fontnormal","Normal"),Common.getLocalizedString("settings.fontbig","Big")};
        final int[] fontSizesInt=new int[]{Font.SIZE_SMALL,Font.SIZE_MEDIUM,Font.SIZE_LARGE};
        final ChoiceGroup itemFontSize=new ChoiceGroup(Common.getLocalizedString("settings.fontsize","Font size"),List.EXCLUSIVE,fontSizes,null);
        for(int i=0;i<fontSizesInt.length;i++)
            if(fontSizesInt[i]==font.getSize()) itemFontSize.setSelectedIndex(i,true);
        //String[] useNewLines=new String[]{Common.getLocalizedString("settings.newlinesyes","Yes"),Common.getLocalizedString("settings.newlinesno","No")};

	String[] verseSplit=new String[]{Common.getLocalizedString("settings.versesplitNo","No split"),Common.getLocalizedString("settings.versesplitYes","Split")};
        final int[] verseSplitInt=new int[]{0,1};
        final ChoiceGroup itemVerseSplit=new ChoiceGroup(Common.getLocalizedString("settings.versesplit","On verse"),List.EXCLUSIVE,verseSplit,null);
        for(int i=0;i<verseSplitInt.length;i++)
	    if(verseSplitInt[i]==getVerseSplit()) itemVerseSplit.setSelectedIndex(i,true);

        /*String[] menuTypes=new String[]{Common.getLocalizedString("settings.menu.simple","Simple"),Common.getLocalizedString("settings.menu.normal","Normal")};
        final ChoiceGroup itemMenuType=new ChoiceGroup(Common.getLocalizedString("settings.menu","Menu type"),List.EXCLUSIVE,menuTypes,null);
        int setMenuType=(Common.SETTINGS.useSimpleMenu())?0:1;
        itemMenuType.setSelectedIndex(setMenuType,true);

        final boolean canChangeLocale=Common.getLocaleNames().length>1;
        String[] localeNames=Common.getLocaleNames();
        final ChoiceGroup itemLocale=new ChoiceGroup(Common.getLocalizedString("settings.locale","Locale"),List.EXCLUSIVE,localeNames,null);
        final int selLocale=Common.SETTINGS.getLocale();
        if(selLocale!=-1) itemLocale.setSelectedIndex(selLocale,true);
        */
        
        f.append(itemFontSize);
        /*if(canChangeLocale) f.append(itemLocale);
        f.append(itemMenuType);*/
        f.append(itemVerseSplit);
        f.append(itemTextBg);
        f.append(itemTextFg);
        f.append(itemTitleBg);
        f.append(itemTitleFg);

        final Command cmdOk=new Command(Common.getLocalizedString("settings.ok","OK"),Command.OK,1);
        final Command cmdCancel=new Command(Common.getLocalizedString("settings.cancel","Cancel"),Command.BACK,1);
        final Displayable disp=back;
        f.addCommand(cmdOk);
        f.addCommand(cmdCancel);
        f.setCommandListener(new CommandListener(){
            public void commandAction(Command command, Displayable displayable) {
                if(command==cmdOk){
                    Settings s=Common.SETTINGS;
                    s.setBgTextColor(itemTextBg.getSelectedColor());
                    s.setFgTextColor(itemTextFg.getSelectedColor());
                    s.setBgTitleColor(itemTitleBg.getSelectedColor());
                    s.setFgTitleColor(itemTitleFg.getSelectedColor());
                    boolean reload=false;
                    int newFontSize=fontSizesInt[itemFontSize.getSelectedIndex()];
                    if(s.getFont().getSize()!=newFontSize){
                        s.setFont(Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_PLAIN,newFontSize));
                        reload=true;
                    }
                    int newVerseSplit=itemVerseSplit.getSelectedIndex();
		    if(s.getVerseSplit()!=newVerseSplit){
		        s.setVerseSplit(itemVerseSplit.getSelectedIndex());
		        reload=true;
		    }
                    /*s.setUseSimpleMenu(itemMenuType.getSelectedIndex()==0);
                    if(canChangeLocale){
                        int newLocale=itemLocale.getSelectedIndex();
                        if(newLocale!=selLocale){
                            //System.out.println("Old locale id="+selLocale+", new locale id="+newLocale);
                            //System.out.println(Common.getLocalizedString("go.ok","Hello1"));
                            s.setLocale(newLocale);
                            Common.reloadLocale();
                            reload=true;
                        }
                    } */   
                    if(reload) back.getMidletInterface().reloadScreens(); else back.getMidletInterface().show(disp);                   
                }
                if(command==cmdCancel){
                    back.getMidletInterface().show(disp);
                }
            }        
        });
        back.getMidletInterface().show(f);
    }

    /**
     * ���������� ������� ��������� �� ���������
     * @return ������� ��������� �� ������
     */
    public int getVerseSplit(){
        return verseSplit;
    }

    /**
     * �������� ��������� ��������� ���������
     * @return ������� ��������� �� ������
     */
    public void setVerseSplit(int verseSplit){
        this.verseSplit = verseSplit;
    }

    /**
     * ���������� ���� ���������� ������
     * @return ���� ���������� ������
     */
    public String getLastSearchKey() {
        return lastSearchKey;
    }

    /**
     * ������������� ���� ���������� ������
     * @param lastSearchKey ���� ���������� ������
     */
    public void setLastSearchKey(String lastSearchKey) {
        this.lastSearchKey = lastSearchKey;
    }
    
    /**
     * ���������� ����� ����� ��� ������ ������
     * @return ����� �����
     */
    public int getBookFrom() {
        return bookFrom;
    }

    /**
     * ������������� ����� ����� ��� ������ ������
     * @param bookFrom ����� ����� ��� ������ ������
     */
    public void setBookFrom(int bookFrom) {
        this.bookFrom = bookFrom;
    }
/**
     * ���������� ����� ��������� ����� ��� ������
     * @return ����� �����
     */
    public int getBookTo() {
        return bookTo;
    }

    /**
     * ������������� ����� ��������� ����� ��� ������
     * @param bookTo ����� ��������� ����� ��� ������
     */
    public void setBookTo(int bookTo) {
        this.bookTo = bookTo;
    }
    
    /**
     * ���������� ����������� �� ������������ ����� ����������� ������
     * @return ����������� �� ������������ ����� ����������� ������
     */
    public int getMaxResCount() {
        return resCount;
    }

    /**
     * ������������� ����������� �� ������������ ����� ����������� ������
     * @param resCount ����������� �� ������������ ����� ����������� ������
     */
    public void setMaxResCount(int resCount) {
        this.resCount = resCount;
    }
}