
package br.edu.ifsul.cc.lpoo.cv.model.dao;

/**
 *
 * @author brener
 */
public interface InterfacePersistencia {
            
    public Boolean conexaoAberta();
    
    public void fecharConexao();
}