package com.ohgiraffers.section01.simple;

import org.junit.jupiter.api.*;

import javax.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleJPQLTests {


    private  static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeAll
    public  static void initFactoty(){

        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");

    }

    @BeforeEach//EntityManager는 매번 만들어져야하므로 test하나가 수행되기 전마다 수행되는 BeforeEach 작성
    public void initManager(){

        entityManager = entityManagerFactory.createEntityManager();

    }

    @AfterAll //BeforeAll과 AfterAll은 static으로 작성한다.
    public  static  void closeFactory(){

        entityManagerFactory.close();
    }


    @AfterEach
    public void closeManager(){
        entityManager.close();
    }

    @Test
    public void TypedQuery를_이용한_단일메뉴_조회_테스트(){

        //when
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode = 7";//구문작성
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);//수행하고싶은 jpql구문 , 실행시 반환받을 데이터타입 //createQuery를 호출하면 쿼리실행은 아니고 세팅한상태
        //실제 실행은  TypedQuery를 가지고 query.getSingleResult(); 코드에서 실행
        //TypedQuery는 반환타입 지정시 사용한다.
        String resultMenuName = query.getSingleResult();
        //then
        assertEquals("민트미역국", resultMenuName);



    }
    @Test
    public void Query를_이용한_단일메뉴_조회_테스트(){
        //when
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode = 7";//구문작성
        //반환타입을 지정하지 않고 jpql을 사용할때눈 TypedQuery가 아닌 Query를 사용한다!!!
        Query query = entityManager.createQuery(jpql);
        //반환타입이 지정되어있지않으므로 상위타입인 Object타입으로 작성한다.
        Object resultMenuName = query.getSingleResult();
    }
    @Test
    public void TypedQuery를_이용한_단일행_조회_테스트(){

        //when
        //조회된컬럼이 하나의 컬럼이 아니라 전체를 조회하고 싶을때
        String jpql = "SELECT m FROM menu_section01 as m WHERE m.menuCode = 7";//구문작성
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        Menu foundMenu = query.getSingleResult();
        //then
        assertEquals(7, foundMenu.getMenuCode());
        System.out.println(foundMenu);

    }

    @Test
    public void TypedQuery를_이용한_다중행_조회_테스트(){

        //when
        //조회된컬럼이 하나의 컬럼이 아니라 전체를 조회하고 싶을때
        String jpql = "SELECT m FROM menu_section01 as m";//구문작성
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> foundMenuList = query.getResultList();
        //then
        assertNotNull(foundMenuList);
       foundMenuList.forEach(System.out::println);

    }

    @Test
    public void Query를_이용한_다중행_조회_테스트(){

        //when
        //조회된컬럼이 하나의 컬럼이 아니라 전체를 조회하고 싶을때
        String jpql = "SELECT m FROM menu_section01 as m";//구문작성
        Query query = entityManager.createQuery(jpql);
        List<Menu> foundMenuList = query.getResultList();//<Menu>제네릭을 제거해도 되는데 써도 에러가 나지는 않음.
        //then
        assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println);

    }

    //연산자 like , in (여러조건을 한번에 체크), distinct같은 연산자를 jpql에서 어떻게 사용하는지

    @Test
    public void distinct를_활용한_중복제거_여러_행_조회_테스트(){

        //when
        //카테고리의 중복없이 조회
        String jpql = "SELECT DISTINCT m.categoryCode FROM menu_section01 m";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        //조회결과가 여러개이므로 List로 받는다 ResultList()
        List<Integer> categoryCodeList = query.getResultList();
        //then
        assertNotNull(categoryCodeList);
        categoryCodeList.forEach(System.out::println);
    }

    @Test
    public void in_연산자를_활용한_조회_테스트(){
        //카테고리 코드가 6이거나 10인 menu조회
        String jpql = "SELECT m FROM menu_section01 m WHERE m.categoryCode IN(6,10)";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }

    @Test
    public void like_연산자를_활용한_조회_테스트(){
        //"마늘"이 메뉴이름이 들어간 menu 조회
        String jpql = "SELECT m FROM menu_section01 m WHERE m.menuName like'%마늘%'";
        List<Menu> menuList =  entityManager.createQuery(jpql, Menu.class).getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }
}
