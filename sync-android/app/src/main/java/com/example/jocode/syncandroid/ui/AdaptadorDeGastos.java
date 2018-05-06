package com.example.jocode.syncandroid.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jocode.syncandroid.R;

/**
 * Adaptador del recycler view
 */
public class AdaptadorDeGastos extends RecyclerView.Adapter<AdaptadorDeGastos.ExpenseViewHolder> {

    private Cursor cursor;
    private Context context;

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView monto;
        public TextView etiqueta;
        public TextView fecha;


        public ExpenseViewHolder(View view) {
            super(view);
            monto = (TextView) view.findViewById(R.id.monto);
            etiqueta = (TextView) view.findViewById(R.id.etiqueta);
            fecha = (TextView) view.findViewById(R.id.fecha);

        }
    }

    public AdaptadorDeGastos(Context context) {
        this.context= context;

    }

    @Override
    public int getItemCount() {
        if (cursor!=null)
            return cursor.getCount();
        return 0;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_layout, viewGroup, false);
        return new ExpenseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        String monto;
        String etiqueta;
        String fecha;

        monto = cursor.getString(1);
        etiqueta = cursor.getString(2);
        fecha = cursor.getString(3);

        viewHolder.monto.setText("$"+monto);
        viewHolder.etiqueta.setText(etiqueta);
        viewHolder.fecha.setText(fecha);
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }


}
