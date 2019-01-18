package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.UploadImageRequest;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.UUID;

public class ImageEditorActivity extends Activity {
    private File imageFile;
    private Bitmap image;
    private ImageView imageView;
    private FloatingActionButton send;
    private FloatingActionButton cancel;
    private boolean deleteFile = true;
    private User target;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        target = (User) intent.getSerializableExtra("target");
        setContentView(R.layout.activity_image_editor);
        imageView = findViewById(R.id.image_editor_image_view);
        send = findViewById(R.id.upload_photo);
        cancel = findViewById(R.id.cancel_upload_photo);
        String imagePath = getIntent().getStringExtra("IMAGE_PATH");
        imageFile = new File(imagePath);

        if (imageFile.exists()) {
            image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imageView.setImageBitmap(image);
        }

        cancel.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        send.setOnClickListener(view -> {
            uploadImage();
            deleteFile = false;
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (deleteFile && imageFile.exists())
            imageFile.delete();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void uploadImage() {
        if (image == null)
            return;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        TcpManagerService.getInstance().submitMessage(new UploadImageRequest(
                UserPreferencesService.getInstance(getApplication()).getAuthenticationKey(),
                UUID.randomUUID().toString(),
                ".jpg",
                Base64.getEncoder().encodeToString(data),
                target,
                UserPreferencesService.getInstance(getApplication()).getCurrentUser()));
    }
}
