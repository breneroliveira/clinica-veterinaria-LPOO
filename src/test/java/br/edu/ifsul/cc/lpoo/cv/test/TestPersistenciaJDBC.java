
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author brener
 */

public class TestPersistenciaJDBC {
    
    //@Test
    public void testConexao() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
            System.out.println("\nAbriu a conexão com o BD via JDBC.\n");
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("\nNão abriu a conexão com o BD via JDBC.\n");
        }       
    }
    
    //@Test
    public void testPersistenciaConsultaReceita() throws Exception {
        
        DateFormat formatada = new SimpleDateFormat("dd/MM/yyyy");
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
        
            List<Consulta> lista = persistencia.listConsultas();
            
            if(!lista.isEmpty()) {
            
                for(Consulta c : lista) {
                
                    System.out.println("\n-- ID da consulta: " + c.getId() 
                                       + " \n-- Data da consulta: " +  formatada.format(c.getData().getTime())
                                       + " \n-- Observação: " + c.getObservacao() 
                                       + " \n-- Data de retorno: " + formatada.format(c.getData_retorno().getTime()) 
                                       + " \n-- Valor (R$): " + c.getValor() 
                                       + " \n-- CPF do médico: " + c.getMedico().getCpf() 
                                       + " \n-- ID do pet: " + c.getPet().getId() + "\n");
                    
                    if(c.getReceitas() != null && !c.getReceitas().isEmpty()) {
                        
                        for(Receita r : c.getReceitas()) {

                            System.out.println("-- ID da receita: " + r.getId() 
                                               + " \n-- Orientação: " + r.getOrientacao()
                                               + " \n-- ID da cosulta: " + r.getConsulta().getId() + "\n");

                            persistencia.remover(r);
                            System.out.println("Receita de ID " + r.getId() + " removida.\n");
                            
                        }
                        
                    }
                    
                    persistencia.remover(c);
                    System.out.println("Consulta de ID " + c.getId() + " removida.\n");
                
                }
                
            } else {
                
                System.out.println("\nNão encontrou a consulta.");
                
                Consulta cons = new Consulta();
                Calendar data_convertida_1 = Calendar.getInstance();
                data_convertida_1.set(Calendar.YEAR, 2022);
                data_convertida_1.set(Calendar.MONTH, 02 + 1);
                data_convertida_1.set(Calendar.DAY_OF_MONTH, 03);
                cons.setData(data_convertida_1);
                cons.setObservacao("Nenhuma");
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 2022);
                data_convertida_2.set(Calendar.MONTH, 02 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 20);
                cons.setData_retorno(data_convertida_2);
                cons.setValor(50F);
                Medico medi = new Medico();
                medi.setCpf("19248526080");
                cons.setMedico(medi);
                Pet pet = new Pet();
                pet.setId(1);
                cons.setPet(pet);
                
                persistencia.persist(cons); // INSERT na tabela.
                System.out.println("Cadastrou a consulta " + cons.getId() + ".\n");
                
                System.out.println("Não encontrou a receita.");
                Receita rece = new Receita();
                rece.setConsulta(cons);
                rece.setOrientacao("Nenhuma");

                persistencia.persist(rece); // INSERT na tabela.
                System.out.println("Cadastrou a receita " + rece.getId() + ".\n");
                
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }
    
    }
    
    //@Test
    public void testListPersistenciaPessoa() throws Exception {
        
        DateFormat formatada = new SimpleDateFormat("dd/MM/yyyy");
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
        
            List<Pessoa> lista = persistencia.listPessoas();
            
            if(!lista.isEmpty()) {
            
                for(Pessoa p : lista) {
                
                    System.out.println("\n-- CPF da pessoa: " + p.getCpf()
                                       + " \n-- RG da pessoa: " +  p.getRg()
                                       + " \n-- Nome da pessoa: " + p.getNome()
                                       + " \n-- Senha: " + p.getSenha()
                                       + " \n-- Número de celular: " + p.getNumero_celular()
                                       + " \n-- Email: " + p.getEmail()
                                       + " \n-- Data do cadastro: " + formatada.format(p.getData_cadastro().getTime())
                                       + " \n-- Data de nascimento: " + formatada.format(p.getData_nascimento().getTime())
                                       + " \n-- CEP: " + p.getCep()
                                       + " \n-- Endereço: " + p.getEndereco()
                                       + " \n-- Complemento: " + p.getComplemento()
                                       + " \n-- Tipo: " + p.getTipo()+ "\n");
                    
                    persistencia.remover(p);
                    System.out.println("Pessoa de CPF " + p.getCpf() + " removida.\n");
                
                }
                
            } else {
                
                System.out.println("\nNão encontrou a pessoa.");
                
                Pessoa pes = new Pessoa();
                
                pes.setCpf("78945612312");
                
                pes.setRg("4563219870");
                
                pes.setNome("Thomas");
                
                pes.setSenha("4321");
                
                pes.setNumero_celular("54996358745");
                
                pes.setEmail("thomas@gmail.com");
                
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 2001);
                data_convertida_2.set(Calendar.MONTH, 6 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 22);
                pes.setData_nascimento(data_convertida_2);
                
                pes.setCep("36985241");
                
                pes.setEndereco("Rua Luiz II");
                
                pes.setComplemento("Nenhum");
                
                pes.setTipo("M");
                
                persistencia.persist(pes); // INSERT na tabela.
                System.out.println("Cadastrou a pessoa de CPF " + pes.getCpf() + ".\n");
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }        
                       
    }
    
    @Test
    public void testListPersistenciaFuncionario() throws Exception {
        
        DateFormat formatada = new SimpleDateFormat("dd/MM/yyyy");
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
        
            List<Funcionario> lista = persistencia.listFuncionarios();
            
            if(!lista.isEmpty()) {
            
                for(Funcionario f : lista) {
                
                    System.out.println("\n-- CPF da pessoa: " + f.getCpf()
                                       + " \n-- RG da pessoa: " +  f.getRg()
                                       + " \n-- Nome da pessoa: " + f.getNome()
                                       + " \n-- Senha: " + f.getSenha()
                                       + " \n-- Número de celular: " + f.getNumero_celular()
                                       + " \n-- Email: " + f.getEmail()
                                       + " \n-- Data do cadastro: " + formatada.format(f.getData_cadastro().getTime())
                                       + " \n-- Data de nascimento: " + formatada.format(f.getData_nascimento().getTime())
                                       + " \n-- CEP: " + f.getCep()
                                       + " \n-- Endereço: " + f.getEndereco()
                                       + " \n-- Complemento: " + f.getComplemento()
                                       + " \n-- Tipo: " + f.getTipo()
                                       + " \n-- Cargo: " + f.getCargo()
                                       + " \n-- Número CTPS: " + f.getNumero_ctps()
                                       + " \n-- Número PIS: " + f.getNumero_pis() + "\n");
                    
                    persistencia.remover(f);
                    System.out.println("Pessoa de CPF " + f.getCpf() + " removida.\n");
                
                }
                
            } else {
                
                System.out.println("\nNão encontrou a pessoa.");
                
                Funcionario f = new Funcionario();
                
                f.setCpf("78945612312");
                
                f.setRg("4563219870");
                
                f.setNome("Thomas");
                
                f.setSenha("4321");
                
                f.setNumero_celular("54996358745");
                
                f.setEmail("thomas@gmail.com");
                
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 2001);
                data_convertida_2.set(Calendar.MONTH, 6 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 22);
                f.setData_nascimento(data_convertida_2);
                
                f.setCep("36985241");
                
                f.setEndereco("Rua Luiz II");
                
                f.setComplemento("Nenhum");
                
                f.setTipo("Fun");
                
                f.setCargo(Cargo.ADESTRADOR);
                
                f.setNumero_ctps("43207777");
                
                f.setNumero_pis("8817128");
                
                persistencia.persist(f); // INSERT na tabela.
                System.out.println("Cadastrou a pessoa de CPF " + f.getCpf() + ".\n");
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }        
                       
    }
    
    //@Test
    public void testGeracaoPessoaLogin() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
            System.out.println("\nAbriu a conexão com o BD via JDBC.\n");
            
            Pessoa p = persistencia.doLogin("78945612312", "4321");

            if(p == null) {
                
                System.out.println("Não há nenhuma pessoa cadastrada.\n");
                
            } else {
                System.out.println("Encontrou uma pessoa cadastrada.\n");
            }
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("\nNão abriu a conexão com o BD via JDBC.\n");
        }
        
    }
    
}

// ** IMPLEMENTAR O MÉTODO LOGIN AQUI IGUAL A DO JPA.
// ** IMPLEMENTAR MÉTODO PRA CRUD PRA FUNCIONÁRIO.
// ** 