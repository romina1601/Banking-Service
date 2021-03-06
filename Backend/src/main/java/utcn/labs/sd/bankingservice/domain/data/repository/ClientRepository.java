package utcn.labs.sd.bankingservice.domain.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utcn.labs.sd.bankingservice.domain.data.entity.Client;
import utcn.labs.sd.bankingservice.domain.dto.ClientDTO;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String> {


}
