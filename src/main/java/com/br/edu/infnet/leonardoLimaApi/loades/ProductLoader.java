package com.br.edu.infnet.leonardoLimaApi.loades;

import com.br.edu.infnet.leonardoLimaApi.dtos.ProductDTO;
import com.br.edu.infnet.leonardoLimaApi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class ProductLoader implements ApplicationRunner {

    @Value("${path.product}")
    private String path;

    private final ProductService service;

    @Autowired
    public ProductLoader(ProductService service) {
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

            ProductDTO dto = new ProductDTO();
            dto.setName(campos[0]);
            dto.setDescription(campos[1]);
            dto.setPrice(Double.parseDouble(campos[2]));
            dto.setInActive(Boolean.parseBoolean(campos[3]));
            service.insert(dto);

            linha = leitura.readLine();
        }

        System.out.println("Leitura do arquivo de Produto completa !!");
        leitura.close();
    }
}
