package com.example.sandhu.interfaces2;
import com.example.sandhu.model2.Driver;


public interface FirebaseDriverListener {
    void onDriverAdded(Driver var1);

    void onDriverRemoved(Driver var1);

    void onDriverUpdated(Driver var1);
}
