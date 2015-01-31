package com.rubick.bechakini;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class UploadPhotoActivity extends ActionBarActivity implements View.OnClickListener{

    Button btnAddPhoto;
    SquareImageView[] ivs;
    Button[] btnRemoves;

    ArrayList<Bitmap> bitmaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        btnAddPhoto = (Button) findViewById(R.id.uploadphoto_btn_AddPhoto);

        ivs = new SquareImageView[6];

        ivs[0] = (SquareImageView) findViewById(R.id.uploadPhoto_iv_0);
        ivs[1] = (SquareImageView) findViewById(R.id.uploadPhoto_iv_1);
        ivs[2] = (SquareImageView) findViewById(R.id.uploadPhoto_iv_2);
        ivs[3] = (SquareImageView) findViewById(R.id.uploadPhoto_iv_3);
        ivs[4] = (SquareImageView) findViewById(R.id.uploadPhoto_iv_4);
        ivs[5] = (SquareImageView) findViewById(R.id.uploadPhoto_iv_5);

        btnRemoves = new Button[6];

        btnRemoves[0] = (Button) findViewById(R.id.uploadPhoto_btnRm_0);
        btnRemoves[1] = (Button) findViewById(R.id.uploadPhoto_btnRm_1);
        btnRemoves[2] = (Button) findViewById(R.id.uploadPhoto_btnRm_2);
        btnRemoves[3] = (Button) findViewById(R.id.uploadPhoto_btnRm_3);
        btnRemoves[4] = (Button) findViewById(R.id.uploadPhoto_btnRm_4);
        btnRemoves[5] = (Button) findViewById(R.id.uploadPhoto_btnRm_5);

        bitmaps = new ArrayList<Bitmap>();


        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        for( Button btn : btnRemoves ){
            btn.setOnClickListener(this);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ok) {

            startActivity(new Intent(UploadPhotoActivity.this, HomeActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void refreshIvs(){

        int i;

        for(i=0; i<bitmaps.size(); ++i){

            ivs[i].setImageBitmap(bitmaps.get(i));
            ivs[i].setVisibility(View.VISIBLE);
            btnRemoves[i].setVisibility(View.VISIBLE);
        }

        for(int j = i; j<6; ++j){
            ivs[j].setVisibility(View.INVISIBLE);
            btnRemoves[j].setVisibility(View.INVISIBLE);
        }

        if(i==6){
            btnAddPhoto.setEnabled(false);
        } else {
            btnAddPhoto.setEnabled(true);
        }
    }

    private void selectImage()
    {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadPhotoActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    //ivPhoto.setImageBitmap(bitmap);
                    bitmaps.add(rotateBitmap(bitmap,90));
                    refreshIvs();

                    String path = android.os.Environment.getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                bitmaps.add(rotateBitmap(thumbnail,-90));
                refreshIvs();

                Log.w("path of image from gallery......******************.........", picturePath + "");
                //ivPhoto.setImageBitmap(thumbnail);
            }
        }
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onClick(View v) {

        for(int i=0; i<bitmaps.size(); ++i){
            if(v == btnRemoves[i]){
                bitmaps.remove(i);
                refreshIvs();
            }
        }

    }
}
