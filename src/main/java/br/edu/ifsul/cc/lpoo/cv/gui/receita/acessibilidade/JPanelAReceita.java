
package br.edu.ifsul.cc.lpoo.cv.gui.receita.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author brener
 */

public class JPanelAReceita extends JPanel {
    
    private CardLayout cardLayout;
    private Controle controle;
    
    private JPanelAReceitaFormulario formulario;
    private JPanelAReceitaListagem listagem;
    
    public JPanelAReceita(Controle controle) {
        
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents() {
        
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        
        formulario = new JPanelAReceitaFormulario(this, controle);
        listagem = new JPanelAReceitaListagem(this, controle);
        
        this.add(formulario, "tela_receita_formulario");
        this.add(listagem, "tela_receita_listagem");
        
        cardLayout.show(this, "tela_receita_listagem");
    }
    
    public void showTela(String nomeTela) {
        
        if(nomeTela.equals("tela_receita_listagem")) {
            
            listagem.populaTable();
            
        } else if(nomeTela.equals("tela_receita_formulario")) {
            
            getFormulario().populaComboConsulta(); // DÚVIDA: SANADA
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
    public JPanelAReceitaFormulario getFormulario() {
        return formulario;
    }
}