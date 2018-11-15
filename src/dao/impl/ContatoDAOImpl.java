/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Contato;
import bean.Telefone;
import dao.ConnectionFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author erick
 */
public class ContatoDAOImpl implements dao.ContatoDAO {
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public void insert(Contato contato) throws Exception {
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("insert into contato (nome, datanascimento, email, id_tipocontato) "
                    + "values (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, contato.getNome());
            ps.setDate(2, new java.sql.Date(contato.getDataNascimento().getTime()));
            ps.setString(3, contato.getEmail());
            ps.setInt(4, contato.getTipoContato().getId());
            contato.setId(ps.executeUpdate()); // Executa o insert e seta o id retornado no contato
            // Insert telefones do contato
            ps = conn.prepareStatement("insert into telefone (ddd, telefone, id_contato) values (?, ?, ?)");
            for (Telefone telefone : contato.getTelefones()) {
                ps.setInt(1, telefone.getDdd());
                ps.setString(2, telefone.getTelefone());
                ps.setInt(3, contato.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Contato não pôde ser criado" + e);
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
    }

    @Override
    public Contato select(Integer id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Contato contato) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Contato> list(String termo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Contato> listAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
