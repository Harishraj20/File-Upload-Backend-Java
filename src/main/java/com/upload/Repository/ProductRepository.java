package com.upload.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

}
