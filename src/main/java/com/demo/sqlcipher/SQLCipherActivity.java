package com.demo.sqlcipher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.demo.sqlite.R;
import com.j256.ormlite.dao.Dao;
import net.sqlcipher.database.SQLiteDatabase;

import java.sql.SQLException;
import java.util.List;

public class SQLCipherActivity extends Activity {
    private SQLiteOrmWithCipherHelper helper;
    private Dao<User, String> dao;

    private EditText etFirstName;
    private EditText etLastName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeSQLCipher();
        setContentView(R.layout.activity_cipher);
        final Button btnSave = (Button) findViewById(R.id.btnSave);
        final Button btnGet = (Button) findViewById(R.id.btnGet);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLasttName);
        final TextView tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        final TextView tvLastName = (TextView) findViewById(R.id.tvLastName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User() {{
                    firstname = etFirstName.getText().toString();
                    lastname = etLastName.getText().toString();
                }};
                Log.d("SQLCipherTest", "saving user: " + user);
                try {
                    dao.create(user);
                } catch (SQLException e) {
                    // handle exception
                    Log.e("SQLCipherTest", "can't save user: " + user);
                }
                Log.d("SQLCipherTest", "saved user: " + user);
            }
        });
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<User> users = dao.queryForAll();
                    Log.d("SQLCipherTest", "users: " + users.size());
                    if (!users.isEmpty()) {
                        User user = users.get(users.size()-1);
                        Log.d("SQLCipherTest", "found user: " + user);
                        tvFirstName.setText(user.firstname);
                        tvLastName.setText(user.lastname);
                    }
                } catch (SQLException e) {
                    // handle exception
                }
            }
        });
    }

    private void initializeSQLCipher() {
        SQLiteDatabase.loadLibs(this);
        helper = new SQLiteOrmWithCipherHelper(getApplicationContext());
        helper.getWritableDatabase(SQLiteOrmWithCipherHelper.DATABASE_PASSWORD);
        try {
            dao = helper.getDao(User.class);
        } catch (SQLException e) {
            // handle exception
        }
    }
}