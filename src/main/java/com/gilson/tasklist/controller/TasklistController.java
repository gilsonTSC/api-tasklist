package com.gilson.tasklist.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gilson.tasklist.dto.AtualizaStatusDTO;
import com.gilson.tasklist.dto.TasklistDTO;
import com.gilson.tasklist.entity.Tasklist;
import com.gilson.tasklist.exception.RegraNegocioException;
import com.gilson.tasklist.service.TasklistService;
import com.gilson.tasklist.util.enums.StatusTaskEnum;

@RestController
@RequestMapping("/api/task")
public class TasklistController {

	@Autowired
	private TasklistService service;
	
	@GetMapping
	public ResponseEntity<Object> buscarTodos() {
		List<Tasklist> list = this.service.buscarTodos();
		
		List<TasklistDTO> dto = list.stream().map(obj -> this.converter(obj)).collect(Collectors.toList());
		return new ResponseEntity<Object>(dto, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> buscar( @PathVariable("id") Long id ) {
		return this.service.buscarPorId(id)
					.map( tasklist -> new ResponseEntity<Object>(this.converter(tasklist), HttpStatus.OK) )
					.orElseGet( () -> new ResponseEntity<Object>(HttpStatus.NOT_FOUND) );
	}
	
	@PostMapping
	public ResponseEntity<Object> salvar( @RequestBody TasklistDTO dto ) {
		try {
			dto.setCriacao(new Date());
			Tasklist entidade = this.converter(dto);
			entidade = this.service.salvar(entidade);
			return ResponseEntity.ok(entidade);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar( @PathVariable("id") Long id, @RequestBody TasklistDTO dto ) {
		dto.setEdicao(new Date());
		if(dto.getStatus().equals(StatusTaskEnum.CONCLUIDO)) {
			dto.setConclusao(new Date());
		}
		if(dto.getStatus().equals(StatusTaskEnum.CANCELADO)) {
			dto.setRemocao(new Date());
		}
		return this.service.buscarPorId(id).map( entity -> {
			try {
				Tasklist tasklist = this.converter(dto);
				tasklist.setId(entity.getId());
				this.service.atualizar(tasklist);
				return ResponseEntity.ok(tasklist);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () ->
			new ResponseEntity("Task não encontrada na base de Dados.", HttpStatus.BAD_REQUEST) );
	}
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus( @PathVariable("id") Long id , @RequestBody AtualizaStatusDTO dto ) {
		return this.service.buscarPorId(id).map( entity -> {
			StatusTaskEnum statusSelecionado = StatusTaskEnum.valueOf(dto.getStatus());
			
			if(statusSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar o status da Task.");
			}
			
			if(statusSelecionado.equals(StatusTaskEnum.CONCLUIDO)) {
				entity.setConclusao(new Date());
			}
			if(statusSelecionado.equals(StatusTaskEnum.CANCELADO)) {
				entity.setRemocao(new Date());
			}
			
			try {
				entity.setStatus(statusSelecionado);
				this.service.atualizar(entity);
				return ResponseEntity.ok(entity);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		
		}).orElseGet( () ->
		new ResponseEntity("Task não encontrada na base de Dados.", HttpStatus.BAD_REQUEST) );
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deletar( @PathVariable("id") Long id ) {
		return this.service.buscarPorId(id).map( entity -> {
			entity.setRemocao(new Date());
			this.service.deletar(entity);
			return new ResponseEntity<Object>( HttpStatus.NO_CONTENT );
		}).orElseGet( () -> 
			new ResponseEntity<Object>("Task não encontrada na base de Dados.", HttpStatus.BAD_REQUEST) );
	}
	
	private TasklistDTO converter(Tasklist tasklist) {
		return TasklistDTO.builder()
				.id(tasklist.getId())
				.titulo(tasklist.getTitulo())
				.descricao(tasklist.getDescricao())
				.status(tasklist.getStatus())
				.edicao(tasklist.getEdicao())
				.criacao(tasklist.getCriacao())
				.conclusao(tasklist.getConclusao())
				.remocao(tasklist.getRemocao())
				.situacao(tasklist.getSituacao())
				.build();
	}
	
	private Tasklist converter(TasklistDTO dto) {
		return Tasklist.builder()
				.id(dto.getId())
				.titulo(dto.getTitulo())
				.descricao(dto.getDescricao())
				.status(dto.getStatus())
				.edicao(dto.getEdicao())
				.criacao(dto.getCriacao())
				.conclusao(dto.getConclusao())
				.remocao(dto.getRemocao())
				.situacao(dto.getSituacao())
				.build();
	}
	
}