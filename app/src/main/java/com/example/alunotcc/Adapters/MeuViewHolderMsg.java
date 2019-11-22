package com.example.alunotcc.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.alunotcc.R;


public class MeuViewHolderMsg extends RecyclerView.ViewHolder {

        final TextView mensagem;
        final TextView professor;
        final TextView data;
        final TextView turma;
      //  ItemClickListener itemClickListener;


        public MeuViewHolderMsg(View view) {
            super(view);
            mensagem = (TextView)
                    view.findViewById(R.id.mensagem_Prof);
            professor = (TextView)
                    view.findViewById(R.id.nome_Prof);

            data = (TextView)
                    view.findViewById(R.id.dataMsg);

            turma= (TextView)
                    view.findViewById(R.id.turmaMsg);


            // itemView.setOnClickListener(this);

        }

}
