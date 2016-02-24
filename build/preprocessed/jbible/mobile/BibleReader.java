/**
 * BibleReader.java - ��������� ����������, �������� ���������� ������
 */

package jbible.mobile;

import java.io.*;
import java.util.*;
import jbible.data.*;

/**
 * ������ ������ ������ � ������� ��������� ��������� jBible.
 * <p>������ �������� �������: �� ���������� ����� �������� bible ����� ���������������.
 * � �������� bible ������ ���������� �������� ���� index, ������� ��������� ���������:
 * 1. 1 ���� - ���������� ����
 * 2. ������ (UTF-8) - �������� �����
 * 3. 1 ���� - ���������� ���� (�����_����)
 * 4. �����_���� ����, ������ �� ������� �������� ���������� ������ � �����
 * ���� ������ [2..4] ����������� ������� ���, ������� ���� � ������.
 * <p>��� ������ ����� ���������� ���� ���������� bible/�����_�����, � ������� ��������� ������ ���� ����� (�����
 * � ������� 1.txt .. �����_����.txt). ����� ����������� � <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/io/DataInput.html#modified-utf-8">
 * ����������������</a> ��������� UTF-8, ������ ���� ����� ���������� � ��������� ������ � ����������� 
 * �������� �������� ������ \n.
 * @author ���������� K. �����, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru<a>
 * @author ������ �. ������, e-mail: <a href="mailto:biblelamp@gmail.com">biblelamp@gmail.com</a>
 */
public class BibleReader implements TextSource {
    
    /**
     * ������ ����
     */
    private Book[] books;

    /**
     * ������ ������ ��������� �������
     * @throws java.io.IOException ������������� � ������ ������ �����-������ ��� ��������� ������� ������ � �����
     */
    public BibleReader() throws IOException {
        String indexFileName = "/"+Common.BIBLE_TEXT_DIR+"/"+Common.BIBLE_INDEX;
        InputStream index = this.getClass().getResourceAsStream(indexFileName);
        if (index==null) throw new IOException("Bible index file not found: "+indexFileName);
        DataInputStream is = new DataInputStream(index);
        books = new Book[is.readByte()]; // ������� ������ ��������-����
        for(int n=0;n<books.length;n++) {
            String bookName = is.readUTF().trim(); // �������� �����
            int chapterCount = is.readUnsignedByte(); // ���������� ����
            books[n] = new Book(n+1,bookName); // ������� �����
            for(int j=0;j<chapterCount;j++)
                books[n].createChapter(j+1).setVerseCount(is.readUnsignedByte()); // �������������� �����
        }
        is.close();
    }

    /**
     * �������� ������ ����
     * @return ������ ����
     */
    public Book[] getBibleContents() {
        return books;
    }
    
    /**
     * �������� ������ ���� �������� �����
     * @param book ����� ����� (������ ���� ������ 0)
     * @return ������ ���� �����
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
     * �������� ����� �� �������� ����� �������� �����
     * @param book ����� ����� (������ ���� ������ 0)
     * @param chapter ����� ����� (������ ���� ������ 0)
     * @return ������ ������ �����
     */
    public String[] getChapterVerses(int book, int chapter) {
        String fileName = "/"+Common.BIBLE_TEXT_DIR+"/"+book+"/"+chapter+".txt";
        InputStream entry = this.getClass().getResourceAsStream(fileName);
        String[] result = new String[books[book-1].getChapter(chapter).getVerseCount()]; // �������� ����� ������
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