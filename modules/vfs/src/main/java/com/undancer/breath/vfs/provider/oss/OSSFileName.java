package com.undancer.breath.vfs.provider.oss;

import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.provider.local.LocalFileName;

/**
 * Created by undancer on 14-5-12.
 */
public class OSSFileName extends LocalFileName {
    protected OSSFileName(String scheme, String rootFile, String path, FileType type) {
        super(scheme, rootFile, path, type);
    }
}
