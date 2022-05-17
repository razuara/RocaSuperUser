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

public class EstadoAdapter extends FirebaseRecyclerAdapter<EstadoModel,EstadoAdapter.myViewHolder>
{
    public EstadoAdapter(@NonNull FirebaseRecyclerOptions<EstadoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EstadoAdapter.myViewHolder holder, int position, @NonNull EstadoModel model) {
        holder.nombre.setText(model.getNombre());
    }

    @NonNull
    @Override
    public EstadoAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estado_item,parent,false);
        return new EstadoAdapter.myViewHolder(view);
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


                    int position = getAdapterPosition();
                    String idEstado = getRef(position).getKey();
                    Intent intent = new Intent(itemView.getContext(), EstadoEditarActivity.class);
                    intent.putExtra("idEstado",idEstado);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
