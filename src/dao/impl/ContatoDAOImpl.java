/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Contato;
import bean.Telefone;
import dao.ConnectionFactory;
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
 * @author erick
 */
public class ContatoDAOImpl implements dao.ContatoDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public Integer insert(Contato contato) throws Exception {
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("insert into contato (nome, datanascimento, email, id_tipocontato) "
                    + "values (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, contato.getNome());
            ps.setDate(2, new java.sql.Date(contato.getDataNascimento().getTime()));
            ps.setString(3, contato.getEmail());
            ps.setInt(4, contato.getTipoContato().getId());
            ps.executeUpdate(); // Executa o insert e seta o id retornado no contato
            rs = ps.getGeneratedKeys();
            rs.next();
            contato.setId(rs.getInt(1));
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
            return null;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return contato.getId();
    }

    @Override
    public Contato select(Integer id) throws Exception {
        Contato contato = new Contato();
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("select id, nome, datanascimento, email, id_tipocontato from contato where id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                TelefoneDAO telDao = new TelefoneDAOImpl();
                TipoContatoDAO tipoDao = new TipoContatoDAOImpl();
                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setDataNascimento(rs.getDate("datanascimento"));
                contato.setEmail(rs.getString("email"));
                contato.setTipoContato(tipoDao.select(rs.getInt("id_tipocontato")));
                contato.setTelefones(telDao.listPorContato(contato));
            } else {
                return null;
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao pesquisar contato" + e);
            return null;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return contato;
    }

    @Override
    public Boolean update(Contato contato) throws Exception {
        // Esse metodo não deleta telefones deletados da lista na interface
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("update contato set nome=?, datanascimento=?, email=?, id_tipocontato=? where id=?");
            ps.setString(1, contato.getNome());
            ps.setDate(2, new java.sql.Date(contato.getDataNascimento().getTime()));
            ps.setString(3, contato.getEmail());
            ps.setInt(4, contato.getTipoContato().getId());
            ps.setInt(5, contato.getId());
            ps.executeUpdate();
            TelefoneDAO telDao = new TelefoneDAOImpl();
            telDao.deletePorContato(contato.getId());
            for (Telefone telefone : contato.getTelefones()) {
                    telDao.insert(telefone);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Contato não pôde ser atualizado" + e);
            return false;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return true;
    }

    @Override
    public void delete(Integer id) throws Exception {
        try {
            TelefoneDAO telDao = new TelefoneDAOImpl();
            telDao.deletePorContato(id);
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("delete from contato where id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao deletar contato" + e);
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
    }

    @Override
    public List<Contato> list(String termo) throws Exception {
        List<Contato> contatos = new ArrayList<Contato>();
        Contato contato;
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("select id, nome, datanascimento, email, id_tipocontato from contato where nome like ? or email like ?");
            ps.setString(1, "%" + termo + "%");
            ps.setString(2, "%" + termo + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                contato = new Contato();
                TelefoneDAO telDao = new TelefoneDAOImpl();
                TipoContatoDAO tipoDao = new TipoContatoDAOImpl();
                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setDataNascimento(rs.getDate("datanascimento"));
                contato.setEmail(rs.getString("email"));
                contato.setTipoContato(tipoDao.select(rs.getInt("id_tipocontato")));
                contato.setTelefones(telDao.listPorContato(contato));
                contatos.add(contato);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao pesquisar contatos" + e);
            return null;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return contatos;
    }

    @Override
    public List<Contato> listAll() throws Exception {
        List<Contato> contatos = new ArrayList<Contato>();
        Contato contato;
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement("select id, nome, datanascimento, email, id_tipocontato from contato");
            rs = ps.executeQuery();
            while (rs.next()) {
                contato = new Contato();
                TelefoneDAO telDao = new TelefoneDAOImpl();
                TipoContatoDAO tipoDao = new TipoContatoDAOImpl();
                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setDataNascimento(rs.getDate("datanascimento"));
                contato.setEmail(rs.getString("email"));
                contato.setTipoContato(tipoDao.select(rs.getInt("id_tipocontato")));
                contato.setTelefones(telDao.listPorContato(contato));
                contatos.add(contato);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao pesquisar contatos" + e);
            return null;
        } finally {
            ConnectionFactory.close(conn, ps, rs);
        }
        return contatos;
    }

}
