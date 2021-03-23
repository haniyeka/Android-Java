package com.example.mappictures;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CamPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    String TAG = "CameraPreview";
    Context context;
    //used for the camera
    String cameraId;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mPreviewBuilder;
    private CameraCaptureSession mPreviewSession;
    //needed for take picture
    private Size[] jpegSizes;
    int width = 640;
    int height = 480;
    CameraCharacteristics characteristics;
    ImageReader reader;
    Handler backgroudHandler;
    CaptureRequest.Builder captureBuilder;
    List<Surface> outputSurfaces;
    File file;
    LatLng location;

    public CamPreview(Context context, String CameraID, LatLng Location) {
        super(context);
        this.context = context;
        //set the Camera to be used
        cameraId = CameraID;
        location = Location;
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    /*
    This is the one to call to take a picture.
     */
    public void TakePicture(File filename) {
        file = filename;
        try {
            mCameraDevice.createCaptureSession(outputSurfaces, mCaptureStateCallback, backgroudHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    //Methods for the SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "Surfaceview Created");
        openCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCameraDevice != null) {
            mCameraDevice.close();
        }
    }
    //setup the camera objects

    private void openCamera() {

        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        Log.e(TAG, "openCamera Start");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            try {
                //String cameraId = manager.getCameraIdList()[0];

                // setup the camera perview.  should wrap this in a checkpermissions, which studio is bitching about
                // except it has been done before this fragment is called.
                manager.openCamera(cameraId, mStateCallback, null);


                //setup for taking the picture here, so we only do it once, instead at "take picture" time.
                characteristics = manager.getCameraCharacteristics(cameraId);
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);

                //setup the width and height size, assuming camera knows it or use default 640x480.
                if (jpegSizes != null && 0 < jpegSizes.length) {
                    width = jpegSizes[0].getWidth();
                    height = jpegSizes[0].getHeight();
                }
                reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
                outputSurfaces = new ArrayList<Surface>(2);
                outputSurfaces.add(reader.getSurface());


                HandlerThread thread = new HandlerThread("CameraPicture");
                thread.start();
                backgroudHandler = new Handler(thread.getLooper());
                reader.setOnImageAvailableListener(readerListener, backgroudHandler);

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Don't have permission to camera!");
        }
        Log.e(TAG, "openCamera End");
    }

    private int getJpegOrientation(CameraCharacteristics c, int deviceOrientation) {
        if (deviceOrientation == android.view.OrientationEventListener.ORIENTATION_UNKNOWN)
            return 0;
        int sensorOrientation = c.get(CameraCharacteristics.SENSOR_ORIENTATION);
        deviceOrientation = (deviceOrientation + 45) / 90 * 90;
        boolean facingFront = c.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT;
        if (facingFront) deviceOrientation = -deviceOrientation;
        int jpegOrientation = (sensorOrientation + deviceOrientation + 360) % 360;
        return jpegOrientation;
    }


    protected void startPreview() {

        if (null == mCameraDevice) {
            Log.e(TAG, "startPreview fail, return");
            return;
        }

        Surface surface = mHolder.getSurface();

        try {
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (CameraAccessException e) {

            e.printStackTrace();
        }
        mPreviewBuilder.addTarget(surface);

        try {
            mCameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {

                @Override
                public void onConfigured(CameraCaptureSession session) {
                    mPreviewSession = session;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                    Toast.makeText(context, "onConfigureFailed", Toast.LENGTH_LONG).show();
                }
            }, null);
        } catch (CameraAccessException e) {

            e.printStackTrace();
        }
    }

    protected void updatePreview() {

        if (null == mCameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }
        mPreviewBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        HandlerThread thread = new HandlerThread("CameraPreview");
        thread.start();
        Handler backgroundHandler = new Handler(thread.getLooper());

        try {
            mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, backgroundHandler);
        } catch (CameraAccessException e) {

            e.printStackTrace();
        }
    }

    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(CameraDevice camera) {
            Log.e(TAG, "onOpened");
            mCameraDevice = camera;
            startPreview();
            try {
                captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            try {
                captureBuilder.addTarget(reader.getSurface());
                captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                int deviceorientation = getResources().getConfiguration().orientation;
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getJpegOrientation(characteristics, deviceorientation));
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            Log.e(TAG, "onDisconnected");
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            Log.e(TAG, "onError");
        }

    };

    ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {

        @Override
        public void onImageAvailable(ImageReader reader) {
            Image image = null;
            try {
                image = reader.acquireLatestImage();
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.capacity()];
                buffer.get(bytes);
                save(bytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (image != null) {
                    image.close();
                }
            }
        }

        private void save(byte[] bytes) throws IOException {
            OutputStream output = null;
            try {
                output = new FileOutputStream(file);
                output.write(bytes);
            } finally {
                if (null != output) {
                    output.close();
                }
            }
        }

    };

    CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {

        @Override
        public void onCaptureCompleted(CameraCaptureSession session,
                                       CaptureRequest request, TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.DATA, file.toString());
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Picture newpic = new Picture(file,location);
            MapsActivity.pictures.add(newpic);
            Toast.makeText(context, "Saved:" + file , Toast.LENGTH_SHORT).show();
            Log.v(TAG, "Saved:" + file);
            startPreview();
        }

    };

    CameraCaptureSession.StateCallback mCaptureStateCallback = new CameraCaptureSession.StateCallback() {

        @Override
        public void onConfigured(CameraCaptureSession session) {
            try {
                session.capture(captureBuilder.build(), captureListener, backgroudHandler);
            } catch (CameraAccessException e) {

                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    };


}
