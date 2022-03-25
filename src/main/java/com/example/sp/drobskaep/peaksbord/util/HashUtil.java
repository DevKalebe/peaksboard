package com.example.sp.drobskaep.peaksbord.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {
	public static String hash(String palavra) {
		// "tempero" do hash
		String salt = "D@S@N@Y";
		// adicionar o tempero a palavra
		palavra = salt + palavra;
		// gera o hashS
		String hash = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString() ;
		
		return hash;
	}
}
