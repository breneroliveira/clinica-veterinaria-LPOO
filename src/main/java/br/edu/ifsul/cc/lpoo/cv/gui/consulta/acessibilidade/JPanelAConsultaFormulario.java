
package br.edu.ifsul.cc.lpoo.cv.gui.consulta.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author brener
 */

public class JPanelAConsultaFormulario extends JPanel implements ActionListener {
    
    private JPanelAConsulta pnlAConsulta;
    private Controle controle;
    
    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;
    
    private JPanel pnlDadosCadastrais;    
    private JPanel pnlCentroDadosCadastrais;
    private GridBagLayout gridBagLayoutDadosCadastrais;
    
    private JLabel lblId;
    private JTextField txfId;
    
    private JLabel lblData;
    private JTextField txfData;
    
    private JLabel lblObservacao;
    private JTextField txfObservacao;
    
    private JLabel lblData_retorno;
    private JTextField txfData_retorno;
    
    private JLabel lblValor;
    private JTextField txfValor;
    
    private JLabel lblMedico;
    private JComboBox cbxMedico;
    
    private JLabel lblPet;
    private JComboBox cbxPet;
    
    private Consulta consulta;
    private SimpleDateFormat format;
    
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;
    
    //private JPanel pnlDadosVendas;
    //private JPanel pnlDadosProdutos;

    
    public JPanelAConsultaFormulario(JPanelAConsulta pnlAConsulta, Controle controle) {
        
        this.pnlAConsulta = pnlAConsulta;
        this.controle = controle;
        
        initComponents();
        
    }
    
