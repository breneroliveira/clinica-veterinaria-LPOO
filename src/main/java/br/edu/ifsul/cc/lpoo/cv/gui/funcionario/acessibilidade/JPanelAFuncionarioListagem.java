
package br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author telmo
 */
public class JPanelAFuncionarioListagem extends JPanel implements ActionListener {
    
    private JPanelAFuncionario pnlAFuncionario;
    private Controle controle;
    
    private BorderLayout borderLayout;
    private JPanel pnlNorte;
    private JLabel lblFiltro;
    private JTextField txfFiltro;
    private JButton btnFiltro;
    
    private JPanel pnlCentro;
    private JScrollPane scpListagem;
    private JTable tblListagem;
    private DefaultTableModel modeloTabela;
    
    private JPanel pnlSul;
    private JButton btnNovo;
    private JButton btnAlterar;
    private JButton btnRemover;
    
    public JPanelAFuncionarioListagem(JPanelAFuncionario pnlAFuncionario, Controle controle) {
        
        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;
        
        initComponents();
    }
    
    private void initComponents(){
        
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);//seta o gerenciado border para este painel
        
        pnlNorte = new JPanel();
        pnlNorte.setLayout(new FlowLayout());
        
        lblFiltro = new JLabel("Filtrar por CPF:");
        pnlNorte.add(lblFiltro);
        
        txfFiltro = new JTextField(20);
        pnlNorte.add(txfFiltro);
        
        btnFiltro = new JButton("Filtrar");
        btnFiltro.addActionListener(this);
        btnFiltro.setFocusable(true); // Acessibilidade.
        btnFiltro.setToolTipText("btnFiltrar"); // Acessibilidade.
        btnFiltro.setActionCommand("botao_filtro");
        pnlNorte.add(btnFiltro);
        
        this.add(pnlNorte, BorderLayout.NORTH); // Adiciona o painel na posição norte.
        
        pnlCentro = new JPanel();
        pnlCentro.setLayout(new BorderLayout());
            
        scpListagem = new JScrollPane();
        tblListagem =  new JTable();
        
        modeloTabela = new DefaultTableModel(
            new String [] {
                "CPF", "Data cadastro", "Endereço"
            }, 0
        );
        
        tblListagem.setModel(modeloTabela);
        scpListagem.setViewportView(tblListagem);
    
        pnlCentro.add(scpListagem, BorderLayout.CENTER);
        
        this.add(pnlCentro, BorderLayout.CENTER); // Adiciona o painel na posição norte.
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this);
        btnNovo.setFocusable(true); // Acessibilidade.
        btnNovo.setToolTipText("btnNovo"); // Acessibilidade.
        btnNovo.setMnemonic(KeyEvent.VK_N);
        btnNovo.setActionCommand("botao_novo");
        
        pnlSul.add(btnNovo);
        
        btnAlterar = new JButton("Editar");
        btnAlterar.addActionListener(this);
        btnAlterar.setFocusable(true); // Acessibilidade.
        btnAlterar.setToolTipText("btnAlterar"); // Acessibilidade.
        btnAlterar.setActionCommand("botao_alterar");
        
        pnlSul.add(btnAlterar);
        
        btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this);
        btnRemover.setFocusable(true); // Acessibilidade.
        btnRemover.setToolTipText("btnRemvoer"); // Acessibilidade.
        btnRemover.setActionCommand("botao_remover");
        
        pnlSul.add(btnRemover); // Adiciona o botão na fila organizada pelo flowlayout.
        
        this.add(pnlSul, BorderLayout.SOUTH); // Adiciona o painel na posição norte.
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    
        if(arg0.getActionCommand().equals(btnNovo.getActionCommand())) {
            
            pnlAFuncionario.showTela("tela_funcionario_formulario");
            
        } else if(arg0.getActionCommand().equals(btnAlterar.getActionCommand())) {
            
            
        } else if(arg0.getActionCommand().equals(btnRemover.getActionCommand())) {           
            
        }   
    
    }
    
}