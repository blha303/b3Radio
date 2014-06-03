package comau.blha303.b3radiostuff;

import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener  {

	private MediaPlayer player;
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    public void startClick(View v) {
//    	((Button)v).setEnabled(false);
    	TextView error = (TextView)findViewById(R.id.already_playing);
    	try {
    		player.setDataSource(((EditText)findViewById(R.id.editText1)).getText().toString());
        	player.prepareAsync();
        	error.setText(R.string.not_ready);
        	error.setVisibility(View.VISIBLE);
        	player.setOnPreparedListener(new OnPreparedListener() {
        		TextView error = (TextView)findViewById(R.id.already_playing);
                public void onPrepared(MediaPlayer mp) {
                	error.setVisibility(View.INVISIBLE);
                    player.start();
                }
            });
    	} catch (IllegalArgumentException e) {
            error.setText(R.string.invalid_url);
            error.setVisibility(View.VISIBLE);
        } catch (IllegalStateException e) {
        	error.setText(R.string.already_playing);
            error.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            error.setText(R.string.invalid_url);
            error.setVisibility(View.VISIBLE);
        }
    }
    
    public void stopClick(View v) {
    	TextView error = (TextView)findViewById(R.id.already_playing);
    	error.setVisibility(View.INVISIBLE);
    	if (player.isPlaying()) {
            player.stop();
            player.release();
            player = new MediaPlayer();
        }
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
    	Button h365 = (Button)findViewById(R.id.h365show);
    	Button choon = (Button)findViewById(R.id.choon);
    	Button poon = (Button)findViewById(R.id.poon);
    	Button djftw = (Button)findViewById(R.id.djftw);
    	h365.setText(R.string.hive_button_hide);
    	h365.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideH365Controls(v);
			}
    		
    	});
    	choon.setVisibility(View.VISIBLE);
    	poon.setVisibility(View.VISIBLE);
    	djftw.setVisibility(View.VISIBLE);
    }
    
    public void hideH365Controls(View v) {
    	Button h365 = (Button)findViewById(R.id.h365show);
    	Button choon = (Button)findViewById(R.id.choon);
    	Button poon = (Button)findViewById(R.id.poon);
    	Button djftw = (Button)findViewById(R.id.djftw);
    	h365.setText(R.string.hive_button);
    	h365.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showH365Controls(v);
			}
    		
    	});
    	choon.setVisibility(View.INVISIBLE);
    	poon.setVisibility(View.INVISIBLE);
    	djftw.setVisibility(View.INVISIBLE);    	
    }
    
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
			} else if (((Button)v).equals((Button)findViewById(R.id.h365show))) {
				showH365Controls(v);
			} else if (((Button)v).equals((Button)findViewById(R.id.choon))) {
				choonClick(v);
			} else if (((Button)v).equals((Button)findViewById(R.id.poon))) {
				poonClick(v);
			} else if (((Button)v).equals((Button)findViewById(R.id.djftw))) {
				djftwClick(v);
			}
		}
	}

}
