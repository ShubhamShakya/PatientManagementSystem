package com.example.pms_patientmanagementsystem;

import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyPatientPreviousAppointmentAdapter extends FirebaseRecyclerAdapter<previousAppointmentPatientDataFromFirebase,MyPatientPreviousAppointmentAdapter.MyViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyPatientPreviousAppointmentAdapter(@NonNull FirebaseRecyclerOptions<previousAppointmentPatientDataFromFirebase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull previousAppointmentPatientDataFromFirebase model) {
        holder.mDoctorMail.setText(model.getDoctorMail());
        holder.mDisease.setText(model.getDisease());
        holder.mAppDate.setText(model.getDate());
        holder.mPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ViewPrescription.class);
                intent.putExtra("prescription",model.getPrescription());
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_previous_appointment,parent,false);
        return new MyViewHolder(v);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mDoctorMail,mAppDate,mDisease,mPrescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mDoctorMail=itemView.findViewById(R.id.previousAppointmentPatientDoctorMail);
            mAppDate=itemView.findViewById(R.id.previousAppointmentPatientAppointmentDate);
            mDisease=itemView.findViewById(R.id.previousAppointmentPatientDisease);
            mPrescription=itemView.findViewById(R.id.previousAppointmentPatientViewPrescription);
        }
    }
}
