package unfpa.shakawat.anc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText user_id,pass;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        user_id = (EditText) findViewById(R.id.user_id);
        pass = (EditText) findViewById(R.id.pass);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_id.getText().toString().compareTo("ancdemo")==0 &&
                pass.getText().toString().compareTo("ancdemo")==0){
                    startActivity(new Intent(MainActivity.this,ListActivity.class));
                    finish();
                }else {
                    Toast.makeText(MainActivity.this,"User id or Password Mismatch",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}