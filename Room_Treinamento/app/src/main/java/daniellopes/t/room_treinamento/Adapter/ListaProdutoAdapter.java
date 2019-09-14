package daniellopes.t.room_treinamento.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import daniellopes.t.room_treinamento.Model.Produto;
import daniellopes.t.room_treinamento.R;

public class ListaProdutoAdapter extends RecyclerView.Adapter<ListaProdutoAdapter.ProdutoViweHolter> {

    private final List<Produto> produtos;
    private final Context context;
    private final DecimalFormat df = new DecimalFormat("R$ #0.00");
    private OnItemClickListener mListenner;

    public ListaProdutoAdapter(Context context, List<Produto> produtos) {
        this.produtos = produtos;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdutoViweHolter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.item_produto, parent, false);
        return new ProdutoViweHolter(view, mListenner);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProdutoAdapter.ProdutoViweHolter holder, int position) {
        Produto produto = produtos.get(position);
        holder.vincula(produto);
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    class ProdutoViweHolter extends RecyclerView.ViewHolder {

        private final TextView campoProduto;
        private final TextView campoPreco;
        private final TextView campoQuantidade;
        private final ImageView imageDelete;

        public ProdutoViweHolter(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            campoProduto = itemView.findViewById(R.id.item_produto_nome);
            campoPreco = itemView.findViewById(R.id.item_produto_preco);
            campoQuantidade = itemView.findViewById(R.id.item_produto_quantidade);
            imageDelete = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditaClick(position);
                        }
                    }
                }
            });

            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }

        private void vincula(Produto produto) {
            preencheCampos(produto);
        }

        private void preencheCampos(Produto produto) {
            campoProduto.setText(produto.getNome());
            campoPreco.setText(String.valueOf(df.format(produto.getPreco())));
            campoQuantidade.setText(String.valueOf(produto.getQuantidade()));
        }
    }

    public void atualiza(List<Produto> produtos) {
        this.produtos.clear();
        this.produtos.addAll(produtos);
        notifyDataSetChanged();
    }

    public void remove(Produto produto) {
        produtos.remove(produto);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onEditaClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListenner = listener;
    }
}
