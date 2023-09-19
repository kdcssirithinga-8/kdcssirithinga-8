package com.blackey.fuel_delivery_app2;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public interface StorageReference {
    StorageReference child(String profile_photos);

    Task<Uri> getDownloadUrl();
}
