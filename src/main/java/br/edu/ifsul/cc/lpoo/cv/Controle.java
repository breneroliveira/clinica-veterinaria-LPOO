
package br.edu.ifsul.cc.lpoo.cv;

import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import br.edu.ifsul.cc.lpoo.cv.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cv.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cv.gui.JPanelHome;
import br.edu.ifsul.cc.lpoo.cv.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cv.gui.funcionario.JPanelFuncionario;
import br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade.JPanelAFuncionario;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import javax.swing.JOptionPane;

/**
 *
 * @author brener
 */

public class Controle {
    
    private PersistenciaJDBC conexaoJDBC;
    
    private JFramePrincipal frame; // Frame principal da minha aplicação gráfica.
    
    private JPanelAutenticacao pnlAutenticacao; // Painel para a autenticação da Pessoa.
    
    private JMenuBarHome menuBar; // Menu principal.
    
    private JPanelHome pnlHome; // Painel de boas vindas (home).
        
    private JPanelFuncionario pnlFuncionario; // Painel de manutenção para funcionário.
    private JPanelAFuncionario pnlAFuncionario; // Painel de manutenção para funcionário.
    
    // Construtor.
    public Controle() {
        
    }
    
    public boolean conectarBD() throws Exception {

        conexaoJDBC = new PersistenciaJDBC();

        if(getConexaoJDBC() != null) {

            return getConexaoJDBC().conexaoAberta();
            
        }

        return false;
    }
    
    public void fecharBD() {

        System.out.println("Fechando conexão com o Banco de Dados.");
        getConexaoJDBC().fecharConexao();

    }
    
    public void initComponents() {
        
        // Inicia a interface gráfica.
        // "Caminho feliz" : passo 5
        
        frame = new JFramePrincipal();
        
        pnlAutenticacao = new JPanelAutenticacao(this);
        
        menuBar = new JMenuBarHome(this);
        
        pnlHome = new JPanelHome(this);
        
        pnlFuncionario = new JPanelFuncionario(this);
        
        pnlAFuncionario = new JPanelAFuncionario(this);
        
        frame.addTela(pnlAutenticacao, "tela_autenticacao"); // Carta 1.
        frame.addTela(pnlHome, "tela_home"); // Carta 2.
        
        frame.addTela(pnlAFuncionario, "tela_funcionario");  // Carta 3 - poderia adicionar opcionalmente: pnlFuncionario.
        //frame.addTela(pnlFuncionario, "tela_funcionario"); // Carta 3 - poderia adicionar opcionalmente: pnlFuncionario.
        
        frame.showTela("tela_autenticacao"); // Apresenta a carta cujo nome é "tela_autenticacao".
        
        frame.setVisible(true); // Torna visível o jframe.
        
    }
    
    public void autenticar(String cpf, String senha) {
        
        try {

            Funcionario f =  conexaoJDBC.doLogin(cpf, senha);
            
            if(f != null) {

                JOptionPane.showMessageDialog(pnlAutenticacao, "Funcionário de CPF " + f.getCpf() /*+ " e nome "*/
                                             + /*f.getNome() +*/ " autenticado com sucesso.", "Autenticação", 
                                             JOptionPane.INFORMATION_MESSAGE);

                frame.setJMenuBar(menuBar); // Adiciona o menu de barra no frame.
                frame.showTela("tela_home"); // Muda a tela para o painel de boas vindas (home).

            } else {
                JOptionPane.showMessageDialog(pnlAutenticacao, "Dados inválidos.", "Autenticação", 
                                             JOptionPane.INFORMATION_MESSAGE);
            }

        } catch(Exception e) {
            JOptionPane.showMessageDialog(pnlAutenticacao, "Erro ao executar a autenticação no Banco de Dados.", 
                                         "Autenticação", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void showTela(String nomeTela) {
        
        if(nomeTela.equals("tela_autenticacao")){
                        
            pnlAutenticacao.cleanForm();            
            frame.showTela(nomeTela);            
            pnlAutenticacao.requestFocus();
            
        } else {
            frame.showTela(nomeTela);
        }   
        
    }
    
    /**
     * @return the conexaoJDBC
     */
    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }
    
}