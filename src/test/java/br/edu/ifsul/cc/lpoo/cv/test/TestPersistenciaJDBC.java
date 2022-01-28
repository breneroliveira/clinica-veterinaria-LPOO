
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import org.junit.Test;

/**
 *
 * @author brener
 */

public class TestPersistenciaJDBC {
    
    @Test
    public void testConexao() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
            System.out.println("Abriu a conexao com o BD via JDBC.");
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("Nao abriu a conexao com o BD via JDBC.");
        }       
    }
    
}