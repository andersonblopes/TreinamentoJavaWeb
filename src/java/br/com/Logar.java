/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com;

import java.sql.Connection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Helena
 */

@ManagedBean
@RequestScoped
public class Logar {

    /**
     *
     */
    public static String usuario;

    /**
     *
     */
    public static String senha;
    private Conexao conectar;

    /**
     *
     */
    public static Connection con = null;
    private String conectou = "Digite o usuário e a senha.";
    
    /**
     *
     */
    public Logar(){
        
    }

    /**
     *
     * @return
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return
     */
    public String getSenha() {
        return senha;
    }

    /**
     *
     * @param senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     *
     * @return
     */
    public String getConectou() {
        return conectou;
    }

    /**
     *
     * @param conectou
     */
    public void setConectou(String conectou) {
        this.conectou = conectou;
    }
    
    /**
     *
     */
    public void logar(){
        try {
            con = conectar.conectar(usuario, senha);
            if(con != null){
                setConectou("OK!");
                FacesContext.getCurrentInstance().getExternalContext().redirect("menu.jsf");
            }else{
                setConectou("Usuário e/ou senha inválidos!");
            }
        }
         catch (Exception e) {
            setConectou("Houve erro de conexão!"+e.getMessage());
        }
    }
    
    /**
     *
     * @return
     */
    public Connection logarRetorno(){
        try {
            con = conectar.conectar(usuario, senha);
            return con;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     *
     */
    public void encerrar(){
        conectar.encerrarSessao();
        con = null;
    }
}
