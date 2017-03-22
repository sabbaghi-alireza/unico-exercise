package com.unico.exercise.service;

import com.unico.exercise.data.ElementDAO;
import com.unico.exercise.model.Element;
import com.unico.exercise.util.MathUtil;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import java.util.List;

/**
 * Created by Alireza on 3/16/2017.
 */
@Stateless
public class ElementService {

    @Inject
    private ElementDAO elementDAO;

    public Long save(Element element) {
        element.setId(null);
        return elementDAO.save(element);
    }

    public List<Element> findAll() {
        return elementDAO.findAll();
    }

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:/queue/ElementQueue")
    private Queue elementQueue;

    public Long process(Element element) {
        element.setId(null);
        elementDAO.save(element);
        context.createProducer().send(elementQueue, element);
        return element.getId();
    }

    public Integer computeGCD() {
        try {
            if (context.createBrowser(elementQueue).getEnumeration().hasMoreElements()) {
                Element element = context.createConsumer(elementQueue).receiveBody(Element.class);
                int gcd = MathUtil.calculateGCD(element.getFirst(), element.getSecond());
                Element dbElement = elementDAO.findById(element.getId());
                dbElement.setGcd(gcd);
                elementDAO.update(dbElement);
                return gcd;
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Integer> getGCDList() {
        return elementDAO.getGCDList();
    }

    public Integer getGCDSum() {
        return elementDAO.getGCDSum();
    }
}
