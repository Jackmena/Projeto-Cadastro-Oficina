package projeto.oficina.cadastro_cliente_carro.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.oficina.cadastro_cliente_carro.model.Carro;
import projeto.oficina.cadastro_cliente_carro.model.CarroRepository;
import projeto.oficina.cadastro_cliente_carro.model.Cliente;
import projeto.oficina.cadastro_cliente_carro.model.ClienteRepository;
import projeto.oficina.cadastro_cliente_carro.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        Carro carro = cliente.getCarro();

        if(carro.getPlaca() == null || !carroRepository.existsById(carro.getPlaca())){
            carroRepository.save(carro);
        }

        cliente.setCarro(carro);
        clienteRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if(clienteExistente.isPresent()) {
            Cliente clienteAtualizado = clienteExistente.get();

             // Atualiza o nome do cliente
            clienteAtualizado.setNome(cliente.getNome());

             // Verifica se o carro associado precisa ser atualizado
            Carro novoCarro = cliente.getCarro();

            if(novoCarro != null) {
                Carro carroExistente = clienteAtualizado.getCarro();

                if(carroExistente == null || !carroExistente.getPlaca().equals(novoCarro.getPlaca())) {
                     // Se o carro é novo ou tem placa diferente, salva o novo carro
                    carroRepository.save(novoCarro);
                    clienteAtualizado.setCarro(novoCarro);
                } else {
                    // Atualiza as informações do carro existente
                    carroExistente.setMarca(novoCarro.getMarca());
                    carroExistente.setModelo(novoCarro.getModelo());
                    carroExistente.setAno(novoCarro.getAno());
                    carroRepository.save(carroExistente);
                }

            }

            clienteRepository.save(clienteAtualizado);
        } else {
            throw new NoSuchElementException("Cliente com id " + id + " não encontrado.");
        }
    }

    @Override
    public void deletar(Long id) {
       clienteRepository.deleteById(id);
    }

}
