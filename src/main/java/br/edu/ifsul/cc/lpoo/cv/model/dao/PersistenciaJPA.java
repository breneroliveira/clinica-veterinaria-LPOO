
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import java.util.List;
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
        /*entity.getTransaction().begin(); // Abrir a transação.
        entity.persist(o); // Realiza o INSERT ou UPDATE.
        entity.getTransaction().commit(); // Comita a transação (comando sql).*/
    }

    @Override
    public void remover(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Consulta> listConsultas() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Receita> listReceitas() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Pessoa> listPessoas() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Funcionario doLogin(String cpf, String senha) throws Exception {
        
        List<Funcionario> list = entity.createNamedQuery("Funcionario.login").setParameter("paramN", cpf).setParameter("paramS", senha).getResultList();
        if(list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
        
    }

    @Override
    public List<Funcionario> listFuncionarios() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Medico> listMedicos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pet> listPets() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> listClientes() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}