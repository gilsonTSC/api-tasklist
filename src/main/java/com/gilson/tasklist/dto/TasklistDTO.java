package com.gilson.tasklist.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gilson.tasklist.util.enums.SituacaoTaskEnum;
import com.gilson.tasklist.util.enums.StatusTaskEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TasklistDTO {

	private Long id;
	
	@NotNull(message = "Informe um titulo")
	private String titulo;
	
	@NotNull(message = "Informe o status")
	@Pattern(regexp="^(CONCLUIDA|ANDAMENTO|CANCELADO)$", message = "Para o status somente são aceitos os valores CONCLIDO, ANDAMENTO ou CANCELADO")
	private StatusTaskEnum status;
	
	private String descricao;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date criacao;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date edicao;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date remocao;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date conclusao;
	
	private SituacaoTaskEnum situacao;
	
}