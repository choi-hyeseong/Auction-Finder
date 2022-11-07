package com.comet.auctionfinder.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MemberRequestDtoTest {

    @Test
    @DisplayName("validator test")
    void VALIDATE_TEST() {
        MemberRequestDto requestDto = MemberRequestDto.builder().build();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MemberRequestDto>> violationSet = validator.validate(requestDto);
        violationSet.stream().forEach((i) -> System.out.println(i.getMessage()));
        assertNotEquals(violationSet.size(), 0);
    }

}