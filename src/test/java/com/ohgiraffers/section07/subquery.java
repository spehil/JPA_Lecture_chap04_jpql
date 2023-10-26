package com.ohgiraffers.section07;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class subquery {


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
    public void 서브쿼리를_이용한_메뉴_조회_테스트(){
        //given
        String categoryNameParameter = "한식";
        //when
        String jpql = "SELECT m FROM menu_section07 m WHERE m.categoryCode" +
                " =(SELECT c.categoryCode FROM category_section07 c WHERE c.categoryName = :categoryName)";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter("categoryName", categoryNameParameter)
                .getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }

}
