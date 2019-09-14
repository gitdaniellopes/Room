package daniellopes.t.room_treinamento.DataBase;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class Migrations {

    private static final Migration MIGRATION_1_3 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE produto ADD COLUMN quantidade INT");
//            database.execSQL("ALTER TABLE pro");
              database.execSQL("ALTER TABLE Produto ADD COLUMN quantidade TEXT");
        }
    };

    static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_3};
}
