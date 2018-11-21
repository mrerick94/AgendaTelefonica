/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.TipoContato;
import java.util.List;

/**
 *
 * @author erick
 */
public interface TipoContatoDAO {
    
    Integer insert(TipoContato tipoContato) throws Exception;
    
    TipoContato select(Integer id) throws Exception;
    
    Boolean update(TipoContato tipoContato) throws Exception;
    
    void delete(Integer id) throws Exception;
    
    List<TipoContato> list(String termo) throws Exception;
    
    List<TipoContato> listAll() throws Exception;
}
