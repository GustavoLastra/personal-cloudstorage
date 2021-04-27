package com.udacity.jwdnd.course1.cloudstorage.credential;

import com.udacity.jwdnd.course1.cloudstorage.auth.HashService;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final HashService hashService;

    public CredentialService(CredentialMapper credentialMapper, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
    }

    public Integer saveCredential(Credential credential) throws IOException {
        String encodedSalt = hashService.getEncodedSalt();
        credential.setKey(encodedSalt);
        credential.setPassword(hashService.getHashedValue(credential.getPassword(), encodedSalt));

        if (credential.getCredentialId() != null && credentialMapper.getCredential(credential) != null) {
            return credentialMapper.updateCredential(credential);
        }

        return credentialMapper.saveCredential(credential);
    }

    public List<Credential> getCredentialList(Integer userId) {
        return credentialMapper.getCredentialList(userId);
    }

    public Integer deleteCredential(Integer credentialId, Integer userId) {
        return credentialMapper.deleteCredential(credentialId, userId);
    }

    public Integer updateCredential(Credential credential, Integer userId) {
        String encodedSalt = hashService.getEncodedSalt();
        credential.setKey(encodedSalt);
        credential.setPassword(hashService.getHashedValue(credential.getPassword(), encodedSalt));
        return  credentialMapper.updateCredential(credential);
    }
}
