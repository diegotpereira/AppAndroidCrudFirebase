package br.java.appandroidcrudfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.java.appandroidcrudfirebase.adapter.PedidoAdapter;
import br.java.appandroidcrudfirebase.modelo.Pedido;

public class DetalheActivity extends AppCompatActivity {

    private DatabaseReference databaseReferencePedidos;

    private TextView clienteNome;
    private EditText editText;
    private EditText editTextPreco;

    private FloatingActionButton floatingActionButton;

    private PedidoAdapter pedidoAdapter;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    List<Pedido> pedidoLista;

    @Override
    protected void onStart() {
        super.onStart();
        databaseReferencePedidos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pedidoLista.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Pedido pedido = postSnapshot.getValue(Pedido.class);
                    pedidoLista.add(pedido);
                }
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(DetalheActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                pedidoAdapter = new PedidoAdapter(DetalheActivity.this, pedidoLista);
                recyclerView.setAdapter(pedidoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Intent intent = getIntent();

        databaseReferencePedidos = FirebaseDatabase.getInstance().getReference("pedidos").child(intent.getStringExtra(MainActivity.CLIENTE_ID));

        clienteNome = (TextView) findViewById(R.id.clienteNome);
        clienteNome.setText(intent.getStringExtra(MainActivity.CLIENTE_NOME));

        recyclerView = (RecyclerView) findViewById(R.id.minha_recycler_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.addPedidos);
        editText = (EditText) findViewById(R.id.editTextNome);
        editTextPreco = (EditText) findViewById(R.id.editTextPreco);

        pedidoLista = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length() > 0 && editTextPreco.getText().length() > 0) {
                    String id = databaseReferencePedidos.push().getKey();
                    Pedido pedido = new Pedido(id, editText.getText().toString(), Integer.parseInt(editTextPreco.getText().toString()));
                    databaseReferencePedidos.child(id).setValue(pedido);

                    Toast.makeText(getApplicationContext(), "Novo pedido adicionado", Toast.LENGTH_SHORT).show();

                    editText.setText("");
                    editTextPreco.setText("");


                } else {
                    exibirDialogoAlert();
                }
            }
        });
    }
    private void exibirDialogoAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetalheActivity.this);
        builder.setTitle("Erro");
        builder.setMessage("Insira o pedido/preço!");

        String textoPositivo = "OK";
        builder.setPositiveButton(textoPositivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}