
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author brener
 */

public class PersistenciaJPA implements InterfacePersistencia {
    
    public EntityManagerFactory factory;    // Fabrica de gerenciadores de entidades.
    public EntityManager entity;            // Gerenciador de entidades JPA.
    
    public PersistenciaJPA() {
        
        // parametro: é o nome da unidade de persistencia (Persistence Unit).
        factory = Persistence.createEntityManagerFactory("pu_db_clinica_veterinaria");
        entity = factory.createEntityManager();
    }

    @Override
    public Boolean conexaoAberta() {
        
        return entity.isOpen();   
    }

    @Override
    public void fecharConexao() {
        
        entity.close();
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void persist(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}