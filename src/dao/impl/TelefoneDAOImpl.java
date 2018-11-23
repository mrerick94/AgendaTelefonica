/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Contato;
import bean.Telefone;
import dao.ConnectionFactory;
import dao.ContatoDAO;
import dao.TelefoneDAO;
import dao.TipoContatoDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alunos
 */
public class TelefoneDAOImpl implements dao.TelefoneDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public Integer insert(Telefone telefone) throws Exception {
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("insert into telefone (ddd, telefone, id_contato) values (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, telefone.getDdd());
            ps.setString(2, telefone.getTelefone());
            ps.setInt(3, telefone.getContato().getId());
            telefone.setId(ps.executeUpdate());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Telefone não pôde ser criado" + e);
            return null;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return telefone.getId();
    }

    @Override
    public Telefone select(Integer id) throws Exception {
        Telefone telefone = new Telefone();
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("select id, ddd, telefone, id_contato from telefone where id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                ContatoDAO contatoDao = new ContatoDAOImpl();
                telefone.setId(rs.getInt("id"));
                telefone.setDdd(rs.getInt("ddd"));
                telefone.setTelefone(rs.getString("telefone"));
                telefone.setContato(contatoDao.select(rs.getInt("id_contato")));
            } else {
                return null;
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao pesquisar telefone" + e);
            return null;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return telefone;
    }

    @Override
    public Boolean update(Telefone telefone) throws Exception {
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("update telefone set ddd=?, telefone=? where id=?");
            ps.setInt(1, telefone.getDdd());
            ps.setString(2, telefone.getTelefone());
            ps.setInt(3, telefone.getId());
            ps.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Telefone não pôde ser atualizado" + e);
            return false;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return true;
    }

    @Override
    public void delete(Integer id) throws Exception {
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("delete from telefone where id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao deletar telefone" + e);
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
    }

    @Override
    public void deletePorContato(Integer idContato) throws Exception {
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("delete from telefone where id_contato=?");
            ps.setInt(1, idContato);
            ps.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao deletar telefones" + e);
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
    }
    
    @Override
    public List<Telefone> listPorContato(Contato contato) throws Exception {
        List<Telefone> telefones = new ArrayList<Telefone>();
        Telefone telefone;
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("select id, ddd, telefone from telefone where id_contato=?");
            ps.setInt(1, contato.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                telefone = new Telefone();
                telefone.setId(rs.getInt("id"));
                telefone.setDdd(rs.getInt("ddd"));
                telefone.setTelefone(rs.getString("telefone"));
                telefone.setContato(contato);
                telefones.add(telefone);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao pesquisar telefones" + e);
            return null;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return telefones;
    }

    @Override
    public List<Telefone> listAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
