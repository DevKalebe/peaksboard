package com.example.sp.drobskaep.peaksbord.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class FirebaseUtil {
	// variável para guardar as credenciais de acesso
	private Credentials credenciais;
	// variável para acessar e manipular o storage
	private Storage storege;
	// constante para o nome do bucket
	private final String BUCKET_NAME = "peaksboardcloud.appspot.com";
	// constante para o prefixo da URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/"+BUCKET_NAME+"/o/";
	// constante para o sufixo da URL
	private String SUFFIX = "?alt=media";
	// constante para URL
	private String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;
	
	public FirebaseUtil() {
		// acessar o arquivo JSON com a chave privada
		Resource resource = new ClassPathResource("keyFirebase.json");
		
		try {
			// gera uma credencial no Firebase através de chave do arquivo
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			// cria o storage para manipular os dados no Firebase
			storege = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
		} catch (IOException e) {
			
			throw new RuntimeException(e.getMessage());
		}
	}
	
	// método para extrair a estensão do arquivo
	public String getExtensao(String nomeArquivo) {
		// extrai o trecho do arquivo onde está a extensão
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}
	
	// método que faz o upload
	public String upload(MultipartFile arquivo) {
		// gera um nome aleatório para o arquivo
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		
		return "";
	}
}