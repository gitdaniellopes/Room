package daniellopes.t.room_treinamento.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import daniellopes.t.room_treinamento.Model.Produto;

@Dao
public interface ProdutoDAO {

    @Insert
    void salva(Produto produto);

    @Update
    void edita(Produto produto);

    @Delete
    void remove(Produto produto);

    @Query("SELECT * FROM produto")
    List<Produto> todos();

    @Query("DELETE FROM Produto")
    void removeTodos();
}
