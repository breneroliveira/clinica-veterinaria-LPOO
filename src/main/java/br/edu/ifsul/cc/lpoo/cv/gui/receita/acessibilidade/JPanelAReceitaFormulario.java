
package br.edu.ifsul.cc.lpoo.cv.gui.receita.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author brener 
 */

public class JPanelAReceitaFormulario extends JPanel implements ActionListener {
    
    private JPanelAReceita pnlAReceita;
    private Controle controle;
    
    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;
    
    private JPanel pnlDadosCadastrais;    
    private JPanel pnlCentroDadosCadastrais;
    private GridBagLayout gridBagLayoutDadosCadastrais;
    
    private JLabel lblId;
    private JTextField txfId;
    
    private JLabel lblOrientacao;
    private JTextField txfOrientacao;
    
    private JLabel lblConsulta;
    private JComboBox cbxConsulta;
    
    private Receita receita;
    private SimpleDateFormat format;
    
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;
    
    public JPanelAReceitaFormulario(JPanelAReceita pnlAReceita, Controle controle) {
        
        this.pnlAReceita = pnlAReceita;
        this.controle = controle;
        
        initComponents();
        
    }
    
    public void populaComboConsulta() {
        
        cbxConsulta.removeAllItems(); // Zera o combo.

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxConsulta.getModel();

        model.addElement("Selecione"); // Primeiro item.
        try {

            List<Consulta> listConsultas = controle.getConexaoJDBC().listConsultas();
            for(Consulta c : listConsultas) {
                model.addElement(c);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar receitas: " + ex.getLocalizedMessage(), 
                                         "Receitas", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }     
    }
    
    public Receita getReceitabyFormulario() {
        
        // Validação do formulário.
         if(txfOrientacao.getText().trim().length() > 0 && 
            cbxConsulta.getSelectedIndex() > 0) {

            Receita r = new Receita();
            
            String strId = txfId.getText();
            Integer id_convert = Integer.parseInt(strId);
            r.setId(id_convert);
            
            r.setOrientacao(txfOrientacao.getText().trim());
            
            r.setConsulta((Consulta) cbxConsulta.getSelectedItem());
            
            if(receita != null){
                r.setId(receita.getId());
            }
            
            return r;
         }
         return null;
    }
    
    public void setReceitaFormulario(Receita r) {

        if(r == null){ // Se o parâmetro estiver nulo, limpa o formulário.
            
            txfId.setEditable(false);
            txfId.setText("");
            txfOrientacao.setText("");;
            cbxConsulta.setSelectedIndex(0);
            
            receita = null;

        } else {

            receita = r;
            
            txfId.setEditable(false);
            txfId.setText(receita.getId().toString());
            txfOrientacao.setText(receita.getOrientacao());
            cbxConsulta.getModel().setSelectedItem(receita.getConsulta());

        }

    }
    
    private void initComponents() {
        
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        
        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);
        
        pnlDadosCadastrais = new JPanel();
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);
        
        lblId = new JLabel("ID: ");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; // Ancoragem a direita.
        pnlDadosCadastrais.add(lblId, posicionador); // O add adiciona o rótulo no painel.
        
        txfId = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfId, posicionador); // O add adiciona o rótulo no painel.
        
        lblOrientacao = new JLabel("Orientação: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; // Ancoragem a direita.
        pnlDadosCadastrais.add(lblOrientacao, posicionador); // O add adiciona o rótulo no painel.
        
        txfOrientacao = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfOrientacao, posicionador); // O add adiciona o rótulo no painel.
        
        lblConsulta = new JLabel("Consulta: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; // Ancoragem a direita.
        pnlDadosCadastrais.add(lblConsulta, posicionador); // O add adiciona o rotulo no painel.
                
        cbxConsulta = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxConsulta, posicionador); // O add adiciona o rotulo no painel.
        
        tbpAbas.addTab("Dados cadastrais", pnlDadosCadastrais);
        
        /*pnlDadosVendas = new JPanel();
        tbpAbas.addTab("Vendas", pnlDadosVendas);
        
        pnlDadosProdutos = new JPanel();
        tbpAbas.addTab("Produtos", pnlDadosProdutos);*/
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true); // Acessibilidade.
        btnGravar.setToolTipText("btnGravarReceita"); // Acessibilidade.
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_receita");
        
        pnlSul.add(btnGravar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true); // Acessibilidade.
        btnCancelar.setToolTipText("btnCancelarReceita"); // Acessibilidade.
        btnCancelar.setActionCommand("botao_cancelar_formulario_receita");
        
        pnlSul.add(btnCancelar);
        
        this.add(pnlSul, BorderLayout.SOUTH);
        
        format = new SimpleDateFormat("dd/MM/yyyy");
        
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getActionCommand().equals(btnGravar.getActionCommand())) {
            
            Receita r = getReceitabyFormulario(); // Recupera os dados do formulário.
            
            if(r != null) {

                try {
                    
                    pnlAReceita.getControle().getConexaoJDBC().persist(r);
                    
                    JOptionPane.showMessageDialog(this, "Consulta armazenada.", "Salvar", 
                                                 JOptionPane.INFORMATION_MESSAGE);
            
                    pnlAReceita.showTela("tela_receita_listagem");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar receita: " + ex.getMessage(), 
                                                 "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha o formulário.", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else if(arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            pnlAReceita.showTela("tela_receita_listagem");
        }
    }
    
}