package br.java.appandroidcrudfirebase.modelo;

public class Cliente {

    private String clienteId;
    private String clienteNome;
    private String clienteEndereco;

    public Cliente() {
    }

    public Cliente(String clienteId, String clienteNome, String clienteEndereco) {
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.clienteEndereco = clienteEndereco;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getClienteEndereco() {
        return clienteEndereco;
    }

    public void setClienteEndereco(String clienteEndereco) {
        this.clienteEndereco = clienteEndereco;
    }
}
