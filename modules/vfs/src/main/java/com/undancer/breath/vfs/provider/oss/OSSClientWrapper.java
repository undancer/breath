package com.undancer.breath.vfs.provider.oss;

import com.aliyun.openservices.ClientConfiguration;
import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.HttpMethod;
import com.aliyun.openservices.oss.OSS;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.UserAuthenticationData;
import org.apache.commons.vfs2.util.UserAuthenticatorUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by undancer on 14-5-12.
 */
class OSSClientWrapper implements OSS {

    private OSSFileName root;
    private FileSystemOptions options;

    private OSSClient client;

    OSSClientWrapper(OSSFileName root, FileSystemOptions options) {
        this.root = root;
        this.options = options;
        this.client = getClient();
    }

    public OSSClient getClient() {
        if (client == null) {
            UserAuthenticationData authData = null;
            try {
                authData = UserAuthenticatorUtils.authenticate(options, OSSFileProvider.AUTHENTICATOR_TYPES);

                String endpoint = OSSFileSystemConfigBuilder.getInstance().getEndpoint();
                String accessKeyId = OSSFileSystemConfigBuilder.getInstance().getAccessKeyId();
                String accessKeySecret = OSSFileSystemConfigBuilder.getInstance().getAccessKeySecret();
                ClientConfiguration config = OSSFileSystemConfigBuilder.getInstance().getConfig();

                if (StringUtils.isBlank(endpoint)) {
                    endpoint = "http://oss.aliyuncs.com/";
                }

                this.client = new OSSClient(endpoint, accessKeyId, accessKeySecret, config);
            } finally {
                UserAuthenticatorUtils.cleanup(authData);
            }
        }
        return this.client;
    }

    @Override
    public Bucket createBucket(String bucketName) throws OSSException, ClientException {
        return getClient().createBucket(bucketName);
    }

    @Override
    public Bucket createBucket(CreateBucketRequest createBucketRequest) throws OSSException, ClientException {
        return getClient().createBucket(createBucketRequest);
    }

    @Override
    public void deleteBucket(String bucketName) throws OSSException, ClientException {
        getClient().deleteBucket(bucketName);
    }

    @Override
    public List<Bucket> listBuckets() throws OSSException, ClientException {
        return getClient().listBuckets();
    }

    @Override
    public void setBucketAcl(String bucketName, CannedAccessControlList cannedAccessControlList) throws OSSException,
            ClientException {
        getClient().setBucketAcl(bucketName, cannedAccessControlList);
    }

    @Override
    public AccessControlList getBucketAcl(String bucketName) throws OSSException, ClientException {
        return getClient().getBucketAcl(bucketName);
    }

    @Override
    public String getBucketLocation(String bucketName) throws OSSException, ClientException {
        return getClient().getBucketLocation(bucketName);
    }

    @Override
    @Deprecated
    public boolean doesBucketExist(String bucketName) throws OSSException, ClientException {
        return getClient().doesBucketExist(bucketName);
    }

    @Override
    public ObjectListing listObjects(String bucketName) throws OSSException, ClientException {
        return getClient().listObjects(bucketName);
    }

    @Override
    public ObjectListing listObjects(String bucketName, String prefix) throws OSSException, ClientException {
        return getClient().listObjects(bucketName, prefix);
    }

    @Override
    public ObjectListing listObjects(ListObjectsRequest listObjectsRequest) throws OSSException, ClientException {
        return getClient().listObjects(listObjectsRequest);
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, InputStream input,
                                     ObjectMetadata metadata) throws OSSException, ClientException {
        return getClient().putObject(bucketName, key, input, metadata);
    }

    @Override
    public CopyObjectResult copyObject(String sourceBucketName, String sourceKey, String destinationBucketName,
                                       String destinationKey) throws OSSException, ClientException {
        return getClient().copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
    }

    @Override
    public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest) throws OSSException, ClientException {
        return getClient().copyObject(copyObjectRequest);
    }

    @Override
    public OSSObject getObject(String bucketName, String key) throws OSSException, ClientException {
        return getClient().getObject(bucketName, key);
    }

    @Override
    public ObjectMetadata getObject(GetObjectRequest getObjectRequest, File file) throws OSSException, ClientException {
        return getClient().getObject(getObjectRequest, file);
    }

    @Override
    public OSSObject getObject(GetObjectRequest getObjectRequest) throws OSSException, ClientException {
        return getClient().getObject(getObjectRequest);
    }

    @Override
    public ObjectMetadata getObjectMetadata(String bucketName, String key) throws OSSException, ClientException {
        return getClient().getObjectMetadata(bucketName, key);
    }

    @Override
    public void deleteObject(String bucketName, String key) throws OSSException, ClientException {
        getClient().deleteObject(bucketName, key);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration) throws ClientException {
        return getClient().generatePresignedUrl(bucketName, key, expiration);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethod method) throws ClientException {
        return getClient().generatePresignedUrl(bucketName, key, expiration, method);
    }

    @Override
    public URL generatePresignedUrl(GeneratePresignedUrlRequest generatePresignedUrlRequest) throws ClientException {
        return getClient().generatePresignedUrl(generatePresignedUrlRequest);
    }

    @Override
    public void abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest) throws OSSException, ClientException {
        getClient().abortMultipartUpload(abortMultipartUploadRequest);
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest) throws OSSException, ClientException {
        return getClient().completeMultipartUpload(completeMultipartUploadRequest);
    }

    @Override
    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest) throws OSSException, ClientException {
        return getClient().initiateMultipartUpload(initiateMultipartUploadRequest);
    }

    @Override
    public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest listMultipartUploadsRequest) throws OSSException, ClientException {
        return getClient().listMultipartUploads(listMultipartUploadsRequest);
    }

    @Override
    public PartListing listParts(ListPartsRequest listPartsRequest) throws OSSException, ClientException {
        return getClient().listParts(listPartsRequest);
    }

    @Override
    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) throws OSSException, ClientException {
        return getClient().uploadPart(uploadPartRequest);
    }
}
