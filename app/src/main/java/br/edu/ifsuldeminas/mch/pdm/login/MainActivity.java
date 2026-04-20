package br.edu.ifsuldeminas.mch.pdm.login;



import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String USER_NAME="leticia";
    private static final String PW="admin";

    private static final String LOG_TAG ="login_activity_main";
    private Button buttonLogin;
    private Button buttonRegister;
    private Button buttonForgotPW;
    private EditText editTextUser;

    private EditText editTextPW;

    private ImageView imageViewCircular;

    private ActivityResultLauncher<String> startWelcomeActLauncher =
            registerForActivityResult(new SimpleContract(), new ActivityResultCallback<String>() {
        @Override
        public void onActivityResult(String result) {
            if(result == null || "".equals(result))
                return;

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = findViewById(R.id.buttonLoginId);
        buttonRegister = findViewById(R.id.buttonRegisterId);
        buttonForgotPW = findViewById(R.id.buttonForgotPwId);
        editTextUser = findViewById(R.id.textInputLayoutUserId);
        editTextPW = findViewById(R.id.textInputLayoutPWId);
        imageViewCircular = findViewById(R.id.imageViewCircularId);

        //Classe aninhada
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @NonNull
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public void onClick(View v) {

                String userName = editTextUser.getText().toString();
                String userPW = editTextPW.getText().toString();

                if (USER_NAME.equals(userName) && PW.equals(userPW)) {
                    //via launcher
                    //startWelcomeActLauncher.launch(userName);
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    intent.putExtra("user_name", userName);
                    startActivityForResult(intent, 1);

                } else {
                    Toast toast = Toast.makeText(
                            getBaseContext(),
                            R.string.invalid_user_or_pw,
                            Toast.LENGTH_LONG);
                    toast.show();
                    editTextUser.requestFocus();
                }
            }
        });
        //Classe externa
        buttonRegister.setOnClickListener(new RegisterClickListener());

        //Lambdas com interfaces SAM (single abstract method)
        buttonForgotPW.setOnClickListener((View view) -> {
            Toast.makeText(view.getContext(),
                    R.string.button_forgot_pw_clicked,
                    Toast.LENGTH_LONG).show();
        });
       Log.d(LOG_TAG, "Método onCreate da Main Activity executou com sucesso!");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(LOG_TAG, "ActivityMain foi fechada");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(LOG_TAG, "ActivityMain passou por aqui!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Este código intercepta a "foto_result" que você enviou da Welcome
        if (resultCode == RESULT_OK && data != null && data.hasExtra("foto_result")) {
            Bitmap foto = (Bitmap) data.getExtras().get("foto_result");
            if (foto != null) {
                imageViewCircular.setImageBitmap(foto);
                imageViewCircular.setVisibility(View.VISIBLE);
            }
        }
    }
}