package com.unina.ajibika;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsListViewArrayAdapter  extends ArrayAdapter<News> {
	  private final Context context;
	  private final ArrayList<News> newslist;
	  private int lastPosition = -1;
	  public NewsListViewArrayAdapter(Context context, ArrayList<News> news) {
	    super(context, R.layout.newslistviewrowlayout, news);
	    this.context = context;
	    this.newslist = news;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView;
	    Date date =new Date();
	    // it comes out like this 2013-08-31 15:55:22 so adjust the date format
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss",Locale.US);
		
	    try {
			date = df.parse(newslist.get(position).getPublication_date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	    long publishedtime = date.getTime();
	    CharSequence timePassedString = DateUtils.getRelativeTimeSpanString(publishedtime, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
	    if(newslist.get(position).getImage_url().equalsIgnoreCase("null")){
	            rowView = inflater.inflate(R.layout.newslistviewrowlayoutwithoutimage, parent, false);
	    	    TextView news_title = (TextView) rowView.findViewById(R.id.news_title);
	    	    TextView published = (TextView) rowView.findViewById(R.id.published);
		        TextView news_content = (TextView) rowView.findViewById(R.id.news_content);
		        news_title.setText(newslist.get(position).getTitle());
			    news_content.setText(Html.fromHtml(newslist.get(position).getMessage()));
			    published.setText(timePassedString);
			   
	    }
	    else{
	    	    rowView = inflater.inflate(R.layout.newslistviewrowlayout, parent, false);
	    	    TextView news_title = (TextView) rowView.findViewById(R.id.news_title);
	    	    TextView published = (TextView) rowView.findViewById(R.id.published);
		        TextView news_content = (TextView) rowView.findViewById(R.id.news_content);
		        SmartImageView imageView = (SmartImageView) rowView.findViewById(R.id.newsimage);
		        news_title.setText(newslist.get(position).getTitle());
		        published.setText(timePassedString);
			    news_content.setText(Html.fromHtml(newslist.get(position).getMessage()));
			    imageView.setImageUrl(newslist.get(position).getImage_url());
	    
	    }
	

		    
		    Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		    rowView.startAnimation(animation);
		    lastPosition = position;
	    return rowView;
	  }
}
