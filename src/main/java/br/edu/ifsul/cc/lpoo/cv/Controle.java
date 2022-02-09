
package br.edu.ifsul.cc.lpoo.cv;

import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import br.edu.ifsul.cc.lpoo.cv.gui.JFramePrincipal;

/**
 *
 * @author brener
 */

public class Controle {
    
    private PersistenciaJDBC conexaoJDBC;
    
    private JFramePrincipal frame; // Frame principal da minha aplicação gráfica.
    
        
    // Construtor.
    public Controle() {
                        
    }
    
    public boolean conectarBD() throws Exception {

            conexaoJDBC = new PersistenciaJDBC();

            if(conexaoJDBC!= null){
                
                return conexaoJDBC.conexaoAberta();
                
            }

            return false;
    }
    
    public void fecharBD() {

        System.out.println("Fechando conexão com o Banco de Dados.");
        conexaoJDBC.fecharConexao();

    }
    
    public void initComponents() {
        
        // Inicia a interface gráfica.
        // "Caminho feliz" : passo 5
        
        frame = new JFramePrincipal();
        
        frame.setVisible(true); // Torna visível o jframe.
        
    }
    
}