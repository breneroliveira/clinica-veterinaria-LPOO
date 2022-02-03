
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import org.junit.Test;

/**
 *
 * @author brener
 */

public class TestPersistenciaJPA {
    
    @Test
    public void testConexaoGeracaoTabelas() {
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()) {
            System.out.println("\nAbriu a conexão com o BD via JPA.\n");
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("\nNão abriu a conexão com o BD via JPA.\n");
        }       
    }
}