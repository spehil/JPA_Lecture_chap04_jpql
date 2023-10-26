package com.ohgiraffers.section05.paging;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PagingTests {

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
    public void  페이징_API를_이용한_조회_테스트(){

        //given
        int offset = 10;//조회를 건너뛰고자하는 행의 갯수 건너뛰고나서(10) 바로 다음이 첫시작
        int limit = 5;//한번 조회할때 조회하고자하는 컨텐츠 ex. 11~15
        //when
        String jpql = "SELECT m FROM menu_section04 m ORDER BY m.menuCode DESC";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setFirstResult(offset)//첫번째 결과가 무엇이었으면 좋겠는지, 조회가 시작할 위치
                .setMaxResults(limit)//페이징처리시 사용하는 메소드, 최대한 몇개까지 조회할건지
                .getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);


    }
}
