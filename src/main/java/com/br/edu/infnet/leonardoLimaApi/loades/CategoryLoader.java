package com.br.edu.infnet.leonardoLimaApi.loades;

import com.br.edu.infnet.leonardoLimaApi.dtos.CategoryDTO;
import com.br.edu.infnet.leonardoLimaApi.entities.Category;
import com.br.edu.infnet.leonardoLimaApi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class CategoryLoader implements ApplicationRunner {

    @Value("${path.category}")
    private String path;

    private final CategoryService service;

    @Autowired
    public CategoryLoader(CategoryService service) {
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

            Category category = new Category();
            category.setName(campos[0]);
            category.setInActive(Boolean.parseBoolean(campos[1]));
            service.insert(new CategoryDTO(category));

            linha = leitura.readLine();
        }

        System.out.println("Leitura do arquivo de Categoria completa !!");
        leitura.close();
    }
}
