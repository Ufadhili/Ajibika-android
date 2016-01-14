package com.unina.ajibika;

import java.util.ArrayList;
import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectsListViewArrayAdapter  extends ArrayAdapter<Projects> {
	  private final Context context;
	  private final ArrayList<Projects> projects;
	  private int lastPosition = -1;
	  public ProjectsListViewArrayAdapter(Context context, ArrayList<Projects> projects) {
	    super(context, R.layout.peoplelistviewrowlayout, projects);
	    this.context = context;
	    this.projects = projects;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View rowView = inflater.inflate(R.layout.projectslistviewrowlayout, parent, false);
	        TextView projects_title = (TextView) rowView.findViewById(R.id.project_title);
	        TextView projects_content = (TextView) rowView.findViewById(R.id.project_description);
	        TextView projects_year = (TextView) rowView.findViewById(R.id.projects_year_funded);
	        TextView projects_status = (TextView) rowView.findViewById(R.id.projects_status);
	        SmartImageView projectimage = (SmartImageView) rowView.findViewById(R.id.project_image);
		    projects_title.setText(projects.get(position).getTitle());
		    projects_content.setText(Html.fromHtml(projects.get(position).getSummary()));
		    projectimage.setImageUrl(projects.get(position).getImage());
		    projects_year.setText(projects.get(position).getYear_funded());
		    projects_status.setText(projects.get(position).getStatus());
		    Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		    rowView.startAnimation(animation);
		    lastPosition = position;
	    return rowView;
	  }
}
