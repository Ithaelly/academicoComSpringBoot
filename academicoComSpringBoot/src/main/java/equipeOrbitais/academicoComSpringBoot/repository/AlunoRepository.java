package equipeOrbitais.academicoComSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import equipeOrbitais.academicoComSpringBoot.models.Aluno;
import java.util.Optional;

/*jpaRepository ele tem vários métodos como: salvar, deletar, find by id. Então é só criar uma instância dessa 
interface AlunoRepository e utilizar esse métodos no nosso modelo Aluno, facilitando a persistência no bd, sem precisar criar DAO*/
public interface AlunoRepository extends JpaRepository<Aluno, Long>{  //JpaRepository<nomeDaEntidade, tipoDoID>
	 Optional<Aluno> findByMatricula(String matricula);
}