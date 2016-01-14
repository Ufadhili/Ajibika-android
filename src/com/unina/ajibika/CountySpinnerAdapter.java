package com.unina.ajibika;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CountySpinnerAdapter extends ArrayAdapter<String>
    {
        private Activity context;
        List<String> countyname = null;

        public CountySpinnerAdapter(Activity context, int resource,List<String> counties)
        {
            super(context, resource, counties);
            this.context = context;
            this.countyname = counties;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) 
        {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        	View v = super.getDropDownView(position, convertView,parent);

            ((TextView) v).setGravity(Gravity.CENTER);
            return v;   
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {  

           
        	// This view starts when we click the spinner.
            View row = convertView;
            if(row == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                row = inflater.inflate(R.layout.county_spinner_layout, parent, false);
            }

           
            TextView countyItem = (TextView) row.findViewById(R.id.countyName);
            if(countyname.get(position)!= null)
            {   
            	 if(countyItem != null)
            		 countyItem.setText(countyname.get(position));
            }

            return row;
        }
}

