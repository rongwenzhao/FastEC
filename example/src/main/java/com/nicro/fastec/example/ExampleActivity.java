package com.nicro.fastec.example;

import com.nicro.latte.activities.ProxyActivity;
import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.ec.launcher.LauncherScrollDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherScrollDelegate();
    }
}
