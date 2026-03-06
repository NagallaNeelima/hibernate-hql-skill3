package com.inventory.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;

public class HQLDemo {

    public static void main(String[] args) {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        // Sorting
        sortProductsByPrice(session);

        // Pagination
        paginationExample(session);

        // Aggregate
        countProducts(session);

        // LIKE Query
        likeQueryExample(session);

        session.close();
        factory.close();
    }

   
    public static void sortProductsByPrice(Session session) {

        String hql = "FROM Product p ORDER BY p.price ASC";

        Query<Product> query = session.createQuery(hql, Product.class);

        List<Product> products = query.list();

        System.out.println("Products sorted by price:");

        for (Product p : products) {
            System.out.println(p.getName() + " - " + p.getPrice());
        }
    }

   
    public static void paginationExample(Session session) {

        Query<Product> query = session.createQuery("FROM Product", Product.class);

        query.setFirstResult(0);
        query.setMaxResults(3);

        List<Product> products = query.list();

        System.out.println("\nFirst 3 Products:");

        for (Product p : products) {
            System.out.println(p.getName());
        }
    }

  
    public static void countProducts(Session session) {

        String hql = "SELECT COUNT(p) FROM Product p";

        Query<Long> query = session.createQuery(hql, Long.class);

        Long count = query.uniqueResult();

        System.out.println("\nTotal Products: " + count);
    }

    
    public static void likeQueryExample(Session session) {

        String hql = "FROM Product p WHERE p.name LIKE :pattern";

        Query<Product> query = session.createQuery(hql, Product.class);

        query.setParameter("pattern", "D%");

        List<Product> list = query.list();

        System.out.println("\nProducts starting with D:");

        for (Product p : list) {
            System.out.println(p.getName());
        }
    }
}