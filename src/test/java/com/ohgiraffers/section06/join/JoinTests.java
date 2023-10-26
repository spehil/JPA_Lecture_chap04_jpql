package com.ohgiraffers.section06.join;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JoinTests {


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
    public void 내부조인을_이용한_조회_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section06 m JOIN m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);//이때 다시 조회가 일어난다. 카테고리가 있는만큼 다시 select 조회가 이루어진다.
        //JPQL은 메뉴에 대한 조회만 이루어지고 카테고리에 대한 조회는 필요시 조회된다 -> 카테고리 갯수마다 select조회해와서 이점은 추후에 보완하는 방법 배우기.
    }

    @Test
    public void 내부조인을_이용한_조회_테스트(){
        //when
        String jpql = "SELECT m.menuName, c.categoryName FROM menu_section06 m RIGHT JOIN m.category c" +
        " ORDER BY m.category.categoryCode";//카테고리를 기준으로 RIGHT JOIN
        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();
        //Object배열로  m.menuName, c.categoryName두개의 값을 받을거임.

        //then
        assertNotNull(menuList);
        menuList.forEach(row ->{
            Stream.of(row).forEach(col -> System.out.print(col+ " "));
            System.out.println();
        });
    }

    @Test
    public void 컬렉션조인을_이용한_조회_테스트(){

        //when
        String jpql = "SELECT c.categoryName , m.menuName FROM category_section06 c LEFT JOIN c.menuList m";

        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();
        //Object배열로  m.menuName, c.categoryName두개의 값을 받을거임.

        //then
        assertNotNull(categoryList);
        categoryList.forEach(row ->{
            Stream.of(row).forEach(col -> System.out.print(col+ " "));
            System.out.println();
        }); //결과는  내부조인을_이용한_조회_테스트와 같게 나옴 컬렉션을 이용해서 해본거임
    }

    @Test
    public void 세타조인을_이용한_조회_테스트(){
        //세타조인(=크로스 조인)은 JPQL로 구현하면 어떻게 작성하는지 test해보기
        //when
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section06 c, menu_section06 m";
        //FROM절에 엔티티를 나열하므로써 크로스조인을 할수있다.

        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(row ->{
            Stream.of(row).forEach(col -> System.out.print(col+ " "));
            System.out.println();
        });

        @Test
        public void 페치조인을_이용한_조회_테스트(){
            //when
            String jpql = "SELECT m FROM menu_section06 m JOIN FETCH m.category c";
            //FETCH조인 : 처음 조회할때 한번에 전부 조회한다는것, 지연로딩없이 조인된다.
        //페치를 뺴고 조인하면 내부조인이 일어나고 내부조인은 지연로딩이 일어나서 효율적으로 쓰고자 하는 전략으로  category가 필요할때 다시 조회하는데
        //내가 원하는건 카테고리도 전부 한번에 조회하고싶어서 JOIN뒤에 FETCH를 붙여서 카테고리에 대한 정보도 한번에 조회해온다.


            List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
            //then
            assertNotNull(menuList);
            menuList.forEach(System.out::println);//이때 다시 조회가 일어난다. 카테고리가 있는만큼 다시 select 조회가 이루어진다.
            //JPQL은 메뉴에 대한 조회만 이루어지고 카테고리에 대한 조회는 필요시 조회된다 -> 카테고리 갯수마다 select조회해와서 이점은 추후에 보완하는 방법 배우기.
        }


    }



}
