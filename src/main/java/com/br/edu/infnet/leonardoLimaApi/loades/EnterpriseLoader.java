package com.br.edu.infnet.leonardoLimaApi.loades;

import com.br.edu.infnet.leonardoLimaApi.dtos.EnterpriseDTO;
import com.br.edu.infnet.leonardoLimaApi.services.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class EnterpriseLoader implements ApplicationRunner {

    @Value("${path.enterprise}")
    private String path;

    private final EnterpriseService service;

    @Autowired
    public EnterpriseLoader(EnterpriseService service) {
        this.service = service;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader(path);
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();
        String[] campos = null;

        while (linha != null) {
            campos = linha.split(";");

            EnterpriseDTO enterpriseDto = new EnterpriseDTO();
            enterpriseDto.setName(campos[0]);
            enterpriseDto.setEmail(campos[1]);
            enterpriseDto.setPassword(campos[2]);
            enterpriseDto.setInAtivo(Boolean.parseBoolean(campos[3]));
            enterpriseDto.setPhoto(campos[4]);
            enterpriseDto.setCnpj(campos[5]);

            service.insert(enterpriseDto);
            linha = leitura.readLine();
        }

        System.out.println("Leitura do arquivo de Empresa completa !!");
        leitura.close();
    }
}
