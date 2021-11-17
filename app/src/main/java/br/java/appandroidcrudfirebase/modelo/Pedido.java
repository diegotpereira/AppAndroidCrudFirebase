package br.java.appandroidcrudfirebase.modelo;

public class Pedido {

    private String pedidoId;
    private String pedidoNome;
    private int pedidoPreco;

    public Pedido() {
    }

    public Pedido(String pedidoId, String pedidoNome, int pedidoPreco) {
        this.pedidoId = pedidoId;
        this.pedidoNome = pedidoNome;
        this.pedidoPreco = pedidoPreco;
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

    public int getPedidoPreco() {
        return pedidoPreco;
    }

    public void setPedidoPreco(int pedidoPreco) {
        this.pedidoPreco = pedidoPreco;
    }
}
