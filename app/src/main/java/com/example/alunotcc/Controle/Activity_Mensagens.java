package com.example.alunotcc.Controle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alunotcc.Modelo.Curso;
import com.example.alunotcc.Modelo.Mensagem;
import com.example.alunotcc.Modelo.Turma;
import com.example.alunotcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class Activity_Mensagens extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {


    private ListView aliasListMsg;
    private Spinner aliasSpnCursos;
    private Spinner aliasSpnTurmas;
    private Button  aliasBtnSeeMsg;
    private List<Turma> turmas;
    private List<Curso> cursos;
    private List<Mensagem> mensagens;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);




        aliasListMsg = findViewById(R.id.gerencTurmas);
        aliasSpnCursos = findViewById(R.id.spinnerCursosConf);
        aliasSpnTurmas = findViewById(R.id.spinnerTurmasConf);
        aliasBtnSeeMsg = findViewById(R.id.btnSeeMensagens);

        carregarSpnCurso();

        cursos = new ArrayList<>();
        mensagens = new ArrayList<>();
        turmas = new ArrayList<>();


        aliasSpnCursos.setOnItemSelectedListener(this);

        aliasBtnSeeMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                carregarMsg();



            }
        });
    }


    public void carregarMsg(){
        Curso curso = (Curso) aliasSpnCursos.getSelectedItem();
        Turma turma = (Turma) aliasSpnTurmas.getSelectedItem();


        FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas").document(turma.getId())
                .collection("mensagens").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                   mensagens.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String admin = document.getString("adminMsg");
                        String data = document.getString("dataMensagem");
                        String id = document.getString("id");
                        String idAdmin = document.getString("idAdmin");
                        String mensagem = document.getString("mensagem");
                        Boolean mudanca = document.getBoolean("mudancaHorario");
                        Boolean paraTodos = document.getBoolean("paraTodos");
                        String semestre = document.getString("semestreMensagem");
                        long time = document.getLong("timeMassage");
                        String turmaAno = document.getString("turmaAnoMensagem");



                       Mensagem u = new Mensagem(id, idAdmin, mensagem, admin, turmaAno, semestre, data, time, paraTodos, mudanca);


                       mensagens.add(u);






                    }
                     ArrayAdapter <Mensagem> adaptador = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, mensagens);
                     aliasListMsg.setAdapter(adaptador);
                    adaptador.notifyDataSetChanged();



                    if(mensagens.isEmpty()){

                        Toast.makeText(Activity_Mensagens.this, "não há mensagens nesta turma", Toast.LENGTH_SHORT).show();
                    }


                } else {

                }
            }
        });

    }



    public void carregarSpnCurso(){
        token = FirebaseInstanceId.getInstance().getToken();
        FirebaseFirestore.getInstance().collection("alunos").document(token).collection("cursos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cursos.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String nomeCurso = document.getString("curso");


                                Curso u = new Curso();
                                u.setId(document.getId());
                                u.setCurso(nomeCurso);

                                cursos.add(u);


                            }

                            final ArrayAdapter<Curso> adaptador = new ArrayAdapter <>(getBaseContext(), android.R.layout.simple_spinner_item, cursos);
                            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            aliasSpnCursos.setAdapter(adaptador);
                            adaptador.notifyDataSetChanged();

                        } else {

                        }
                    }
                });

    }

    public void carregarSpnTurmas(){

        Curso curso = (Curso) aliasSpnCursos.getSelectedItem();

        FirebaseFirestore.getInstance().collection("alunos").document(token).collection("cursos").document(curso.getId())
                .collection("turmas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           turmas.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String nomeCurso = document.getString("curso");
                                String ano = document.getString("ano");
                                String semestre = document.getString("semestre");



                                Turma u = new Turma();
                                u.setId(document.getId());
                                u.setAno(ano);
                                u.setSemestre(semestre);
                                u.setCurso(nomeCurso);

                                turmas.add(u);


                            }

                            final ArrayAdapter<Turma> adaptador = new ArrayAdapter <>(getBaseContext(), android.R.layout.simple_spinner_item, turmas);
                            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            aliasSpnTurmas.setAdapter(adaptador);
                            adaptador.notifyDataSetChanged();

                        } else {

                        }
                    }
                });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        carregarSpnTurmas();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.gerencTurmas:

                Intent intent = new Intent(Activity_Mensagens.this, Activity_Gerenciar_Turmas.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}



