package com.gilson.tasklist.service.impl;

import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gilson.tasklist.entity.Tasklist;
import com.gilson.tasklist.exception.RegraNegocioException;
import com.gilson.tasklist.repository.TasklistRepository;
import com.gilson.tasklist.service.TasklistService;
import com.gilson.tasklist.util.enums.SituacaoTaskEnum;
import com.gilson.tasklist.util.enums.StatusTaskEnum;

@Service
public class TasklistServiceImpl implements TasklistService{

	@Autowired
	private TasklistRepository repository;
	
	@Override
	@Transactional
	public Tasklist salvar(Tasklist tasklist) {
		this.validar(tasklist);
		tasklist.setSituacao(SituacaoTaskEnum.ATIVO);
		return this.repository.save(tasklist);
	}

	@Override
	@Transactional
	public Tasklist atualizar(Tasklist tasklist) {
		Objects.requireNonNull(tasklist.getId());
		this.validar(tasklist);
		return this.repository.save(tasklist);
	}

	@Override
	@Transactional
	public void deletar(Tasklist tasklist) {
		Objects.requireNonNull(tasklist.getId());
		tasklist.setSituacao(SituacaoTaskEnum.INATIVO);
		this.atualizar(tasklist);
	}

	@Override
	public Optional<Tasklist> buscarPorId(Long id) {
		return this.repository.findById(id);
	}

	@Override
	@Transactional
	public void atualizarStatus(Tasklist tasklist, StatusTaskEnum status) {
		tasklist.setStatus(status);
		this.atualizar(tasklist);
	}

	@Override
	public void validar(Tasklist tasklist) {
		if(tasklist.getTitulo() == null || tasklist.getTitulo().trim().equals("")) {
			throw new RegraNegocioException("Informe um título.");
		}
		
		if(tasklist.getStatus() == null) {
			throw new RegraNegocioException("Informe um status.");
		}
	}

}