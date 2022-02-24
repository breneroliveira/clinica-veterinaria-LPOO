
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import java.util.List;

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
    
    public List<Consulta> listConsultas() throws Exception;
    
    public List<Receita> listReceitas() throws Exception;
    
    public List<Pessoa> listPessoas() throws Exception;
    
    public List<Funcionario> listFuncionarios() throws Exception;
    
    public Funcionario doLogin(String cpf, String senha) throws Exception;
    
}