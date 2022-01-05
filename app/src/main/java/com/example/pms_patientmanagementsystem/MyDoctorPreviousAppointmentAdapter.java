package com.example.pms_patientmanagementsystem;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyDoctorPreviousAppointmentAdapter extends FirebaseRecyclerAdapter<previousAppointmentDoctorDataFromFirebase,MyDoctorPreviousAppointmentAdapter.MyViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyDoctorPreviousAppointmentAdapter(@NonNull FirebaseRecyclerOptions<previousAppointmentDoctorDataFromFirebase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull previousAppointmentDoctorDataFromFirebase model) {
        holder.mPatientMail.setText(model.getPatientMail());
        holder.mDisease.setText(model.getDisease());
        holder.mAppDate.setText(model.getDate());
        holder.mViewPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext().getApplicationContext(),ViewPrescription.class);
                intent.putExtra("prescription",model.getPrescription());
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_previous_appointment,parent,false);
        return new MyViewHolder(v);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mPatientMail,mAppDate,mDisease,mViewPrescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mPatientMail=itemView.findViewById(R.id.previousAppointmentDoctorPatientMail);
            mAppDate=itemView.findViewById(R.id.previousAppointmentDoctorAppointmentDate);
            mDisease=itemView.findViewById(R.id.previousAppointmentDoctorDisease);
            mViewPrescription=itemView.findViewById(R.id.previousAppointmentDoctorViewPrescription);
        }
    }
}
