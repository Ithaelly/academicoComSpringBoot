package equipeOrbitais.academicoComSpringBoot.dto;

import java.util.List;
import java.util.stream.Collectors;

import equipeOrbitais.academicoComSpringBoot.models.Pessoa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //cria um construtor com todos
@NoArgsConstructor //cria um construtor vazio

public class PessoaDTO {
		private long id;
		private String nome;
		private String cpf;
		
		public PessoaDTO(Pessoa pessoa) {
			this.id = pessoa.getId();
			this.nome = pessoa.getNome();
			this.cpf = pessoa.getCpf();
		}
		
		public static List<PessoaDTO> convert(List <Pessoa> pessoa){
			return pessoa.stream().map(PessoaDTO::new).collect(Collectors.toList());
			/*stream: facilita na manipulação de coleções
			 * map: mapear todos os atributos dessa classe
			 * collect: vai ta coletando todos esses dados de cima e vai passar ela pela Collectors.toList()
			 * Collectors.toList: vai fazer uma lista com esses dados coletados */
		}
}
