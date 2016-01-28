package students.molecular.podobip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.graphics.Typeface;


/**
 * Created by kilosakeyrocker on 28/01/16.
 */
public class ItemAdapter extends BaseAdapter {
    String [] nameItem;
    Context context;
    String [] textItem;
    String fontPath = "JLSDataGothicC_NC.otf";

    private static LayoutInflater inflater=null;
    public ItemAdapter(Context context, String[] headers, String[] content) {
        // TODO Auto-generated constructor stub
        this.context = context;
        nameItem = headers;
        textItem = content;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return nameItem.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View rowView;

        rowView = inflater.inflate(R.layout.item_view, null);
        TextView header=(TextView) rowView.findViewById(R.id.textView1);
        TextView text =(TextView) rowView.findViewById(R.id.textView2);

        header.setText(nameItem[position]);
        text.setText(textItem[position]);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        text.setTypeface(tf);

        return rowView;
    }
}
