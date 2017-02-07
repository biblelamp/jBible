/**
 * ChapterScreen.java - Описывает экран для отображения текста главы
 */

package jbible.mobile.ui;

import java.io.*;
import java.lang.*; // добавил 18 сентября
import javax.microedition.lcdui.*;
import jbible.data.*;
import jbible.mobile.*;
import jbible.utils.*;


/**
 * Экран для отображения текста главы
 * @author Константин Беляк, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 * @author Роман Ершков, e-mail: <a href="mailto:roman_er@mail.ru">roman_er@mail.ru</a>
 * @author Сергей Ирюпин, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 */
public class ChapterScreen extends Canvas implements CommandListener, Runnable {
    
    /**
     * Список строк текста
     */
    private String[] lines;
    
    /**
     * Массив номеров строк, на которых начинаются соответствующие стихи
     */
    private int[] links;
    
    /**
     * Текущая строка
     */
    private int curr=0;
    
    /**
     * Высота экрана в строках (для поэкранного листания)
     */
     private int heightScreenPage=1;
    
    /**
     * Книга
     */
    private Book book;
    
    /**
     * Номер текущей главы
     */
    private int chapterNum;
    
    /**
     * Текущий стих
     */
    private int verse=1;
    
    /**
     * Выбраная книга (для меню перехода)
     */
    private Book selectedBook;
    
    /**
     * Выбранная глава (для меню перехода)
     */
    private Chapter selectedChapter;
    
    /**
     * Команда Добавить закладку
     */
    private Command cmdAddBookmark=new Command(Common.getLocalizedString("menuitem.addbookmark","Add bookmark"),Command.SCREEN,2);
    /**
     * Команда Показать закладки
     */
    private Command cmdBookmarks=new Command(Common.getLocalizedString("menuitem.bookmarks","Bookmarks"),Command.SCREEN,2);
    /**
     * Команда Показать результаты поиска
     */
    private Command cmdSearchResults=new Command(Common.getLocalizedString("menuitem.searchres","Search results"),Command.SCREEN,2);
    /**
     * Команда Новый поиск
     */
    private Command cmdNewSearch=new Command(Common.getLocalizedString("menuitem.newsearch","New search"),Command.SCREEN,2);
    /**
     * Комманда Настроить
     */
    private Command cmdSettings=new Command(Common.getLocalizedString("menuitem.settings","Settings"),Command.SCREEN,2);
    /**
     * Комманда О программе
     */
    private Command cmdAbout=new Command(Common.getLocalizedString("menuitem.about","About"),Command.SCREEN,2);
    /**
     * Комманда Выход
     */
    private Command cmdExit=new Command(Common.getLocalizedString("menuitem.exit","Exit"),Command.EXIT,2);
    /**
     * Комманда Вызвать меню перехода
     */
    private Command cmdGoTo=new Command(Common.getLocalizedString("menuitem.goto","GoTo"),Command.OK,1);

    /**
     * Команда Перейти
     */
    private Command cmdDoGoTo=new Command(Common.getLocalizedString("go.ok","GoTo"),Command.OK,1);
    /**
     * Команда Отмена перехода
     */
    private Command cmdDoCancel=new Command(Common.getLocalizedString("go.cancel","Back"),Command.BACK,2);

    /**
     * Источник для загрузки текстов
     */
    private TextSource src;
    
    /**
     * Список книг
     */
    private Book[] books;
    
    /**
     * Элемент меню перехода Стих
     */
    private SelectMenuItem menuItemVerse;
    
    /**
     * Элемент меню перехода Глава
     */
    private SelectMenuItem menuItemChapter;
    
    /**
     * Элемент меню перехода Книга
     */
    private SelectMenuItem menuItemBook;

    /**
     * Экран для отображения закладок
     */
    private BookmarksScreen bookmarksScreen;
    
    /**
     * Признак того что загрузка ещё не завершена
     */
    private boolean loading=true;

    /**
     * Строка Загрузка
     */
    private String loadingString=Common.getLocalizedString("go.wait","Loading")+"..."; 

    /**
     * Строка с номером загружаемой главы
     */
    private String loadingNum="";    

