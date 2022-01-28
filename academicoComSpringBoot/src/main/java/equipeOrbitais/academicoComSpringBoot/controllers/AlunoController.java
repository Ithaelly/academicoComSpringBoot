package equipeOrbitais.academicoComSpringBoot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import equipeOrbitais.academicoComSpringBoot.dto.AlunoDTO;
import equipeOrbitais.academicoComSpringBoot.dto.PessoaDTO;
import equipeOrbitais.academicoComSpringBoot.models.Aluno;
import equipeOrbitais.academicoComSpringBoot.models.Pessoa;
import equipeOrbitais.academicoComSpringBoot.repository.AlunoRepository;
import equipeOrbitais.academicoComSpringBoot.repository.PessoaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;;

@Controller
//@RestController //permite definir um controller com características REST. Defini que essa classe AlunoController é a que vai receber as requisições http
@CrossOrigin(origins = "http://localhost:8080") //antigo: @CrossOrigin(origins = "*")
@RequestMapping(value="/api") // permite definir uma rota. Caso não seja informado o método HTTP da rota, ela será definida para todos os métodos.
@Api(value="Academico com Spring Boot")

public class AlunoController {
	/*Model permite adicionar atributos que o Thymeleaf pode usar para renderizar os dados.*/
	Pessoa pessoa = new Pessoa();
	AlunoDTO alunoDTO = new AlunoDTO();
	PessoaDTO pessoaDTO = new PessoaDTO();
	
	@Autowired //delega ao Spring Boot a inicialização do objeto;
	AlunoRepository alunoRepository;
	@Autowired
	PessoaRepository pessoaRepository;
	
	//Método que faz abrir a página inicial com a lista dos alunos
    @GetMapping(value = "/telaInicial") 	//caminho p/ testar na URL
    public String telaInicial(Model model){
        model.addAttribute("listaAlunos", listaAlunos());
        return "alunos/paginaInicial";		//caminho das pastar p/ o arquivo html da página que quero
    }	
    
	// Método que faz mudar da páginaInicial.html para a paginaAlunos.html
    @GetMapping(value = "/telaCadastro")
    public String telaCadastro(@ModelAttribute("aluno") Aluno aluno){
        return "alunos/paginaAlunos";  //caminho a partir da pasta: templates
    }
    // Método do botão de remover aluno na paginaInicial
    @GetMapping(value = "/remover/{matricula}")
    public String excluir(@PathVariable(value = "matricula") String matricula, Model model) {
		Optional<Aluno> aluno =  alunoRepository.findByMatricula(matricula);
        if(aluno.isPresent()){
           alunoRepository.delete(aluno.get());
           model.addAttribute("listaAlunos", listaAlunos()); 
           return "alunos/paginaInicial";	
        }
        else {
        	throw new IllegalArgumentException("Pessoa inválida.");	
        }
    }
        	
    // Método do botão confirmar cadastro
    @PostMapping(value = "/adicionar")
    public String salvar(@ModelAttribute("aluno") Aluno aluno){
        Optional<Pessoa> pessoa =  pessoaRepository.findByCpf(aluno.getPessoa().getCpf()); 
        Optional<Aluno> oldAluno = alunoRepository.findByMatricula(aluno.getMatricula());
        
        if(pessoa.isPresent()){
        	if((alunoRepository.findByMatricula(aluno.getMatricula()) == null) || (oldAluno.isEmpty())) {
        		aluno.setPessoa(pessoa.get());
        		alunoRepository.save(aluno);
	        	return "alunos/paginaInicial";		     
        	}
       }
    	 throw new IllegalArgumentException("Pessoa não existe ou matrícula já está cadastrada! Tente novamente!");   	
    }
    
    // Método do botao de alterar
    @GetMapping(value = "/alterar/{matricula}")
    public String alterar(@PathVariable(value = "matricula") String matricula, Model model){
        Optional<Aluno> aluno =  alunoRepository.findByMatricula(matricula);
            if(aluno.isPresent()){
                model.addAttribute("aluno", aluno.get());
                return "alunos/paginaAlunos";
            }
            throw new IllegalArgumentException("Aluno inválido!");
    }
    
    // Método de quando clica no botao confirmar a alteração
    @GetMapping(value = "/atualizar")
    public String atualizar(@ModelAttribute("aluno") Aluno newAluno){
        Optional<Pessoa> oldPessoa = pessoaRepository.findByCpf(newAluno.getPessoa().getCpf());
        Optional<Aluno> oldAluno = alunoRepository.findByMatricula(newAluno.getMatricula());

        if(oldAluno.isPresent()){
            if(oldPessoa != null){
            	Aluno aluno = oldAluno.get();
            	aluno.setPessoa(oldPessoa.get());
            	aluno.setMatricula(newAluno.getMatricula());
            	aluno.setAnoEntrada(newAluno.getAnoEntrada());
                alunoRepository.save(aluno);
                return "alunos/paginaInicial";		
            }
        }
        throw new IllegalArgumentException("Erro ao alterar aluno. Pessoa inexistente ou matrícula já em uso!");
    }

    
    
    @ApiOperation(value="Retorna uma lista de alunos cadastrados")
	@GetMapping("/alunos")
	public List<AlunoDTO> listaAlunos(){
		List<Aluno> alunos = alunoRepository.findAll();
		return AlunoDTO.convert(alunos);	
	}
	
	@ApiOperation(value="Retorna um unico aluno pelo id dele")
	@GetMapping("/aluno/{id}")
	public Optional<Aluno> listaAlunoUnico(@PathVariable(value="id") long id){ //@PathVariable indica que o valor da variável virá de uma informação da rota
		return alunoRepository.findById(id);
	}
	@ApiOperation(value="Salva o aluno")
	@PostMapping("/aluno")
    public ResponseEntity<Aluno> Post(@RequestBody Aluno aluno){		
        Optional<Aluno> oldAluno = alunoRepository.findByMatricula(aluno.getMatricula());
        if(oldAluno.isEmpty()){
        	alunoRepository.save(aluno);
        	return new ResponseEntity<Aluno>(HttpStatus.OK);     	     
        }
        else {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);     	
        }
    }

	@ApiOperation(value="Deleta o aluno pela matricula")
	@DeleteMapping(value = "/aluno/{matricula}")
    public ResponseEntity<Object> deleteAluno(@PathVariable(value = "matricula") String matricula){
		Optional<Aluno> aluno =  alunoRepository.findByMatricula(matricula);
        if(aluno.isPresent()){
           alunoRepository.delete(aluno.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@ApiOperation(value="Atualiza o aluno pela matricula")
	@PutMapping("/aluno/{matricula}")
    public ResponseEntity<Aluno> Put(@PathVariable(value = "matricula") String matricula,  @RequestBody Aluno newAluno){
        Optional<Aluno> oldAluno = alunoRepository.findByMatricula(matricula);
        if(oldAluno.isPresent()){
        	Aluno aluno = oldAluno.get();
        	aluno.setMatricula(newAluno.getMatricula());
        	aluno.setAnoEntrada(newAluno.getAnoEntrada());
            alunoRepository.save(aluno);
            return new ResponseEntity<Aluno>(aluno, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
