package com.undancer.breath.core;

import com.undancer.breath.core.util.RequestUtils;

/**
 * Created by undancer on 14-4-25.
 */
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ViewNotFoundException extends RuntimeException {

    public ViewNotFoundException() {
        super(RequestUtils.getUrl());
    }

}
