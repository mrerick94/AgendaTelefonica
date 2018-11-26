/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.TipoContato;
import dao.ConnectionFactory;
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
public class TipoContatoDAOImpl implements  TipoContatoDAO {
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public Integer insert(TipoContato tipoContato) throws Exception {
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("insert into tipocontato (nome) values (?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, tipoContato.getNome());
            tipoContato.setId(ps.executeUpdate());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("TipoContato não pôde ser criado" + e);
            return null;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return tipoContato.getId();
    }

    @Override
    public TipoContato select(Integer id) throws Exception {
        TipoContato tipo = new TipoContato();
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("select id, nome from tipocontato where id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                tipo.setId(rs.getInt("id"));
                tipo.setNome(rs.getString("nome"));
            } else {
                return null;
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao pesquisar TipoContato" + e);
            return null;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return tipo;
    }

    @Override
    public Boolean update(TipoContato tipoContato) throws Exception {
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("update tipocontato set nome=? where id=?");
            ps.setString(1, tipoContato.getNome());
            ps.setInt(2, tipoContato.getId());
            ps.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("TipoContato não pôde ser atualizado" + e);
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
            ps = conn.prepareStatement("delete from tipocontato where id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao deletar tipocontato" + e);
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
    }

    @Override
    public List<TipoContato> list(String termo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TipoContato> listAll() throws Exception {
        List<TipoContato> tipos = new ArrayList<TipoContato>();
        TipoContato tipo;
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("select id, nome from tipocontato order by id");
            rs = ps.executeQuery();
            while (rs.next()) {
                tipo = new TipoContato();
                tipo.setId(rs.getInt("id"));
                tipo.setNome(rs.getString("nome"));
                tipos.add(tipo);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao pesquisar Tipos de Contato" + e);
            return null;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return tipos;
    }
    
}
