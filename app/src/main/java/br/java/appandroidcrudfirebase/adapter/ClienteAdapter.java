package br.java.appandroidcrudfirebase.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import br.java.appandroidcrudfirebase.ItemClickListener;
import br.java.appandroidcrudfirebase.R;
import br.java.appandroidcrudfirebase.modelo.Cliente;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {

    List<Cliente> clienteLista;
    Context context;
    private ItemClickListener clickListener;

    public ClienteAdapter(Context context, List<Cliente> clienteLista) {
        this.clienteLista = clienteLista;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView clienteNome;
        public TextView clienteEndereco;
        public ImageView clienteFoto;
        public ImageView opcaoMenu;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            clienteNome = (TextView) itemView.findViewById(R.id.clienteNome);
            clienteEndereco = (TextView) itemView.findViewById(R.id.clienteEndereco);
            clienteFoto = (ImageView) itemView.findViewById(R.id.clienteFoto);
            opcaoMenu = (ImageView) itemView.findViewById(R.id.opcaoMenu);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public ClienteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clienteadapter_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.clienteNome.setText(clienteLista.get(position).getClienteNome());
        holder.clienteEndereco.setText(clienteLista.get(position).getClienteEndereco());
        holder.clienteFoto.setImageResource(R.drawable.user);
        holder.opcaoMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.opcaoMenu);
                popupMenu.inflate(R.menu.menu_itens);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.editar:
                                atualizarCliente(clienteLista.get(position));

                            case R.id.deletar:
                                deletarCliente(clienteLista.get(position).getClienteId());

                            break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private Cliente atualizarCliente;
    private void atualizarCliente(Cliente cliente) {
        atualizarCliente = cliente;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.atualizar_dialogo, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextNome = (EditText) dialogView.findViewById(R.id.editTextNome);
        final Spinner spinnerAtualizar = (Spinner) dialogView.findViewById(R.id.spinnerAtualizar);
        final Button btnAtualizar = (Button) dialogView.findViewById(R.id.btnAtualizarCliente);

        editTextNome.setText(atualizarCliente.getClienteNome());

        dialogBuilder.setTitle("Edição do cliente");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNome.getText().length() > 0) {
                    DatabaseReference minhaRef = FirebaseDatabase.getInstance().getReference("clientes").child(atualizarCliente.getClienteId());
                    Cliente cliente = new Cliente(atualizarCliente.getClienteId(), editTextNome.getText().toString(), spinnerAtualizar.getSelectedItem().toString());
                    minhaRef.setValue(cliente);
                    b.dismiss();
                    Toast.makeText(context, "Atualizado pelo cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deletarCliente(String id) {
        DatabaseReference minhaRef = FirebaseDatabase.getInstance().getReference("clientes").child(id);
        minhaRef.removeValue();
        DatabaseReference minhaRefPedidos = FirebaseDatabase.getInstance().getReference("pedidos").child(id);
        minhaRefPedidos.removeValue();

        Toast.makeText(context, "Cliente Excluído", Toast.LENGTH_SHORT).show();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    @Override
    public int getItemCount() {
        return clienteLista.size();
    }
}