    /**
     * Меню перехода
     */
    private Menu mainMenu=new Menu(3,this,0,Common.getLocalizedString("go.title","Contents")){
        public void fire(int selectedIndex){
            if(selectedIndex<3){
                int bookNum=menuItemBook.getSelectedIndex()+1;
                int chapterNum=menuItemChapter.getSelectedIndex()+1;
                int verseNum=menuItemVerse.getSelectedIndex()+1;
                load(bookNum,chapterNum,verseNum);
            }
            createMenu();
        }
    };

    /**
     * Ссылка на интерфейс мидлета
     */ 
    private MidletInterface midlet;

    /**
     * Создаёт новый экран просмотра главы (основной экран)
     * @param src Источник текстов
     * @param start Загружаемая глава
     * @param midlet Интерфейс мидлета
     */
    public ChapterScreen(TextSource src,Bookmark start,MidletInterface midlet) {
        this.src=src;
        this.midlet=midlet;
        setFullScreenMode(true);
        load((start==null)?Common.SETTINGS.getLastPage():start);

        menuItemVerse=new SelectMenuItem(selectedChapter.getVerseCount(),verse-1,this){
            public void select(int index){
            }
            public String getItemText(int index){
                return Common.getLocalizedString("go.verse","Verse")+" "+(index+1);
            }
        };
        menuItemChapter=new SelectMenuItem(selectedBook.getChaptersCount(),chapterNum-1,this){
            public void select(int index){
                selectedChapter=selectedBook.getChapter(index+1);
                menuItemVerse.setItemCount(selectedChapter.getVerseCount());
            }
            public String getItemText(int index){
                return Common.getLocalizedString("go.chapter","Chapter")+" "+(index+1);
            }
        };
        menuItemBook=new SelectMenuItem(books.length,selectedBook.getNum()-1,this){
            public void select(int index){
                selectedBook=books[index];
                menuItemChapter.setItemCount(selectedBook.getChaptersCount());
            }
            public String getItemText(int index){
                return books[index].getName();
            }
        };

        bookmarksScreen=new BookmarksScreen(src,this,midlet);
        this.setCommandListener(this);

        createMenu();

        mainMenu.setItem(menuItemBook,0);
        mainMenu.setItem(menuItemChapter,1);
        mainMenu.setItem(menuItemVerse,2);
    }

    /**
     * Обновляет экран в соответствии с новыми настройками (шрифт, локаль)
     */
    public void update(){
        int i;
        for(i=0;i<links.length-1;i++){
              if(links[i]<=curr && curr<links[i+1]) break;
        }
        verse=i+1;
        repaint();
    }

