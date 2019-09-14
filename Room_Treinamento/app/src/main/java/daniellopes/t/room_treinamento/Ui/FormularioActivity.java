package daniellopes.t.room_treinamento.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import daniellopes.t.room_treinamento.DAO.ProdutoDAO;
import daniellopes.t.room_treinamento.DataBase.ProdutoDataBase;
import daniellopes.t.room_treinamento.Model.Produto;
import daniellopes.t.room_treinamento.R;

import static daniellopes.t.room_treinamento.Ui.ConstantesActivities.CHAVE_PRODUTO;

public class FormularioActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_PRODUTO = "Novo produto";
    private static final String TITULO_APPBAR_EDITA_PRODUTO = "Edita produto";
    private EditText campoNome;
    private EditText campoPreco;
    private EditText campoQuantidade;
    private Button botaocadastrar;
    private Produto produto;
    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        ProdutoDataBase dataBase = ProdutoDataBase.getInstance(this);
        produtoDAO = dataBase.getRoomProdutoDAO();
        bind();
        carregaProduto();

        botaocadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizaFormulario();
            }
        });

    }

    private void bind() {
        campoNome = findViewById(R.id.campo_nome_produto);
        campoPreco = findViewById(R.id.campo_preco_produto);
        campoQuantidade = findViewById(R.id.campo_quantidade_produto);
        botaocadastrar = findViewById(R.id.botao_salvar);
    }

    private void carregaProduto() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PRODUTO)) {
            setTitle(TITULO_APPBAR_EDITA_PRODUTO);
            produto = (Produto) dados.getSerializableExtra(CHAVE_PRODUTO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_PRODUTO);
            produto = new Produto();
        }
    }

    private void preencheCampos() {
        //Editar
        campoNome.setText(produto.getNome());
        campoPreco.setText(String.valueOf(produto.getPreco()));
        campoQuantidade.setText(String.valueOf(produto.getQuantidade()));
    }

    private void finalizaFormulario() {
        preencheProduto();
        if (produto.temIdValido()) {
            produtoDAO.edita(produto);
        } else {
            produtoDAO.salva(produto);
        }
        finish();
    }

    private void preencheProduto() {
        String nome = campoNome.getText().toString();
        String preco = campoPreco.getText().toString();
        String quantidade = campoQuantidade.getText().toString();

        produto.setNome(nome);
        produto.setPreco(Double.parseDouble(preco));
        produto.setQuantidade(Integer.parseInt(quantidade));
    }
}
