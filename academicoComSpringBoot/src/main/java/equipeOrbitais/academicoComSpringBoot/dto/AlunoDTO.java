package equipeOrbitais.academicoComSpringBoot.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import equipeOrbitais.academicoComSpringBoot.models.Aluno;
import equipeOrbitais.academicoComSpringBoot.repository.AlunoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //cria um construtor com todos
@NoArgsConstructor //cria um construtor vazio

public class AlunoDTO {
		private long id;
		private String matricula;	
		private int anoEntrada;	
		private PessoaDTO pessoaDTO;
		
		AlunoRepository alunoRepository;
		
		public AlunoDTO(Aluno aluno) {
			this.id = aluno.getId();
			this.matricula = aluno.getMatricula();
			this.anoEntrada = aluno.getAnoEntrada();
			this.pessoaDTO = new PessoaDTO(aluno.getPessoa());
		}
		
		public static List<AlunoDTO> convert(List <Aluno> aluno){
			return aluno.stream().map(AlunoDTO::new).collect(Collectors.toList());
			/*stream: facilita na manipulação de coleções
			 * map: mapear todos os atributos dessa classe
			 * collect: vai ta coletando todos esses dados de cima e vai passar ela pela Collectors.toList()
			 * Collectors.toList: vai fazer uma lista com esses dados coletados */
		}
		
		 // Retorna os dados de alunos do bd
	    public List<AlunoDTO> getAlunos() {
	        List<Aluno> listaAluno = alunoRepository.findAll();
	        List<AlunoDTO> listaAlunoDTO = new ArrayList<>();

	        for (Aluno a : listaAluno) {
	            listaAlunoDTO.add(new AlunoDTO(a));
	        }
	        return listaAlunoDTO;
	    }
}
