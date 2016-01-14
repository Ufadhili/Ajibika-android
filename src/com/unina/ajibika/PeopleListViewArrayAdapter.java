package com.unina.ajibika;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PeopleListViewArrayAdapter  extends ArrayAdapter<Person> {
	  private final Context context;
	  private final List<Person> personlist;
	  private int lastPosition = -1;
	  public PeopleListViewArrayAdapter(Context context, List<Person> persons) {
	    super(context, R.layout.peoplelistviewrowlayout, persons);
	    this.context = context;
	    this.personlist = persons;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View rowView = inflater.inflate(R.layout.peoplelistviewrowlayout, parent, false);
	        TextView person_title = (TextView) rowView.findViewById(R.id.person_title);
	        TextView person_position = (TextView) rowView.findViewById(R.id.person_position);
	        TextView person_subtitle = (TextView) rowView.findViewById(R.id.person_subtitle);
	        
	        SmartImageView personimage = (SmartImageView) rowView.findViewById(R.id.personimage);
		    person_title.setText(personlist.get(position).title+" "+personlist.get(position).person_name);
		    person_position.setText(personlist.get(position).getPosition_name());
		    person_subtitle.setText(personlist.get(position).getSubtitle());
		    personimage.setImageUrl(personlist.get(position).getImage_url());
            Log.i("Person image",personlist.get(position).getImage_url());
		    Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		    rowView.startAnimation(animation);
		    lastPosition = position;
		   
	    return rowView;
	  }
}
