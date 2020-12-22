package com.sw1tech.app.authoritie;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AuthoritieService {

    private AuthoritieRepository _repo;

    public AuthoritieService(AuthoritieRepository repo) {
        _repo = repo;
    }

    public void doSave(AuthoritieEntity authoritieEntity) {
        _repo.save(authoritieEntity);
    }

    public void doDelete(Integer id) {
        _repo.deleteById(id);
    }

    public void doSaveAll(List<AuthoritieEntity> authoritieEntity) {
        _repo.saveAll(authoritieEntity);
    }

    public void doDeleteAll(List<AuthoritieEntity> authoritieEntity) {
        _repo.deleteInBatch(authoritieEntity);
    }

}