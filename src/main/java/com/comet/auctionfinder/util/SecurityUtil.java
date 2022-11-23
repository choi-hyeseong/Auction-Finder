package com.comet.auctionfinder.util;

import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    //ajax 요청시 isauthentication 통과 못하므로 따로 예외처리
    public static boolean checkAjax(UserDetails details) {
        return details == null;
    }
}
