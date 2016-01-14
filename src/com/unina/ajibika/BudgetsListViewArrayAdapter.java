package com.unina.ajibika;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.unina.ajibika.BillsListViewArrayAdapter.DownloadFileFromURL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BudgetsListViewArrayAdapter  extends ArrayAdapter<Budgets> {
	  private final Context context;
	  private final ArrayList<Budgets> budgetslist;
	  private int lastPosition = -1;
	  // button to show progress dialog
	    Button btnShowProgress;
	 
		  // Progress Dialog
	    private ProgressDialog pDialog;
	 
	    // Progress dialog type (0 - for Horizontal progress bar)
	    public static final int progress_bar_type = 0;
	 
	    // File url to download
	    private static String file_url,file_name ;
	    public BudgetsListViewArrayAdapter(Context context, ArrayList<Budgets> budgets) {
	    super(context, R.layout.budgetslistviewrowlayout, budgets);
	    this.context = context;
	    this.budgetslist = budgets;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View rowView = inflater.inflate(R.layout.budgetslistviewrowlayout, parent, false);
	        file_url=budgetslist.get(position).getFile_url();
	        File file=new File(file_url);
	        String[] fileoriginal=file.getName().split(Pattern.quote("?"));
	        file_name=fileoriginal[0]; 
	        // show progress bar button
	        btnShowProgress = (Button) rowView.findViewById(R.id.downloadbutton);
	        TextView budgets_title = (TextView) rowView.findViewById(R.id.budgets_title);
	        budgets_title.setText(budgetslist.get(position).getTitle());
		    Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		    rowView.startAnimation(animation);
		    lastPosition = position;
		    /**
	         * Show Progress bar click event
	         * */
	        btnShowProgress.setOnClickListener(new View.OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	                // starting new Async Task
	                new DownloadFileFromURL().execute(file_url);
	            }
	        });
	    return rowView;
	  }
	  
	  
	  /**
	   * Background Async Task to download file
	   * */
	  class DownloadFileFromURL extends AsyncTask<String, String, String> {
	   
	      /**
	       * Before starting background thread
	       * Show Progress Bar Dialog
	       * */
	      @Override
	      protected void onPreExecute() {
	          super.onPreExecute();
	          /**
	    	   * Showing Dialog
	    	   * */
	          pDialog = new ProgressDialog(getContext());
	          pDialog.setMessage("Downloading file to "+Environment.getExternalStorageDirectory() + "/budgets. Please wait...");
	          pDialog.setIndeterminate(false);
	          pDialog.setMax(100);
	          pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	          pDialog.setCancelable(true);
	          pDialog.show();
	      }
	   
	      /**
	       * Downloading file in background thread
	       * */
	      @Override
	      protected String doInBackground(String... f_url) {
	          int count;
	          try {
	              URL url = new URL(f_url[0]);
	              URLConnection conection = url.openConnection();
	              conection.connect();
	              // getting file length
	              int lenghtOfFile = conection.getContentLength();
	   
	              // input stream to read file - with 8k buffer
	              InputStream input = new BufferedInputStream(url.openStream(), 8192);
	           // create a File object for the parent directory
	              File folder = new File(Environment.getExternalStorageDirectory() + "/budgets");
	              boolean success = true;
	              if (!folder.exists()) {
	                  //Toast.makeText(MainActivity.this, "Directory Does Not Exist, Create It", Toast.LENGTH_SHORT).show();
	                  success = folder.mkdir();
	              }
	              // have the object build the directory structure, if needed.
	             
	              // create a File object for the output file
	              File outputFile = new File(folder,file_name);
	             
	              // Output stream to write file
	              OutputStream output = new FileOutputStream(outputFile);
	   
	              byte data[] = new byte[1024];
	   
	              long total = 0;
	   
	              while ((count = input.read(data)) != -1) {
	                  total += count;
	                  // publishing the progress....
	                  // After this onProgressUpdate will be called
	                  publishProgress(""+(int)((total*100)/lenghtOfFile));
	   
	                  // writing data to file
	                  output.write(data, 0, count);
	              }
	   
	              // flushing output
	              output.flush();
	   
	              // closing streams
	              output.close();
	              input.close();
	   
	          } catch (Exception e) {
	        	  //Toast.makeText(context, "File Download Failed", Toast.LENGTH_SHORT).show();
	              Log.e("Error: ", e.getMessage());
	          }
	   
	          return null;
	      }
	   
	      /**
	       * Updating progress bar
	       * */
	      protected void onProgressUpdate(String... progress) {
	          // setting progress percentage
	          pDialog.setProgress(Integer.parseInt(progress[0]));
	     }
	   
	      /**
	       * After completing background task
	       * Dismiss the progress dialog
	       * **/
	      @Override
	      protected void onPostExecute(String file_url) {
	          // dismiss the dialog after the file was downloaded
	    	  pDialog.dismiss();
	   
	          // Displaying downloaded image into image view
	          // Reading image path from sdcard
	          //String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
	          // setting downloaded into image view
	         // my_image.setImageDrawable(Drawable.createFromPath(imagePath));
	      }
	   
	  }
}
