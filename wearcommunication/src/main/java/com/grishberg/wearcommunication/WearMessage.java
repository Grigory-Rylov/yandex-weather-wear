package com.grishberg.wearcommunication;

import android.os.Parcelable;

/**
 * Created by grishberg on 21.07.17.
 */

public abstract class WearMessage implements Parcelable {
    public abstract byte[] toBytes();
}
