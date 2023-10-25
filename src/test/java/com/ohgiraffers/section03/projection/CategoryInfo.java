package com.ohgiraffers.section03.projection;

public class CategoryInfo {
    //두개가 많이 사용해서 내가 사용하는 타입으로 정의하고 싶다면 이렇게 따로 클래스로 만들어서 작성한다.
    private int categoryCode;
    private String categoryName;

    public CategoryInfo() {
    }

    public CategoryInfo(int categoryCode, String categoryName) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoryInfo{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
