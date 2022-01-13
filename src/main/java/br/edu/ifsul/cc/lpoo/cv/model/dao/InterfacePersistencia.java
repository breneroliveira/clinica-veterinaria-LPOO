
package br.edu.ifsul.cc.lpoo.cv.model.dao;

/**
 *
 * @author brener
 */

public interface InterfacePersistencia {
            
    public Boolean conexaoAberta();
    
    public void fecharConexao();
    
    public Object find(Class c, Object id) throws Exception; // SELECT.
    
    public void persist(Object o) throws Exception; // INSERT ou UPDATE.
    
    public void remover(Object o) throws Exception; // DELETE.
    
}