
package br.edu.ifsul.cc.lpoo.cv.gui.consulta.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author brener
 */

public class JPanelAConsulta extends JPanel {
    
    private CardLayout cardLayout;
    private Controle controle;
    
    private JPanelAConsultaFormulario formulario;
    private JPanelAConsultaListagem listagem;
    
    public JPanelAConsulta(Controle controle) {
        
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents() {
        
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        
        formulario = new JPanelAConsultaFormulario(this, controle);
        listagem = new JPanelAConsultaListagem(this, controle);
        
        this.add(formulario, "tela_consulta_formulario");
        this.add(listagem, "tela_consulta_listagem");
        
        cardLayout.show(this, "tela_consulta_listagem");
    }
    
    public void showTela(String nomeTela) {
        
        if(nomeTela.equals("tela_consulta_listagem")) {
            
            listagem.populaTable();
            
        } else if(nomeTela.equals("tela_consulta_formulario")) {
            
            getFormulario().populaComboMedico();
            getFormulario().populaComboPet();
        }
        
        cardLayout.show(this, nomeTela);
    }

    /**
     * @return the controle
     */
    
    public Controle getControle() {
        return controle;
    }
    
    /**
     * @return the formulario
     */
    public JPanelAConsultaFormulario getFormulario() {
        return formulario;
    }
    
}