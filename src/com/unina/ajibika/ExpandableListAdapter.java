package com.unina.ajibika;
import com.unina.ajibika.R;


import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Activity context;
    private Map<DrawerItem, List<DrawerItem>> menuCollection;
    private List<DrawerItem> navmenus;
 
    public ExpandableListAdapter(Activity context, List<DrawerItem> navmenus,
            Map<DrawerItem, List<DrawerItem>> menuCollection) {
        this.context = context;
        this.menuCollection = menuCollection;
        this.navmenus = navmenus;
    }
 
    public Object getChild(int groupPosition, int childPosition) {
        return menuCollection.get(navmenus.get(groupPosition)).get(childPosition);
    }
 
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
     
     
    public View getChildView(final int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        final String navmenu = ((DrawerItem) getChild(groupPosition, childPosition)).getItemName();
        LayoutInflater inflater = context.getLayoutInflater();
         Log.i("-----------------","Getting here");
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }
         
        TextView item = (TextView) convertView.findViewById(R.id.navmenu);
         
       
         
        item.setText(navmenu);
        return convertView;
    }
 
    public int getChildrenCount(int groupPosition) {
        return menuCollection.get(navmenus.get(groupPosition)).size();
    }
 
    public Object getGroup(int groupPosition) {
        return navmenus.get(groupPosition);
    }
 
    public int getGroupCount() {
        return navmenus.size();
    }
 
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String menuTitle = ((DrawerItem)getGroup(groupPosition)).getItemName();
        int imgresource = ((DrawerItem)getGroup(groupPosition)).getImgResID();
        ImageView icon,menuiconexpand = null ;
        TextView itemName = null;
        if (convertView == null) {
        	
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
            
            
          
      
         }
        itemName = (TextView) convertView
                .findViewById(R.id.menutitle);
        
        icon = (ImageView) convertView.findViewById(R.id.menuicon);
		icon.setImageResource(imgresource);
		
		menuiconexpand =(ImageView) convertView.findViewById(R.id.menuiconexpand);
		if (getChildrenCount(groupPosition) > 0) {
			menuiconexpand.setVisibility(View.VISIBLE);
			menuiconexpand.setImageResource(
	                isExpanded ? R.drawable.ic_action_collapse: R.drawable.ic_action_expand);
	    } else {
	    	menuiconexpand.setVisibility(View.GONE);
	    }
        
        
         itemName.setText(menuTitle);
        return convertView;
    }
 
    public boolean hasStableIds() {
        return true;
    }
 
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
  

}