package com.example.admin.todosoft;


        import android.content.res.Resources;
        import android.widget.ArrayAdapter;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import java.util.ArrayList;

/**
 * Created by admin on 23.11.2015.
 */
public class TodoAdapter extends ArrayAdapter<TodoTask> {

    //private final Context context;
    //private final  values;
    private int resource;
    private LayoutInflater inflater;

    public TodoAdapter(Context context,int r ,ArrayList values) {

        super(context, r , values);

        resource = r;
        inflater = LayoutInflater.from( context );
    }

    /*
    private static class ViewHolder {
        private TextView itemView;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
/*
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.list_row, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.ItemView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

*/
        //create a new view of the layout and inflate it in the row
        convertView = (RelativeLayout) inflater.inflate( resource, null );

        TextView tvDate = (TextView)convertView.findViewById(R.id.date);
        TextView tvDesc = (TextView)convertView.findViewById(R.id.desc);

        TodoTask item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            tvDate.setText(String.format("%s", item.date));
            tvDesc.setText(String.format("%s", item.description));

        }

        return convertView;
    }

    /*
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView textViewDate = (TextView) rowView.findViewById(R.id.date);
        TextView textViewDesc = (TextView) rowView.findViewById(R.id.desc);
        textViewDesc.setText(values[position]);
        // Изменение иконки для Windows и iPhone
        /*
        String s = values[position];
        if (s.startsWith("Windows7") || s.startsWith("iPhone")
                || s.startsWith("Solaris")) {
            imageView.setImageResource(R.drawable.no);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }

        return rowView;
    }
    */
}
