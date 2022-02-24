
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import java.util.Calendar;
import org.junit.Test;

/**
 *
 * @author brener
 */

public class TestPersistenciaJPA {
    
    //@Test
    public void testConexaoGeracaoTabelas() {
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()) {
            System.out.println("\nAbriu a conexão com o BD via JPA.\n");
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("\nNão abriu a conexão com o BD via JPA.\n");
        }       
    }
    
    /*@Test
    public void testGeracaoFuncionarioLogin() throws Exception {
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()) {
            System.out.println("\nAbriu a conexão com o BD via JPA.\n");
            
            Funcionario f = persistencia.doLogin("12345678912", "1234");
            // só pra ver se ha cadastro
            if(f == null) {
                f = new Funcionario();
                
                f.setCpf("12345678912");
                f.setSenha("1234");
                
                f.setRg("4563219870");
                
                f.setNome("Thomas");
                
                f.setNumero_celular("54996358745");
                
                f.setEmail("thomas@gmail.com");
                
                Calendar data_convertida_1 = Calendar.getInstance();
                data_convertida_1.set(Calendar.YEAR, 2022);
                data_convertida_1.set(Calendar.MONTH, 02 + 1);
                data_convertida_1.set(Calendar.DAY_OF_MONTH, 03);
                f.setData_cadastro(data_convertida_1);
                
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 2001);
                data_convertida_2.set(Calendar.MONTH, 6 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 22);
                f.setData_nascimento(data_convertida_2);
                
                f.setCep("36985241");
                
                f.setEndereco("Rua Luiz II");
                
                f.setComplemento("Nenhum");
                
                f.setTipo("M");
                
                persistencia.persist(f);
                System.out.println("Cadastrou uma nova pessoa.");
            } else {
                System.out.println("Encontrou uma pessoa cadastrada.");
            }
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("\nNão abriu a conexão com o BD via JPA.\n");
        }
        
    }*/
    
}