/**
 * Script para manipular os dados da tela
 */

$(document).ready(function() {
	
	$('#tabelaListaFerramentas').DataTable(configurarTabelaListaFerramentas());
	$('[data-toggle="tooltip"]').tooltip();
	
	$("#botaoAdicionarNovaFerramenta").click(function(){
		
		var titulo = $('#tituloNovaFerramenta').val().trim();
		var link = $('#linkNovaFerramenta').val().trim();
		var descricao = $('#descricaoNovaFerramenta').val().trim();
		var tags = $('#tagsNovaFerramenta').val().trim();
		
		if(titulo.length == 0 || link.length == 0 || descricao.length == 0 || tags.length == 0) {
			$('#alertaAdicionarFerramenta').removeClass('alert-warning');
			$('#alertaAdicionarFerramenta').addClass('alert-danger');
		} else {
			
			$('#alertaAdicionarFerramenta').removeClass('alert-danger');
			$('#alertaAdicionarFerramenta').addClass('alert-warning');
			$('#alertaAdicionarFerramenta').text('Aguarde, processando requisição.');
			
			var dadosTags = [];
			$.each(tags.split(' '), function(index, value) {
				var dadosTag = {name: value};
				dadosTags[index] = dadosTag;
			});			
			
			var dadosFerramenta = {
				title: titulo,
				link,
				description: descricao,
				tags: dadosTags
			}
			
			$.ajax({
			  url: "/tools",
			  type: "POST",
			  dataType: "xml/html/script/json", // expected format for response
			  contentType: "application/json", // send as JSON
			  data: JSON.stringify(dadosFerramenta),

			  complete: function(evt) {
				  if(evt.status == 200) {
					  $('#alertaAdicionarFerramenta').removeClass('alert-warning');
					  $('#alertaAdicionarFerramenta').addClass('alert-success');
					  $('#alertaAdicionarFerramenta').text('Ferramenta adicionada com sucesso.');
					  $("#botaoAdicionarNovaFerramenta").attr("disabled", true);
					  recarregarListaFerramentas('');  					  
				  } else {
					  $('#alertaAdicionarFerramenta').removeClass('alert-warning');
					  $('#alertaAdicionarFerramenta').addClass('alert-danger');
					  $('#alertaAdicionarFerramenta').text('Ocorreu um erro de requisição.');
				  }
			  }
			});
		}
	});
	
	$("#botaoRemoverFerramenta").click(function(){		
		var idFerramenta = $('#idFerramenta').val().trim();
		if(idFerramenta.length == 0 || isNaN(idFerramenta)) {
			$('#alertaRemoverFerramenta').removeClass('alert-info');
			$('#alertaRemoverFerramenta').addClass('alert-danger');
			$('#alertaRemoverFerramenta').text('Favor informar um valor numérico.');
		} else {
			
			$('#alertaRemoverFerramenta').removeClass('alert-danger');
			$('#alertaRemoverFerramenta').addClass('alert-info');
			$('#alertaRemoverFerramenta').text('Aguarde, processando requisição.');
			
			$.ajax({
				url: "/tools/"+idFerramenta,
				type: "DELETE",
				dataType: "xml/html/script/json", // expected format for response
				contentType: "application/json", // send as JSON
				
				complete: function(evt) {
					if(evt.status == 200) {
						$('#alertaRemoverFerramenta').removeClass('alert-warning');
						$('#alertaRemoverFerramenta').addClass('alert-success');
						$('#alertaRemoverFerramenta').text('Processo de remoção concluído.');
						$("#botaoRemoverFerramenta").attr("disabled", true);
						recarregarListaFerramentas('');  					  
					} else {
						$('#alertaRemoverFerramenta').removeClass('alert-info');
						$('#alertaRemoverFerramenta').addClass('alert-danger');
						$('#alertaRemoverFerramenta').text('Ocorreu um erro de requisição.');
					}
				}
			});
		}
	});
	
	$("#botaoFiltrarFerramentas").click(function(){
		var tipoFiltro = $( "input[type=radio][name=tipoFiltroBusca]:checked" ).val();
		var valor = $('#valorFiltroBusca').val();		
		var parametroBusca = '';
			
		if(valor != null && valor.length > 0 && valor.trim() !== ''){
			parametroBusca += '?'
			if(tipoFiltro == 'titulo') {
				parametroBusca += 'tool=' + valor;
			} else if(tipoFiltro == 'tag') {
				parametroBusca += 'tag=' + valor;
			}
		}
		
		recarregarListaFerramentas(parametroBusca);
	});
	
	$('#modalAdicionarFerramenta').on('hidden.bs.modal', function(evt){		
		$('#tituloNovaFerramenta').val('');
		$('#linkNovaFerramenta').val('');
		$('#descricaoNovaFerramenta').val('');
		$('#tagsNovaFerramenta').val('');
		$('#alertaAdicionarFerramenta').removeClass('alert-danger');
		$('#alertaAdicionarFerramenta').addClass('alert-warning');
		$('#alertaAdicionarFerramenta').text('Atenção! Todos os campos são obrigatórios.');
		$("#botaoAdicionarNovaFerramenta").attr("disabled", false);
	});
	
	$('#modalRemoverFerramenta').on('hidden.bs.modal', function(evt){		
		$('#idFerramenta').val('');
		$('#alertaRemoverFerramenta').removeClass('alert-danger');
		$('#alertaRemoverFerramenta').addClass('alert-info');
		$('#alertaRemoverFerramenta').text('Favor informar o id da ferramenta a ser removida.');
		$("#botaoRemoverFerramenta").attr("disabled", false);
	});
	
});

function recarregarListaFerramentas(parametroBusca) {
	
	$.get(('/tools' + parametroBusca), function(data, status){
		if(data != null) {
			var datatable = $('#tabelaListaFerramentas').DataTable();
			datatable.clear();
			datatable.rows.add(data.data);
			datatable.draw()
		}
	});
}


function configurarTabelaListaFerramentas() {
	return {
		"select": true,
		"ajax" : '/tools',
		"columns" : [ {
			"data" : "id"
		}, {
			"data" : "title"
		}, {
			"data" : "link"
		}, {
			"data" : "description"
		}, {
			"data" : "tags[,].name"
		} ],
		"filter" : false,
		"language" : {
			"sEmptyTable" : "Nenhum registro encontrado",
			"sInfo" : "Mostrando de _START_ até _END_ de _TOTAL_ registros",
			"sInfoEmpty" : "Mostrando 0 até 0 de 0 registros",
			"sInfoFiltered" : "(Filtrados de _MAX_ registros)",
			"sInfoPostFix" : "",
			"sInfoThousands" : ".",
			"sLengthMenu" : "_MENU_ resultados por página",
			"sLoadingRecords" : "Carregando...",
			"sProcessing" : "Processando...",
			"sZeroRecords" : "Nenhum registro encontrado",
			"sSearch" : "Pesquisar",
			"oPaginate" : {
				"sNext" : "Próximo",
				"sPrevious" : "Anterior",
				"sFirst" : "Primeiro",
				"sLast" : "Último"
			},
			"oAria" : {
				"sSortAscending" : ": Ordenar colunas de forma ascendente",
				"sSortDescending" : ": Ordenar colunas de forma descendente"
			}
		}
	}
}
