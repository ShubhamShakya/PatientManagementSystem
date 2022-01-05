package com.example.pms_patientmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
public class MyPatientDoctorAdapter extends FirebaseRecyclerAdapter<patientDataFromFirebase,MyPatientDoctorAdapter.MyViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public MyPatientDoctorAdapter(@NonNull FirebaseRecyclerOptions<patientDataFromFirebase> options){
        super(options);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_book_appointment,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position,@NonNull patientDataFromFirebase model) {

        //patientDataFromFirebase user=list.get(position);
        holder.mName.setText("Dr."+model.getName());
        holder.mSpecialization.setText(model.getSpecialization());
        holder.mEmail.setText(model.getEmail());

        holder.mBookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent=new Intent(v.getContext(),AfterClickingBook.class);
                intent.putExtra("doctorMail",model.getEmail());
                intent.putExtra("doctorName",model.getName());

                    v.getContext().startActivity(intent);

            }
        });

    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mName,mEmail,mSpecialization;
        Button mBookAppointmentButton;
        Context context;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            context=itemView.getContext();
            mName=itemView.findViewById(R.id.tvPatientDoctorName);
            mEmail=itemView.findViewById(R.id.tvPatientDoctorEmail);
            mSpecialization=itemView.findViewById(R.id.tvPatientDoctorSpecilization);

            mBookAppointmentButton=itemView.findViewById(R.id.buttonBookAppointment);
        }
    }

}
