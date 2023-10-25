package com.ohgiraffers.section03.projection;

import org.junit.jupiter.api.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectionTests {


    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeAll
    public static void initFactoty() {

        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");

    }

    @BeforeEach//EntityManager는 매번 만들어져야하므로 test하나가 수행되기 전마다 수행되는 BeforeEach 작성
    public void initManager() {

        entityManager = entityManagerFactory.createEntityManager();

    }

    @AfterAll //BeforeAll과 AfterAll은 static으로 작성한다.
    public static void closeFactory() {

        entityManagerFactory.close();
    }


    @AfterEach
    public void closeManager() {
        entityManager.close();
    }


    @Test
    public void 단일_엔티티_프로젝션_테스트() {

        //when
        String jpql = "SELECT m FROM menu_section03 m";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        menuList.get(0).setMenuName("test01");//객체를 변경해준것.
        entityTransaction.commit();
    }

    @Test
    public void 양방향_연관관계_엔터티_프로젝션_테스트(){
        //given
        int menuCodeParameter = 3;
        //when
        String jpql = "SELECT m.category FROM bidirection_menu m WHERE m.menuCode = :menuCode";
        BiDirectionCategory categoryOfMenu = entityManager.createQuery(jpql,BiDirectionCategory.class)
                .setParameter("menuCode", menuCodeParameter)
                .getSingleResult();

        //then
        System.out.println(categoryOfMenu);//영속성 컨텍스트에 관리되고있어서 update로 이름이 변경된다.
        System.out.println(categoryOfMenu.getMenuList());//역방향 탐색

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        categoryOfMenu.setCategoryName("test02");//객체를 변경해준것.
        categoryOfMenu.getMenuList().get(1).setMenuName("test3");
        entityTransaction.commit();
    }

    @Test
    public void 임베디드_타입_프로젝션_테스트(){

    String jpql = "SELECT m.menuInfo FROM embedded_menu m";
    List<MenuInfo> menuInfoList = entityManager.createQuery(jpql, MenuInfo.class).getResultList();
    //then
        assertNotNull(menuInfoList);
        menuInfoList.forEach(System.out::println);
    }

    @Test
    public  void TypedQuery를_이용한_스칼라_타입_프로젝션_테스트(){


        //스칼라가 단일값을 의미하는것으로 카테고리 네임만 조회하기
        //when
        String jpql ="SELECT c.categoryName FROM category_section03 c";
        List<String> categoryNameList = entityManager.createQuery(jpql, String.class).getResultList();
        //then
        assertNotNull(categoryNameList);
        categoryNameList.forEach(System.out::println);

    }

    @Test
    public void Query를_이용한_스칼라_타입_프로젝션_테스트(){
        //when
        String jpql ="SELECT c.categoryCode, c.categoryName FROM category_section03 c";
        //반환타입지정을 c.categoryCode, c.categoryName 두개를 오브젝트의 배열형태로 받는다.
        List<Object[]> categoryList = entityManager.createQuery(jpql).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(row ->
                Arrays.stream(row).forEach(System.out::println));//오브젝트의 배열을 한행씩 반복해서 작성한다. 람다식으로
        //스칼라타입을 여러개 나열할경우 내가 원하는타입으로 매핑시키는것도 가능하다.
    }

    @Test
    public void new_명령어를_활용한_프로젝션_테스트(){

        //스칼라타입을 여러개 나열할경우 내가 원하는타입으로 매핑시키는것도 가능하다.
        //when
        String jpql ="SELECT new com.ohgiraffers.section03.projection.CategoryInfo(c.categoryCode, c.categoryName) FROM category_section03 c";//매개변수 생성자 호출해서 값을 반환해서 넣어줌
        List<CategoryInfo> categoryList = entityManager.createQuery(jpql, CategoryInfo.class).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(System.out::println);

    }
}