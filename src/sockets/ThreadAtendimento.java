/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ThreadAtendimento implements Runnable {

    private static Socket conexao;
    private static ServerSocket servidor;
    private static DataInputStream entrada;
    private static DataOutputStream saida;
    public static DefaultTableModel model;
    public static JTable tabela;
    private static int count = 0;

    public ThreadAtendimento(Socket conexao, JTable tabela, DefaultTableModel model) {
        this.conexao = conexao;
        this.tabela = tabela;
        this.model = model;
    }

    @Override
    public void run() {
        try {

            System.out.println("Conectado");

            count++;

            model.addRow(new Object[]{
                "Cliente " + count,
                "Conectado",
                ""
            });

            tabela.setModel(model);

            int registros = tabela.getRowCount();

            entrada = new DataInputStream(conexao.getInputStream());

            String dadosCliente = entrada.readUTF();

            System.out.println("Mensagem do cliente: " + dadosCliente);

            tabela.setValueAt(dadosCliente, registros - 1, 2); // Inserindo dado

            tabela.setValueAt("Desconectado", registros - 1, 1); // Desconectando status

            saida = new DataOutputStream(conexao.getOutputStream());

            saida.writeUTF("Mensagem recebida!");

            if (dadosCliente.equals("fim")) {
                conexao.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ThreadAtendimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
