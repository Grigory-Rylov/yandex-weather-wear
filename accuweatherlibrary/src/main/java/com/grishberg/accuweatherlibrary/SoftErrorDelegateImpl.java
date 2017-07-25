package com.grishberg.accuweatherlibrary;

import com.github.grigoryrylov.reststacklib.SoftErrorDelegate;

/**
 * Created by grishberg on 24.07.17.
 */

public class SoftErrorDelegateImpl implements SoftErrorDelegate {
    @Override
    public Throwable checkSoftError(Object body) {
        return null;
    }
}
