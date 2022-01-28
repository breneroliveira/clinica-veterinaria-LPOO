
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author brener
 */

public class PersistenciaJDBC implements InterfacePersistencia {
    
    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "ifsulbd";
    public static final String URL = "jdbc:postgresql://localhost:5432/db_clinica_veterinaria";
    private Connection con = null;
    
    public PersistenciaJDBC() throws Exception {
        
        Class.forName(DRIVER); // Carregamento do driver postgresql em tempo de execucao.
        System.out.println("Tentando estabelecer conexao JDBC com: " + URL + " ...");
            
        this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA);
        
    }

    @Override
    public Boolean conexaoAberta() {
        try {
            if(con != null)
                return !con.isClosed(); // Verifica se a conexao esta aberta.
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public void fecharConexao() {
        try {
            this.con.close(); // Fecha a conexao.
            System.out.println("Fechou conexao JDBC.");
        } catch (SQLException e) {
            e.printStackTrace(); // Gera uma pilha de erro na saida.
        }
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        
        if(c == Consulta.class) {
            
            // tb_consulta
            PreparedStatement ps = this.con.prepareStatement("select id, data, observacao, data_retorno, valor "
                                                            + "from tb_consulta where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
            
                Consulta con = new Consulta();
                
                con.setId(rs.getInt("id"));
                
                Calendar data_var = Calendar.getInstance();
                data_var.setTimeInMillis(rs.getDate("data").getTime());
                con.setData(data_var);
                    
                con.setObservacao(rs.getString("observacao"));
                
                Calendar data_retorno_var = Calendar.getInstance();
                data_retorno_var.setTimeInMillis(rs.getDate("data_retorno").getTime());
                con.setData_retorno(data_retorno_var);
                
                ps.close();
                
                return con;                
            }
            
        } else if(c == Receita.class) {
            
            // tb_receita
            PreparedStatement ps = this.con.prepareStatement("select id, orientacao from "
                                                            + "tb_receita where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
            
                Receita res = new Receita();
                
                res.setId(rs.getInt("id"));
                
                res.setOrientacao(rs.getString("orientacao"));
                
                ps.close();
                
                return res;                
            }
            
        }
        
        return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        
        // Descobrir a instancia do Object o.
        if(o instanceof Consulta){
            
            Consulta c = (Consulta) o; // Converter o para o e que eh do tipo Consulta.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(c.getId() == null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_consulta "
                                                                + "(id, data, observacao, data_retorno, valor) "
                                                                + "values (nextval('seq_consulta_id'), "
                                                                + "?, ?, ?, ?)");
                ps.setDate(1, (Date) c.getData().getTime());
                ps.setString(2, c.getObservacao());
                ps.setDate(3, (Date) c.getData_retorno().getTime());
                ps.setFloat(4, c.getValor());
                
                ps.execute();
                
            } else {
                
                // UPDATE.
                PreparedStatement ps = this.con.prepareStatement("update tb_consulta set "
                                                                + "data = ?, "
                                                                + "observacao = ?, "
                                                                + "data_retorno = ?, "
                                                                + "valor = ? "
                                                                + "where id = ?");
                //ps.setDate(1, c.getData());                
                //ps.setDate(1, (Date) c.getData().getTime());
                ps.setDate(1, new java.sql.Date(c.getData().getTimeInMillis()));
                ps.setString(2, c.getObservacao());
                //ps.setDate(3, c.getData_retorno());
                //ps.setDate(3, (Date) c.getData_retorno().getTime());
                ps.setDate(3, new java.sql.Date(c.getData_retorno().getTimeInMillis()));
                ps.setFloat(4, c.getValor());
                ps.setInt(5, c.getId());
                
                ps.execute(); // Executa o comando.
                
            }
        } else if(o instanceof Receita) {
            
            Receita r = (Receita) o; // Converter o para o e que eh do tipo Receita.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(r.getId() == null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_receita "
                                                                + "(id, orientacao) "
                                                                + "values (nextval('seq_receita_id'), ?)");
                ps.setString(1, r.getOrientacao());
                
                ps.execute();
                
            } else {
                
                // UPDATE.
                PreparedStatement ps = this.con.prepareStatement("update tb_receita set "
                                                                + "orientacao = ?, "
                                                                + "where id = ?");
                ps.setString(1, r.getOrientacao());
                ps.setInt(2, r.getId());
                
                ps.execute(); // Executa o comando.
                
            }
        }
    }

    @Override
    public void remover(Object o) throws Exception {
        
        if(o instanceof Consulta) {
            
            Consulta c = (Consulta) o; // Converter o para o e que eh do tipo Consulta.
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_consulta where id = ?");
            ps.setInt(1, c.getId());
            ps.execute();
            
        } else if(o instanceof Receita) {
            
            Receita r = (Receita) o; // Converter o para o e que eh do tipo Receita.
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_receita where id = ?");
            ps.setInt(1, r.getId());
            ps.execute();
            
        }
        
    }

    @Override
    public List<Consulta> listConsultas() throws Exception {
        
        List<Consulta> lista = null;
        
        PreparedStatement ps = this.con.prepareStatement("select id, data, observacao, data_retorno, valor "
                                                        + "from tb_consulta order by id asc");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.
        
        lista = new ArrayList();
        
        while(rs.next()) {
            
            Consulta con = new Consulta();
            
            con.setId(rs.getInt("id"));
            
            Calendar data_cal = Calendar.getInstance();
            data_cal.setTimeInMillis(rs.getDate("data").getTime());
            con.setData(data_cal);
            
            //con.setData(rs.getDate("data"));
            
            con.setObservacao(rs.getString("observacao"));
            
            Calendar data_retorno_cal = Calendar.getInstance();
            data_retorno_cal.setTimeInMillis(rs.getDate("data_retorno").getTime());
            con.setData(data_retorno_cal);
            
            //con.setData_retorno(rs.getDate("data_retorno"));
            
            con.setValor(rs.getFloat("valor"));
            
            lista.add(con); // Adiciona na lista o objeto que contem as informacoes de uma determinada linha do ResultSet.
            
        }
        
        return lista;
        
    }

    @Override
    public List<Receita> listReceitas() throws Exception {
        
        List<Receita> lista = null;
        
        PreparedStatement ps = this.con.prepareStatement("select id, orientacao "
                                                        + "from tb_receita order by id asc");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.
        
        lista = new ArrayList();
        
        while(rs.next()) {
            
            Receita res = new Receita();
        
            res.setId(rs.getInt("id"));
            res.setOrientacao(rs.getString("orientacao"));
            
        }
        
        return lista;
        
    }
}