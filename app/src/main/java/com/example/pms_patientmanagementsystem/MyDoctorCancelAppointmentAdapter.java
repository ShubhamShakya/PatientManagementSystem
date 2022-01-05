package com.example.pms_patientmanagementsystem;

import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyDoctorCancelAppointmentAdapter extends FirebaseRecyclerAdapter<doctorCancelAppointmentDataFromFirebase,MyDoctorCancelAppointmentAdapter.MyViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyDoctorCancelAppointmentAdapter(@NonNull FirebaseRecyclerOptions<doctorCancelAppointmentDataFromFirebase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull doctorCancelAppointmentDataFromFirebase model) {
        holder.mPatientMail.setText(model.getPatientMail());
        holder.mAppTime.setText(model.getTime());
        holder.mAppDate.setText(model.getDate());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                String temp=firebaseUser.getEmail();
                int i=temp.indexOf('@');
                String doctorAppointmentDatabaseName=temp.substring(0,i);

                DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
                Query query=ref.child(doctorAppointmentDatabaseName).orderByChild("patientMail").equalTo(model.getPatientMail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            dataSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(getClass().getName(),"onCancelled", error.toException());
                    }
                });

                String t=model.getPatientMail();
                int id=t.indexOf('@');
                String doctorAppointmentDatabaseNamePatient=t.substring(0,id);
                Query query1=ref.child(doctorAppointmentDatabaseNamePatient).orderByChild("doctorMail").equalTo(temp);
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            dataSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(getClass().getName(),"onCancelled",error.toException());
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_cancel_appointment,parent,false);
        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mPatientMail,mAppDate,mAppTime;
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mPatientMail=itemView.findViewById(R.id.cancelAppointmentDoctorPatientMail);
            mAppDate=itemView.findViewById(R.id.cancelAppointmentDoctorAppointmentDate);
            mAppTime=itemView.findViewById(R.id.cancelAppointmentDoctorAppointmentTime);
            button=itemView.findViewById(R.id.cancelAppointmentDoctorButton);
        }
    }

}
