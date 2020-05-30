package com.gilson.tasklist.service;

import java.util.Optional;

import com.gilson.tasklist.entity.Tasklist;
import com.gilson.tasklist.util.enums.StatusTaskEnum;

public interface TasklistService {

	Tasklist salvar(Tasklist tasklist);
	
	Tasklist atualizar(Tasklist tasklist);
	
	void deletar(Tasklist tasklist);
	
	Optional<Tasklist> buscarPorId(Long id );
	
	void atualizarStatus(Tasklist tasklist, StatusTaskEnum status);
	
	void validar(Tasklist tasklist);
	
}