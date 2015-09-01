/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Helena
 */
@ManagedBean
@RequestScoped
public class Clientes {

    private Logar logar = new Logar();

    private int id_cliente;
    private String nome;
    private String data_de_nascimento;
    private long cpf;
    private ArrayList<Clientes> vetor = new ArrayList<Clientes>();

    /**
     *
     */
    public Clientes selectedCliente;

    /**
     *
     */
    public Clientes() {

    }

    /**
     *
     * @param data
     * @return
     */
    public java.sql.Date converteDataUtilParaSQL(Date data) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date dataUtil = data;
        java.sql.Date dataSql = null;

        try {
            dataUtil = new java.sql.Date(dataUtil.getTime());
            dataSql = (java.sql.Date) dataUtil;
        } catch (Exception e) {
            System.err.println("Houve o seguinte erro: " + e.getMessage());
        }
        return dataSql;
    }

    /**
     *
     */
    public void inserir() {
        try {
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(data_de_nascimento);

            String sqlStmt = "INSERT INTO clientes.clientes (nome,cpf,data_de_nascimento) VALUES (?,?,?)";
            PreparedStatement stmt = logar.con.prepareStatement(sqlStmt);
            stmt.setString(1, nome);
            stmt.setLong(2, cpf);
            stmt.setDate(3, converteDataUtilParaSQL(date));
            stmt.execute();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Inserção OK!"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaClientes.jsf");

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage()));
        }
    }

    /**
     *
     * @param p_id_cliente
     */
    public void alterar(int p_id_cliente) {
        try {
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(data_de_nascimento);

            String sqlStmt = "UPDATE  clientes.clientes SET nome = ?, cpf = ?, data_de_nascimento = ? WHERE id_cliente = ?";
            PreparedStatement stmt = logar.con.prepareStatement(sqlStmt);
            stmt.setString(1, nome);
            stmt.setLong(2, cpf);
            stmt.setDate(3, converteDataUtilParaSQL(date));
            stmt.setInt(4, p_id_cliente);
            stmt.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Alteração OK!"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaClientes.jsf");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage()));
        }
    }

    /**
     *
     * @param p_id_cliente
     */
    public void excluir(int p_id_cliente) {
        try {
            String sqlStmt = "DELETE clientes.clientes WHERE id_cliente = ?";
            PreparedStatement stmt = logar.con. prepareStatement(sqlStmt);
            stmt.setInt(1, p_id_cliente);
            stmt.execute();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Exclusão OK!"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaClientes.jsf");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage()));
        }
    }

    /**
     *
     */
    public void chamarImpressao() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/treinamentoJavaWeb/impressao");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage()));
        }
    }

    /**
     *
     */
    public void chamarInserirCliente() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("inserirCliente.jsf");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage()));
        }
    }

    /**
     *
     */
    public void chamarListaCliente() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaCliente.jsf");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage()));
        }
    }

    /**
     *
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        setNome(((Clientes) event.getObject()).getNome());
        setData_de_nascimento(((Clientes) event.getObject()).getData_de_nascimento());
        setCpf(((Clientes) event.getObject()).getCpf());
    }

    /**
     *
     * @return
     */
    public List<Clientes> getRetornaClientes() {
        try {
            String sqlStmt = "SELECT id_cliente, to_char(data_de_nascimento,'DD/MM/YYYY') as data_de_nascimento, cpf FROM clientes.clientes ORDER BY nome";
            Statement stmt = (Statement) logar.con.createStatement();

            ResultSet rs = stmt.executeQuery(sqlStmt);
            vetor.clear();
            while (rs.next()) {
                Clientes cliente = new Clientes();

                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setData_de_nascimento(rs.getString("data_de_nascimento"));
                cliente.setCpf(rs.getLong("cpf"));

                cliente.getId_cliente();
                cliente.getNome();
                cliente.getData_de_nascimento();
                cliente.getCpf();

                vetor.add(cliente);
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage()));
        }
        return vetor;
    }

    /**
     *
     * @param selectClientes
     */
    public void setSelectedCliente(Clientes selectClientes) {
        this.selectedCliente = selectClientes;
    }

    /**
     *
     * @return
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     *
     * @param id_cliente
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return
     */
    public String getData_de_nascimento() {
        return data_de_nascimento;
    }

    /**
     *
     * @param data_de_nascimento
     */
    public void setData_de_nascimento(String data_de_nascimento) {
        this.data_de_nascimento = data_de_nascimento;
    }

    /**
     *
     * @return
     */
    public long getCpf() {
        return cpf;
    }

    /**
     *
     * @param cpf
     */
    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

}
