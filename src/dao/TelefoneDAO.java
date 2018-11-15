/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Telefone;
import java.util.List;

/**
 *
 * @author erick
 */
public interface TelefoneDAO {
    
    void insert(Telefone telefone) throws Exception;
    
    Telefone select(Integer id) throws Exception;
    
    void update(Telefone telefone) throws Exception;
    
    void delete(Integer id) throws Exception;
    
    List<Telefone> list(String termo) throws Exception;
    
    List<Telefone> listAll() throws Exception;
}
