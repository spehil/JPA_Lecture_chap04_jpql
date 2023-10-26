package com.ohgiraffers.section06.join;

import com.ohgiraffers.section03.projection.BiDirectionCategory;

import javax.persistence.*;

@Entity(name = "menu_section06")
@Table(name = "tbl_menu")
public class Menu {
    @Id
    private int menuCode;
    private String menuName;
    private int menuPrice;
    @ManyToOne//Many:menu를 의미하고 one이 카테고리를 의미)
    @JoinColumn(name = "categoryCode")
    private com.ohgiraffers.section03.projection.BiDirectionCategory category;
    private String orderableStatus;

    public Menu() {
    }

    public Menu(int menuCode, String menuName, int menuPrice, com.ohgiraffers.section03.projection.BiDirectionCategory category, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
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

    public com.ohgiraffers.section03.projection.BiDirectionCategory getCategory() {
        return category;
    }

    public void setCategory(BiDirectionCategory category) {
        this.category = category;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "BiDirectionMenu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", category=" + category +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
