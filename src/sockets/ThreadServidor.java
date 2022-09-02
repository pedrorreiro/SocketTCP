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
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class ThreadServidor implements Runnable {

    private static Socket conexao;
    private static DataInputStream entrada;
    private static DataOutputStream saida;
    private static ServerSocket servidor;
    private static int porta;
    public static DefaultTableModel model;
    public static JTable tabela;
    private static int count = 0;

    public ThreadServidor(Socket conexao, JTable tabela, DefaultTableModel model, int porta) {
        this.conexao = conexao;
        this.porta = porta;
        this.tabela = tabela;
        this.model = model;
    }

    @Override
    public void run() {
        try {

            servidor = new ServerSocket(porta);

            while (true) {

                System.out.println("Aguardando conexão...");

                conexao = servidor.accept();

                new Thread(new ThreadAtendimento(conexao, tabela, model)).start();

            }

        } catch (IOException ex) {
            Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
