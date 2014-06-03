package comau.blha303.b3radiostuff;

import java.io.IOException;
import java.util.Iterator;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener  {

	private MediaPlayer player;
	private boolean restart;
	private boolean starting;
	private boolean looping;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment())
					.commit();
		}
		
		player = new MediaPlayer();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		Spinner spinner;
		TextView storage;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			
			storage = (TextView) rootView.findViewById(R.id.urlstorage);
			spinner = (Spinner) rootView.findViewById(R.id.spinner);
			spinner.setSelection(((MainActivity)getActivity()).getSpinner());
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					((MainActivity)getActivity()).saveSpinner(position);
					String a = parent.getItemAtPosition(position).toString();
					if (a.contains("Hive365") || a.contains("H365")) {
						((MainActivity)getActivity()).showH365Controls(view);
					} else {
						((MainActivity)getActivity()).hideH365Controls(view);
					}
					TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(' ');
					splitter.setString(a);
					String url = getLastElement(splitter.iterator());
					storage.setText(url);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					storage.setText(getString(R.string.default_url));
				}
				
				public String getLastElement(Iterator<String> itr) {
				    String lastElement = itr.next();
				    while(itr.hasNext()) {
				        lastElement=itr.next();
				    }
				    return lastElement;
				}
				
			});
			
			return rootView;
		}
	}
	
	public int getSpinner() {
		SharedPreferences prefs = getSharedPreferences("comau.blha303.b3radiostuff", Context.MODE_PRIVATE);
		return prefs.getInt("spinnerpos", 0);
	}
	
	public void saveSpinner(int position) {
		SharedPreferences prefs = getSharedPreferences("comau.blha303.b3radiostuff", Context.MODE_PRIVATE);
		Editor edit = prefs.edit();
		edit.putInt("spinnerpos", position);
		edit.commit();
	}
	
	public void startClick(View v) {
		((Button)findViewById(R.id.start)).setEnabled(false);
		starting = true;
		TextView error = (TextView)findViewById(R.id.already_playing);
		TextView urlstorage = (TextView)findViewById(R.id.urlstorage);
		try {
			player.setDataSource(urlstorage.getText().toString());
			error.setText(R.string.not_ready);
			error.setVisibility(View.VISIBLE);
			player.setOnPreparedListener(new OnPreparedListener() {
				TextView error = (TextView)findViewById(R.id.already_playing);
				public void onPrepared(MediaPlayer mp) {
					error.setVisibility(View.INVISIBLE);
					player.start();
					starting = false;
					((Button)findViewById(R.id.start)).setEnabled(true);
					((Button)findViewById(R.id.stop)).setEnabled(true);
				}
			});
			player.prepareAsync();
		} catch (IllegalArgumentException e) {
			error.setText(R.string.invalid_url);
			error.setVisibility(View.VISIBLE);
		} catch (IllegalStateException e) {
			if (restart) {
				error.setText(R.string.starting);
				error.setVisibility(View.VISIBLE);
			} else {
				restart = true;
				stopClick(v);
			}
		} catch (IOException e) {
			error.setText(R.string.invalid_url);
			error.setVisibility(View.VISIBLE);
		}
		restart = false;
	}
	
	public void stopClick(View v) {
		TextView error = (TextView)findViewById(R.id.already_playing);
		if (starting) error.setText(R.string.starting);
		else error.setVisibility(View.INVISIBLE);
		if (player.isPlaying()) {
			player.stop();
			player.release();
			player = new MediaPlayer();
		}
		if (looping) return;
		if (restart) startClick(v);
		else ((Button)findViewById(R.id.start)).setEnabled(true);
	}
	
	public void choonClick(View v) {
		TextView error = (TextView)findViewById(R.id.already_playing);
		error.setText(R.string.not_yet_implemented);
		error.setVisibility(View.VISIBLE);
	}
	
	public void poonClick(View v) {
		TextView error = (TextView)findViewById(R.id.already_playing);
		error.setText(R.string.not_yet_implemented);
		error.setVisibility(View.VISIBLE);		
	}
	
	public void djftwClick(View v) {
		TextView error = (TextView)findViewById(R.id.already_playing);
		error.setText(R.string.not_yet_implemented);
		error.setVisibility(View.VISIBLE);
	}
	
	public void showH365Controls(View v) {
		Button choon = (Button)findViewById(R.id.choon);
		if (choon.getVisibility() == View.VISIBLE) return;
		Button poon = (Button)findViewById(R.id.poon);
		Button djftw = (Button)findViewById(R.id.djftw);
		choon.setVisibility(View.VISIBLE);
		poon.setVisibility(View.VISIBLE);
		djftw.setVisibility(View.VISIBLE);
	}
	
	public void hideH365Controls(View v) {
		Button choon = (Button)findViewById(R.id.choon);
		if (choon.getVisibility() == View.INVISIBLE) return;
		Button poon = (Button)findViewById(R.id.poon);
		Button djftw = (Button)findViewById(R.id.djftw);
		choon.setVisibility(View.INVISIBLE);
		poon.setVisibility(View.INVISIBLE);
		djftw.setVisibility(View.INVISIBLE);		
	}
//	
//	public void togAddURLControls(View v) {
//		View title = findViewById(R.id.addurl);
//		View name = findViewById(R.id.inpname);
//		View url = findViewById(R.id.inpurl);
//		View save = findViewById(R.id.url_save);
//		if (title.getVisibility() == View.INVISIBLE) {
//			title.setVisibility(View.VISIBLE);
//			name.setVisibility(View.VISIBLE);
//			url.setVisibility(View.VISIBLE);
//			save.setVisibility(View.VISIBLE);
//		} else if (title.getVisibility() == View.VISIBLE) {
//			title.setVisibility(View.INVISIBLE);
//			name.setVisibility(View.INVISIBLE);
//			url.setVisibility(View.INVISIBLE);
//			save.setVisibility(View.INVISIBLE);
//		}
//	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (player.isPlaying()) {
			player.stop();
		}
	}


	@Override
	public void onClick(View v) {
		if (v instanceof Button) {
			if (((Button)v).equals((Button)findViewById(R.id.start))) {
				startClick(v);
			} else if (((Button)v).equals((Button)findViewById(R.id.stop))) {
				stopClick(v);
			} else if (((Button)v).equals((Button)findViewById(R.id.choon))) {
				choonClick(v);
			} else if (((Button)v).equals((Button)findViewById(R.id.poon))) {
				poonClick(v);
			} else if (((Button)v).equals((Button)findViewById(R.id.djftw))) {
				djftwClick(v);
			}
//			else if (((Button)v).equals((Button)findViewById(R.id.show_add))) {
//				togAddURLControls(v);
//			}
		}
	}

}
