/**
 * TextSource.java - Описание представления источника получения текстов Библии
 */
package jbible.data;

import java.io.*;

/**
 * Интерфейс для получения текстов Библии из внешнего источника
 * @author Беляк Константин, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public interface TextSource {

    /**
     * Возвращает содержание Библии как список книг
     * @return Список книг
     */
    public Book[] getBibleContents();
    
    /**
     * Возвращает содержание книги как список глав
     * @param book Номер книги (должен быть больше 0)
     * @return Список глав или null, если книга не найдена
     */
    public Chapter[] getBookContents(int book);

    /**
     * Возвращает стихи главы
     * @param book Номер книги (должен быть больше 0)
     * @param chapter Номер главы (должен быть больше 0)
     * @return Список стихов главы или null, если главы не существует
     */
    public String[] getChapterVerses(int book, int chapter);
	
}
