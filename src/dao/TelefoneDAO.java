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
    
    Integer insert(Telefone telefone) throws Exception;
    
    Telefone select(Integer id) throws Exception;
    
    Boolean update(Telefone telefone) throws Exception;
    
    void delete(Integer id) throws Exception;
    
    void deletePorContato(Integer idContato) throws Exception;
    
    List<Telefone> listPorContato(Integer idContato) throws Exception;
    
    List<Telefone> listAll() throws Exception;
}
