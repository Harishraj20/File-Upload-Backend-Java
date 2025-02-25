package com.upload.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.upload.Models.Product;

@Repository
@Transactional
public class ProductRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public String saveProduct(Product prod) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(prod);
            return "Success";

        } catch (HibernateException e) {
            System.out.println("Hibernate Exception is: " + e);

            return null;
        } catch (Exception e) {
            System.out.println("General Exception is: " + e);
            return null;
        }

    }

    @SuppressWarnings("unchecked")
    public List<Product> fetchProductFromDB() {
        try {
            Session session = sessionFactory.getCurrentSession();
            @SuppressWarnings("deprecation")
            Criteria criteria = session.createCriteria(Product.class);

            return criteria.list();

        } catch (HibernateException e) {
            System.out.println("Hibernate Exception is: " + e);

            return null;
        } catch (Exception e) {
            System.out.println("General Exception is: " + e);
            return null;
        }
    }

    public void updateCount(Map<Integer, Integer> productIds) {

        System.out.println(productIds);

        try {
            Session session = sessionFactory.getCurrentSession();

            String hql = "FROM Product WHERE id IN :ids";

            @SuppressWarnings("rawtypes")
            Query query = session.createQuery(hql);
            query.setParameter("ids", productIds.keySet());

            @SuppressWarnings("unchecked")
            List<Product> products = query.getResultList();

            if (products.isEmpty()) {
                return;
            }

            for (Product product : products) {

                int decreaseQuantity = productIds.get(product.getId());
                product.setQuantity(product.getQuantity() - decreaseQuantity);
            }

        } catch (HibernateException e) {
            System.out.println("Hibernate Exception is: " + e);

        } catch (Exception e) {
            System.out.println("General Exception is: " + e);

        }
    }

    public void incrementCount(int productId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Product product = session.get(Product.class, productId);
            System.out.println(product);
            product.setQuantity(product.getQuantity() + 1);
            System.out.println(product);

        } catch (HibernateException e) {
            System.out.println("Hibernate Exception is: " + e);

        } catch (Exception e) {
            System.out.println("General Exception is: " + e);

        }
    }

    @SuppressWarnings("unchecked")
    public List<Product> listSearchResults(String val) {
        try {
            Session session = sessionFactory.getCurrentSession();

            @SuppressWarnings("deprecation")
            Criteria criteria = session.createCriteria(Product.class);
            criteria.add(Restrictions.or(
                    Restrictions.ilike("productName", val, MatchMode.ANYWHERE),
                    Restrictions.ilike("productDescription", val, MatchMode.ANYWHERE)));
            criteria.setMaxResults(10);
            return criteria.list();

        } catch (HibernateException e) {
            System.out.println("Hibernate Exception is: " + e);
            return null;

        } catch (Exception e) {
            System.out.println("General Exception is: " + e);
            return null;

        }
    }

    public Product fetchProductById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Product product = session.get(Product.class, id);
            return product;

        } catch (HibernateException e) {
            System.out.println("Hibernate Exception is: " + e);
            return null;

        } catch (Exception e) {
            System.out.println("General Exception is: " + e);
            return null;

        }
    }

}
