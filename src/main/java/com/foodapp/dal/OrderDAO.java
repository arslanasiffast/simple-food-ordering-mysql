package com.foodapp.dal;

import com.foodapp.entity.FoodOrder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDAO {

    public void save(FoodOrder order) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(order);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<FoodOrder> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaQuery<FoodOrder> query = session.getCriteriaBuilder().createQuery(FoodOrder.class);
            query.from(FoodOrder.class);
            return session.createQuery(query).getResultList();
        }
    }
}
