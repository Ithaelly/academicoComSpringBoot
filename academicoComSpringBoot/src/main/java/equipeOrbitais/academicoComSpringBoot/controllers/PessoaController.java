package equipeOrbitais.academicoComSpringBoot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import equipeOrbitais.academicoComSpringBoot.dto.PessoaDTO;
import equipeOrbitais.academicoComSpringBoot.models.Pessoa;
import equipeOrbitais.academicoComSpringBoot.repository.PessoaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
//@RestController //permite definir um controller com características REST. Defini que essa classe AlunoController é a que vai receber as requisições http
@RequestMapping(value="/api") // permite definir uma rota. Caso não seja informado o método HTTP da rota, ela será definida para todos os métodos.
@CrossOrigin(origins = "http://localhost:8080")//@CrossOrigin(origins = "*")
@Api(value="Academico com Spring Boot")

public class PessoaController {
	
	@Autowired //delega ao Spring Boot a inicialização do objeto;
	PessoaRepository pessoaRepository;
	
	@ApiOperation(value="Retorna uma lista de pessoas cadastradas")
	@GetMapping("/pessoas")
	public List<PessoaDTO> listaPessoas(){
		List<Pessoa> pessoas = pessoaRepository.findAll();
		return PessoaDTO.convert(pessoas);	
	}
	
	@ApiOperation(value="Retorna uma unica pessoa pelo id dela")
	@GetMapping("/pessoa/{id}")
	public Optional<Pessoa> listaPessoaUnica(@PathVariable(value="id") long id){ //@PathVariable indica que o valor da variável virá de uma informação da rota
		return pessoaRepository.findById(id);
	}
	
	@ApiOperation(value="Salva a pessoa")
	@PostMapping("/pessoa")
    public ResponseEntity<Pessoa> Post(@RequestBody Pessoa pessoa){		
        Optional<Pessoa> oldPessoa = pessoaRepository.findByCpf(pessoa.getCpf());
        if(oldPessoa.isEmpty()){
        	pessoaRepository.save(pessoa);
        	return new ResponseEntity<Pessoa>(HttpStatus.OK);     	     
        }
        else {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);    	
        }
    }
	
	@ApiOperation(value="Deleta a pessoa pelo cpf")
	@DeleteMapping(value = "/pessoa/{cpf}")
    public ResponseEntity<Object> deletePessoa(@PathVariable(value = "cpf") String cpf){
		Optional<Pessoa> pessoa =  pessoaRepository.findByCpf(cpf);
        if(pessoa.isPresent()){
        	pessoaRepository.delete(pessoa.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@ApiOperation(value="Atualiza a pessoa pelo cpf")
		@PutMapping("/pessoa/{cpf}")
	    public ResponseEntity<Pessoa> Put(@PathVariable(value = "cpf") String cpf,  @RequestBody Pessoa newPessoa){
	        Optional<Pessoa> oldPessoa = pessoaRepository.findByCpf(cpf);
	        if(oldPessoa.isPresent()){
	        	Pessoa pessoa = oldPessoa.get();
	        	pessoa.setCpf(newPessoa.getCpf());
	        	pessoa.setNome(newPessoa.getNome());
	        	pessoaRepository.save(pessoa);
	            return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
	        }
	        else
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
}