    /**
     * Перерисовка экрана
     * @param g Графический контекст
     */
    public void paint(Graphics g){
        g.setFont(Common.SETTINGS.getFont());
        int lineHeight=g.getFont().getHeight();
        g.setColor(Common.SETTINGS.getBgTextColor());
        g.fillRect(0,0,getWidth(),getHeight());
        if(loading){
            Font bold=Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
            Font normal=g.getFont();
            int h=bold.getHeight()+Common.SETTINGS.getVerticalSpace()+normal.getHeight();
            g.setColor(Common.SETTINGS.getFgTextColor());
            g.drawString(loadingString,getWidth()/2,(getHeight()-h)/2,Graphics.TOP|Graphics.HCENTER);
            g.setFont(bold);
            g.drawString(loadingNum,getWidth()/2,(getHeight()-h)/2+h,Graphics.BOTTOM|Graphics.HCENTER);
            return;
        }
        g.setColor(Common.SETTINGS.getBgTitleColor());
        String title=book.getName()+" "+chapterNum+":"+verse;
        Caption titleCaption=new Caption(title);
        Font normFont=g.getFont();
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_BOLD,normFont.getSize()));
        titleCaption.resize(getWidth(),g.getFont());
        String[] titleLines=titleCaption.getLines();
        int titleHeight=lineHeight*titleLines.length+Common.SETTINGS.getVerticalSpace()*2;
        g.fillRect(0,0,getWidth(),titleHeight);
        g.setColor(Common.SETTINGS.getFgTitleColor());
        for(int i=0;i<titleLines.length;i++){
            g.drawString(titleLines[i],Common.SETTINGS.getHorizontalSpace(),Common.SETTINGS.getVerticalSpace()+i*lineHeight,Graphics.TOP|Graphics.LEFT);
        }
        g.drawLine(0,titleHeight,getWidth(),titleHeight);
        g.setFont(normFont);
        int textHeight=getHeight()-titleHeight-Common.SETTINGS.getVerticalSpace();
        int textLineCount=textHeight/lineHeight+1;
        heightScreenPage=textLineCount-1; // запоминаем высоту экрана в строках (-1 строка)
        if(lines.length-curr<textLineCount) textLineCount=lines.length-curr;
        g.setColor(Common.SETTINGS.getFgTextColor());
        for(int i=0;i<textLineCount;i++){
            if(Common.ALIGN_RIGHT) g.drawString(lines[curr+i],getWidth()-Common.SETTINGS.getHorizontalSpace(),titleHeight+i*lineHeight,Graphics.TOP|Graphics.RIGHT);
            else g.drawString(lines[curr+i],Common.SETTINGS.getHorizontalSpace(),titleHeight+i*lineHeight,Graphics.TOP|Graphics.LEFT);
        }
        mainMenu.paint(g);
    }

    /**
     * Перехват повторного нажатия на клавишу
     * @param key Код нажатой клавиши
     */
    public void keyRepeated(int key){
        keyPressed(key);
    }
    
    /**
     * Перехват нажатия на клавишу
     * @param key Код нажатой клавиши
     */
    public void keyPressed(int key){
        if(loading) return;
        int action=getGameAction(key); // превращаем код в платформонезависимый
        if(mainMenu.isVisible()) mainMenu.action(action);
        else{
            switch(action){
                case UP:
                case LEFT:
                    if(curr>0 && action!=LEFT){
                        curr--;
                        update();                        
                    }else{
                            int bookNum=book.getNum();
                            if(chapterNum>1) load(bookNum,chapterNum-1,(action!=LEFT)?book.getChapter(chapterNum-1).getVerseCount():1); 
                            else if(bookNum>1){
                                int newBookNum=bookNum-1;
                                int newChapterNum=books[newBookNum-1].getChaptersCount();
                                int newVerseNum=(action!=LEFT)?books[newBookNum-1].getChapter(newChapterNum).getVerseCount():1;
                                load(newBookNum,newChapterNum,newVerseNum);                                
                            }    
                    }  
                    break;
                case DOWN:
                case RIGHT:                    
                    if(curr<lines.length-1 && action!=RIGHT){
                        curr++;
                        update();                        
                    }else{
                        if(chapterNum<book.getChaptersCount()) load(book.getNum(),chapterNum+1,1);
                        else if(book.getNum()<books.length){
                            load(book.getNum()+1,1,1);
                        }
                    }
                    break;
                case FIRE:
                    commandAction(cmdGoTo,this);
                    break;
            }
            /*
             * Обрабатываем "чистый код" так как функция getGameAction()
             * возвращает в разных эмуляторах разные коды
             * одних и тех же клавиш. Памятка по кодам клавиш:
             *  [1] [2] [3] -> [49] [50] [51]
             *  [4] [5] [6] -> [52] [53] [54]
             *  [7] [8] [9] -> [55] [56] [57]
             *  [*] [0] [#] -> [42] [48] [35]
             */
            switch(key){
                case 42: // клавиша [*] новый поиск
                    commandAction(cmdNewSearch,this);
                    break;
                case 48: // клавиша [0] список закладок
                    commandAction(cmdBookmarks,this);
                    break;
                case 35: // клавиша [#] поставить закладку
                    commandAction(cmdAddBookmark,this);
                    break;
                case 49: // клавиша [1] в начало главы
                    curr=0;
                    update();
                    break;
                case 55: // клавиша [7] в конец главы
                    curr=lines.length-heightScreenPage;
                    update();
                    break;
                case 51: // клавиша [3] страница вверх
                    if(curr>=heightScreenPage){
                        curr=curr-heightScreenPage;
                        update();
                    }else{
                        int bookNum=book.getNum();
                        if (curr>0) {
                             curr=0;
                             update();
                         }else{
                            if(chapterNum>1) load(bookNum,chapterNum-1,book.getChapter(chapterNum-1).getVerseCount());
                            else if(bookNum>1){
                                    int newBookNum=bookNum-1;
                                    int newChapterNum=books[newBookNum-1].getChaptersCount();
                                    int newVerseNum=books[newBookNum-1].getChapter(newChapterNum).getVerseCount();
                                    load(newBookNum,newChapterNum,newVerseNum);
                                 }
                         }
                    } 
                    break;
                case 57: // клавиша [9] страница вниз
                    if(curr<lines.length-heightScreenPage){
                        curr=curr+heightScreenPage;
                        update();
                    }else{
                        if(chapterNum<book.getChaptersCount()) load(book.getNum(),chapterNum+1,1);
                        else if(book.getNum()<books.length){
                                load(book.getNum()+1,1,1);
                              }     
                    }
                    break;
            }
        }
    }

    /**
     * Обработчик событий
     * @param c Команда
     * @param d Источник
     */
    public void commandAction(Command c,Displayable d){
        if(c==cmdGoTo) {
            removeMenu();
            menuItemBook.setSelectedIndex(book.getNum()-1);
            menuItemChapter.setSelectedIndex(chapterNum-1);
            menuItemVerse.setSelectedIndex(verse-1);
            mainMenu.show();
        }
        if(c==cmdExit){
            midlet.quit();
        }
        if(c==cmdAddBookmark){
            Bookmark.addBookmark(book.getNum(),chapterNum,verse);
        }
        if(c==cmdBookmarks){
            bookmarksScreen.update();
            midlet.show(bookmarksScreen);
        }
        if(c==cmdSearchResults){
            SearchForm.searchResults(false,this);
        }
        if(c==cmdNewSearch){
            SearchForm.searchResults(true,this);
        }
        if(c==cmdSettings){
            Common.SETTINGS.showEditScreen(this);
        }
        if(c==cmdAbout){
            final ChapterScreen scr=this;
            final Command cmdClose=new Command(Common.getLocalizedString("help.about.close","Back"),Command.BACK,1);
            Form f=new Form(Common.getLocalizedString("help.about.title","About"));
            Image icon=null;
            try{
                icon=Image.createImage(Common.ICON_FILE);
            }catch(IOException e){}    
            f.append(new ImageItem(null,icon,Item.LAYOUT_CENTER,"JBible icon"));
            f.append(new StringItem(Common.APP_TITLE," ("+Common.getLocalizedString("app.description",Common.APP_DESCRIPTION)+") "+Common.getLocalizedString("string.version","version")+" "+Common.APP_VERSION+" "+Common.getLocalizedString("string.from","from")+" "+Common.APP_DATE));
            f.append(new StringItem(Common.APP_VENDOR," ("+Common.APP_VENDOR_CONTACTINF+"): "+Common.getLocalizedString("help.about.vendor","vendor")));
            f.append(new StringItem(Common.getLocalizedString("help.about.developers","programming"),": "+Common.APP_DEVELOPERS_CONTACTINF));
            f.append(new StringItem(Common.getLocalizedString("help.about.support","Support"),": "+Common.APP_SUPPORT));
            f.addCommand(cmdClose);
            f.setCommandListener(new CommandListener(){
                public void commandAction(Command command, Displayable displayable) {
                    midlet.show(scr);
                }            
            });
            midlet.show(f);
        }
        if(c==cmdDoGoTo){
            mainMenu.hide();
            mainMenu.fire();            
        }
        if(c==cmdDoCancel){
            mainMenu.hide();
            createMenu();
        }
    }

    /**
     * Не запускать явно! Загрузка главы (предназначена для выполнения в другом потоке)
     */
    public void run(){
        try{
            long time=System.currentTimeMillis(); // отладка
            String[] verses=src.getChapterVerses(book.getNum(),chapterNum);
            if(verses!=null){
                String text="";
                for(int i=0;i<verses.length;i++){
                    if (Common.RTL) text+=Common.strReverse(Integer.toString(i+1))+" "+verses[i]+" ";
                    else text+=(i+1)+" "+verses[i]+" ";
                }
                Caption caption=new Caption(text);
                caption.resize(getWidth(),Common.SETTINGS.getFont());
                links=new int[verses.length];
                lines=caption.getLines();
                int lineIndex=0;
                int linkNum=1;
                while(linkNum<=links.length){
                    String line=lines[lineIndex];
                    //System.out.println(line); // отладка
                    while(line.indexOf(linkNum+"")!=-1){
                        links[linkNum-1]=lineIndex;
                        linkNum++;
                        //System.out.print(lineIndex+" "); // отладка
                    }
                    lineIndex++;
                }
                curr=links[this.verse-1];
                Common.SETTINGS.setLastPage(new Bookmark(this.book.getNum(),chapterNum,this.verse));
                System.out.println("Time: "+(System.currentTimeMillis()-time)+" ms"); // отладка
                loading=false;
                update();
                //JBibleMidlet.show(this);
            }else throw new IllegalArgumentException("Bad chapter: book "+book.getName()+", chapter "+chapterNum);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Выполняет загрузку главы
     * @param book Книга
     * @param chapter Глава
     * @param ve Стих
     */
    public void load(int book, int chapter, int ve){

        System.out.print("Loading "+book+":"+chapter+":"+ve+" ... "); // отладка

        books=src.getBibleContents();
        if(book<=0 || book>books.length) throw new IllegalArgumentException("Bad book index: "+book);
        loadingNum=books[book-1].getName()+" "+chapter+":"+ve;
        loading=true;
        repaint();
        this.book=books[book-1];
        this.chapterNum=chapter;
        this.verse=ve;
        selectedBook=this.book;
        selectedChapter=selectedBook.getChapter(chapterNum);

        Thread t=new Thread(this);
        t.start();

        //loading=false;
    }

    /**
     * Выполняет загрузку главы
     * @param bookmark Закладка
     */
    public void load(Bookmark bookmark){
        load(bookmark.getBook(),bookmark.getChapter(),bookmark.getVerse());
    }

    /**
     * Создаёт меню (для режима просмотра)
     */
    public void createMenu(){
        this.removeCommand(cmdDoGoTo);
        this.removeCommand(cmdDoCancel);
        this.setFullScreenMode(true);
        this.addCommand(cmdGoTo);
        this.addCommand(cmdNewSearch);
        this.addCommand(cmdSearchResults);
        this.addCommand(cmdAddBookmark);
        this.addCommand(cmdBookmarks);
        this.addCommand(cmdSettings);
        this.addCommand(cmdAbout);
        this.addCommand(cmdExit);
    }

    /**
     * Очищает меню (для режима перехода)
     */
    public void removeMenu(){
        this.removeCommand(cmdGoTo);
        this.removeCommand(cmdAddBookmark);
        this.removeCommand(cmdBookmarks);
        this.removeCommand(cmdSearchResults);
        this.removeCommand(cmdNewSearch);
        this.removeCommand(cmdSettings);
        this.removeCommand(cmdAbout);
        this.removeCommand(cmdExit);
        this.setFullScreenMode(false);
        mainMenu.update();
        this.addCommand(cmdDoGoTo);
        this.addCommand(cmdDoCancel);
    }

    /**
     * Сохраняет текущую позицию в настройках
     */
    public void savePage(){
        Common.SETTINGS.setLastPage(currentPage());
    }

    /**
     * Возвращает текущую позицию
     * @return Текущая позиция в главе
     */
    public Bookmark currentPage(){
        return new Bookmark(book.getNum(),chapterNum,verse);
    }

    /**
     * Возвращает источник текстов
     * @return Источник текстов для загрузки
     */
    public TextSource getTextSource(){
        return src;
    }

    /**
     * Возвращает ссылку на интерфейс мидлета
     */
    public MidletInterface getMidletInterface(){
        return midlet;
    }

}