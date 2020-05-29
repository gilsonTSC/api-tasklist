package com.gilson.tasklist.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.gilson.tasklist.util.enums.SituacaoTaskEnum;
import com.gilson.tasklist.util.enums.StatusTaskEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tasklist implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String titulo;
	
	@Enumerated(EnumType.STRING)
	private StatusTaskEnum statusTask;
	
	private String descricao;
	
	private Data criacao;
	
	private Data edicao;
	
	private Data remocao;
	
	private Data conclusao;
	
	private SituacaoTaskEnum situacao;

}