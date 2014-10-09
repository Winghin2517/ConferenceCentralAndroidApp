package com.udacity.devrel.training.conference.android.utils;

import java.io.Serializable;

/**
 * Created by Simon on 2014/10/09.
 */
public class Blobs implements Serializable {

    private String blobKey;
    private String servingUrl;

    public String getBlobKey() {
        return blobKey;
    }

    public String getServingUrl() {
        return servingUrl;
    }

}
