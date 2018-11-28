/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import bean.Contato;
import bean.TipoContato;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alunos
 */
public class Negocio {

    public static void gerarCsv(File file, List<Contato> contatos) throws Exception {
        String nome = file.getAbsolutePath();
        if (!nome.toUpperCase().endsWith(".CSV")) {
            nome = nome + ".csv";
        }
        file = new File(nome);
        FileWriter arquivo = new FileWriter(file);
        PrintWriter gravar = new PrintWriter(arquivo);
        gravar.println("Nome;Email;DataNascimento;Telefone 1;Tipo Contato;");
        for (Contato contato : contatos) {
            gravar.println(contato.getNome() + ";" + contato.getEmail() + ";" + contato.getDataNascimento().toString() + ";"
                    + "(" + contato.getTelefones().get(0).getDdd() + ") " + contato.getTelefones().get(0).getTelefone() + ";" + contato.getTipoContato().getNome() + ";");
        }
        arquivo.close();
        gravar.close();
    }

    public static List<Contato> filtrarPorTipo(List<Contato> contatos, TipoContato tipo) {
        if (tipo.getNome().equals("Não Filtrar")) {
            return contatos;
        }
        List<Contato> contatosFiltrados = new ArrayList<Contato>();
        for (Contato contato : contatos) {
            if (contato.getTipoContato().getNome().equals(tipo.getNome())) {
                contatosFiltrados.add(contato);
            }
        }
        return contatosFiltrados;
    }

    public static void executarCsv(File file) {
        String command = "cmd.exe /C start excel " + file.getPath();
        Runtime run = Runtime.getRuntime();
        try {
            Process pp = run.exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
