/**
 * MenuItem.java - ��������� ������� ����
 */

package jbible.mobile.ui;

import javax.microedition.lcdui.*;
import java.io.*;
import jbible.mobile.*;

/**
 * ������� ����
 * @author Konstantin K. Beliak, e-mail: <a href="mailto:smbadm@yandex.ru">smbadm@yandex.ru</a>
 */
public abstract class MenuItem {
    
    /**
     * ������� ���������� ������ ����
     */
    private boolean active;
    
    /**
     * ������ ������������� ������
     */
    private int width;
    
    /**
     * ������ ������������� ������
     */
    private int height;
    
    /**
     * �������� ������ ���� �� ��� y
     */
    private int y;
    
    /*public MenuItem(int width, int height, int y){
        this.setWidth(width);
        this.setHeight(height);
        this.setY(y);
    }*/
    
    /**
     * ��������� �������� ������������
     * @param action �������� ������������ (���������� � ������� getGameAction)
     */
    public void action(int action){
    
    }
    
    /**
     * ��������� ������ ���� (������ ���������� ����)
     * @param g �������� ��� ���������
     */
    public abstract void paint(Graphics g);

    /**
     * ���������, �������� �� ����� ���� �������� � ������ ������
     * @return true ���� ����� ���� ������� � false � ��������� ������
     */
    public boolean isActive() {
        return active;
    }

    /**
     * ������ ����� ���� �������� ��� ����������
     * @param active true ���� ����� ���� ������ ����� �������� � false � ��������� ������
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * ���������� ������ ������������� ������
     * @return ������ ������������� ������
     */
    public int getWidth() {
        return width;
    }

    /**
     * ���������� �������� ������ ���� �� ��� y
     * @return �������� ������ ���� �� ��� y
     */
    public int getY() {
        return y;
    }

    /**
     * ���������� ������ ������ ����
     * @return ������ ������ ����
     */
    public int getHeight() {
        return height;
    }

    /**
     * ������������� ������ ������������� ������
     * @param width ������ ������������� ������
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * ������������� ������ ������ ����
     * @param height ������ ������ ����
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * ������������� �������� ������ ���� �� ��� y
     * @param y �������� ������ ���� �� ��� y
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * ��������� ��������� ���� � ����� � ��������� ���������� �������� ������
     */
    public void update(){
    }
    
}