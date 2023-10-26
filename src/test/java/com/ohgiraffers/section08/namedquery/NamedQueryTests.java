package com.ohgiraffers.section08.namedquery;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NamedQueryTests {


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
    public void 동적쿼리를_이용한_조회_테스트() {
        //네임드쿼리(정적쿼리) , String jpql = "" ->동적쿼리
        //검색파라미터가 있을때 어떻게 컨트롤할건지 동적쿼리로 작성

        //given
        String searchName = "한우";//검색조건 파라미터라고 생각하고 작성  null이나 빈문자열이면 else구문으로 넘어간다.
        int searchCategoryCode = 4;

        //when
        StringBuilder jpql = new StringBuilder("SELECT m FROM menu_section08 m ");//파라미터가 있든없든 조회되는 구문(공통적인 부분)
        //구문에 맞게 가공 동적쿼리
        if (searchName != null && !searchName.isEmpty() && searchCategoryCode > 0) {
            jpql.append("WHERE ");
            jpql.append("m.menuName LIKE '%' || '%' ");
            jpql.append("AND ");
            jpql.append("m.categoryCode = :categoryCode ");

        } else {
            if (searchName != null && searchName.isEmpty()) {
                jpql.append("WHERE ");
                jpql.append("m.menuName LIKE '%' || '%' ");

            } else if (searchCategoryCode > 0) {
                jpql.append("WHERE ");
                jpql.append("m.categoryCode = :categoryCode ");

            }
        }

        TypedQuery<Menu> query = entityManager.createQuery(jpql.toString(), Menu.class);
        if (searchName != null && searchName.isEmpty() && searchCategoryCode > 0) {
            query.setParameter("menuName", searchName);
            query.setParameter("categoryCode", searchCategoryCode);

        } else if (searchCategoryCode > 0) {
            query.setParameter("categoryCode", searchCategoryCode);
        }

        List<Menu> menuList = query.getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
        //조회할때 JPQL와 마이바티스를 혼용해서 사용하는케이스도있다.
    }

    @Test
    public void 네임드쿼리를_이용한_조회_테스트(){
        //정적쿼리는 호출시 만드는게 아니라 저장해놓고 가져다가 사용한다. @NamedQuery를 이용해서

        //when
        List<Menu> menuList = entityManager.createNamedQuery("menu_section08_selectMenuList", Menu.class)
                .getResultList();//반환타입지정: Menu.class
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }

}



