package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerDServiceImpl implements CustomerDService{
    private final CustomerRepository repository;
}