    public void populaComboMedico(){
        
        cbxMedico.removeAllItems(); // Zera o combo.

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxMedico.getModel();

        model.addElement("Selecione"); // Primeiro item.
        try {

            List<Medico> listMedicos = controle.getConexaoJDBC().listMedicos();
            for(Medico m : listMedicos) {
                model.addElement(m);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar médicos: " + ex.getLocalizedMessage(), 
                                         "Médicos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }     
    }
    
    public void populaComboPet(){
        
        cbxPet.removeAllItems(); // Zera o combo.

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxPet.getModel();

        model.addElement("Selecione"); // Primeiro item.
        try {

            List<Pet> listPets = controle.getConexaoJDBC().listPets();
            for(Pet p : listPets) {
                model.addElement(p);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar pets: " + ex.getLocalizedMessage(), 
                                         "Pets", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }     
    }
    
    public Consulta getConsultabyFormulario() {
        
        // Validação do formulário.
         if(txfData.getText().trim() != null && 
            txfObservacao.getText().trim().length() > 0 &&
            txfData_retorno.getText().trim() != null && 
            txfValor.getText().trim().length() > 0 &&
            cbxMedico.getSelectedIndex() > 0 &&
            cbxPet.getSelectedIndex() > 0) {

            Consulta c = new Consulta();
            
            /*String strId = txfId.getText();
            Integer id_convert = Integer.parseInt(strId);
            c.setId(id_convert);*/
            
            String strData = txfData.getText();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                cal.setTime(sdf.parse(strData));
                c.setData(cal);
            } catch (ParseException ex) {
                Logger.getLogger(JPanelAConsultaFormulario.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            c.setObservacao(txfObservacao.getText().trim());
            
            String strData_retorno = txfData_retorno.getText();
            Calendar cal_2 = Calendar.getInstance();
            SimpleDateFormat sdf_2 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                cal_2.setTime(sdf_2.parse(strData_retorno));
                c.setData_retorno(cal_2);
            } catch (ParseException ex) {
                Logger.getLogger(JPanelAConsultaFormulario.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            c.setValor(Float.parseFloat(txfValor.getText().trim()));
            
            c.setMedico((Medico) cbxMedico.getSelectedItem());
            c.setPet((Pet) cbxPet.getSelectedItem());
            
            if(consulta != null){
                c.setId(consulta.getId());
            }
            
            return c;
         }
         return null;
    }
    
    public void setConsultaFormulario(Consulta c) {

        if(c == null){ // Se o parâmetro estiver nulo, limpa o formulário.
            
            txfId.setEditable(false);
            txfId.setText("");
            txfData.setText("");
            txfObservacao.setText("");
            txfData_retorno.setText("");
            txfValor.setText("");
            cbxMedico.setSelectedIndex(0);
            cbxPet.setSelectedIndex(0);
            
            consulta = null;

        } else {

            consulta = c;
            
            txfId.setEditable(false);
            txfId.setText(consulta.getId().toString());
            //txfData.setText(consulta.getData().toString());
            txfData.setText(format.format(c.getData().getTime()));
            txfObservacao.setText(consulta.getObservacao());
            //txfData_retorno.setText(consulta.getData_retorno().toString());
            txfData_retorno.setText(format.format(c.getData_retorno().getTime()));
            txfValor.setText(consulta.getValor().toString());
            cbxMedico.getModel().setSelectedItem(consulta.getMedico());
            cbxPet.getModel().setSelectedItem(consulta.getPet());

        }

    }
    
    private void initComponents(){
        
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
        posicionador.gridy = 0; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfId, posicionador); // O add adiciona o rótulo no painel.
        
        lblData = new JLabel("Data da consulta: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblData, posicionador); // O add adiciona o rótulo no painel.
        try {
            txfData = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (ParseException ex) {
            Logger.getLogger(JPanelAConsultaFormulario.class.getName()).log(Level.SEVERE, null, ex);
        }
        txfData.setColumns(6);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfData, posicionador); // O add adiciona o rótulo no painel.
        
        lblObservacao = new JLabel("Observação: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; // Ancoragem a direita.
        pnlDadosCadastrais.add(lblObservacao, posicionador); // O add adiciona o rótulo no painel.
        
        txfObservacao = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfObservacao, posicionador); // O add adiciona o rótulo no painel.
        
        lblData_retorno = new JLabel("Data de retorno: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblData_retorno, posicionador); // O add adiciona o rótulo no painel.
        try {
            txfData_retorno = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (ParseException ex) {
            Logger.getLogger(JPanelAConsultaFormulario.class.getName()).log(Level.SEVERE, null, ex);
        }
        txfData_retorno.setColumns(6);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfData_retorno, posicionador); // O add adiciona o rótulo no painel.
        
        lblValor = new JLabel("Valor: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; // Ancoragem a direita.
        pnlDadosCadastrais.add(lblValor, posicionador); // O add adiciona o rótulo no painel.
        
        txfValor = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfValor, posicionador); // O add adiciona o rótulo no painel.
        
        lblMedico = new JLabel("Médico: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; // Ancoragem a direita.
        pnlDadosCadastrais.add(lblMedico, posicionador); // O add adiciona o rotulo no painel.
                
        cbxMedico = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxMedico, posicionador); // O add adiciona o rotulo no painel.
        
        lblPet = new JLabel("Pet: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; // Ancoragem a direita.
        pnlDadosCadastrais.add(lblPet, posicionador); // O add adiciona o rotulo no painel.
                
        cbxPet = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxPet, posicionador); // O add adiciona o rotulo no painel.
        
        tbpAbas.addTab("Dados cadastrais", pnlDadosCadastrais);
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true); // Acessibilidade.
        btnGravar.setToolTipText("btnGravarProduto"); // Acessibilidade.
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_produto");
        
        pnlSul.add(btnGravar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true); // Acessibilidade.
        btnCancelar.setToolTipText("btnCancelarProduto"); // Acessibilidade.
        btnCancelar.setActionCommand("botao_cancelar_formulario_produto");
        
        pnlSul.add(btnCancelar);
        
        this.add(pnlSul, BorderLayout.SOUTH);
        
        format = new SimpleDateFormat("dd/MM/yyyy");
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getActionCommand().equals(btnGravar.getActionCommand())) {
            
            Consulta c = getConsultabyFormulario(); // Recupera os dados do formulário.
            
            if(c != null) {

                try {
                    
                    pnlAConsulta.getControle().getConexaoJDBC().persist(c);
                    
                    JOptionPane.showMessageDialog(this, "Consulta armazenada.", "Salvar", JOptionPane.INFORMATION_MESSAGE);
            
                    pnlAConsulta.showTela("tela_consulta_listagem");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar consulta: " + ex.getMessage(), "Salvar", 
                                                 JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha o formulário.", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else if(arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            pnlAConsulta.showTela("tela_consulta_listagem");
        }
    }
    
}