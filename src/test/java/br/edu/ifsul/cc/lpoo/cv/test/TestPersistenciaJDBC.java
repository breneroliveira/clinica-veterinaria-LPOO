
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Raca;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
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
    
    @Test
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
                data_convertida_1.set(Calendar.DAY_OF_MONTH, 15);
                cons.setData(data_convertida_1);
                cons.setObservacao("Nenhuma");
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 2022);
                data_convertida_2.set(Calendar.MONTH, 02 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 27);
                cons.setData_retorno(data_convertida_2);
                cons.setValor(50F);
                Medico medi = new Medico();
                medi.setCpf("91549413007");
                cons.setMedico(medi);
                Pet pet = new Pet();
                pet.setId(2);
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
                                       + " \n-- Complemento: " + p.getComplemento() + "\n");
                                       //+ " \n-- Tipo: " + p.getTipo()+ "\n");
                    
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
                
                //pes.setTipo("M");
                
                persistencia.persist(pes); // INSERT na tabela.
                System.out.println("Cadastrou a pessoa de CPF " + pes.getCpf() + ".\n");
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }
        
    }
    
    //@Test
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
                                       //+ " \n-- Tipo: " + f.getTipo()
                                       + " \n-- Cargo: " + f.getCargo()
                                       + " \n-- Número CTPS: " + f.getNumero_ctps()
                                       + " \n-- Número PIS: " + f.getNumero_pis() + "\n");
                    
                    persistencia.remover(f);
                    System.out.println("Funcionário de CPF " + f.getCpf() + " removido.\n");
                
                }
                
            } else {
                
                System.out.println("\nNão encontrou o funcionário.");
                
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
                
                //f.setTipo("Fun");
                
                f.setCargo(Cargo.ADESTRADOR);
                
                f.setNumero_ctps("43207777");
                
                f.setNumero_pis("8817128");
                
                persistencia.persist(f); // INSERT na tabela.
                System.out.println("Cadastrou o funcionário de CPF " + f.getCpf() + ".\n");
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }
                       
    }
    
    //@Test
    public void testListPersistenciaMedico() throws Exception {
        
        DateFormat formatada = new SimpleDateFormat("dd/MM/yyyy");
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
        
            List<Medico> lista = persistencia.listMedicos();
            
            if(!lista.isEmpty()) {
            
                for(Medico m : lista) {
                
                    System.out.println("\n-- CPF da pessoa: " + m.getCpf()
                                       + " \n-- RG da pessoa: " +  m.getRg()
                                       + " \n-- Nome da pessoa: " + m.getNome()
                                       + " \n-- Senha: " + m.getSenha()
                                       + " \n-- Número de celular: " + m.getNumero_celular()
                                       + " \n-- Email: " + m.getEmail()
                                       + " \n-- Data do cadastro: " + formatada.format(m.getData_cadastro().getTime())
                                       + " \n-- Data de nascimento: " + formatada.format(m.getData_nascimento().getTime())
                                       + " \n-- CEP: " + m.getCep()
                                       + " \n-- Endereço: " + m.getEndereco()
                                       + " \n-- Complemento: " + m.getComplemento()
                                       + " \n-- Número CRVM: " + m.getNumero_crmv()+ "\n");
                    
                    persistencia.remover(m);
                    System.out.println("Médico de CPF " + m.getCpf() + " removido.\n");
                
                }
                
            } else {
                
                System.out.println("\nNão encontrou o médico.");
                
                Medico med = new Medico();
                
                med.setCpf("91549413007");
                
                med.setRg("1997234975");
                
                med.setNome("Luis");
                
                med.setSenha("1234");
                
                med.setNumero_celular("54995245378");
                
                med.setEmail("luis@gmail.com");
                
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 1971);
                data_convertida_2.set(Calendar.MONTH, 9 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 14);
                med.setData_nascimento(data_convertida_2);
                
                med.setCep("58108186");
                
                med.setEndereco("Rua Machado de Assis");
                
                med.setComplemento("Nenhum");
                
                med.setNumero_crmv("98567");
                
                persistencia.persist(med); // INSERT na tabela.
                System.out.println("Cadastrou o médico de CPF " + med.getCpf() + ".\n");
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }        
                       
    }
    
    //@Test
    public void testListPersistenciaCliente() throws Exception {
        
        DateFormat formatada = new SimpleDateFormat("dd/MM/yyyy");
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
        
            List<Cliente> lista = persistencia.listClientes();
            
            if(!lista.isEmpty()) {
            
                for(Cliente cl : lista) {
                
                    System.out.println("\n-- CPF da pessoa: " + cl.getCpf()
                                       + " \n-- RG da pessoa: " +  cl.getRg()
                                       + " \n-- Nome da pessoa: " + cl.getNome()
                                       + " \n-- Senha: " + cl.getSenha()
                                       + " \n-- Número de celular: " + cl.getNumero_celular()
                                       + " \n-- Email: " + cl.getEmail()
                                       + " \n-- Data do cadastro: " + formatada.format(cl.getData_cadastro().getTime())
                                       + " \n-- Data de nascimento: " + formatada.format(cl.getData_nascimento().getTime())
                                       + " \n-- CEP: " + cl.getCep()
                                       + " \n-- Endereço: " + cl.getEndereco()
                                       + " \n-- Complemento: " + cl.getComplemento()
                                       + " \n-- Data da última visita: " + formatada.format(cl.getData_ultima_visita().getTime()) + "\n");
                    
                    persistencia.remover(cl);
                    System.out.println("Cliente de CPF " + cl.getCpf() + " removido.\n");
                
                }
                
            } else {
                
                System.out.println("\nNão encontrou o cliente.");
                
                Cliente cl = new Cliente();
                
                cl.setCpf("50009171037");
                
                cl.setRg("1309555656");
                
                cl.setNome("João");
                
                cl.setSenha("4567");
                
                cl.setNumero_celular("54991325988");
                
                cl.setEmail("joao@gmail.com");
                
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 1997);
                data_convertida_2.set(Calendar.MONTH, 3 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 7);
                cl.setData_nascimento(data_convertida_2);
                
                cl.setCep("99930000");
                cl.setEndereco("Rua Jorge Ben");
                cl.setComplemento("Nenhum");
                
                persistencia.persist(cl); // INSERT na tabela.
                System.out.println("Cadastrou o cliente de CPF " + cl.getCpf() + ".\n");
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }        
                       
    }
    
    //@Test
    public void testListPersistenciaPet() throws Exception {
        
        DateFormat formatada = new SimpleDateFormat("dd/MM/yyyy");
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
        
            List<Pet> lista = persistencia.listPets();
            
            if(!lista.isEmpty()) {
            
                for(Pet pe : lista) {
                
                    System.out.println("\n-- ID do pet: " + pe.getId()
                                       + " \n-- Nome do pet: " + pe.getNome()
                                       + " \n-- Data de nascimento: " + formatada.format(pe.getData_nascimento().getTime())
                                       + " \n-- Observação: " +  pe.getObservacao() 
                                       + " \n-- CPF do cliente: " + pe.getCliente().getCpf()
                                       + " \n-- ID da raça: " + pe.getRaca().getId() + "\n");
                    
                    persistencia.remover(pe);
                    System.out.println("Pet de ID " + pe.getId() + " removido.\n");
                
                }
                
            } else {
                
                System.out.println("\nNão encontrou o pet.");
                
                Pet pe = new Pet();
                
                pe.setNome("Mimi");
                
                Calendar data_convertida = Calendar.getInstance();
                data_convertida.set(Calendar.YEAR, 2015);
                data_convertida.set(Calendar.MONTH, 5 + 1);
                data_convertida.set(Calendar.DAY_OF_MONTH, 19);
                pe.setData_nascimento(data_convertida);
                
                pe.setObservacao("Nenhuma");
                
                Cliente cli = new Cliente();
                cli.setCpf("50009171037");
                pe.setCliente(cli);
                
                Raca ra = new Raca();
                ra.setId(1);
                pe.setRaca(ra);
                
                persistencia.persist(pe); // INSERT na tabela.
                System.out.println("Cadastrou o pet de ID " + pe.getId()+ ".\n");
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }        
                       
    }
    
    //@Test
    public void testGeracaoFuncionarioLogin() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
            System.out.println("\nAbriu a conexão com o BD via JDBC.\n");
            
            Funcionario f = persistencia.doLogin("78945612312", "4321");

            if(f == null) {
                
                System.out.println("Não há nenhum funcionário cadastrado.\n");
                
            } else {
                System.out.println("Encontrou uma funcionário cadastrado.\n");
            }
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("\nNão abriu a conexão com o BD via JDBC.\n");
        }
        
    }
    
}