package com.smdev.gearbybe.service.impl;

import com.smdev.gearbybe.model.entity.CodeEntity;
import com.smdev.gearbybe.model.entity.UserEntity;
import com.smdev.gearbybe.repository.CodeRepository;
import com.smdev.gearbybe.repository.UserRepository;
import com.smdev.gearbybe.service.CodeService;
import com.smdev.gearbybe.service.EmailService;
import com.smdev.gearbybe.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Autowired
    public CodeServiceImpl(CodeRepository codeRepository, EmailService emailService, UserRepository userRepository) {
        this.codeRepository = codeRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean generate(String email) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
        if(optionalUserEntity.isEmpty()){
            return false;
        }

        String code = CodeUtil.generateCode();
        Optional<CodeEntity> codeEntityOptional = codeRepository.findByUserEntity_Email(email);
        if(codeEntityOptional.isEmpty()){
            CodeEntity codeEntity = new CodeEntity();
            codeEntity.setCode(code);
            codeEntity.setUserEntity(optionalUserEntity.get());
            codeRepository.save(codeEntity);
        }else{
            CodeEntity codeEntity = codeEntityOptional.get();
            codeEntity.setCode(code);
            codeRepository.save(codeEntity);
        }

        return true;
    }

    @Override
    public boolean activate(String email, String code) {
        Optional<CodeEntity> codeEntity = codeRepository.findByUserEntity_Email(email);
        if(codeEntity.isEmpty()){
            return false;
        }

        if(codeEntity.get().getCode().equals(code)){
            codeRepository.deleteById(codeEntity.get().getId());
            return true;
        }

        return false;
    }
}
