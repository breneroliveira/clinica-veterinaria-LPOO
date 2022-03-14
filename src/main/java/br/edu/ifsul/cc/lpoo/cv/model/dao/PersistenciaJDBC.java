
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Raca;
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
            
        /*}  else if(c == Pessoa.class) {
            
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
            }*/
            
        } else if(c == Funcionario.class) {
            PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, pes.nome, pes.senha, "
                                                            + "pes.numero_celular, pes.email, pes.data_cadastro, "
                                                            + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                            + "pes.complemento, Fun.cargo, " // TIREI ", pes.tipo"
                                                            + "Fun.numero_ctps, Fun.numero_pis "
                                                            + "from tb_pessoa as pes "
                                                            + "inner join tb_funcionario as Fun on "
                                                            + "pes.cpf = Fun.cpf where pes.cpf = ?");
                                                            
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                
                Funcionario f = new Funcionario();
                
                f.setCpf(rs.getString("cpf"));
                
                f.setRg(rs.getString("rg"));
                
                f.setNome(rs.getString("nome"));
                
                f.setSenha(rs.getString("senha"));
                
                f.setNumero_celular(rs.getString("numero_celular"));
                
                f.setEmail(rs.getString("email"));
                
                if(rs.getDate("data_cadastro") != null) {
                    Calendar data_cadastro_cal = Calendar.getInstance();
                    data_cadastro_cal.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                    f.setData_cadastro(data_cadastro_cal);
                }
                
                Calendar data_nascimento_cal = Calendar.getInstance();
                data_nascimento_cal.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                f.setData_nascimento(data_nascimento_cal);
                
                f.setCep(rs.getString("cep"));
                
                f.setEndereco(rs.getString("endereco"));
                
                f.setComplemento(rs.getString("complemento"));
                
                //f.setTipo(rs.getString("tipo"));
                
                f.setCargo(Cargo.getCargo(rs.getString("cargo")));
                
                f.setNumero_ctps(rs.getString("numero_ctps"));
                
                f.setNumero_pis(rs.getString("numero_pis"));
                
                return f;
            }
        } else if(c == Medico.class) {
            PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, pes.nome, pes.senha, "
                                                            + "pes.numero_celular, pes.email, pes.data_cadastro, "
                                                            + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                            + "pes.complemento, M.numero_crmv "
                                                            + "from tb_pessoa as pes "
                                                            + "inner join tb_medico as M on "
                                                            + "pes.cpf = M.cpf where pes.cpf = ?");
                                                            
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                
                Medico m = new Medico();
                
                m.setCpf(rs.getString("cpf"));
                m.setRg(rs.getString("rg"));               
                m.setNome(rs.getString("nome"));                
                m.setSenha(rs.getString("senha"));
                m.setNumero_celular(rs.getString("numero_celular"));
                m.setEmail(rs.getString("email"));
                
                Calendar data_cadastro_cal = Calendar.getInstance();
                data_cadastro_cal.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                m.setData_cadastro(data_cadastro_cal);
                
                Calendar data_nascimento_cal = Calendar.getInstance();
                data_nascimento_cal.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                m.setData_nascimento(data_nascimento_cal);
                
                m.setCep(rs.getString("cep"));
                m.setEndereco(rs.getString("endereco"));
                m.setComplemento(rs.getString("complemento"));
                //f.setTipo(rs.getString("tipo"));
                m.setNumero_celular(rs.getString("numero_crmv"));
                
                return m;
            }
        } else if(c == Cliente.class) {
            PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, pes.nome, pes.senha, "
                                                            + "pes.numero_celular, pes.email, pes.data_cadastro, "
                                                            + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                            + "pes.complemento, C.data_ultima_visita "
                                                            + "from tb_pessoa as pes "
                                                            + "inner join tb_cliente as C on "
                                                            + "pes.cpf = C.cpf where pes.cpf = ?");
                                                            
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                
                Cliente cl = new Cliente();
                
                cl.setCpf(rs.getString("cpf"));
                
                cl.setRg(rs.getString("rg")); 
                
                cl.setNome(rs.getString("nome"));    
                
                cl.setSenha(rs.getString("senha"));
                
                cl.setNumero_celular(rs.getString("numero_celular"));
                
                cl.setEmail(rs.getString("email"));
                
                Calendar data_cadastro_cal = Calendar.getInstance();
                data_cadastro_cal.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                cl.setData_cadastro(data_cadastro_cal);
                
                Calendar data_nascimento_cal = Calendar.getInstance();
                data_nascimento_cal.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                cl.setData_nascimento(data_nascimento_cal);
                
                cl.setCep(rs.getString("cep"));
                
                cl.setEndereco(rs.getString("endereco"));
                
                cl.setComplemento(rs.getString("complemento"));
                
                Calendar data_ultima_cal = Calendar.getInstance();
                data_ultima_cal.setTimeInMillis(rs.getDate("data_ultima_visita").getTime());
                cl.setData_ultima_visita(data_ultima_cal);
                
                return cl;
            }
        } else if(c == Pet.class) {
            
            // tb_pet
            PreparedStatement ps = this.con.prepareStatement("select id, nome, data_nascimento, observacao, "
                                                            + "cliente_cpf, raca_id from "
                                                            + "tb_pet where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
            
                Pet pe = new Pet();
                
                pe.setId(rs.getInt("id"));
                
                pe.setNome(rs.getString("nome"));
                
                Calendar data_nascimento_cal = Calendar.getInstance();
                data_nascimento_cal.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                pe.setData_nascimento(data_nascimento_cal);
                
                pe.setObservacao(rs.getString("observacao"));
                
                Cliente cli = new Cliente();
                cli.setCpf(rs.getString("cliente_cpf"));
                pe.setCliente(cli);
                
                Raca ra = new Raca();
                ra.setId(rs.getInt("raca_id"));
                pe.setRaca(ra);
                
                return pe;                
            }
        
        }
        
        return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        
        // Descobrir a instancia do Object o.
        if(o instanceof Consulta) {
            
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
        } /*else if(o instanceof Pessoa) {
            
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
                ps.setDate(6, new java.sql.Date(p.getData_nascimento().getTimeInMillis()));
                ps.setString(7, p.getCep());
                ps.setString(8, p.getEndereco());
                ps.setString(9, p.getComplemento());
                ps.setString(10, p.getTipo());
                ps.setString(11, p.getCpf());
                
                ps.execute(); // Executa o comando.
                
            }
            
        }*/ else if(o instanceof Funcionario) {
            
            Funcionario f = (Funcionario) o; // Converter o para o e que eh do tipo Pessoa.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(f.getData_cadastro() == null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                                                                + "(cpf, rg, nome, senha, numero_celular, "
                                                                + "email, data_cadastro, data_nascimento, "
                                                                + "cep, endereco, complemento, tipo) "
                                                                + "values (?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?, ?)");
                
                ps.setString(1, f.getCpf());
                ps.setString(2, f.getRg());
                ps.setString(3, f.getNome());
                ps.setString(4, f.getSenha());
                ps.setString(5, f.getNumero_celular());
                ps.setString(6, f.getEmail());
                ps.setDate(7, new java.sql.Date(f.getData_nascimento().getTimeInMillis()));
                ps.setString(8, f.getCep());
                ps.setString(9, f.getEndereco());
                ps.setString(10, f.getComplemento());
                ps.setString(11, "Fun");
                //ps.setString(12, "Fun");
                
                ps.execute();
                
                PreparedStatement ps2 = this.con.prepareStatement("insert into tb_funcionario " 
                                                                 + "(cargo, numero_ctps, numero_pis, cpf) values "
                                                                 + "(?, ?, ?, ?)");
                
                ps2.setString(1, f.getCargo().toString());
                ps2.setString(2, f.getNumero_ctps());
                ps2.setString(3, f.getNumero_pis());
                ps2.setString(4, f.getCpf());
                
                /*System.out.println("CPF: " + f.getCpf() + "\n");
                System.out.println("PIS: " + f.getNumero_pis()+ "\n");
                System.out.println("CTPS: " + f.getNumero_ctps() + "\n");
                System.out.println("Cargo: " + f.getCargo().toString() + "\n");*/
                
                ps2.execute();
                
            } else {
                
                // UPDATE.                
                PreparedStatement ps2 = this.con.prepareStatement("update tb_funcionario set "
                                                                + "cargo = ?, "
                                                                + "numero_ctps = ?, "
                                                                + "numero_pis = ? "
                                                                + "where cpf = ?");
                
                ps2.setString(1, f.getCargo().toString());
                ps2.setString(2, f.getNumero_ctps());
                ps2.setString(3, f.getNumero_pis());
                ps2.setString(4, f.getCpf());
                
                ps2.execute(); // Executa o comando.
                
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
                                                                + "tipo = 'Fun' "
                                                                + "where cpf = ?");
                
                ps.setString(1, f.getRg());
                ps.setString(2, f.getNome());
                ps.setString(3, f.getSenha());
                ps.setString(4, f.getNumero_celular());
                ps.setString(5, f.getEmail());
                ps.setDate(6, new java.sql.Date(f.getData_nascimento().getTimeInMillis()));
                ps.setString(7, f.getCep());
                ps.setString(8, f.getEndereco());
                ps.setString(9, f.getComplemento());
                //ps.setString(10, f.getTipo());
                ps.setString(10, f.getCpf());
                
                ps.execute(); // Executa o comando.
                
            }
        } else if(o instanceof Medico) {
            
            Medico med = (Medico) o; // Converter o para o e que eh do tipo Pessoa.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(med.getData_cadastro() == null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                                                                + "(cpf, rg, nome, senha, numero_celular, "
                                                                + "email, data_cadastro, data_nascimento, "
                                                                + "cep, endereco, complemento, tipo) " // TIREI ", tipo"
                                                                + "values (?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?, ?)"); // TIREI UMA "?"
                
                ps.setString(1, med.getCpf());
                ps.setString(2, med.getRg());
                ps.setString(3, med.getNome());
                ps.setString(4, med.getSenha());
                ps.setString(5, med.getNumero_celular());
                ps.setString(6, med.getEmail());
                ps.setDate(7, new java.sql.Date(med.getData_nascimento().getTimeInMillis()));
                ps.setString(8, med.getCep());
                ps.setString(9, med.getEndereco());
                ps.setString(10, med.getComplemento());
                ps.setString(11, "M");
                //ps.setString(11, f.getTipo());
                
                ps.execute();
                
                PreparedStatement ps2 = this.con.prepareStatement("insert into tb_medico " 
                                                                 + "(numero_crmv, cpf) values "
                                                                 + "(?, ?)");
                
                ps2.setString(1, med.getNumero_crmv().toString());
                ps2.setString(2, med.getCpf());
                
                /*System.out.println("CPF: " + f.getCpf() + "\n");
                System.out.println("PIS: " + f.getNumero_pis()+ "\n");
                System.out.println("CTPS: " + f.getNumero_ctps() + "\n");
                System.out.println("Cargo: " + f.getCargo().toString() + "\n");*/
                
                ps2.execute();
                
            } else {
                
                // UPDATE.                
                PreparedStatement ps2 = this.con.prepareStatement("update tb_medico set "
                                                                + "numero_crmv = ? "
                                                                + "where cpf = ?");
                
                ps2.setString(1, med.getNumero_crmv().toString());
                ps2.setString(2, med.getCpf());
                
                ps2.execute(); // Executa o comando.
                
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
                                                                + "tipo = 'M' "
                                                                + "where cpf = ?");
                
                ps.setString(1, med.getRg());
                ps.setString(2, med.getNome());
                ps.setString(3, med.getSenha());
                ps.setString(4, med.getNumero_celular());
                ps.setString(5, med.getEmail());
                ps.setDate(6, new java.sql.Date(med.getData_nascimento().getTimeInMillis()));
                ps.setString(7, med.getCep());
                ps.setString(8, med.getEndereco());
                ps.setString(9, med.getComplemento());
                //ps.setString(10, f.getTipo());
                ps.setString(10, med.getCpf());
                
                ps.execute(); // Executa o comando.
                
            }
        } else if(o instanceof Cliente) {
            
            Cliente cl = (Cliente) o; // Converter o para o e que eh do tipo Pessoa.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(cl.getData_cadastro() == null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                                                                + "(cpf, rg, nome, senha, numero_celular, "
                                                                + "email, data_cadastro, data_nascimento, "
                                                                + "cep, endereco, complemento, tipo) " // TIREI ", tipo"
                                                                + "values (?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?, ?)");
                
                ps.setString(1, cl.getCpf());
                ps.setString(2, cl.getRg());
                ps.setString(3, cl.getNome());
                ps.setString(4, cl.getSenha());
                ps.setString(5, cl.getNumero_celular());
                ps.setString(6, cl.getEmail());
                ps.setDate(7, new java.sql.Date(cl.getData_nascimento().getTimeInMillis()));
                ps.setString(8, cl.getCep());
                ps.setString(9, cl.getEndereco());
                ps.setString(10, cl.getComplemento());
                ps.setString(11, "C");
                
                ps.execute();
                
                PreparedStatement ps2 = this.con.prepareStatement("insert into tb_cliente " 
                                                                 + "(data_ultima_visita, cpf) values "
                                                                 + "(now(), ?)");
                
                ps2.setString(1, cl.getCpf());
                
                ps2.execute();
                
            } else {
                
                // UPDATE.                
                PreparedStatement ps2 = this.con.prepareStatement("update tb_cliente set "
                                                                + "data_ultima_visita = now() "
                                                                + "where cpf = ?");
                
                ps2.setString(1, cl.getCpf());
                
                ps2.execute(); // Executa o comando.
                
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
                                                                + "tipo = 'C' "
                                                                + "where cpf = ?");
                
                ps.setString(1, cl.getRg());
                ps.setString(2, cl.getNome());
                ps.setString(3, cl.getSenha());
                ps.setString(4, cl.getNumero_celular());
                ps.setString(5, cl.getEmail());
                ps.setDate(6, new java.sql.Date(cl.getData_nascimento().getTimeInMillis()));
                ps.setString(7, cl.getCep());
                ps.setString(8, cl.getEndereco());
                ps.setString(9, cl.getComplemento());
                ps.setString(10, cl.getCpf());
                
                ps.execute(); // Executa o comando.
                
            }
        } else if(o instanceof Pet) {
            
            Pet p = (Pet) o; // Converter o para o e que eh do tipo Receita.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(p.getId() == null) {
                
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pet "
                                                                + "(id, nome, data_nascimento, observacao, cliente_cpf, "
                                                                + "raca_id) "
                                                                + "values (nextval('seq_receita_id'), ?, ?, ?, ?, ?) "
                                                                + "returning id");
                
                ps.setString(1, p.getNome());
                ps.setDate(2, new java.sql.Date(p.getData_nascimento().getTimeInMillis()));
                ps.setString(3, p.getObservacao());
                ps.setString(4, p.getCliente().getCpf());
                ps.setInt(5, p.getRaca().getId());
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    p.setId(rs.getInt(1));
                }
                
            } else {
                
                // UPDATE.
                PreparedStatement ps = this.con.prepareStatement("update tb_pet set "
                                                                + "nome = ?, "
                                                                + "data_nascimento = ?, "
                                                                + "observacao = ?, "
                                                                + "cliente_cpf = ?, "
                                                                + "raca_id = ? "
                                                                + "where id = ?");
                
                ps.setString(1, p.getNome());
                ps.setDate(2, new java.sql.Date(p.getData_nascimento().getTimeInMillis()));
                ps.setString(3, p.getObservacao());
                ps.setString(4, p.getCliente().getCpf());
                ps.setInt(5, p.getRaca().getId());
                ps.setInt(6, p.getId());
                
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
            
        }/* else if(o instanceof Pessoa) {
            
            Pessoa p = (Pessoa) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps.setString(1, p.getCpf());
            ps.execute();
            
        } */else if(o instanceof Funcionario) {
            
            Funcionario f = (Funcionario) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_funcionario where cpf = ?");
            ps.setString(1, f.getCpf());
            ps.execute();
            
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps2.setString(1, f.getCpf());
            ps2.execute();
        } else if(o instanceof Medico) {
            
            Medico med = (Medico) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_medico where cpf = ?");
            ps.setString(1, med.getCpf());
            ps.execute();
            
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps2.setString(1, med.getCpf());
            ps2.execute();
        } else if(o instanceof Cliente) {
            
            Cliente cl = (Cliente) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_cliente where cpf = ?");
            ps.setString(1, cl.getCpf());
            ps.execute();
            
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps2.setString(1, cl.getCpf());
            ps2.execute();
        } else if(o instanceof Pet) {
            
            Pet p = (Pet) o; // Converter o para o e que eh do tipo Pet.
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_pet where id = ?");
            ps.setInt(1, p.getId());
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

            if(rs.getDate("data_cadastro") != null) {
                Calendar dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
                p.setData_cadastro(dtCad);
            }

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            p.setData_nascimento(dtU);
            
            p.setCep(rs.getString("cep"));
            
            p.setEndereco(rs.getString("endereco"));
            
            p.setComplemento(rs.getString("complemento"));
            
            //p.setTipo(rs.getString("tipo"));
            
            lista.add(p);
        
        }
        
        return lista;
        
    }
    
    @Override
    public List<Funcionario> listFuncionarios() throws Exception {
                
        List<Funcionario> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, "
                                                        + "pes.nome, pes.senha, pes.numero_celular, "
                                                        + "pes.email, pes.data_cadastro, "
                                                        + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                        + "pes.complemento, Fun.cargo, " // TIREI "pes.tipo,"
                                                        + "Fun.numero_ctps, Fun.numero_pis from tb_pessoa "
                                                        + "as pes inner join tb_funcionario as Fun on "
                                                        + "pes.cpf = Fun.cpf");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.

        lista = new ArrayList();
        while(rs.next()) {
            
            Funcionario f = new Funcionario();
            
            f.setCpf(rs.getString("cpf"));
            
            f.setRg(rs.getString("rg"));
            
            f.setNome(rs.getString("nome"));
            
            f.setSenha(rs.getString("senha"));
            
            f.setNumero_celular(rs.getString("numero_celular"));
            
            f.setEmail(rs.getString("email"));

            if(rs.getDate("data_cadastro") != null) {
                Calendar dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
                f.setData_cadastro(dtCad);
            }

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            f.setData_nascimento(dtU);
            
            f.setCep(rs.getString("cep"));
            
            f.setEndereco(rs.getString("endereco"));
            
            f.setComplemento(rs.getString("complemento"));
            
            //f.setTipo(rs.getString("tipo"));
            
            f.setCargo(Cargo.getCargo(rs.getString("cargo")));
            
            f.setNumero_ctps(rs.getString("numero_ctps"));
            
            f.setNumero_pis(rs.getString("numero_pis"));
            
            lista.add(f);
        
        }
        
        return lista;
        
    }
    
    @Override
    public List<Medico> listMedicos() throws Exception {
        
        List<Medico> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, "
                                                        + "pes.nome, pes.senha, pes.numero_celular, "
                                                        + "pes.email, pes.data_cadastro, "
                                                        + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                        + "pes.complemento, " // TIREI "pes.tipo, "
                                                        + "M.numero_crmv from tb_pessoa "
                                                        + "as pes inner join tb_medico as M on "
                                                        + "pes.cpf = M.cpf");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.

        lista = new ArrayList();
        while(rs.next()){
            
            Medico med = new Medico();
            
            med.setCpf(rs.getString("cpf"));
            
            med.setRg(rs.getString("rg"));
            
            med.setNome(rs.getString("nome"));
            
            med.setSenha(rs.getString("senha"));
            
            med.setNumero_celular(rs.getString("numero_celular"));
            
            med.setEmail(rs.getString("email"));

            if(rs.getDate("data_cadastro") != null) {
                Calendar dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
                med.setData_cadastro(dtCad);
            }

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            med.setData_nascimento(dtU);
            
            med.setCep(rs.getString("cep"));
            
            med.setEndereco(rs.getString("endereco"));
            
            med.setComplemento(rs.getString("complemento"));
            
            //f.setTipo(rs.getString("tipo"));
            
            med.setNumero_crmv(rs.getString("numero_crmv"));
            
            lista.add(med);
        
        }
        
        return lista;
        
    }
    
    @Override
    public List<Cliente> listClientes() throws Exception {
        List<Cliente> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, "
                                                        + "pes.nome, pes.senha, pes.numero_celular, "
                                                        + "pes.email, pes.data_cadastro, "
                                                        + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                        + "pes.complemento, " // TIREI "pes.tipo, "
                                                        + "C.data_ultima_visita from tb_pessoa "
                                                        + "as pes inner join tb_cliente as C on "
                                                        + "pes.cpf = C.cpf");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.

        lista = new ArrayList();
        while(rs.next()){
            
            Cliente c = new Cliente();
            
            c.setCpf(rs.getString("cpf"));
            
            c.setRg(rs.getString("rg"));
            
            c.setNome(rs.getString("nome"));
            
            c.setSenha(rs.getString("senha"));
            
            c.setNumero_celular(rs.getString("numero_celular"));
            
            c.setEmail(rs.getString("email"));

            Calendar dtCad = Calendar.getInstance();
            dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
            c.setData_cadastro(dtCad);

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            c.setData_nascimento(dtU);
            
            c.setCep(rs.getString("cep"));
            
            c.setEndereco(rs.getString("endereco"));
            
            c.setComplemento(rs.getString("complemento"));
            
            Calendar dtV = Calendar.getInstance();
            dtV.setTimeInMillis(rs.getDate("data_ultima_visita").getTime());
            c.setData_nascimento(dtV);
            
            lista.add(c);
        
        }
        
        return lista;
    }

    @Override
    public List<Pet> listPets() throws Exception {
        List<Pet> lista = null;
        
        PreparedStatement ps = this.con.prepareStatement("select id, nome, data_nascimento, observacao, "
                                                        + "cliente_cpf, raca_id "
                                                        + "from tb_pet order by id asc");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.

        lista = new ArrayList();
        while(rs.next()){
            
            Pet p = new Pet();
            
            p.setId(rs.getInt("id"));
            
            p.setNome(rs.getString("nome"));

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            p.setData_nascimento(dtU);
            
            p.setObservacao(rs.getString("observacao"));
            
            Cliente cli = new Cliente();
            cli.setCpf(rs.getString("cliente_cpf"));
            p.setCliente(cli);
            
            Raca ra = new Raca();
            ra.setId(rs.getInt("raca_id"));
            p.setRaca(ra);
            
            lista.add(p);
        
        }
        
        rs.close();
        
        return lista;
    }
    
    @Override
    public Funcionario doLogin(String cpf, String senha) throws Exception {
                
        Funcionario funcionario = null;
        
         PreparedStatement ps = 
            this.con.prepareStatement("select p.cpf, p.senha from tb_pessoa p where p.cpf = ? and p.senha = ? ");
                        
            ps.setString(1, cpf);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery(); // O ponteiro do ResultSet inicialmente está na linha -1.
            
            if(rs.next()) { // Se a matriz (ResultSet) tem uma linha.

                funcionario = new Funcionario();
                funcionario.setCpf(rs.getString("cpf"));                
            }
        
            ps.close();
            return funcionario;
        
    }
    
}