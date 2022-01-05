package com.example.pms_patientmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MyDoctorScheduledAppointmentAdapter extends FirebaseRecyclerAdapter<doctorAppointmentDataFromFirebase,MyDoctorScheduledAppointmentAdapter.MyViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyDoctorScheduledAppointmentAdapter(@NonNull FirebaseRecyclerOptions<doctorAppointmentDataFromFirebase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull doctorAppointmentDataFromFirebase model) {
        holder.mPatientMail.setText(model.getPatientMail());
        holder.mAppointmentDate.setText(model.getDate());
        holder.mAppointmentTime.setText(model.getTime());
        holder.mDisease.setText(model.getDisease());

        holder.mBtnItemDoctorScheduledAppointmentConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),GivePrescription.class);
                intent.putExtra("patientMail",model.getPatientMail());
                intent.putExtra("appDate",model.getDate());
                intent.putExtra("appTime",model.getTime());
                intent.putExtra("disease",model.getDisease());
                intent.putExtra("doctorMail", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_scheduled_appointment,parent,false);
        return new MyViewHolder(v);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView mPatientMail,mAppointmentDate,mAppointmentTime,mDisease;
    Button mBtnItemDoctorScheduledAppointmentConfirm;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mPatientMail=itemView.findViewById(R.id.scheduledAppointmentDoctorAppointmentEmail);
            mAppointmentTime= itemView.findViewById(R.id.scheduledAppointmentDoctorAppointmentTime);
            mAppointmentDate=itemView.findViewById(R.id.scheduledAppointmentDoctorAppointmentDate);
            mDisease=itemView.findViewById(R.id.scheduledAppointmentDoctorDisease);
            mBtnItemDoctorScheduledAppointmentConfirm=itemView.findViewById(R.id.btnItemDoctorScheduledAppointmentConfirm);
        }
    }
}


