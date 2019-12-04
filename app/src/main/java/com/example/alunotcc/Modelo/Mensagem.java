package com.example.alunotcc.Modelo;

import androidx.annotation.NonNull;

public class Mensagem {

        public Mensagem(String id, String idRemetente,String mensagem, String remetenteMsg, String turmaAnoMensagem, String semestreMensagem, String dataMensagem , long timeMessage,
                        boolean paraTodos, boolean mudancaHorario){

            this.id = id;
            this.idRemetente = idRemetente;
            this.mensagem = mensagem;
            this.remetenteMsg = remetenteMsg;
            this.turmaAnoMensagem = turmaAnoMensagem;
            this.semestreMensagem = semestreMensagem;
            this.dataMensagem = dataMensagem;
            this.timeMassage = timeMessage;
            this.paraTodos = paraTodos;
            this.mudancaHorario = mudancaHorario;
        }

        private String id;
        private String idRemetente;
        private long timeMassage;
        private String mensagem;

        private String remetenteMsg;



        public boolean isMudancaHorario() {
            return mudancaHorario;
        }

        public void setMudancaHorario(boolean mudancaHorario) {
            this.mudancaHorario = mudancaHorario;
        }

        private String turmaAnoMensagem;
        private String semestreMensagem;
        private String dataMensagem;
        private boolean paraTodos;
        private boolean mudancaHorario;
        public String getDataMensagem()
        {
            return dataMensagem;
        }

        public void setDataMensagem(String dataMensagem)
        {
            this.dataMensagem = dataMensagem;
        }

        public String getTurmaAnoMensagem()
        {
            return turmaAnoMensagem;
        }

        public void setTurmaAnoMensagem(String turmaAnoMensagem) {
            this.turmaAnoMensagem = turmaAnoMensagem;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }


        public long getTimeMassage() {
            return timeMassage;
        }

        public void setTimeMassage(long timeMassage) {
            this.timeMassage = timeMassage;
        }

        public String getRemetenteMsg() {
            return remetenteMsg;
        }

        public void setRemetenteMsg(String adminMsg) {
            this.remetenteMsg = adminMsg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSemestreMensagem() {
            return semestreMensagem;
        }

        public void setSemestreMensagem(String semestreMensagem) {
            this.semestreMensagem = semestreMensagem;
        }
        public boolean isParaTodos() {
            return paraTodos;
        }

        public void setParaTodos(boolean paraTodos) {
            this.paraTodos = paraTodos;
        }

        public String getIdRemetente() {
            return idRemetente;
        }

        public void setIdRemetente(String idAdmin) {
            this.idRemetente = idAdmin;
        }


    @NonNull
    @Override
    public String toString() {
        return " De: " + remetenteMsg + "\n Mensagem: " + mensagem + "\n Para: " +
                turmaAnoMensagem +"/"+semestreMensagem +"\n Data: " +
                dataMensagem + " Hora: " +timeMassage;
    }
}

