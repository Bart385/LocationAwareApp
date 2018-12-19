package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.Messages.ImageMessage;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;

import java.io.File;
import java.util.Date;

public class ImageEditorActivity extends Activity {
    private TcpManagerService tcpManagerService;

    private File imageFile;
    private Bitmap image;
    private ImageView imageView;
    private FloatingActionButton send;
    private FloatingActionButton cancel;
    private boolean deleteFile = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_image_editor);
        imageView = findViewById(R.id.image_editor_image_view);
        send = findViewById(R.id.upload_photo);
        cancel = findViewById(R.id.cancel_upload_photo);
        tcpManagerService = TcpManagerService.getInstance(error -> {

                },
                message -> {

                });
        String imagePath = getIntent().getStringExtra("IMAGE_PATH");
        imageFile = new File(imagePath);

        if (imageFile.exists()) {
            image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imageView.setImageBitmap(image);
        }

        cancel.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });

        send.setOnClickListener(view -> {
            uploadImage();
            deleteFile = false;
            finish();
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (deleteFile && imageFile.exists())
            imageFile.delete();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private void uploadImage() {
        if (image == null)
            return;
        tcpManagerService.submitMessage(new ImageMessage(Constants.USERNAME, ".jpg", new Date(), image));
    }
}
