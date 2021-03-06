package com.undancer.breath.vfs.provider.oss;

import com.aliyun.openservices.oss.OSS;
import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.AbstractFileSystem;

import java.util.Collection;

/**
 * Created by undancer on 14-5-12.
 */
public class OSSFileSystem extends AbstractFileSystem {

    private OSS client;
    private String bucketName;

    protected OSSFileSystem(OSS client, OSSFileName rootName, FileSystemOptions fileSystemOptions) {
        this(client, rootName, null, fileSystemOptions);
    }

    protected OSSFileSystem(OSS client, OSSFileName rootName, FileObject parentLayer, FileSystemOptions fileSystemOptions) {
        super(rootName, parentLayer, fileSystemOptions);
        this.client = client;
        this.bucketName = rootName.getRootFile();
    }

    protected FileObject createFile(AbstractFileName name) throws Exception {
        return new OSSFileObject(client, bucketName, name, this);
    }

    protected void addCapabilities(Collection<Capability> caps) {
        caps.addAll(OSSFileProvider.capabilities);
    }
}
