package com.example.alunotcc.Controle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.alunotcc.Modelo.Curso;
import com.example.alunotcc.Modelo.Turma;
import com.example.alunotcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

public class Activity_Mensagens extends AppCompatActivity {


    private ListView aliasListMsg;
    private Spinner aliasSpnCursos;
    private Spinner aliasSpnTurmas;
    private List<Turma> turmas;
    private List<Curso> cursos;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);




        aliasListMsg = findViewById(R.id.listMsg);
        aliasSpnCursos = findViewById(R.id.spinnerCursosConf);
        aliasSpnTurmas = findViewById(R.id.spinnerTurmasConf);

        carregarSpnCurso();

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
}
