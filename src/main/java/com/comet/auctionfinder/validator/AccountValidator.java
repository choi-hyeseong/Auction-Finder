package com.comet.auctionfinder.validator;

import com.comet.auctionfinder.dto.MemberRequestDto;
import com.comet.auctionfinder.service.MemberService;
import com.comet.auctionfinder.validator.annotation.AccountCheck;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Component
@AllArgsConstructor
public class AccountValidator implements ConstraintValidator<AccountCheck, MemberRequestDto> {
    private MemberService service;

    @Override
    public boolean isValid(MemberRequestDto value, ConstraintValidatorContext context) {
        if (service.isUserIDExist(value.getUserId())) {
            addConstraint("아이디가 이미 존재합니다.", context);
            return false;
        }
        else if (service.isNickNameExist(value.getNickName())) {
            addConstraint("닉네임이 이미 존재합니다.", context);
            return false;
        }
        else {
            return true;
        }

    }

    private void addConstraint(String message, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
