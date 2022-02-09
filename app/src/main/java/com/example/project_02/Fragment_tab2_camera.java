package com.example.project_02;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

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
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.provider.MediaStore;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Fragment_tab2_camera extends Fragment {

    Button  btnClose;
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


    //카메라 프리뷰에 필요한 변수
    PreviewView previewView;
    ProcessCameraProvider processCameraProvider;
    int lensFacing = CameraSelector.LENS_FACING_FRONT;
    ImageCapture imageCapture;
    Fragment BaumannFragment;

    int test;

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


        //bottomsheetDialog 객체 생성
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater1.inflate(R.layout.capture, null, false);
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(view);

        //fragment3.xml
        View v = inflater.inflate(R.layout.fragment_camera, container, false);

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
        //카메라 권한 요청
        //이 구조의 문제점: 한번 더 버튼을 눌러야 카메라 기능이 동작
//        if (allPermissionsGranted()) {
//            bindPreview();
//            bindImageCapture();
//        } else {
//            ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
//        }

        //위 문제점 개선한 구조 -> 권한 설정 후 딜레이 주기
        if (allPermissionsGranted()) {
            bindPreview();
            bindImageCapture();
        } else {
            ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);

            //권한 설정하고 1초 뒤에 카메라 바인딩
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindPreview();
                    bindImageCapture();
                }
            },1000);
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

                                imgPlant.setImageBitmap(bitmap);

                                //close() 안해주면 Too many acquire images. Close image to be able to process next 오류발생
                                //이미지 데이터가 계속 쌓이게 되면서 사진이 찍히지 않음
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
            Toast.makeText(getActivity().getApplicationContext(), "확인", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
            processCameraProvider.unbindAll(); //프리뷰 카메라 종료
            //Volley 이용해서 서버로 bitmap -> base64로 변환해서 전송

            //여기서 프로그래스 다이얼로그 불러오기
            progressON(getActivity(), "Loading...");
            sendImage();
        });

        btnClose.setOnClickListener(view12 -> {
            Toast.makeText(getActivity().getApplicationContext(), "닫기", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
        });
        return v;
    }

    //이미지 플라스크로 전송
    private void sendImage() {

        //비트맵 이미지를 byte로 변환 -> base64형태로 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] imageBytes = baos.toByteArray();
        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //base64형태로 변환된 이미지 데이터를 플라스크 서버로 전송
        String flask_url = "http://220.80.203.107:5000/sendFrame";
        StringRequest request = new StringRequest(Request.Method.POST, flask_url,
                response -> {
                    Log.d("cameraFragment", response);

                    // base64룰 Bitmap 타입으로 다시 디코드.
                    byte[] decodedString = Base64.decode(response, Base64.DEFAULT);
                    Bitmap b = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    // 번들로 이미지 전송
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("a",b);
                    BaumannFragment bau = new BaumannFragment();
                    bau.setArguments(bundle);

                    //여기서 끝
                    progressOFF();
                    mainactivity.getSupportFragmentManager().beginTransaction().replace(R.id.container,bau).commit();


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
                .setTargetAspectRatio(AspectRatio.RATIO_4_3) //디폴트 표준 비율
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

    // 유틸 함수
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
        processCameraProvider.unbindAll(); //프리뷰 카메라 종료
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

    public void progressON(Activity activity, String message){

        if( getActivity() == null || getActivity().isFinishing() ){
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
        //GIF이미지를 적용하기 위해 Glide 라이브러리 사용
        Glide.with(this).load(R.raw.face_ai).into(img_progress);

        TextView tv_message = progressDialog.findViewById(R.id.tv_message);
        if (!TextUtils.isEmpty(message)) {
            tv_message.setText(message);
        }

    }
}