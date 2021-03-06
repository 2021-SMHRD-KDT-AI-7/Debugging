package com.example.project_02;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class Fragment_tab2_camera extends Fragment {

    Button btnClose;
    Button btnOK;
    ImageView imgPlant;
    ImageView btnCapture;
    TextureView cameraPreView;
    AppCompatDialog progressDialog;

    private RequestQueue queue;
    private String currentPhotoPath;
    private Bitmap bitmap;
    private String imageString;

    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
    MainActivity mainactivity;
    BottomSheetDialog bottomSheetDialog;


    //????????? ???????????? ????????? ??????
    PreviewView previewView;
    ProcessCameraProvider processCameraProvider;
    int lensFacing = CameraSelector.LENS_FACING_FRONT;
    ImageCapture imageCapture;
    Fragment BaumannFragment;

    int test;

    // ????????? ??????????????? ?????????
    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        matrix.setScale(-1, 1);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
        // Canvas ?????? ??????????????? ??????????????? ????????? ?????? ??????????????? ???????????? ????????????
        //        Canvas canvas = new Canvas(rotatedBitmap);
        //        canvas.drawBitmap(original, 5.0f, 0.0f, null);

        return rotatedBitmap;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainactivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainactivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        queue = Volley.newRequestQueue(getActivity());


        //bottomsheetDialog ?????? ??????
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater1.inflate(R.layout.capture, null, false);
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(view);

        //fragment3.xml
        View v = inflater.inflate(R.layout.fragment_tab2_camera, container, false);

        //cameraPreView = v.findViewById(R.id.cameraPreView);
        previewView = v.findViewById(R.id.previewView);
        btnCapture = v.findViewById(R.id.btnCapture);

        imgPlant = view.findViewById(R.id.user_img);
        btnOK = view.findViewById(R.id.btnOK);
        btnClose = view.findViewById(R.id.btnClose);

        try {
            processCameraProvider = ProcessCameraProvider.getInstance(getActivity()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //????????? ?????? ??????
        //??? ????????? ?????????: ?????? ??? ????????? ????????? ????????? ????????? ??????
//        if (allPermissionsGranted()) {
//            bindPreview();
//            bindImageCapture();
//        } else {
//            ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
//        }

        //??? ????????? ????????? ?????? -> ?????? ?????? ??? ????????? ??????
        if (allPermissionsGranted()) {
            bindPreview();
            bindImageCapture();
        } else {
            ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);

            //?????? ???????????? 1??? ?????? ????????? ?????????
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindPreview();
                    bindImageCapture();
                }
            }, 1000);
        }


        btnCapture.setOnClickListener(view13 -> {
            imageCapture.takePicture(ContextCompat.getMainExecutor(getActivity()),
                    new ImageCapture.OnImageCapturedCallback() {
                        @Override
                        public void onCaptureSuccess(@NonNull ImageProxy image) {
                            try {
                                @SuppressLint({"UnsafeExperimentalUsageError", "UnsafeOptInUsageError"})
                                Image mediaImage = image.getImage();
                                bitmap = ImageUtil.mediaImageToBitmap(mediaImage);

                                Log.d("MainActivity", Integer.toString(bitmap.getWidth())); //4128
                                Log.d("MainActivity", Integer.toString(bitmap.getHeight())); //3096

                                // ????????? ?????? ????????? ??????
                                bitmap = rotateBitmap(bitmap, 180);

                                imgPlant.setImageBitmap(bitmap);

                                //close() ???????????? Too many acquire images. Close image to be able to process next ????????????
                                //????????? ???????????? ?????? ????????? ????????? ????????? ????????? ??????
                                image.close();
                                mediaImage.close();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                                super.onCaptureSuccess(image);
                        }
                    }
            );
            bottomSheetDialog.show();
        });

        btnOK.setOnClickListener(view1 -> {
            Toast.makeText(getActivity().getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
            processCameraProvider.unbindAll(); //????????? ????????? ??????
            //Volley ???????????? ????????? bitmap -> base64??? ???????????? ??????

            //????????? ??????????????? ??????????????? ????????????
            progressON(getActivity(), "Loading...");
            sendImage();
        });

        btnClose.setOnClickListener(view12 -> {
            Toast.makeText(getActivity().getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
        });
        return v;
    }

    //????????? ??????????????? ??????
    private void sendImage() {

        //????????? ???????????? byte??? ?????? -> base64????????? ??????
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] imageBytes = baos.toByteArray();
        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //base64????????? ????????? ????????? ???????????? ???????????? ????????? ??????
        String flask_url = "http://172.30.1.47:5000/sendFrame";
        StringRequest request = new StringRequest(Request.Method.POST, flask_url,
                response -> {
                    Log.d("cameraFragment", response);

                    // base64??? Bitmap ???????????? ?????? ?????????.
                    byte[] decodedString = Base64.decode(response, Base64.DEFAULT);
                    Bitmap b = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    // ????????? ????????? ??????
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("a", b);
                    BaumannFragment bau = new BaumannFragment();
                    bau.setArguments(bundle);

                    //????????? ???
                    progressOFF();
                    mainactivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, bau).commit();


//                    imgPlant.setImageBitmap(b);
//                    bottomSheetDialog.show();

//                 if (response.equals("true")) {
//                        Log.d("cameraFragment", "Uploaded Successful");
//                    } else {
//                        Log.d("cameraFragment", "Some error occurred!");
//                    }
                },
                error -> {
                    Log.d("cameraFragment", "Some error occurred -> " + error);
                    progressOFF();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("image", imageString);

                return params;
            }
        };
        queue.add(request);
    }

    void bindPreview() {
        previewView.setScaleType(PreviewView.ScaleType.FIT_CENTER);
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();
        Preview preview = new Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3) //????????? ?????? ??????
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        processCameraProvider.bindToLifecycle(this, cameraSelector, preview);
    }

    void bindImageCapture() {
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();
        imageCapture = new ImageCapture.Builder()
                .build();
        processCameraProvider.bindToLifecycle(this, cameraSelector, imageCapture);
    }

    // ?????? ??????
    private boolean allPermissionsGranted() {

        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        processCameraProvider.unbindAll(); //????????? ????????? ??????
    }

    public void progressSET(String message) {

        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }

        TextView tv_message = progressDialog.findViewById(R.id.tv_message);
        if (!TextUtils.isEmpty(message)) {
            tv_message.setText(message);
        }

    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void progressON(Activity activity, String message) {

        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {

            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();

        }

        ImageView img_progress = progressDialog.findViewById(R.id.img_progress);
        //GIF???????????? ???????????? ?????? Glide ??????????????? ??????
        Glide.with(this).load(R.raw.loading).into(img_progress);

        TextView tv_message = progressDialog.findViewById(R.id.tv_message);
        if (!TextUtils.isEmpty(message)) {
            tv_message.setText(message);
        }

    }
}