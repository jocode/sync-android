package com.example.jocode.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.jocode.sqlite.data.LawyersContract;


/**
 * Existe un adaptador especial para el manejo de bases de datos llamado CursorAdapter.
 * Esta clase permite poblar una lista a través de un cursor.
 */
public class LawyersCursorAdapter extends CursorAdapter {

    private Context context;

    public LawyersCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.context = context;
    }

    /**
     * newView() Infla cada view de la lista
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_lawyer, parent, false);
    }

    /**
     * bindView() Es el encargado de poblar la lista con los datos del cursor
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);

        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex(LawyersContract.LawyerEntry.NAME));
        String avatarUri = cursor.getString(cursor.getColumnIndex(LawyersContract.LawyerEntry.AVATAR_URI));

        // Setup.
        nameText.setText(name);

        Glide.with(context)
                .load(Uri.parse("file:///android_asset/" + avatarUri))
                .asBitmap()
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatarImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        avatarImage.setImageDrawable(drawable);
                    }
                });


    }
}
