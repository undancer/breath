package com.undancer.breath.vfs.provider.oss;

import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.AbstractOriginatingFileProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by undancer on 14-5-12.
 */

public class OSSFileProvider extends AbstractOriginatingFileProvider {


    //    public static final Type ENDPOINT = new Type("endpoint");
//    public static final Type ACCESS_KEY_ID = new Type("accessKeyId");
//    public static final Type ACCESS_KEY_SECRET = new Type("accessKeySecret");
//    public static final Type CONFIG = new Type("config");
    public final static UserAuthenticationData.Type[] AUTHENTICATOR_TYPES = new UserAuthenticationData.Type[]{
//            ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET, CONFIG
            UserAuthenticationData.USERNAME, UserAuthenticationData.PASSWORD
    };
    public final static Collection<Capability> capabilities = Collections.unmodifiableCollection(Arrays.<Capability>asList(
            Capability.LIST_CHILDREN
    ));
    private static FileSystemOptions defaultFileSystemOptions = new FileSystemOptions();

    public OSSFileProvider() {
        super();
        setFileNameParser(OSSFileNameParser.getInstance());
    }

    public static FileSystemOptions getDefaultFileSystemOptions() {
        return defaultFileSystemOptions;
    }

    /**
     * @param name
     * @param options
     * @return
     * @throws FileSystemException
     */
    protected FileSystem doCreateFileSystem(FileName name, FileSystemOptions options) throws
            FileSystemException {
        FileSystemOptions fsOptions = options != null ? options : defaultFileSystemOptions;
        OSSClientWrapper client = new OSSClientWrapper((OSSFileName) name, fsOptions);
        return new OSSFileSystem(client, (OSSFileName) name, fsOptions);
    }

    /**
     * @return
     */
    public Collection<Capability> getCapabilities() {
        return capabilities;
    }

    @Override
    public FileSystemConfigBuilder getConfigBuilder() {
        return OSSFileSystemConfigBuilder.getInstance();
    }
}
