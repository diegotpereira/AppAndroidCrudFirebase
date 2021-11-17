package br.java.appandroidcrudfirebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.java.appandroidcrudfirebase.R;
import br.java.appandroidcrudfirebase.modelo.Pedido;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder> {

    List<Pedido> pedidoLista;
    Context context;

    public PedidoAdapter(Context context, List<Pedido> pedidoLista) {
        this.pedidoLista = pedidoLista;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pedidoNome;
        public TextView pedidoPreco;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pedidoNome = (TextView) itemView.findViewById(R.id.pedidoNome);
            pedidoPreco = (TextView) itemView.findViewById(R.id.pedidoPreco);
            itemView.setTag(itemView);
        }
    }

    @NonNull
    @Override
    public PedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedidoadapter_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapter.ViewHolder holder, int position) {
        holder.pedidoNome.setText("Nome do Pedido: "+" " + pedidoLista.get(position).getPedidoNome());
        holder.pedidoPreco.setText("Preço do pedido: "+" " + String.valueOf(pedidoLista.get(position).getPedidoPreco() + " " + "R$"));
    }

    @Override
    public int getItemCount() {
        return pedidoLista.size();
    }
}
