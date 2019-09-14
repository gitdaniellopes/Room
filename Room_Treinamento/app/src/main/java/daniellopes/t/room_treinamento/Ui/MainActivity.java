package daniellopes.t.room_treinamento.Ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import daniellopes.t.room_treinamento.Adapter.ListaProdutoAdapter;
import daniellopes.t.room_treinamento.DAO.ProdutoDAO;
import daniellopes.t.room_treinamento.DataBase.ProdutoDataBase;
import daniellopes.t.room_treinamento.Model.Produto;
import daniellopes.t.room_treinamento.R;

import static daniellopes.t.room_treinamento.Ui.ConstantesActivities.CHAVE_PRODUTO;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Produto> produtos = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private ListaProdutoAdapter produtoAdapter;
    private ProdutoDAO produtoDAO;
    private Produto produtoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bind();
        ProdutoDataBase dataBase = ProdutoDataBase.getInstance(this);
        produtoDAO = dataBase.getRoomProdutoDAO();
        configuraAdapter();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormularioActivity.class);
                startActivity(intent);

            }
        });
    }

    private void bind() {
        mRecyclerView = findViewById(R.id.recycleProdutos);
    }

    private void configuraAdapter() {
        produtoAdapter = new ListaProdutoAdapter(this, produtos);
        configuraRecycleView();
    }

    private void configuraRecycleView() {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(produtoAdapter);
        eventosDeClickRecycleView();
    }


    private void eventosDeClickRecycleView() {
        produtoAdapter.setOnItemClickListener(new ListaProdutoAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                produtoSelecionado = produtos.get(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Confirmar exclusão");
                dialog.setMessage("Deseja excluir o Produto " + produtoSelecionado.getNome() + "?");

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (produtoSelecionado.hashCode() > 0) {
                            remove(produtoSelecionado);
                            Toast.makeText(MainActivity.this, "Produto removido!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Produto não removido!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.setNegativeButton("Não", null);

                dialog.create();
                dialog.show();
            }

            @Override
            public void onEditaClick(int position) {
                produtoSelecionado = produtos.get(position);
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                intent.putExtra(CHAVE_PRODUTO, produtoSelecionado);
                startActivity(intent);
            }
        });


    }

    private void remove(Produto produto) {
        produtoDAO.remove(produto);
        produtoAdapter.remove(produto);
    }

    @Override
    protected void onResume() {
        produtoAdapter.atualiza(produtoDAO.todos());
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
