package de.charlestons_inn.rig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Lennox on 10/07/2015.
 */
public class SuchAdapter extends ArrayAdapter<String> {
    public SuchAdapter(Context context, String[] Namen) {
        super(context,R.layout.item_layout_list,Namen);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflat_row= LayoutInflater.from(getContext());
        View row= inflat_row.inflate(R.layout.item_layout_list,parent,false);
        String one_item=getItem(position);
        TextView item_text= (TextView)row.findViewById(R.id.item_text);
        item_text.setText(one_item);
        return row;
    }
}
