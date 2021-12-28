package equipeOrbitais.academicoComSpringBoot.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data //essa anotação já cria os gets e sets
@Entity
@Table(schema = "comum", name = "pessoa") //nome da tabela no bd
public class Pessoa {
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="pessoa_seq") //os dados dessa chave serão criados pelo banco de dado
	@SequenceGenerator(name = "pessoa_seq", schema = "comum", sequenceName = "pessoa_seq", allocationSize = 1)//initialValue = 1
	@Column(name="id") //nome da coluna no bd
	private int id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="cpf")
	private String cpf;
	
	//1 pessoa - N alunos
	@OneToMany(fetch = FetchType.EAGER , mappedBy = "pessoa")
	private List<Aluno> listarAlunos = new ArrayList<>();
}
