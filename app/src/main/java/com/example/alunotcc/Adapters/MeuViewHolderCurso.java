package com.example.alunotcc.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.alunotcc.R;

public class MeuViewHolderCurso extends  RecyclerView.ViewHolder{

    final TextView curso;

    final Button notificationHorario;

    final Button notification;

    final TextView turma;
    //  ItemClickListener itemClickListener;


    public MeuViewHolderCurso(View view) {
        super(view);

        curso = (TextView)
                view.findViewById(R.id.nome_CursoConf);

       notificationHorario = (Button)
                view.findViewById(R.id.editBtnNotificationHorario);

        notification = (Button)
                view.findViewById(R.id.editBtnNotification);

        turma= (TextView)
                view.findViewById(R.id.MsgHora);


        // itemView.setOnClickListener(this);

    }
}
