package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserMapper user, CredentialMapper credential) {
        this.userMapper = user;
        this.credentialMapper = credential;
    }

    public void addCredential(String url, String userName, String credentialUserName, String key, String password) {
        Integer userId = userMapper.getUser(userName).getUserId();
        Credential credential = new Credential(0, url, credentialUserName, key, password, userId);
        credentialMapper.addCredentialById(credential);
    }

    public Credential[] getCredentialListings(Integer userId) {
        return credentialMapper.findCredByUser(userId);
    }

    public Credential getCredential(Integer noteId) {
        return credentialMapper.getCredentialById(noteId);
    }

    public void deleteCredential(Integer noteId) {
        credentialMapper.deleteCredentialById(noteId);
    }

    public void updateCredential(Integer credentialId, String newUserName, String url, String key, String password) {
        credentialMapper.updateCredentialById(credentialId, newUserName, url, key, password);
    }
}
