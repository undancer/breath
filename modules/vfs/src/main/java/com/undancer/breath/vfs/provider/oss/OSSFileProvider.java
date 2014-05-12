package com.undancer.breath.vfs.provider.oss;

import com.aliyun.openservices.ClientConfiguration;
import com.aliyun.openservices.oss.OSSClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.AbstractOriginatingFileProvider;
import org.apache.commons.vfs2.util.UserAuthenticatorUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.apache.commons.vfs2.UserAuthenticationData.*;
import static org.apache.commons.vfs2.util.UserAuthenticatorUtils.getData;

/**
 * Created by undancer on 14-5-12.
 */

public class OSSFileProvider extends AbstractOriginatingFileProvider {

    public final static Collection<Capability> capabilities = Collections.unmodifiableCollection(Arrays.<Capability>asList(
            Capability.LIST_CHILDREN
    ));

    public final static UserAuthenticationData.Type[] AUTHENTICATOR_TYPES = new UserAuthenticationData.Type[]{
            DOMAIN, USERNAME, PASSWORD
    };

    private static FileSystemOptions defaultOptions = new FileSystemOptions();

    private OSSClient client;


    public OSSFileProvider() {
        super();
        setFileNameParser(OSSFileNameParser.getInstance());
    }

    public static FileSystemOptions getDefaultOptions() {
        return defaultOptions;
    }

    /**
     * @param rootName
     * @param fileSystemOptions
     * @return
     * @throws FileSystemException
     */
    protected FileSystem doCreateFileSystem(FileName rootName, FileSystemOptions fileSystemOptions) throws
            FileSystemException {

        FileSystemOptions fsOptions = fileSystemOptions != null ? fileSystemOptions : getDefaultOptions();

        if (client == null) {
            UserAuthenticationData authData = null;
            try {
                authData = UserAuthenticatorUtils.authenticate(fsOptions, AUTHENTICATOR_TYPES);

                String endpoint = UserAuthenticatorUtils.toString(getData(authData, DOMAIN, null));
                String accessKeyId = UserAuthenticatorUtils.toString(getData(authData, USERNAME, null));
                String accessKeySecret = UserAuthenticatorUtils.toString(getData(authData, PASSWORD, null));

                if (StringUtils.isBlank(endpoint)) {
                    endpoint = "http://oss.aliyuncs.com/";
                }

                ClientConfiguration config = new ClientConfiguration();

                this.client = new OSSClient(endpoint, accessKeyId, accessKeySecret, config);
            } finally {
                UserAuthenticatorUtils.cleanup(authData);
            }
        }
        return new OSSFileSystem(client, (OSSFileName) rootName, fsOptions);
    }

    /**
     * @return
     */
    public Collection<Capability> getCapabilities() {
        return capabilities;
    }
}
