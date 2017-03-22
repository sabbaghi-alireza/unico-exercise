package com.unico.exercise.security;

import com.unico.exercise.security.entity.Role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.logging.Logger;


@Stateless
public class RoleService {

    @Inject
    EntityManager em;

    @Inject
    private Logger logger;

    public Role save(Role role) {
        if (role.getId() != null){
            em.merge(role);
        }
        else{
            em.persist(role);
        }
        return role;
    }

    public Role findById(long id) {
        return this.em.find(Role.class, id);
    }

    public List<Role> findAll() {
        return this.em
                .createNamedQuery("Role.findAll", Role.class)
                .getResultList();
    }

    public Role findByName(String name) {
        Role result = null;
        try {
            result = this.em.createNamedQuery("Role.findByName", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.info("RoleService : No valid Role was found for [" + name + "] : " + e);
        } finally {
            return result;
        }
    }

    public void delete(long id) {
        Role userReference;
        try {
            userReference = this.em.getReference(Role.class, id);
            this.em.remove(userReference);
        } catch (EntityNotFoundException e) {
            logger.finest("Role with id[" + id + "] was not delete because it doesn't exist." + e);
        }
    }
}