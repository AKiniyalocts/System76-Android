package com.akiniyalocts.system76;


import com.akiniyalocts.system76.base.BaseApplication;
import com.akiniyalocts.system76.data.MainThreadBus;


/**
 * Created by anthony on 10/6/15.
 */
public class System76App extends BaseApplication {

    private static MainThreadBus bus;

    public static MainThreadBus getBus() {
        if(bus == null){
            bus = new MainThreadBus();
        }
        return bus;
    }
}
