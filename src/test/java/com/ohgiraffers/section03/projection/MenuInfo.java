package com.ohgiraffers.section03.projection;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable//엔티티는 아니고 내장타입이될수있는 클래스임을 나타내느 어노테이션
public class MenuInfo {

    private String menuName;
    private int menuPrice;

    public MenuInfo() {
    }

    public MenuInfo(String menuName, int menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    @Override
    public String toString() {
        return "MenuInfo{" +
                "menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                '}';
    }
}
