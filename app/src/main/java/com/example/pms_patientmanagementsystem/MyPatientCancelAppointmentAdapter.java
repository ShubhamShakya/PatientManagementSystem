package com.example.pms_patientmanagementsystem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyPatientCancelAppointmentAdapter extends FirebaseRecyclerAdapter<patientAppointmentDataFromFirebase,MyPatientCancelAppointmentAdapter.MyViewHolder> {

    String TAG=getClass().getName();
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyPatientCancelAppointmentAdapter(@NonNull FirebaseRecyclerOptions<patientAppointmentDataFromFirebase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull patientAppointmentDataFromFirebase model) {
        holder.docName.setText("Dr."+model.getDoctorName());
        holder.docEmail.setText(model.getDoctorMail());
        holder.appDate.setText(model.getDate());
        holder.appTime.setText(model.getTime());

        holder.cancelAppointmentPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                String temp=firebaseUser.getEmail();
                int i=temp.indexOf('@');
                 String patientAppointmentDatabaseName=temp.substring(0,i);

                Query query= ref.child(patientAppointmentDatabaseName).orderByChild("doctorMail").equalTo(model.getDoctorMail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            dataSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(TAG,"onCancelled",error.toException());
                    }
                });
                String t=model.getDoctorMail();
                int id=t.indexOf('@');
                String patientAppointmentDatabaseNameDoctor=t.substring(0,id);
                Query query1=ref.child(patientAppointmentDatabaseNameDoctor).orderByChild("patientMail").equalTo(temp);
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            dataSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG,"on Cancelled",error.toException());
                    }
                });

                Toast.makeText(v.getContext(), "Your Appointment Successfully Cancelled",Toast.LENGTH_LONG).show();
            }
        });
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_cancel_appointment,parent,false);
        return new MyViewHolder(v);
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView docName,docEmail,appDate,appTime;
        Button cancelAppointmentPatientButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            docName=itemView.findViewById(R.id.cancelAppointmentPatientDoctorEmail);
            docEmail=itemView.findViewById(R.id.cancelAppointmentPatientDoctorName);
            appDate=itemView.findViewById(R.id.cancelAppointmentPatientAppointmentDate);
            appTime=itemView.findViewById(R.id.cancelAppointmentPatientAppointmentTime);
            cancelAppointmentPatientButton=itemView.findViewById(R.id.cancelAppointmentPatientButton);
        }
    }
}
