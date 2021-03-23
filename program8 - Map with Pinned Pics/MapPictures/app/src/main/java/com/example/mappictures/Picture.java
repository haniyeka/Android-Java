package com.example.mappictures;

import com.google.android.gms.maps.model.LatLng;
import java.io.File;

public class Picture {
    private File PictureFile;
    private LatLng location;

    public Picture(File filename, LatLng loc){
        PictureFile = filename;
        location = loc;
    }

    public LatLng getPosition() {
        return location;
    }
    public File getFile() {
        return PictureFile;
    }
}
