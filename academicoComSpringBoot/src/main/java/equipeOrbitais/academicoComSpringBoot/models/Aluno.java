package equipeOrbitais.academicoComSpringBoot.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/*Os @ são anotação das classes, tabelas, colunas e atributos que queremos armazenadas no banco de dados, 
 * indicando que os objetos criados são entidades e devem ser persistidos.*/

@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data //essa anotação já cria os gets e sets
@Entity //define que essa classe é uma entidade a ser mapeada pela JPA
@Table(schema = "graduacao", name = "aluno") //nome da tabela no bd
public class Aluno {
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="aluno_seq") //os dados dessa chave serão criados pelo banco de dado
	@SequenceGenerator(name = "aluno_seq", schema = "graduacao", sequenceName = "aluno_seq", allocationSize = 1)
	@Column(name="id") //nome da coluna no bd
	private int id;
	
	@Column(name="matricula") 
	private String matricula;	
	
	@Column(name="ano_entrada")
	private int anoEntrada;	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;
}

