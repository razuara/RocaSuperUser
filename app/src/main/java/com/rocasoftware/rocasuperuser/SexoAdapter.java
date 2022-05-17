package com.rocasoftware.rocasuperuser;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SexoAdapter extends FirebaseRecyclerAdapter<SexoModel,SexoAdapter.myViewHolder> {

    public SexoAdapter(@NonNull FirebaseRecyclerOptions<SexoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull SexoModel model) {
        holder.nombre.setText(model.getNombre());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sexo_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView nombre;

        public myViewHolder(View itemView)
        {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombreTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //String idConductor = email.getText().toString();
                    int position = getAdapterPosition();
                    String idSexo = getRef(position).getKey();
                    Intent intent = new Intent(itemView.getContext(), SexoEditarActivity.class);
                    intent.putExtra("idSexo",idSexo);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }

}
