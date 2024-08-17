package com.radz.wfh.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class WfhUtility {

  public static String generateHashedValue(String password) {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.encode(password);
  }
}
