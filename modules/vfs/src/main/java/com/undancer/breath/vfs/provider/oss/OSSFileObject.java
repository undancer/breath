package com.undancer.breath.vfs.provider.oss;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.*;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.AbstractFileObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Set;

/**
 * Created by undancer on 14-5-12.
 */
public class OSSFileObject extends AbstractFileObject {

    private final static Logger LOGGER = LoggerFactory.getLogger(OSSFileObject.class);

    private OSSClient client;
    private String bucketName;

    private FileType fileType;

    private String delimiter = "/";

    private boolean downloaded = false;

    private OSSObject object;

    private File cacheFile;

    protected OSSFileObject(OSSClient client, String bucketName, AbstractFileName name, OSSFileSystem fs) {
        super(name, fs);
        this.client = client;
        this.bucketName = bucketName;
    }

    protected FileType doGetType() throws Exception {
        System.out.println(this + " - doGetType");
        if (fileType == null) {
            try {
                this.client.listObjects(new ListObjectsRequest(bucketName, getPrefix(getKey()), null, delimiter, 1));
                try {
                    this.client.getObjectMetadata(bucketName, getKey());
                    this.fileType = FileType.FILE;
                } catch (OSSException e) {
                    fileType = FileType.FOLDER;
                }
            } catch (OSSException e) {
                this.fileType = FileType.IMAGINARY;
            }
        }
        return fileType;
    }

    private String getKey() {
        String path = getName().getPath();
        if (StringUtils.startsWith(path, delimiter)) {
            path = StringUtils.substring(path, 1);
        }
        return path;
    }

    private String getPrefix(String prefix) {
        if (!StringUtils.endsWith(prefix, delimiter)) {
            return StringUtils.join(prefix, delimiter);
        }
        return prefix;
    }

    protected String[] doListChildren() throws Exception {
        System.out.println(this + " - doListChildren");
        Set<String> children = Sets.newLinkedHashSet();
        String prefix = getPrefix(getKey());
        String marker = null;
        do {
            ListObjectsRequest request = new ListObjectsRequest(bucketName, prefix, marker, delimiter, 100);
            System.out.println(ToStringBuilder.reflectionToString(request));
            ObjectListing objectListing = this.client.listObjects(request);
            for (String commonPrefix : objectListing.getCommonPrefixes()) {
                String key = StringUtils.removeStart(commonPrefix, prefix);
                if (StringUtils.isNoneBlank(key)) {
                    children.add(key);
                }
            }
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                String key = StringUtils.removeStart(objectSummary.getKey(), prefix);
                if (StringUtils.isNoneBlank(key)) {
                    children.add(key);
                }
            }
            marker = objectListing.getNextMarker();
        } while (marker != null);
        return children.toArray(new String[]{});
    }

    protected long doGetContentSize() throws Exception {
        downloadOnce();
        return this.object.getObjectMetadata().getContentLength();
    }

    protected void downloadOnce() throws FileSystemException {
        if (!this.downloaded) {
            final String failedMessage = "Failed to download OSS Object %s. %s";
            final String objectPath = getName().getPath();
            try {
                GetObjectRequest request = new GetObjectRequest(bucketName, getKey());
                object = this.client.getObject(request);
                LOGGER.info("downloading oss object : {}", objectPath);
                ObjectMetadata metadata = object.getObjectMetadata();
                InputStream inputStream = object.getObjectContent();
                if (metadata.getContentLength() > 0) {
                    ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
                    FileChannel cacheFileChannel = getCacheFileChannel();
                    cacheFileChannel.transferFrom(readableByteChannel, 0, metadata.getContentLength());
                    IOUtils.closeQuietly(cacheFileChannel);
                    IOUtils.closeQuietly(readableByteChannel);
                } else {
                    IOUtils.closeQuietly(inputStream);
                }
            } catch (IOException e) {
                throw new FileSystemException(String.format(failedMessage, objectPath, e.getMessage()), e);
            }
            this.downloaded = true;
        }
    }

    protected FileChannel getCacheFileChannel() throws IOException {
        if (this.cacheFile == null) {
            this.cacheFile = File.createTempFile("oss", ".breath_tmp");
        }
        return new RandomAccessFile(this.cacheFile, "rw").getChannel();
    }

    protected InputStream doGetInputStream() throws Exception {
        downloadOnce();
        return Channels.newInputStream(getCacheFileChannel());
    }
}
