package com.udacity.jwdnd.course1.cloudstorage.credential;

import com.udacity.jwdnd.course1.cloudstorage.auth.HashService;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final HashService hashService;

    public CredentialService(CredentialMapper credentialMapper, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
    }

    public int saveCredential(CredentialForm credentialForm, Integer userId) throws IOException {

        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(credentialForm.getPassword(), encodedSalt);

        return credentialMapper.saveCredential(new Credential(0,credentialForm.getUrl(),credentialForm.getUsername(),encodedSalt,hashedPassword, userId));
    }

    public Credential[] getCredentialList(Integer userId) {
        return credentialMapper.getCredentialList(userId);
    }

}
