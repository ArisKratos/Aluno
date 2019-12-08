package com.example.alunotcc.Controle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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



public class Activity_Gerenciar_Turmas extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    private Spinner aliasSpinnerCurso;
    private Spinner aliasSpinnerTurma;
    private List<Turma> turmas;
    private List<Turma> turmasSpn;
    private AlertDialog alerta;
    private boolean alunoLogado;
    private String UID;
    private String token;
    private Button aliasBtnSeeTurmas;
    private Button aliasBtnAdd;
    private List<Curso> cursos;
    private ListView aliasListTurmaAdd;
    private String nomeCurso;
    private final static String TAG  = "Firelog";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_turmas);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Gerenciar turmas");     //Titulo para ser exibido na sua Action Bar em frente à seta



        aliasSpinnerCurso = findViewById(R.id.spinnerCursosConf);
        aliasSpinnerTurma = findViewById(R.id.spinnerTurmasConf);

//
//        aliasBtnSeeTurmas = findViewById(R.id.editSeeTurmas);
        aliasBtnAdd = findViewById(R.id.btnSeeMensagens);
        aliasListTurmaAdd = findViewById(R.id.listTurmaAdd);

        aliasSpinnerCurso.setOnItemSelectedListener(this);

        cursos = new ArrayList<Curso>();
        turmas = new ArrayList<Turma>();
        turmasSpn = new ArrayList<Turma>();


        carregarSpinnerCurso();


        UID = UUID.randomUUID().toString();






//        aliasBtnSeeTurmas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                carregarListTurmas();
//
//            }
//        });

        aliasListTurmaAdd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Alerta!");
                builder.setMessage("Deseja mesmo excluir essa turma?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {


                        final Turma turma = turmas.get(position);
                        Curso curso = (Curso) aliasSpinnerCurso.getSelectedItem();


                        FirebaseFirestore.getInstance().collection("alunos").document(token).collection("cursos").document(curso.getId())
                                .collection("turmas").document(turma.getId()).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Activity_Gerenciar_Turmas.this, "Turma excluida com sucesso!", Toast.LENGTH_SHORT).show();
                                        carregarListTurmas();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Activity_Gerenciar_Turmas.this, "Erro ao deletar turma", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(), "Ação cancelada", Toast.LENGTH_SHORT).show();
                    }
                });
                alerta = builder.create();
                alerta.show();
                return true;
            }
        });

        aliasBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (aliasSpinnerTurma.getSelectedItem() != null) {

                    Curso curso = (Curso) aliasSpinnerCurso.getSelectedItem();
                    Turma turma = (Turma) aliasSpinnerTurma.getSelectedItem();

                    token = FirebaseInstanceId.getInstance().getToken();

                    Aluno aluno = new Aluno();
                    aluno.setId(UID);
                    aluno.setToken(token);

                    FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas")
                            .document(turma.getId()).collection("alunos").document(aluno.getId())
                            .set(aluno)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void v) {

                                    // Log.i ("Teste \n", documentReference.getId());

                                    Toast.makeText(getApplicationContext(), "Você se registrou com sucesso!", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Teste \n", e.getMessage());
                        }
                    });


                   FirebaseFirestore.getInstance().collection("alunos").document(token).set(aluno);

                   FirebaseFirestore.getInstance().collection("alunos").document(token).collection("cursos")
                           .document(curso.getId()).set(curso);

                    FirebaseFirestore.getInstance().collection("alunos").document(token).collection("cursos")
                            .document(curso.getId()).collection("turmas").document(turma.getId()).
                            set(turma)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void v) {

                                    // Log.i ("Teste \n", documentReference.getId());

                                    //  Toast.makeText(getApplicationContext(), "Você se registrou com sucesso", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Teste \n", e.getMessage());
                        }

                    });
                } else {
                    Toast.makeText(getApplicationContext(), "escolha uma turmas antes", Toast.LENGTH_SHORT).show();
                }
                carregarListTurmas();
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

    public  void carregarListTurmas(){

        token = FirebaseInstanceId.getInstance().getToken();


      final Curso curso = (Curso) aliasSpinnerCurso.getSelectedItem();

        FirebaseFirestore.getInstance().collection("alunos").document(token).collection("cursos")
                .document(curso.getId()).collection("turmas")
                .get()
                .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task <QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            turmas.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {



                                String id = document.getId();
                                String ano = document.getString("ano");
                                String semestre = document.getString("semestre");
                                String curso = document.getString("curso");

                                Turma u = new Turma();

                                u.setId(id);
                                u.setAno(ano);
                                u.setSemestre(semestre);
                                u.setCurso(curso);


                                turmas.add(u);
                                Log.d(TAG, id);
                            }

                            ArrayAdapter <Turma> adaptador = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, turmas);
                            aliasListTurmaAdd.setAdapter(adaptador);
                            adaptador.notifyDataSetChanged();

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }

    public void carregarSpinnerTurma(){

        final Curso curso = (Curso) aliasSpinnerCurso.getSelectedItem();


        FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas")
                .get()
                .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task <QuerySnapshot> task) {
            if (task.isSuccessful()) {
                turmasSpn.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {



                    String id = document.getId();
                    String ano = document.getString("ano");
                    String semestre = document.getString("semestre");
                    String curso = document.getString("curso");

                    Turma u = new Turma();

                    u.setId(id);
                    u.setAno(ano);
                    u.setSemestre(semestre);
                    u.setCurso(curso);



                    turmasSpn.add(u);
                    Log.d(TAG, id);

                }
                final ArrayAdapter <Turma> adaptador = new ArrayAdapter <>(getBaseContext(), android.R.layout.simple_spinner_item, turmasSpn);
                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                aliasSpinnerTurma.setAdapter(adaptador);
                adaptador.notifyDataSetChanged();
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());

            }

                    }

                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();


        carregarSpinnerTurma();
        carregarListTurmas();




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Activity_Mensagens.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                 //Método para matar a activity e não deixa-lá indexada na pilhagem

                break;
            default:break;
        }
        return true;
    }
}


