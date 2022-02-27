
package br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author brener
 */

public class JPanelAFuncionario extends JPanel {
    
    private CardLayout cardLayout;
    private Controle controle;
    
    private JPanelAFuncionarioFormulario formulario;
    private JPanelAFuncionarioListagem listagem;
    
    public JPanelAFuncionario(Controle controle) {
        
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents() {
        
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        
        formulario = new JPanelAFuncionarioFormulario(this, controle);
        listagem = new JPanelAFuncionarioListagem(this, controle);
        
        this.add(formulario, "tela_funcionario_formulario");
        this.add(listagem, "tela_funcionario_listagem");
        
        cardLayout.show(this, "tela_funcionario_listagem");
    }
    
    public void showTela(String nomeTela) {
        
        cardLayout.show(this, nomeTela);
    }

    /**
     * @return the controle
     */
    
    public Controle getControle() {
        return controle;
    }
    
}