package com.udacity.jwdnd.course1.cloudstorage.credential;

import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public int saveCredential(Credential credential, Integer userId) throws IOException {
        return credentialMapper.saveCredential(credential);
    }

    public Credential[] getCredentialList(Integer userId) {
        return credentialMapper.getCredentialList(userId);
    }

}
