/**
 * TextSource.java - �������� ������������� ��������� ��������� ������� ������
 */
package jbible.data;

import java.io.*;

/**
 * ��������� ��� ��������� ������� ������ �� �������� ���������
 * @author ����� ����������, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public interface TextSource {

    /**
     * ���������� ���������� ������ ��� ������ ����
     * @return ������ ����
     */
    public Book[] getBibleContents();
    
    /**
     * ���������� ���������� ����� ��� ������ ����
     * @param book ����� ����� (������ ���� ������ 0)
     * @return ������ ���� ��� null, ���� ����� �� �������
     */
    public Chapter[] getBookContents(int book);

    /**
     * ���������� ����� �����
     * @param book ����� ����� (������ ���� ������ 0)
     * @param chapter ����� ����� (������ ���� ������ 0)
     * @return ������ ������ ����� ��� null, ���� ����� �� ����������
     */
    public String[] getChapterVerses(int book, int chapter);
	
}
