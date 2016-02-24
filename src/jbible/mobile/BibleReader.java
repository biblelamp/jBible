/**
 * BibleReader.java - Описывает анализатор, читающий библейские тексты
 */

package jbible.mobile;

import java.io.*;
import java.util.*;
import jbible.data.*;

/**
 * Читает тексты Библии в формате структуры каталогов jBible.
 * <p>Формат хранения текстов: всё содержимое кроме каталога bible будет проигнорировано.
 * В каталоге bible должен находиться байтовый файл index, имеющий следующую структуру:
 * 1. 1 байт - количество книг
 * 2. Строка (UTF-8) - название книги
 * 3. 1 байт - количество глав (Число_глав)
 * 4. Число_глав байт, каждый из которых содержит количество стихов в главе
 * Блок данных [2..4] повторяется столько раз, сколько книг в сборке.
 * <p>Для каждой книги существует свой подкаталог bible/Номер_книги, в котором находятся тексты глав книги (файлы
 * с именами 1.txt .. Число_книг.txt). Текст представлен в <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/io/DataInput.html#modified-utf-8">
 * модифицированной</a> кодировке UTF-8, каждый стих главы расположен в отдельной строке и завершается 
 * символом перевода строки \n.
 * @author Константин K. Беляк, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru<a>
 * @author Сергей В. Ирюпин, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 */
public class BibleReader implements TextSource {
    
    /**
     * Список книг
     */
    private Book[] books;

    /**
     * Создаёт объект источника текстов
     * @throws java.io.IOException Выбрасывается в случае ошибки ввода-вывода или неверного формата данных в файле
     */
    public BibleReader() throws IOException {
        String indexFileName = "/"+Common.BIBLE_TEXT_DIR+"/"+Common.BIBLE_INDEX;
        InputStream index = this.getClass().getResourceAsStream(indexFileName);
        if (index==null) throw new IOException("Bible index file not found: "+indexFileName);
        DataInputStream is = new DataInputStream(index);
        books = new Book[is.readByte()]; // создаем массив объектов-книг
        for(int n=0;n<books.length;n++) {
            String bookName = is.readUTF().trim(); // название книги
            int chapterCount = is.readUnsignedByte(); // количество глав
            books[n] = new Book(n+1,bookName); // создаем книгу
            for(int j=0;j<chapterCount;j++)
                books[n].createChapter(j+1).setVerseCount(is.readUnsignedByte()); // инициализируем главы
        }
        is.close();
    }

    /**
     * Получает список книг
     * @return Список книг
     */
    public Book[] getBibleContents() {
        return books;
    }
    
    /**
     * Получает список глав указаной книги
     * @param book Номер книги (должен быть больше 0)
     * @return Список глав книги
     */
    public Chapter[] getBookContents(int book) {
        if(book>0 && book<=books.length) {
            Chapter[] result = new Chapter[books[book-1].getChaptersCount()];
            for(int i=0;i<books[book-1].getChaptersCount();i++) 
                result[i] = books[book-1].getChapter(i+1);
            return result;
        } else throw new IllegalArgumentException("Bad book number: "+book);
    }

    /**
     * Получает стихи из указаной главы указаной книги
     * @param book Номер книги (должен быть больше 0)
     * @param chapter Номер главы (должен быть больше 0)
     * @return Список стихов главы
     */
    public String[] getChapterVerses(int book, int chapter) {
        String fileName = "/"+Common.BIBLE_TEXT_DIR+"/"+book+"/"+chapter+".txt";
        InputStream entry = this.getClass().getResourceAsStream(fileName);
        String[] result = new String[books[book-1].getChapter(chapter).getVerseCount()]; // получаем число стихов
        if(entry==null) return null;
        try {
            DataInputStream is = new DataInputStream(entry);
            for(int i=0;i<result.length;i++)
                result[i] = is.readUTF().trim();
            is.close();
            return result;
        } catch(IOException e) {
            return null;
        }
    }

}