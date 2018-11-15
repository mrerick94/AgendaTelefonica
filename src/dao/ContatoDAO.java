/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Contato;
import java.util.List;

/**
 *
 * @author erick
 */
public interface ContatoDAO {
    
    void insert(Contato contato) throws Exception;
    
    Contato select(Integer id) throws Exception;
    
    void update(Contato contato) throws Exception;
    
    void delete(Integer id) throws Exception;
    
    List<Contato> list(String termo) throws Exception;
    
    List<Contato> listAll() throws Exception;
}
