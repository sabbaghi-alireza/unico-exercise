package com.unico.exercise.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

import com.unico.exercise.model.Element;

/**
 * Created by Alireza on 3/16/2017.
 */
@ApplicationScoped
public class ElementDAO {

    @Inject
    private EntityManager em;

    public Element findById(Long id) {
        return em.find(Element.class, id);
    }

    public List<Element> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Element> criteria = cb.createQuery(Element.class);
        Root<Element> number = criteria.from(Element.class);
        criteria.select(number).orderBy(cb.asc(number.get("id")));
        return em.createQuery(criteria).getResultList();
    }

    public Long save(Element element) {
        em.persist(element);
        return element.getId();
    }

    public void update(Element element) {
        em.merge(element);
    }

    public List<Integer> getGCDList() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = cb.createQuery(Integer.class);
        Root<Element> root = criteria.from(Element.class);
        Path<Integer> path = root.get("gcd");
        criteria.select(path).orderBy(cb.asc(root.get("id")));
        return em.createQuery(criteria).getResultList();
    }

    public Integer getGCDSum() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = cb.createQuery(Integer.class);
        Root<Element> root = criteria.from(Element.class);
        Path<Integer> path = root.get("gcd");
        criteria.select(cb.sum(path));
        return em.createQuery(criteria).getSingleResult();
    }
}
