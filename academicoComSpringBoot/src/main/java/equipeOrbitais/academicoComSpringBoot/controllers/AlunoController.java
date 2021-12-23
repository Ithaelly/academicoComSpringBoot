package equipeOrbitais.academicoComSpringBoot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import equipeOrbitais.academicoComSpringBoot.models.Aluno;
import equipeOrbitais.academicoComSpringBoot.repository.AlunoRepository;

@RestController //permite definir um controller com características REST. Defini que essa classe AlunoController é a que vai receber as requisições http
@RequestMapping(value="/api") // permite definir uma rota. Caso não seja informado o método HTTP da rota, ela será definida para todos os métodos.

public class AlunoController {
	
	@Autowired //delega ao Spring Boot a inicialização do objeto;
	AlunoRepository alunoRepository;
	
	//retorna todos os alunos cadastrados
	@GetMapping("/alunos")
	public List<Aluno> listaAlunos(){
		return alunoRepository.findAll();
	}
	
	//retorna os dados de um aluno escolhendo pelo id dele
	@GetMapping("/aluno/{id}")
	public Optional<Aluno> listaAlunoUnico(@PathVariable(value="id") long id){ //@PathVariable indica que o valor da variável virá de uma informação da rota
		return alunoRepository.findById(id);
	}

	//salva o aluno
	@PostMapping("/aluno")
	public Aluno salvaAluno(@RequestBody Aluno aluno) { //@RequestBody indica que o valor do objeto virá do corpo da requisição
		return alunoRepository.save(aluno);
	}
	
	//deletar aluno
	@DeleteMapping("/aluno/{id}")
    public ResponseEntity<Object> deleteAluno(@PathVariable(value = "id") long id){
		Optional<Aluno> aluno =  alunoRepository.findById(id);
        if(aluno.isPresent()){
           alunoRepository.delete(aluno.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
		
	/*@DeleteMapping(value = "/aluno/{matricula}")
    public ResponseEntity<Object> deleteAluno(@PathVariable(value = "matricula") String matricula){
		Optional<Aluno> aluno =  alunoRepository.findByMatricula(matricula);
        if(aluno.isPresent()){
           alunoRepository.delete(aluno.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
	
	//atualizar
	@PutMapping("/aluno/{id}")
    public ResponseEntity<Aluno> Put(@PathVariable(value = "id") long id,  @RequestBody Aluno newAluno){
        Optional<Aluno> oldAluno = alunoRepository.findById(id);
        if(oldAluno.isPresent()){
        	Aluno aluno = oldAluno.get();
        	aluno.setMatricula(newAluno.getMatricula());
        	aluno.setAnoEntrada(newAluno.getAnoEntrada());
        	//aluno.setPessoa(Pessoa pessoa);
            alunoRepository.save(aluno);
            return new ResponseEntity<Aluno>(aluno, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
}
