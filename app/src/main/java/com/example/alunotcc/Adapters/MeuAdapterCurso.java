package com.example.alunotcc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alunotcc.Modelo.Curso;
import com.example.alunotcc.Modelo.Mensagem;
import com.example.alunotcc.Modelo.Turma;
import com.example.alunotcc.R;

import java.util.List;

public class MeuAdapterCurso {
    private List <Curso> cursos;
    private List <Turma> turmas;
    public Context context;
    private View.OnClickListener listener;

    public MeuAdapterCurso(List <Turma> turmas, List <Curso> cursos, Context context) {

        this.cursos = cursos;
        this.turmas = turmas;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.activity_view_holder_cursos_conf, parent, false);

        MeuViewHolderMsg holder = new MeuViewHolderMsg(view);

        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        MeuViewHolderMsg holder = (MeuViewHolderMsg) viewHolder;
        final Curso curso = cursos.get(position);
        final Turma turma = turmas.get(position);


        holder.professor.setText(mensagem.getProfessor());
        holder.turma.setText(mensagem.getTurmaMensagem());
        holder.data.setText(mensagem.getDataMensagem());

      /*  holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String gMsg = mensagens.get(position).getMensagem();


                Intent intent = new Intent(context,Activity_MsgCompleta.class);
                intent.putExtra("Mensagem", gMsg);

                context.startActivity(intent);
            }
        });*/

      /*  holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if(mensagens.get(position).getMensagem().equals("3")){
                    Intent intent = new Intent(v.getContext(), Activity_MsgCompleta.class);
                    context.startActivity(intent);
                }
            }
        });
    }*/

    }
    @Override
    public int getItemCount() {

        return mensagens == null ? 0 : mensagens.size();
    }
    public void  setOnclickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }

    }
}
