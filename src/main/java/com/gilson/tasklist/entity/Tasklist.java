package com.gilson.tasklist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gilson.tasklist.util.enums.SituacaoTaskEnum;
import com.gilson.tasklist.util.enums.StatusTaskEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
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
	private StatusTaskEnum status;
	
	private String descricao;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date criacao;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date edicao;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date remocao;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date conclusao;
	
	@Enumerated(EnumType.STRING)
	private SituacaoTaskEnum situacao;

}