package com.audioStreaming;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import com.facebook.react.modules.network.OkHttpClientProvider;
import com.google.android.exoplayer2.upstream.DataSource;
import saschpe.exoplayer2.ext.icy.IcyHttpDataSource;
import saschpe.exoplayer2.ext.icy.IcyHttpDataSourceFactory;

import java.util.HashMap;

// https://github.com/react-native-kit/react-native-track-player/pull/384/files

public class IcyEvents implements IcyHttpDataSource.IcyMetadataListener, IcyHttpDataSource.IcyHeadersListener {

    private final DataSource.Factory ds;

    public IcyEvents() {
        this.ds = new IcyHttpDataSourceFactory.Builder(OkHttpClientProvider.getOkHttpClient())
        .setUserAgent("Mozilla/5.0 Google")
        .setIcyMetadataChangeListener(this)
        .setIcyHeadersListener(this)
        .build();
    }

    public DataSource.Factory getIcyDataSource() {
        return ds;
    }

    @Override
    public void onIcyMetaData(IcyHttpDataSource.IcyMetadata metadata) {
        Bundle bundle = new Bundle();
        Bundle fullMetadata = new Bundle();

        bundle.putString("type", "icy-metadata");
        bundle.putString("title", metadata.getStreamTitle());
        bundle.putString("url", metadata.getStreamUrl());

        HashMap<String, String> data = metadata.getMetadata();
        for(String key : data.keySet()) {
            fullMetadata.putString(key, data.get(key));
        }

        bundle.putBundle("metadata", fullMetadata);

        Log.d("ICY", "ICY");
        Log.d("ICY2", metadata.getStreamTitle());

    }

    @Override
    public void onIcyHeaders(IcyHttpDataSource.IcyHeaders headers) {
        Bundle bundle = new Bundle();

        bundle.putString("type", "icy-headers");
        bundle.putString("name", headers.getName());
        bundle.putString("genre", headers.getGenre());
        bundle.putString("url", headers.getUrl());
        bundle.putInt("bitrate", headers.getBitRate());
        bundle.putBoolean("public", headers.isPublic());
        Log.d("ICY3", "ICY3");

    }

}
