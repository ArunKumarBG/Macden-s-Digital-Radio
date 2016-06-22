package com.arunkumarbgcbe.macdensdigitalradio.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arunkumarbgcbe.macdensdigitalradio.R;
import com.arunkumarbgcbe.macdensdigitalradio.controls.Controls;
import com.arunkumarbgcbe.macdensdigitalradio.data.Category;
import com.arunkumarbgcbe.macdensdigitalradio.data.Track;
import com.arunkumarbgcbe.macdensdigitalradio.network.ConnectionDetector;
import com.arunkumarbgcbe.macdensdigitalradio.service.MusicService;
import com.arunkumarbgcbe.macdensdigitalradio.service.SongService;
import com.arunkumarbgcbe.macdensdigitalradio.util.AppController;
import com.arunkumarbgcbe.macdensdigitalradio.util.CategoryAdapter;
import com.arunkumarbgcbe.macdensdigitalradio.util.Config;
import com.arunkumarbgcbe.macdensdigitalradio.util.CustomDialog;
import com.arunkumarbgcbe.macdensdigitalradio.util.LocalSharedStorage;
import com.arunkumarbgcbe.macdensdigitalradio.util.PlayerConstants;
import com.arunkumarbgcbe.macdensdigitalradio.util.SubApplication;
import com.arunkumarbgcbe.macdensdigitalradio.util.UtilFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,RecyclerView.OnItemTouchListener {



    public static final String TAG = HomeActivity.class.getName();

    SubApplication application;

    String FONT_COND_BOLD = "FONT_COND_BOLD";
    String FONT_REVICONS = "FONT_REVICONS";
    String FONT_NARROW_BOLD = "FONT_NARROW_BOLD";
    String FONT_NARROW = "FONT_NARROW";
    String FONT_DEFAULT_BOLD = "FONT_DEFAULT_BOLD";

    LocalSharedStorage sharedPref;
    ConnectionDetector connDetector;
    CustomDialog customDialog;

    protected String StrResponse = null;

    TextView textView_title, tv_track_title ,tv_no_network_title;

    RecyclerView listView_Category;

    CardView card_view_musiccontorl, card_view_network;

    ImageView iv_player_control;

    NetworkImageView iv_track_image;

    ArrayList<Track.TrackDetails> mArrAllTrackDetails;

    ArrayList<String> mArrCategory;

    Track mObjTrack;

    public static MusicService musicSrv;
    public static Intent playIntent;
    public static boolean musicBound = false;

    private int mColumns=3;
    private int mWidthHeight=120;

    /*Button btnPlayer;
    static Button btnPause, btnPlay, btnNext, btnPrevious;
    Button btnStop;*/


   // static LinearLayout linearLayoutPlayingSong;

    static TextView playingSong;

    static ImageView imageViewAlbumArt;

    private boolean paused = false, playbackPaused = false;

    static Context context;

    private RelativeLayout parentPanel;


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context =this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);


        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setHomeButtonEnabled(false);

        application = new SubApplication();

        SpannableStringBuilder spannableString = application.getSpannableMessage(getApplicationContext(), FONT_REVICONS,
                getResources().getString(R.string.app_name));
        ab.setTitle(spannableString);

        // Testing purpose
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        int density = getResources().getDisplayMetrics().densityDpi;
        String toastMsg= "";
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                mColumns =2;
                mWidthHeight = 150;
                Log.i("DENCITY", "LDPI - " +toastMsg);
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                mColumns =2;
                mWidthHeight = 150;
                Log.i("DENCITY", "MDPI - " +toastMsg);
                break;
            case DisplayMetrics.DENSITY_HIGH:
                switch(screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        mColumns =4;
                        mWidthHeight = 200;
                        toastMsg = "Large screen";
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                        mColumns =3;
                        mWidthHeight = 180;
                        toastMsg = "Normal screen";
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_SMALL:
                        mColumns =2;
                        mWidthHeight = 180;
                        toastMsg = "Small screen";
                        break;
                    default:
                        mColumns =3;
                        mWidthHeight = 200;
                        toastMsg = "Screen size is neither large, normal or small";
                }
                Log.i("DENCITY", "HDPI - " +toastMsg);
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                switch(screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        mColumns =4;
                        mWidthHeight = 220;
                        toastMsg = "Large screen";
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                        mColumns =3;
                        mWidthHeight = 200;
                        toastMsg = "Normal screen";
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_SMALL:
                        mColumns =2;
                        mWidthHeight = 200;
                        toastMsg = "Small screen";
                        break;
                    default:
                        mColumns =4;
                        mWidthHeight = 220;
                        toastMsg = "Screen size is neither large, normal or small";
                }
                Log.i("DENCITY", "XHDPI - " +toastMsg );
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                switch(screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        mColumns =4;
                        mWidthHeight = 250;
                        toastMsg = "Large screen";
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                        mColumns =3;
                        mWidthHeight = 200;
                        toastMsg = "Normal screen";
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_SMALL:
                        mColumns =2;
                        mWidthHeight = 200;
                        toastMsg = "Small screen";
                        break;
                    default:
                        mColumns =4;
                        mWidthHeight = 200;
                        toastMsg = "Screen size is neither large, normal or small";
                }
                Log.i("DENCITY", "XXHDPI - " +toastMsg);
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                switch(screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        mColumns =4;
                        mWidthHeight = 250;
                        toastMsg = "Large screen";
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                        mColumns =3;
                        mWidthHeight = 200;
                        toastMsg = "Normal screen";
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_SMALL:
                        mColumns =2;
                        mWidthHeight = 200;
                        toastMsg = "Small screen";
                        break;
                    default:
                        mColumns =4;
                        mWidthHeight = 200;
                        toastMsg = "Screen size is neither large, normal or small";
                }
                Log.i("DENCITY", "XXXHDPI - " +toastMsg);
                break;
            default:
                switch(screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        mColumns =4;
                        mWidthHeight = 200;
                        toastMsg = "Large screen";
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                        mColumns =4;
                        mWidthHeight = 200;
                        toastMsg = "Normal screen";
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_SMALL:
                        mColumns =2;
                        mWidthHeight = 200;
                        toastMsg = "Small screen";
                        break;
                    default:
                        mColumns =4;
                        mWidthHeight = 200;
                        toastMsg = "Screen size is neither large, normal or small";
                }
                Log.i("DENCITY", "default - " +toastMsg);
                break;
        }

        initialize();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onResume() {
        if (connDetector.checkInternetConnection()) {
            card_view_network.setVisibility(View.GONE);
            displayMusicPlayerControl();
            getRequest();
        } else {
            setNoNetwork();
            // showMessage(getResources().getString(R.string.network_error));
        }

        super.onResume();
    }

    public static void changeUI(){
        updateUI();
        //changeButton();
    }

   /* public static void changeButton() {
        if(PlayerConstants.SONG_PAUSED){
            btnPause.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
        }else{
            btnPause.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.GONE);
        }
    }*/

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    public static void updateUI() {
        try{
            ArrayList<Track.TrackDetails> mTrackList = UtilFunctions.getSongsList();
            int mTrackIndex = PlayerConstants.SONG_NUMBER;
            playingSong.setText(mTrackList.get(mTrackIndex).getTitle() + "-" + mTrackList.get(mTrackIndex).getGenre());
            String albumUrl = mTrackList.get(mTrackIndex).getGenre();
            URL url = new URL(albumUrl);
            Bitmap albumArt = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            if(albumArt != null){
                imageViewAlbumArt.setBackgroundDrawable(new BitmapDrawable(albumArt));
            }else{
                imageViewAlbumArt.setBackground(context.getResources().getDrawable(R.drawable.ic_audiotrack_gray));
            }
            //linearLayoutPlayingSong.setVisibility(View.VISIBLE);
        }catch(Exception e){}
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showExitConfirmation();
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       /* //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            doShareAppURL();
            Log.e(TAG,"Calling nav_share");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void doShareAppURL() {

        Log.e(TAG,"doShareAppURL");
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Download "+ getString(R.string.app_name)+ " and listen to music ");
        share.putExtra(Intent.EXTRA_TEXT, "Download "+ getString(R.string.app_name)+ " and listen to music \n"+  Config.APP_SHARE_URL);

        startActivity(Intent.createChooser(share, "Share "+getString(R.string.app_name)));
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    private void initialize() {
        getViews();
        initObject();
        sharedPref = new LocalSharedStorage(this);
        connDetector = new ConnectionDetector(this);
        customDialog = new CustomDialog(HomeActivity.this);
    }

    private void initObject() {
        mObjTrack = new Track();

        mArrAllTrackDetails = new ArrayList<Track.TrackDetails>();

        mArrCategory = new ArrayList<String>();
    }


    private void getViews() {

        parentPanel = (RelativeLayout) findViewById(R.id.parentPanel);

        playingSong = (TextView) findViewById(R.id.textNowPlaying);
        application.setTypeface(playingSong, FONT_REVICONS, context);

        textView_title = (TextView) findViewById(R.id.textView_title);
        application.setTypeface(textView_title, FONT_REVICONS, getApplicationContext());

        imageViewAlbumArt = (ImageView) findViewById(R.id.imageViewAlbumArt);

        tv_track_title = (TextView) findViewById(R.id.tv_track_title);
        application.setTypeface(tv_track_title, FONT_REVICONS, getApplicationContext());

        tv_no_network_title = (TextView) findViewById(R.id.tv_no_network_title);
        application.setTypeface(tv_no_network_title, FONT_REVICONS, getApplicationContext());

        card_view_musiccontorl = (CardView) findViewById(R.id.card_view_musiccontorl);

        card_view_network = (CardView) findViewById(R.id.card_view_network);

        iv_player_control = (ImageView) findViewById(R.id.iv_player_control);

        iv_track_image = (NetworkImageView) findViewById(R.id.iv_track_image);

        listView_Category = (RecyclerView) findViewById(R.id.listView_Category);


        /*linearLayoutPlayingSong = (LinearLayout) findViewById(R.id.linearLayoutPlayingSong);

        btnPlayer = (Button) findViewById(R.id.btnMusicPlayer);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);*/
    }

    private void setListeners() {
       /* btnPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,AudioPlayerActivity.class);
                startActivity(i);
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.playControl(getApplicationContext());
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.pauseControl(getApplicationContext());
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Controls.nextControl(getApplicationContext());
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Controls.previousControl(getApplicationContext());
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SongService.class);
                stopService(i);
                linearLayoutPlayingSong.setVisibility(View.GONE);
            }
        });*/
        imageViewAlbumArt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,AudioPlayerActivity.class);
                startActivity(i);
            }
        });
    }

    private void displayMusicPlayerControl() {
        // TODO Auto-generated method stub
        Log.d(TAG,"displayMusicPlayerControl");
        /*if (musicSrv != null) {
            card_view_musiccontorl.setVisibility(View.VISIBLE);
            Log.d(TAG,"displayMusicPlayerControl MusicSrv not null");
            if (sharedPref.getTrackTitle() != null) {
                tv_track_title.setText(sharedPref.getTrackTitle());
            } else {
                Log.d(TAG,"getTrackTitle is null");
                card_view_musiccontorl.setVisibility(View.GONE);

            }

            if (sharedPref.getTrackImageUrl() != null) {
                Picasso.with(context).load(sharedPref.getTrackImageUrl())
                        .error(R.drawable.ic_audiotrack_black).placeholder(R.drawable.ic_audiotrack_black)
                        .into(iv_track_image);
            }
            if (musicSrv.isPng()) {
                iv_player_control.setImageResource(R.drawable.ic_pause_black);
            } else {
                iv_player_control.setImageResource(R.drawable.ic_play_arrow_black);
            }

        } else {
            Log.d(TAG,"displayMusicPlayerControl MusicSrv is null");
            card_view_musiccontorl.setVisibility(View.GONE);

        }*/
        boolean isServiceRunning = UtilFunctions.isServiceRunning(MusicService.class.getName(), getApplicationContext());
        if (isServiceRunning) {
            updateUI();
        }else{
           // linearLayoutPlayingSong.setVisibility(View.GONE);
        }
    }

    private void setNoNetwork() {
        // TODO Auto-generated method stub
        card_view_network.setVisibility(View.VISIBLE);
        textView_title.setVisibility(View.GONE);
        listView_Category.setVisibility(View.GONE);
        card_view_musiccontorl.setVisibility(View.GONE);
        Snackbar.make(parentPanel, getString(R.string.network_error), Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getRequest();
                    }
                }).show();
    }

    private String getRequest() {
        // TODO Auto-generated method stub
        String tag_json_obj = "json_obj_req";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, Config.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StrResponse = response.toString();
                Log.d(TAG, "StrResponse : " + StrResponse);
                mObjTrack=UtilFunctions.jsonParesing(getApplicationContext(),StrResponse);
                mArrAllTrackDetails = mObjTrack.getTrackDetails();
                setUpCategoryList();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
            }
        }) {

        };
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 16 * 1024 * 1024);
        queue = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
        queue.start();

        // clear all volley caches.
        queue.add(new ClearCacheRequest(cache, null));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr, tag_json_obj);
        return StrResponse;
    }

    private void setUpCategoryList() {
        // TODO Auto-generated method stub
        //displayMusicPlayerControl();
        textView_title.setVisibility(View.VISIBLE);
        listView_Category.setVisibility(View.VISIBLE);
        // Remove Duplicate
        ArrayList<String> tempArrCategory = new ArrayList<String>();
        if (mArrAllTrackDetails != null) {
            tempArrCategory.clear();
            for (int i = 0; i < mArrAllTrackDetails.size(); i++) {
                tempArrCategory.add(mArrAllTrackDetails.get(i).getGenre());
            }
        }
        ArrayList<Category> category = new ArrayList<Category>();
        if (tempArrCategory != null) {
            // add elements to tempArrCategory, including duplicates
            HashSet<String> hs = new HashSet<String>();
            hs.addAll(tempArrCategory);
            mArrCategory.clear();
            mArrCategory.addAll(hs);

            Category category1;
            for(int i =0;i<mArrCategory.size();i++)
            {
                category1 = new Category();
                category1.setCategory(mArrCategory.get(i));
                for (int j = 0; j < mArrAllTrackDetails.size(); j++) {
                    if(mArrAllTrackDetails.get(j).getGenre().equalsIgnoreCase(category1.getCategory()))
                    {
                        category1.setIcon(mArrAllTrackDetails.get(j).getArtwork_url());
                    }
                }


                category.add(category1);
            }

        }

        if (mArrCategory != null) {
            // Log.d(TAG, "mArrCategory.size() : " + mArrCategory.size());
            GridLayoutManager gridLayoutManagerVertical =
                    new GridLayoutManager(this,
                            mColumns, //The number of Columns in the grid
                            LinearLayoutManager.VERTICAL,
                            false);
            LinearLayoutManager  mLayoutManager = new LinearLayoutManager(getApplicationContext());
            listView_Category.setHasFixedSize(true);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            listView_Category.setLayoutManager(gridLayoutManagerVertical);
            listView_Category.setItemAnimator(new DefaultItemAnimator());
            listView_Category.setAdapter(new CategoryAdapter(category));

        }
        displayMusicPlayerControl();
    }



    private void resetplayer() {
        // TODO Auto-generated method stub
        if (musicSrv != null) {
            if (musicSrv.isPng()) {
                musicSrv.pausePlayer();
                iv_player_control.setImageResource(R.drawable.ic_play_arrow_black);
            } else {
                musicSrv.go();
                iv_player_control.setImageResource(R.drawable.ic_pause_black);
            }
            if (sharedPref.getTrackTitle() != null) {
                tv_track_title.setText(sharedPref.getTrackTitle());
            }
            ImageLoader mImageLoader;
            if (sharedPref.getTrackImageUrl() != null) {
                // Get the ImageLoader through your singleton class.
                mImageLoader = AppController.getInstance().getImageLoader();
                mImageLoader.get(sharedPref.getTrackImageUrl(), ImageLoader.getImageListener(iv_track_image,
                        R.drawable.ic_audiotrack_black, R.drawable.ic_audiotrack_black));
                iv_track_image.setImageUrl(sharedPref.getTrackImageUrl(), mImageLoader);
            }
            card_view_musiccontorl.setVisibility(View.VISIBLE);

        } else {
            card_view_musiccontorl.setVisibility(View.GONE);

        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            if (musicSrv == null) {
                bindService(playIntent, musicConnection, BIND_AUTO_CREATE);
                startService(playIntent);
            }
        }
    }

    // connect to the service
    public ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void showExitConfirmation() {
        if (!isFinishing()) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getSupportActionBar().getThemedContext());

            SpannableStringBuilder spannableString = application.getSpannableMessage(HomeActivity.this, FONT_REVICONS,
                    getResources().getString(R.string.exit_confirmation));

            builder1.setMessage(spannableString);
            builder1.setCancelable(false);
            builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    try {
                        sharedPref.saveTrackId(null);
                        sharedPref.saveTrackTitle(null);
                        sharedPref.saveTrackImageUrl(null);
                        stopService(playIntent);
                        finish();
                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.cancel();
                }
            });

            final AlertDialog alert11 = builder1.create();
            alert11.show();
            TextView textView = (TextView) alert11.findViewById(android.R.id.message);
            application.setTypeface(textView, FONT_REVICONS, mContext);
            textView.setTextSize(17);

            Button positive_button = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
            positive_button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            positive_button.setTextColor(getResources().getColor(R.color.dialog_pressed_true_grey));
            application.setTypeface(positive_button, FONT_REVICONS, mContext);

            Button negative_button = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
            negative_button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            negative_button.setTextColor(getResources().getColor(R.color.dialog_pressed_false_grey));
            application.setTypeface(negative_button, FONT_REVICONS, mContext);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    public class CategoryAdapter
            extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

        private final List<Category> mValues;
        private int pos;
        public CategoryAdapter(List<Category> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_listitem, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            pos =position;
            holder.mItem = mValues.get(position);
            ImageLoader mImageLoader;
            if (mValues.get(position).getIcon() != null) {
                mImageLoader = AppController.getInstance().getImageLoader();
                mImageLoader.get(mValues.get(position).getIcon(), ImageLoader.getImageListener(holder.mImageView,
                        R.drawable.ic_library_music_gray, R.drawable.ic_library_music_gray));
                holder.mImageView.setImageUrl(mValues.get(position).getIcon(), mImageLoader);
            }
           // holder.mImageView.setImageDrawable(getDrawable(R.drawable.ic_library_music_gray));
            holder.mContentView.setText(mValues.get(position).getCategory());
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public final View mView;
            public final NetworkImageView mImageView;
            public final TextView mContentView;
            public Category mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (NetworkImageView) view.findViewById(R.id.icon);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(android.app.ActionBar.LayoutParams.MATCH_PARENT, mWidthHeight);
                mImageView.setLayoutParams(layoutParams);
                mContentView = (TextView) view.findViewById(R.id.Itemname);
                application.setTypeface(mContentView, FONT_REVICONS, context);
                mView.setOnClickListener(this);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick " + getPosition());
                if (connDetector.checkInternetConnection()) {
                    card_view_network.setVisibility(View.GONE);
                    displayMusicPlayerControl();
                    String title = null;

                    if (mArrCategory != null) {
                        title = mArrCategory.get(getPosition());
                        Log.d(TAG,"title : " +title);
                        PlayerConstants.CATEGORY = title;
                    }
                    if (mObjTrack != null && title != null) {
                        Intent intent = new Intent(HomeActivity.this, TrackListActivity.class);
                        intent.putExtra(getString(R.string.key_title), title);
                        intent.putExtra(getString(R.string.key_download_url), mObjTrack);
                        intent.putExtra(getString(R.string.key_artwork_url), mValues.get(getPosition()).getIcon());
                        startActivity(intent);
                    }
                } else {
                    setNoNetwork();
                    // showMessage(getResources().getString(R.string.network_error));
                }
            }
        }
    }

}
