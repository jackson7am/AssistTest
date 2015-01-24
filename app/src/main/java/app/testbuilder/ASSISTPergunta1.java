package app.testbuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import app.testbuilder.br.com.TestBuilder.DAO.AssistDAO;
import app.testbuilder.br.com.TestBuilder.DAO.TesteDAO;
import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Teste;

public class ASSISTPergunta1 extends ActionBarActivity {

    public Assist assist;
    public AssistDAO aDao;
    public TesteDAO tDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assistpergunta1);

        // START Retrieve data from another activity
        Intent intent = getIntent();

        if (intent != null) {
            assist = intent.getParcelableExtra("ASSIST");
        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);



        alert.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Paciente quer continuar o teste
            }
        });
        alert.setPositiveButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Paciente nunca usou nenhum tipo de droga
                Toast.makeText(getApplicationContext(), "Parabéns por não utilizar drogas!", Toast.LENGTH_LONG).show();
                Intent intent;
                intent = new Intent(ASSISTPergunta1.this, Main.class);
                startActivity(intent);
                finish();
            }
        });

        alert.create();

        String[] perguntasAssist = getResources().getStringArray(R.array.perguntas_assist);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(perguntasAssist[0]);

        Button confirmButton = (Button) findViewById(R.id.button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DEBUG", "BUTTON CLICKED");

                // Check checkboxes
                if(verifyCheckboxes()) {
                    assist = new Assist();
                    aDao = new AssistDAO(getApplicationContext());
                    tDao = new TesteDAO(getApplicationContext());

                    try {
                        String p1 = aDao.booleanToString(getSubstancias());
                        assist.setTeste_id(tDao.getLastId().getId());
                        assist.setP1(p1); //Valores da Questão1

                        boolean sucesso = aDao.inserir(assist); //Gravando o assist
                        if(sucesso) {
                            Log.i("ASSIST-1-IF:",assist.toString());
                        } else {
                            Log.i("ASSIST-1-ELSE:","");
                        }

                        assist = aDao.getLastId(); // android.database.sqlite.SQLiteException: no such table: assit (code 1): , while compiling: SELECT MAX(id) FROM assit
                        assist.setP1(p1); // É necessário devido aDao.getLastId() retornar um novo objeto sem p1
                        assist.setTeste_id(tDao.getLastId().getId());
                        Log.i("ASSIST-1-AFTER:",assist.toString());
                    } catch (SQLException e) {

                        trace("ERROR-Cadastro:" + e.getMessage());
                    }
                    Intent intent = new Intent(ASSISTPergunta1.this, ASSISTPergunta2.class);
                    intent.putExtra("QUESTION", 1);
                    intent.putExtra("SUBSTANCIAS", getSubstancias());
                    intent.putExtra("ASSIST", assist);
                    startActivity(intent);
                    finish();

                // Se não selecionou nenhuma substância
                } else {
                    alert.show();
                }
            }
        });
    }

    int getCheckBoxId(int index) {
      switch (index) {
          case 2:
              return R.id.checkBox2;
          case 3:
              return R.id.checkBox3;
          case 4:
              return R.id.checkBox4;
          case 5:
              return R.id.checkBox5;
          case 6:
              return R.id.checkBox6;
          case 7:
              return R.id.checkBox7;
          case 8:
              return R.id.checkBox8;
          case 9:
              return R.id.checkBox9;
          case 10:
              return R.id.checkBox10;
          default:
              return R.id.checkBox1;
      }
    };

    public boolean verifyCheckboxes() {
        for(int index = 1; index < 11; index++) {
            CheckBox checkBox = (CheckBox) findViewById(getCheckBoxId(index));
            if(checkBox.isChecked()) {
                return true;
            }
        }
        return false;
    }

    public boolean[] getSubstancias() {
        boolean[] substanciasUsadas = new boolean[10];
        int[] checkboxes = {R.id.checkBox1, R.id.checkBox2, R.id.checkBox3, R.id.checkBox4,
                R.id.checkBox5, R.id.checkBox6, R.id.checkBox7, R.id.checkBox8, R.id.checkBox9,
                R.id.checkBox10};

        for(int index = 0; index < checkboxes.length; index++) {
            CheckBox checkBox = (CheckBox) findViewById(checkboxes[index]);
            if(checkBox.isChecked()) {
                substanciasUsadas[index] = true;
            } else {
                substanciasUsadas[index] = false;
            }
        }
        return substanciasUsadas;
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void trace(String msg) {
        toast(msg);
    }

}
