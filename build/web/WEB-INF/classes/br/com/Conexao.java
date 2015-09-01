/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Helena
 */

public class Conexao {
    private static String dbOri = "jdbc:postgresql://localhost:5432/clientes";
    private static Connection con = null;
    
    /**
     *
     * @param usuario
     * @param senha
     * @return
     */
    public static Connection conectar(String usuario, String senha){
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(dbOri, usuario, senha);
        } catch (Exception e) {
            con = null;
        }
        return con;
    }
    
    /**
     *
     */
    public static void encerrarSessao(){
        try {
            con.commit();
            con.close();
            con = null;
        } catch (Exception e) {
            System.err.println("Houve um erro ao encerrar sessão!"+e.getMessage());
        }finally{
            con = null;
        }
    }
    
}
