package daniellopes.t.room_treinamento.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import daniellopes.t.room_treinamento.DAO.ProdutoDAO;
import daniellopes.t.room_treinamento.Model.Produto;

import static daniellopes.t.room_treinamento.DataBase.Migrations.TODAS_MIGRATIONS;

@Database(entities = {Produto.class}, version = 5, exportSchema = false)
public abstract class ProdutoDataBase extends RoomDatabase {

    private static final String BANCO_DE_DADOS = "banco.db";

    public abstract ProdutoDAO getRoomProdutoDAO();

    public static  ProdutoDataBase getInstance(Context context){
        return Room
                .databaseBuilder(context, ProdutoDataBase.class, BANCO_DE_DADOS)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
//                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }
}
