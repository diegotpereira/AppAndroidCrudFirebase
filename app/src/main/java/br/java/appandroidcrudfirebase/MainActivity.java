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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.java.appandroidcrudfirebase.adapter.ClienteAdapter;
import br.java.appandroidcrudfirebase.modelo.Cliente;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    public static final String CLIENTE_NOME = "br.java.appandroidcrudfirebase.clientenome";
    public static final String CLIENTE_ID = "br.java.appandroidcrudfirebase.clienteid";

    private Spinner spinner;
    private EditText editText;
    private FloatingActionButton floatingActionButton;

    private DatabaseReference databaseReferenceClientes;

    private List<Cliente> clientes;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ClienteAdapter clienteAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        databaseReferenceClientes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientes.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Cliente cliente = postSnapshot.getValue(Cliente.class);
                    clientes.add(cliente);
                }

                recyclerView.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                clienteAdapter = new ClienteAdapter(MainActivity.this, clientes);
                clienteAdapter.setClickListener(MainActivity.this);
                recyclerView.setAdapter(clienteAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReferenceClientes = FirebaseDatabase.getInstance().getReference("clientes");

        spinner = (Spinner) findViewById(R.id.spinnerArray);
        editText = (EditText) findViewById(R.id.editTextNome);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.addCliente);
        recyclerView = (RecyclerView) findViewById(R.id.minha_recycler_view);

        clientes = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCliente();
            }
        });
    }


    private void addCliente() {
        if (editText.getText().length() > 0) {
            String id = databaseReferenceClientes.push().getKey();
            Cliente cliente = new Cliente(id, editText.getText().toString(), spinner.getSelectedItem().toString());
            databaseReferenceClientes.child(id).setValue(cliente);
            editText.setText("");

            Toast.makeText(getApplicationContext(), "Novo cliente adicionado", Toast.LENGTH_SHORT).show();

        } else {
            exibirDialogoAlerta();
        }
    }

    private void exibirDialogoAlerta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Erro");
        builder.setMessage("Digite o nome do cliente!");

        String textoPositivo = "OK";
        builder.setPositiveButton(textoPositivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onClick(View view, int position) {
        Cliente cliente = clientes.get(position);

        Intent intent = new Intent(getApplicationContext(), DetalheActivity.class);
        intent.putExtra(CLIENTE_ID, cliente.getClienteId());
        intent.putExtra(CLIENTE_NOME, cliente.getClienteNome());
        startActivity(intent);
    }
}