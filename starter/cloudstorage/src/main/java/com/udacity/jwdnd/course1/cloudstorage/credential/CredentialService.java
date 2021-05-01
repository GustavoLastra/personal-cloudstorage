package com.udacity.jwdnd.course1.cloudstorage.credential;

import com.udacity.jwdnd.course1.cloudstorage.auth.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.auth.HashService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final HashService hashService;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, HashService hashService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    public boolean saveCredential(Credential credential) throws IOException {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        if (credential.getCredentialId() != null && credentialMapper.getCredential(credential) != null) {
            return credentialMapper.updateCredential(credential);
        }
        return credentialMapper.saveCredential(credential);
    }

    public List<Credential> getCredentialList(Integer userId) {
        List<Credential> credentialList = credentialMapper.getCredentialList(userId);
        if (!credentialList.isEmpty()) {
            for (Credential credential : credentialList) {
                credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
            }
        }

        return credentialList;
    }

    public boolean deleteCredential(Integer credentialId, Integer userId) {
        return credentialMapper.deleteCredential(credentialId, userId);
    }

    public boolean updateCredential(Credential credential, Integer userId) {
        Credential cred = credentialMapper.getCredential(credential);
        credential.setKey(cred.getKey());
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        return credentialMapper.updateCredential(credential);
    }
}
