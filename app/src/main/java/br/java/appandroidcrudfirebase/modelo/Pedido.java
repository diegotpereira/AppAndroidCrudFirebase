package br.java.appandroidcrudfirebase.modelo;

public class Pedido {

    private String pedidoId;
    private String pedidoNome;

    public Pedido() {
    }

    public Pedido(String pedidoId, String pedidoNome) {
        this.pedidoId = pedidoId;
        this.pedidoNome = pedidoNome;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getPedidoNome() {
        return pedidoNome;
    }

    public void setPedidoNome(String pedidoNome) {
        this.pedidoNome = pedidoNome;
    }
}
