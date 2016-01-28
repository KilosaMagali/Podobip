package students.molecular.podobip;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by meradi on 28/01/16.
 */
public class AppService extends Service {
    

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
