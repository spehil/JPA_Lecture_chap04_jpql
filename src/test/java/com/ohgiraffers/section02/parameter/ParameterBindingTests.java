package com.ohgiraffers.section02.parameter;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParameterBindingTests {


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
    public void 이름_기준_파라미터_바인딩_메뉴_목록_조회_테스트(){
    //given
        String menuNameParameter = "한우딸기국밥";
        //when
        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName = :menuName";// :(콜론)뒤에 파라미터로 사용할 변수명작성
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter("menuName", menuNameParameter)
                .getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);



    }
    @Test
    public void 위치_기준_파라미터_바인딩_메뉴_목록_조회_테스트(){

        //given
        String menuNameParameter = "한우딸기국밥";
        //when
        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName = ?1";// ?뒤에 숫자를 작성해준다.(숫자는 포지션위치를 나타냄)
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter(1, menuNameParameter)
                .getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);





    }

}
