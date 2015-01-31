package com.rubick.bechakini;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;


public class AdDetailActivity extends ActionBarActivity {


    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    ImageView ivSlide0, ivSlide1, ivSlide2;
    TextView tvSlideName0, tvSlideName1, tvSlideName2;
    TextView tvSlidePrice0, tvSlidePrice1, tvSlidePrice2;

    TextView tvPosted, tvDescription, tvLocation;

    Button btnMap;

    double latitude, longitude;
    String sellerPhone;
    String productName, productDescription, productPrice;
    boolean isFav = false;

    private ViewFlipper mViewFlipper;
    private final GestureDetector imageFlingDetector = new GestureDetector(new SwipeImageDetector());
    private final GestureDetector activityFlingDetector = new GestureDetector(new SwipePageDetector());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);

        initGUI();

        initFlipper();
    }

    void initFlipper(){
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                imageFlingDetector.onTouchEvent(event);
                return true;
            }
        });

        tvDescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                activityFlingDetector.onTouchEvent(event);
                return true;
            }
        });

    }


    void initGUI(){

        ivSlide0 = (ImageView) findViewById(R.id.editor_picks_iv0);
        ivSlide1 = (ImageView) findViewById(R.id.editor_picks_iv1);
        ivSlide2 = (ImageView) findViewById(R.id.editor_picks_iv2);


        tvSlideName0 = (TextView) findViewById(R.id.editor_picks_tvName0);
        tvSlideName1 = (TextView) findViewById(R.id.editor_picks_tvName1);
        tvSlideName2 = (TextView) findViewById(R.id.editor_picks_tvName2);

        tvSlidePrice0 = (TextView) findViewById(R.id.editor_picks_tvPrice0);
        tvSlidePrice1 = (TextView) findViewById(R.id.editor_picks_tvPrice1);
        tvSlidePrice2 = (TextView) findViewById(R.id.editor_picks_tvPrice2);


        tvPosted = (TextView) findViewById(R.id.ad_detail_tv_posted);
        tvDescription = (TextView) findViewById(R.id.ad_detail_tv_description);
        tvLocation = (TextView) findViewById(R.id.ad_detail_tv_location);

        btnMap = (Button) findViewById(R.id.ad_detail_btn_map);


        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, APISpecs.FETCH_SINGLE_AD, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseJson) {
                        try {


                            productName = responseJson.getString("title");

                            tvSlideName0.setText(responseJson.getString("title"));
                            tvSlideName1.setText(responseJson.getString("title"));
                            tvSlideName2.setText(responseJson.getString("title"));


                            productPrice = responseJson.getString("price");

                            tvSlidePrice0.setText(responseJson.getString("price"));
                            tvSlidePrice1.setText(responseJson.getString("price"));
                            tvSlidePrice2.setText(responseJson.getString("price"));

                            String posted = "Posted by <b>" + responseJson.getString("seller_name") + "</b>, on <b>" + responseJson.getString("date") + "</b>";

                            tvPosted.setText(Html.fromHtml(posted));

                            String location = "Under <b>" + responseJson.getString("category_name") + "</b> > <b>" + responseJson.getString("subcategory_name") + "</b> at <b>" + responseJson.getString("location_name") + "</b>";
                            tvLocation.setText(Html.fromHtml(location));

                            latitude = Double.parseDouble(responseJson.getString("lat"));
                            longitude = Double.parseDouble(responseJson.getString("lng"));

                            tvDescription.setText(responseJson.getString("description"));

                            productDescription = responseJson.getString("description");



                            sellerPhone = responseJson.getString("phone_no");


                            VolleySingleton.getInstance(getApplicationContext()).getImageLoader().get(responseJson.getString("image_url1"),
                                    ImageLoader.getImageListener(ivSlide0,
                                            R.drawable.ic_wait, R.drawable.ic_error));

                            VolleySingleton.getInstance(getApplicationContext()).getImageLoader().get(responseJson.getString("image_url2"),
                                    ImageLoader.getImageListener(ivSlide1,
                                            R.drawable.ic_wait, R.drawable.ic_error));

                            VolleySingleton.getInstance(getApplicationContext()).getImageLoader().get(responseJson.getString("image_url3"),
                                    ImageLoader.getImageListener(ivSlide2,
                                            R.drawable.ic_wait, R.drawable.ic_error));




                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();

                    }

                });

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(jsonRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ad_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_phone) {

            String url = "tel:" + sellerPhone;
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_sms) {

            Intent it = new Intent(Intent.ACTION_VIEW);
            it.putExtra("sms_body", "Hi, I am interested in your product.");
            it.putExtra("address", sellerPhone);
            it.setType("vnd.android-dir/mms-sms");
            startActivity(it);
            return true;
        }

        if (id == R.id.action_fav) {

            isFav = true;
            invalidateOptionsMenu();
            HardConstants.favCount++;
            return true;
        }

        if (id == R.id.action_share) {

            BitmapDrawable drawable = (BitmapDrawable) ivSlide0.getDrawable();
            Bitmap finalBitmap = drawable.getBitmap();
            saveImage(finalBitmap);
            Intent shareIntent = new Intent();
            shareIntent.setType("image/");
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this " + productName + " available at " + productPrice + ". " + productDescription );

            String root = Environment.getExternalStorageDirectory().toString();
            String imagePath = root
                    + "/saved_images" +"/a.jpg" ;

            File imageFileToShare = new File(imagePath);

            Uri uri = Uri.fromFile(imageFileToShare);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(shareIntent, "Share this product"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem switchButton = menu.findItem(R.id.action_fav);

        if(isFav){
            switchButton.setIcon(R.drawable.ic_star_solid);
            switchButton.setTitle("Favourite");
        }else{
            switchButton.setIcon(R.drawable.ic_star_holo);
            switchButton.setTitle("Add to favourites");
        }

        return super.onPrepareOptionsMenu(menu);

    }


    class SwipeImageDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(AdDetailActivity.this, R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(AdDetailActivity.this, R.anim.left_out));
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(AdDetailActivity.this, R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(AdDetailActivity.this, R.anim.right_out));
                    mViewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    class SwipePageDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    startActivity(new Intent(AdDetailActivity.this, AdDetailActivity.class));
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    finish();
                    return true;


                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    startActivity(new Intent(AdDetailActivity.this, AdDetailActivity.class));
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    finish();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fName = "a"+".jpg";
        File file = new File (myDir, fName);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            System.out.println("a");
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
