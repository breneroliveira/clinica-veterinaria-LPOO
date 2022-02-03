
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
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
            System.out.println("Abriu a conexao com o BD via JDBC.");
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("Nao abriu a conexao com o BD via JDBC.");
        }       
    }
    
    @Test
    public void testPersistenciaConsultaReceita() throws Exception {
    
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
        
            List<Consulta> lista = persistencia.listConsultas();
            
            if(!lista.isEmpty()) {
            
                for(Consulta c : lista) {
                
                    System.out.println("ID da consulta: " + c.getId() 
                                       + " -- Data da consulta: " + c.getData() 
                                       + " -- Observação:" + c.getObservacao() 
                                       + " -- Data de retorno: " + c.getData_retorno() 
                                       + " -- Valor (R$): " + c.getValor() 
                                       + " -- CPF do médico: " + c.getMedico().getCpf() 
                                       + " -- ID do pet: " + c.getPet().getId());
                    
                    if(c.getReceitas() != null && !c.getReceitas().isEmpty()) {
                        
                        for(Receita r : c.getReceitas()) {

                            System.out.println("ID da receita: " + r.getId() 
                                               + " -- Orientação: " + r.getOrientacao()
                                               + " -- ID da cosulta: " + r.getConsulta().getId());

                            persistencia.remover(r);
                            System.out.println("Receita de ID " + r.getId() + " removida.");
                            
                        }
                        
                    }
                    
                    persistencia.remover(c);
                    System.out.println("Consulta de ID " + c.getId() + " removida.");
                
                }
                
            } else {
                
                System.out.println("Não encontrou a consulta.");
                
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
                System.out.println("Cadastrou a consulta " + cons.getId());
                
                System.out.println("Não encontrou a receita.");
                cons.setId(1);
                Receita rece = new Receita();
                rece.setConsulta(cons);
                rece.setOrientacao("Nenhuma");

                persistencia.persist(rece); // INSERT na tabela.
                System.out.println("Cadastrou a receita " + rece.getId());
                
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }
    
    }
    
    //@Test
    public void testPersistenciaConsulta() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
            
            List<Consulta> lista = persistencia.listConsultas();
            
            if(!lista.isEmpty()) {
            
                for(Consulta c : lista) {

                    System.out.println("ID da consulta: " + c.getId() 
                                       + " -- Data da consulta: " + c.getData() 
                                       + " -- Observação:" + c.getObservacao() 
                                       + " -- Data de retorno: " + c.getData_retorno() 
                                       + " -- Valor (R$): " + c.getValor() 
                                       + " -- CPF do médico: " + c.getMedico().getCpf() 
                                       + " -- ID do pet: " + c.getPet().getId());
                    
                    persistencia.remover(c);
                }
                
            } else {
                
                System.out.println("Não encontrou a consulta.");
                
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
                
                System.out.println("Cadastrou a consulta " + cons.getId());
                
            }
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }
    }
    
    //@Test
    public void testPersistenciaReceita() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
            
            List<Receita> lista = persistencia.listReceitas();
            
            if(!lista.isEmpty()) {
            
                for(Receita r : lista) {

                    System.out.println("ID da receita: " + r.getId() 
                                       + " -- Orientação: " + r.getOrientacao() 
                                       + " -- ID da cosulta: " + r.getConsulta().getId());
                    
                    persistencia.remover(r);
                }
                
            } else {
                
                System.out.println("Não encontrou a receita.");
                
                Receita rece = new Receita();
                
                rece.setOrientacao("Nenhuma");
                
                Consulta consu = new Consulta();
                consu.setId(1);
                rece.setConsulta(consu);
                
                persistencia.persist(rece); // INSERT na tabela.
                
                System.out.println("Cadastrou a receita " + rece.getId());
                
            }
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }
    }
    
}