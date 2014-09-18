package com.demo.sqlcipher;

import android.content.Context;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.sqlcipher.database.SQLiteDatabase;

import java.sql.SQLException;

public class SQLiteOrmWithCipherHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    public static final String DATABASE_PASSWORD = "changeIT";
    private static final int DATABASE_VERSION = 3;

    public SQLiteOrmWithCipherHelper(Context context) {
        // todo you need to obtain password in better way
        super(context, DATABASE_NAME, null, DATABASE_VERSION, DATABASE_PASSWORD);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource source) {
        createTables(source);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource source, int oldVersion, int newVersion) {
        dropTables(source);
        createTables(source);
    }

    @Override
    public void close() {
        super.close();
    }

    private void createTables(ConnectionSource source) {
        try {
            TableUtils.createTable(source, User.class);
        } catch (SQLException e) {
        }
    }

    private void dropTables(ConnectionSource source) {
        try {
            TableUtils.dropTable(source, User.class, true);
        } catch (SQLException e) {
        }
    }
}
