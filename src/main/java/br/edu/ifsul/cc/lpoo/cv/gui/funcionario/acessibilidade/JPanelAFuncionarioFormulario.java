
package br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author telmo
 */
public class JPanelAFuncionarioFormulario extends JPanel implements ActionListener{
    
    
    private JPanelAFuncionario pnlAFuncionario;
    private Controle controle;
    
    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;
    
    private JPanel pnlDadosCadastrais;    
    private JPanel pnlCentroDadosCadastrais;
    
    private GridBagLayout gridBagLayoutDadosCadastrais;
    private JLabel lblCpf;
    private JTextField txfCpf;
    
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;
    
    //private JPanel pnlDadosCompras;
    //private JPanel pnlDadosArtefatos;
    //private JPanel pnlDadosPatentes;
    
    private JPanel pnlDadosConsultas;
    private JPanel pnlDadosReceitas;

    
    public JPanelAFuncionarioFormulario(JPanelAFuncionario pnlAFuncionario, Controle controle) {
        
        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;
        
        initComponents();
        
    }
    
    private void initComponents(){
        
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        
        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);
        
        pnlDadosCadastrais = new JPanel();
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);
        
        lblCpf = new JLabel("Cpf: ");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        pnlDadosCadastrais.add(lblCpf, posicionador); // O add adiciona o rótulo no painel.
        
        txfCpf = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        pnlDadosCadastrais.add(txfCpf, posicionador); // O add adiciona o rótulo no painel.
        
        tbpAbas.addTab("Dados cadastrais", pnlDadosCadastrais);
        
        //pnlDadosCompras = new JPanel();
        //tbpAbas.addTab("Compras", pnlDadosCompras);
        
        //pnlDadosArtefatos = new JPanel();
        //tbpAbas.addTab("Artefatos", pnlDadosArtefatos);
        
        //pnlDadosPatentes = new JPanel();
        //tbpAbas.addTab("Patentes", pnlDadosPatentes);
        
        pnlDadosConsultas = new JPanel();
        tbpAbas.addTab("Consultas", pnlDadosConsultas);
        
        pnlDadosReceitas = new JPanel();
        tbpAbas.addTab("Receitas", pnlDadosReceitas);
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true); // Acessibilidade.
        btnGravar.setToolTipText("btnGravarFuncionario"); // Acessibilidade.
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_funcionario");
        
        pnlSul.add(btnGravar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true); // Acessibilidade.
        btnCancelar.setToolTipText("btnCancelarFuncionario"); // Acessibilidade.
        btnCancelar.setActionCommand("botao_cancelar_formulario_funcionario");
        
        pnlSul.add(btnCancelar);
        
        this.add(pnlSul, BorderLayout.SOUTH);
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getActionCommand().equals(btnGravar.getActionCommand())) {
            
            pnlAFuncionario.showTela("tela_funcionario_listagem");
            
        } else if(arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            
        }
    }
}