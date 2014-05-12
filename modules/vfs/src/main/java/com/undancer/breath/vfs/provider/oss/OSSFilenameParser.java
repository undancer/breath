package com.undancer.breath.vfs.provider.oss;

import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.provider.AbstractFileNameParser;
import org.apache.commons.vfs2.provider.UriParser;
import org.apache.commons.vfs2.provider.VfsComponentContext;

/**
 * Created by undancer on 14-5-12.
 */
public class OSSFilenameParser extends AbstractFileNameParser {

    private static final OSSFilenameParser instance = new OSSFilenameParser();

    private OSSFilenameParser() {
    }

    public static OSSFilenameParser getInstance() {
        return instance;
    }

    @Override
    public FileName parseUri(VfsComponentContext context, FileName base, String filename) throws FileSystemException {
        StringBuilder name = new StringBuilder();
        String scheme = UriParser.extractScheme(filename, name);
        UriParser.canonicalizePath(name, 0, name.length(), this);
        UriParser.fixSeparators(name);
        FileType type = UriParser.normalisePath(name);
        final String bucketName = UriParser.extractFirstElement(name);
        return new OSSFileName(scheme, bucketName, name.toString(), type);
    }
}
