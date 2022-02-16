
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import java.sql.Connection;
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
            PreparedStatement ps = this.con.prepareStatement("select id, data, observacao, data_retorno, valor, "
                                                            + "medico_cpf, pet_id "
                                                            + "from tb_consulta where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
            
                Consulta cons = new Consulta();
                
                cons.setId(rs.getInt("id"));
                
                Calendar data_var = Calendar.getInstance();
                data_var.setTimeInMillis(rs.getDate("data").getTime());
                cons.setData(data_var);
                    
                cons.setObservacao(rs.getString("observacao"));
                
                Calendar data_retorno_var = Calendar.getInstance();
                data_retorno_var.setTimeInMillis(rs.getDate("data_retorno").getTime());
                cons.setData_retorno(data_retorno_var);
                
                cons.setValor(rs.getFloat("valor"));
                
                Medico med = new Medico();
                med.setCpf(rs.getString("medico_cpf"));
                cons.setMedico(med);
                
                Pet pet = new Pet();
                pet.setId(rs.getInt("pet_id"));
                cons.setPet(pet);
                
                PreparedStatement psr = this.con.prepareStatement("select r.id, r.orientacao "
                                                                 + "from tb_receita r, tb_consulta c "
                                                                 + "where r.consulta_id = c.id and "
                                                                 + "c.id = ?");
                
                psr.setInt(1, Integer.parseInt(id.toString()));
                
                ResultSet rs2 = psr.executeQuery();

                while(rs2.next()) {

                    Receita r = new Receita();
                    
                    r.setId(rs2.getInt("id"));
                    r.setOrientacao(rs2.getString("orientacao"));

                    cons.setReceita(r);
                    
                }
                
                return cons;
            }
            
        } else if(c == Receita.class) {
            
            // tb_receita
            PreparedStatement ps = this.con.prepareStatement("select id, orientacao, consulta_id from "
                                                            + "tb_receita where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
            
                Receita rec = new Receita();
                
                rec.setId(rs.getInt("id"));
                
                rec.setOrientacao(rs.getString("orientacao"));
                
                Consulta consu = new Consulta();
                consu.setId(rs.getInt("consulta_id"));
                rec.setConsulta(consu);
                
                return rec;                
            }
            
        }  else if(c == Pessoa.class) {
            
            // tb_pessoa
            PreparedStatement ps = this.con.prepareStatement("select cpf, rg, nome, senha, numero_celular, "
                                                            + "email, data_cadastro, data_nascimento, cep, "
                                                            + "endereco, complemento, tipo from tb_pessoa "
                                                            + "where cpf = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
            
                Pessoa pes = new Pessoa();
                
                pes.setCpf(rs.getString("cpf"));
                
                pes.setRg(rs.getString("rg"));
                
                pes.setNome(rs.getString("nome"));
                
                pes.setSenha(rs.getString("senha"));
                
                pes.setNumero_celular(rs.getString("numero_celular"));
                
                pes.setEmail(rs.getString("email"));
                
                Calendar data_cadastro_cal = Calendar.getInstance();
                data_cadastro_cal.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                pes.setData_cadastro(data_cadastro_cal);
                
                Calendar data_nascimento_cal = Calendar.getInstance();
                data_nascimento_cal.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                pes.setData_nascimento(data_nascimento_cal);
                
                pes.setCep(rs.getString("cep"));
                
                pes.setEndereco(rs.getString("endereco"));
                
                pes.setComplemento(rs.getString("complemento"));
                
                pes.setTipo(rs.getString("tipo"));
                
                return pes;                
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
                                                                + "(id, data, observacao, data_retorno, valor, "
                                                                + "medico_cpf, pet_id) "
                                                                + "values (nextval('seq_consulta_id'), "
                                                                + "?, ?, ?, ?, ?, ?) returning id");
                
                ps.setDate(1, new java.sql.Date(c.getData().getTimeInMillis()));
                ps.setString(2, c.getObservacao());
                ps.setDate(3, new java.sql.Date(c.getData_retorno().getTimeInMillis()));
                ps.setFloat(4, c.getValor());
                ps.setString(5, c.getMedico().getCpf());
                ps.setInt(6, c.getPet().getId());
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    c.setId(rs.getInt(1));
                }
                
            } else {
                
                // UPDATE.
                PreparedStatement ps = this.con.prepareStatement("update tb_consulta set "
                                                                + "data = ?, "
                                                                + "observacao = ?, "
                                                                + "data_retorno = ?, "
                                                                + "valor = ?, "
                                                                + "medico_cpf = ?, "
                                                                + "pet_id = ? "
                                                                + "where id = ?");
                
                ps.setDate(1, new java.sql.Date(c.getData().getTimeInMillis()));
                ps.setString(2, c.getObservacao());
                ps.setDate(3, new java.sql.Date(c.getData_retorno().getTimeInMillis()));
                ps.setFloat(4, c.getValor());
                ps.setString(5, c.getMedico().getCpf());
                ps.setInt(6, c.getPet().getId());
                ps.setInt(7, c.getId());
                
                ps.execute(); // Executa o comando.
                
            }
        } else if(o instanceof Receita) {
            
            Receita r = (Receita) o; // Converter o para o e que eh do tipo Receita.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(r.getId() == null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_receita "
                                                                + "(id, orientacao, consulta_id) "
                                                                + "values (nextval('seq_receita_id'), ?, ?) "
                                                                + "returning id");
                
                ps.setString(1, r.getOrientacao());
                ps.setInt(2, r.getConsulta().getId());
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    r.setId(rs.getInt(1));
                }
                
            } else {
                
                // UPDATE.
                PreparedStatement ps = this.con.prepareStatement("update tb_receita set "
                                                                + "orientacao = ?, "
                                                                + "consulta_id = ? "
                                                                + "where id = ?");
                
                ps.setString(1, r.getOrientacao());
                ps.setInt(2, r.getConsulta().getId());
                ps.setInt(3, r.getId());
                
                ps.execute(); // Executa o comando.
                
            }
        } else if(o instanceof Pessoa) {
            
            Pessoa p = (Pessoa) o; // Converter o para o e que eh do tipo Pessoa.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(p.getData_cadastro()== null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                                                                + "(cpf, rg, nome, senha, numero_celular, "
                                                                + "email, data_cadastro, data_nascimento, "
                                                                + "cep, endereco, complemento, tipo) "
                                                                + "values (?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?, ?)");
                
                ps.setString(1, p.getCpf());
                ps.setString(2, p.getRg());
                ps.setString(3, p.getNome());
                ps.setString(4, p.getSenha());
                ps.setString(5, p.getNumero_celular());
                ps.setString(6, p.getEmail());
                //ps.setDate(7, new java.sql.Date(p.getData_cadastro().getTimeInMillis()));
                //ps.setString(7, "now()");
                ps.setDate(7, new java.sql.Date(p.getData_nascimento().getTimeInMillis()));
                ps.setString(8, p.getCep());
                ps.setString(9, p.getEndereco());
                ps.setString(10, p.getComplemento());
                ps.setString(11, p.getTipo());
                
                ps.execute();
                
            } else {
                
                // UPDATE.
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set "
                                                                + "rg = ?, "
                                                                + "nome = ?, "
                                                                + "senha = ?,"
                                                                + "numero_celular = ?, "
                                                                + "email = ?, "
                                                                + "data_nascimento = ?, "
                                                                + "cep = ?, "
                                                                + "endereco = ?, "
                                                                + "complemento = ?, "
                                                                + "tipo = ? "
                                                                + "where cpf = ?");
                
                ps.setString(1, p.getRg());
                ps.setString(2, p.getNome());
                ps.setString(3, p.getSenha());
                ps.setString(4, p.getNumero_celular());
                ps.setString(5, p.getEmail());
                //ps.setDate(6, new java.sql.Date(p.getData_cadastro().getTimeInMillis()));
                ps.setDate(6, new java.sql.Date(p.getData_nascimento().getTimeInMillis()));
                ps.setString(7, p.getCep());
                ps.setString(8, p.getEndereco());
                ps.setString(9, p.getComplemento());
                ps.setString(10, p.getTipo());
                ps.setString(11, p.getCpf());
                
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
            
        } else if(o instanceof Pessoa) {
            
            Pessoa p = (Pessoa) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps.setString(1, p.getCpf());
            ps.execute();
            
        }
        
    }

    @Override
    public List<Consulta> listConsultas() throws Exception {
        
        List<Consulta> lista = null;
        
        PreparedStatement ps = this.con.prepareStatement("select id, data, data_retorno, observacao, valor, "
                                                        + "medico_cpf, pet_id "
                                                        + "from tb_consulta order by id asc");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.
        
        lista = new ArrayList();
        
        while(rs.next()) {
            
            Consulta cons = new Consulta();
            
            cons.setId(rs.getInt("id"));
            
            Calendar data_cal = Calendar.getInstance();
            data_cal.setTimeInMillis(rs.getDate("data").getTime());
            cons.setData(data_cal);
            
            Calendar data_retorno_cal = Calendar.getInstance();
            data_retorno_cal.setTimeInMillis(rs.getDate("data_retorno").getTime());
            cons.setData_retorno(data_retorno_cal);
            
            cons.setObservacao(rs.getString("observacao"));
            
            cons.setValor(rs.getFloat("valor"));
            
            Medico med = new Medico();
            med.setCpf(rs.getString("medico_cpf"));
            cons.setMedico(med);
            
            Pet pet = new Pet();
            pet.setId(rs.getInt("pet_id"));
            cons.setPet(pet);
            
            PreparedStatement psr = this.con.prepareStatement("select id, orientacao "
                                                             + "from tb_receita where consulta_id = ?"); // AQUI ERA consulta_id NO LUGAR DE id
            
            psr.setInt(1, cons.getId());

            ResultSet rs2 = psr.executeQuery();

            while(rs2.next()) {

                Receita r = new Receita();

                r.setId(rs2.getInt("id"));
                r.setOrientacao(rs2.getString("orientacao"));
                r.setConsulta(cons);

                cons.setReceita(r);

            }
            
            lista.add(cons); // Adiciona na lista o objeto que contem as informacoes de uma determinada linha do ResultSet.
            
        }
        
        return lista;
        
    }

    @Override
    public List<Receita> listReceitas() throws Exception {
        
        List<Receita> lista = null;
        
        PreparedStatement ps = this.con.prepareStatement("select id, orientacao, consulta_id "
                                                        + "from tb_receita order by id asc");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.
        
        lista = new ArrayList();
        
        while(rs.next()) {
            
            Receita rec = new Receita();
        
            rec.setId(rs.getInt("id"));
            
            rec.setOrientacao(rs.getString("orientacao"));
            
            Consulta cons = new Consulta();
            cons.setId(rs.getInt("consulta_id"));
            rec.setConsulta(cons);
            
        }
        
        return lista;
        
    }
    
    @Override
    public List<Pessoa> listPessoas() throws Exception {
                
        List<Pessoa> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select cpf, rg, "
                                                        + "nome, senha, numero_celular, "
                                                        + "email, data_cadastro, data_nascimento, cep, endereco, "
                                                        + "complemento, tipo from tb_pessoa "
                                                        + "order by data_cadastro asc");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.

        lista = new ArrayList();
        while(rs.next()){
            
            Pessoa p = new Pessoa();
            
            p.setCpf(rs.getString("cpf"));
            
            p.setRg(rs.getString("rg"));
            
            p.setNome(rs.getString("nome"));
            
            p.setSenha(rs.getString("senha"));
            
            p.setNumero_celular(rs.getString("numero_celular"));
            
            p.setEmail(rs.getString("email"));

            Calendar dtCad = Calendar.getInstance();
            dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
            p.setData_cadastro(dtCad);

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            p.setData_nascimento(dtU);
            
            p.setCep(rs.getString("cep"));
            
            p.setEndereco(rs.getString("endereco"));
            
            p.setComplemento(rs.getString("complemento"));
            
            p.setTipo(rs.getString("tipo"));

            
            lista.add(p);
        
        }
        
        return lista;
        
    }
    
    @Override
    public Pessoa doLogin(String cpf, String senha) throws Exception {
                
        Pessoa pessoa = null;
        
         PreparedStatement ps = 
            this.con.prepareStatement("select p.cpf, p.senha from tb_pessoa p where p.cpf = ? and p.senha = ? ");
                        
            ps.setString(1, cpf);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery(); // O ponteiro do ResultSet inicialmente está na linha -1.
            
            if(rs.next()) { // Se a matriz (ResultSet) tem uma linha.

                pessoa = new Pessoa();
                pessoa.setCpf(rs.getString("cpf"));                
            }
        
            ps.close();
            return pessoa;
        
    }
    
}