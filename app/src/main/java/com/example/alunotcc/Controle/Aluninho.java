package com.example.alunotcc.Controle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alunotcc.Modelo.Aluno;
import com.example.alunotcc.Modelo.Curso;
import com.example.alunotcc.Modelo.Turma;
import com.example.alunotcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Aluninho extends AppCompatActivity {

    private Spinner aliasSpinnerCurso;
    private Spinner aliasSpinnerTurma;
    private List<Turma> turmas;
    private Button aliasBtnSeeTurmas;
    private EditText aliasAnoTurma;
    private ListView aliasListCursoAdd;
    private Integer aliasAnoTurmaNumero;
    private Button aliasBtnSalvarCursos;
    private Button aliasBtnAdd;
    private List<Curso> cursos;
    private Integer numeroSemestre;
    private String nomeCurso;
    private final static String TAG  = "Firelog";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluninho);

        aliasSpinnerCurso = findViewById(R.id.spinnerCursos);
        aliasSpinnerTurma = findViewById(R.id.spinnerTurmas);
        aliasBtnSalvarCursos = findViewById(R.id.btnSalvarCursos);
        aliasBtnAdd = findViewById(R.id.btnAddCurso);
        aliasListCursoAdd = findViewById(R.id.listCursoAdd);
        aliasBtnSeeTurmas = findViewById(R.id.editBtnSeeTurmas);

        cursos = new ArrayList<Curso>();
        turmas = new ArrayList<Turma>();

        final ArrayList cursosConf = new ArrayList();
        
        carregarSpinnerCurso();




        aliasBtnSalvarCursos.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {


         String uid = UUID.randomUUID().toString();
         String token = FirebaseInstanceId.getInstance().getToken();



      /*  Curso curso = new Curso();
         curso.setId(uid);
         curso.setCurso(nomeCurso);
         FirebaseFirestore.getInstance().collection("cursos").document(curso.getId())
                 .set(curso)
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void v) {

                         // Log.i ("Teste \n", documentReference.getId());

                         Intent intent = new Intent(ManterCurso.this, Listar_Curso.class);
                         startActivity(intent);

                     }
                 }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Log.i("Teste \n", e.getMessage());
             }
         });
     }

     Aluno aluno = new Aluno();

*/
     }


 });


 aliasBtnAdd.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {

         if(aliasSpinnerTurma.getSelectedItem() != null) {
             Curso curso = (Curso) aliasSpinnerCurso.getSelectedItem();
             Turma turma = (Turma) aliasSpinnerTurma.getSelectedItem();




         }
         else {
             Toast.makeText(getApplicationContext(), "Escolha uma turma para salva-la!", Toast.LENGTH_SHORT).show();
         }
     }
   });

        aliasBtnSeeTurmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              carregarSpinnerTurma();


            }
        });
    }







  public void carregarSpinnerCurso() {
        FirebaseFirestore.getInstance().collection("cursos")
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
                                Log.d(TAG, nomeCurso);

                            }

                            final ArrayAdapter<Curso> adaptador = new ArrayAdapter <>(getBaseContext(), android.R.layout.simple_spinner_item, cursos);
                            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            aliasSpinnerCurso.setAdapter(adaptador);
                            adaptador.notifyDataSetChanged();

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });


    }

    public void carregarSpinnerTurma(){

        Curso curso = (Curso) aliasSpinnerCurso.getSelectedItem();


        FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas")
                .get()
                .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task <QuerySnapshot> task) {
            if (task.isSuccessful()) {
                turmas.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {


                    // nao sei pq ele nao carrega a lista!!!
                    String id = document.getId();
                    String ano = document.getString("ano");
                    String semestre = document.getString("semestre");

                    Turma u = new Turma();

                    u.setId(id);
                    u.setAno(ano);
                    u.setSemestre(semestre);


                    turmas.add(u);
                    Log.d(TAG, id);

                }
                final ArrayAdapter <Turma> adaptador = new ArrayAdapter <>(getBaseContext(), android.R.layout.simple_spinner_item, turmas);
                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                aliasSpinnerTurma.setAdapter(adaptador);
                adaptador.notifyDataSetChanged();
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());

            }

                    }

                });
    }
}

