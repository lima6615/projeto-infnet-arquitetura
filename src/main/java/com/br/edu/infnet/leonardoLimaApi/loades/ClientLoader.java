package com.br.edu.infnet.leonardoLimaApi.loades;

import com.br.edu.infnet.leonardoLimaApi.dtos.AddressDTO;
import com.br.edu.infnet.leonardoLimaApi.dtos.ClientDTO;
import com.br.edu.infnet.leonardoLimaApi.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class ClientLoader implements ApplicationRunner {

    @Value("${path.client}")
    private String path;

    private final ClientService clientService;

    @Autowired
    public ClientLoader(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader(path);
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();
        String[] campos = null;

        while (linha != null) {
            campos = linha.split(";");

            ClientDTO clienteDto = new ClientDTO();
            clienteDto.setName(campos[0]);
            clienteDto.setEmail(campos[1]);
            clienteDto.setPassword(campos[2]);
            clienteDto.setInAtivo(Boolean.parseBoolean(campos[3]));
            clienteDto.setPhone(campos[4]);
            clienteDto.setCpf(campos[5]);

            AddressDTO addressDto = new AddressDTO();
            addressDto.setCep(campos[6]);
            clienteDto.setAddress(addressDto);

            clientService.createClientAndAddress(clienteDto, false);
            linha = leitura.readLine();
        }

        System.out.println("Leitura do arquivo de Cliente completa !!");
        leitura.close();
    }
}
