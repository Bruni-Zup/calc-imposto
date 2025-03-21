package imposto.imposto.service;

import imposto.imposto.exception.ResourceNotFoundException;
import imposto.imposto.model.Imposto;
import imposto.imposto.repository.ImpostoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpostoService {

    private static final Logger logger = LoggerFactory.getLogger(ImpostoService.class);
    private final ImpostoRepository impostoRepository;

    public ImpostoService(ImpostoRepository impostoRepository) {
        this.impostoRepository = impostoRepository;
    }

    public List<Imposto> listarTodos() {
        logger.info("Listando todos os impostos");
        return impostoRepository.findAll();
    }

    public Imposto obterImpostoPorId(Long id) {
        logger.info("Obtendo imposto com ID: {}", id);
        return impostoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imposto não encontrado com ID: " + id));
    }

    public Imposto criarImposto(Imposto imposto) {
        logger.info("Criando imposto: {}", imposto.getNome());
        return impostoRepository.save(imposto);
    }

    public void excluirImposto(Long id) {
        logger.info("Excluindo imposto com ID: {}", id);
        if (!impostoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Imposto não encontrado com ID: " + id);
        }
        impostoRepository.deleteById(id);
    }

    public double calcularImposto(Long id, double valorBase) {
        logger.info("Calculando imposto para ID: {} com valor base: {}", id, valorBase);
        Imposto imposto = obterImpostoPorId(id);
        return valorBase * (imposto.getAliquota() / 100);
    }
}
