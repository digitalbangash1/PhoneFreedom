package dtu.projekt.phonefreedom.notification_services;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AppPackageDao {
    //use brackets to escape reserved keywords
    @Query("SELECT [index] FROM app_packages WHERE package_name=:packageName")
    int getPackageIndex(String packageName);

    @Insert
    void insertAppPackage(AppPackage appPackage);
}
