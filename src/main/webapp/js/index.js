function mudarTermo() {
    $("#buscar").html("Buscar por " + $("#termo").val());
}

function listarTodos() {
    let url = "/MavenREST/rest/users";
    $.get(url, exibirResultado);
}

function buscar() {
    let url = "/MavenREST/rest/users/search?termo=" + $("#termo").val() + "&valor=" + $("#termo_valor").val();
    $.get(url, exibirResultado);
}

function exibirResultado(data) {
    let lista = "<table><tr><th>ID</th><th>Nome</th><th>Email</th><th>Opções</th></tr>";
    for (let d of data) {
        lista += "<tr><td>" + d.id + "</td><td>" + d.nome + "</td><td>" + d.email + "</td><td><button onclick='editar(" + JSON.stringify(d) + ");'>Editar</button><button onclick=\"remover(" + d.id + ")\">Apagar</button></td></tr>";
    }
    lista += "</table>";
    $("#conteudo").html(lista);
}

function remover(id) {
    $.ajax({
        url: '/MavenREST/rest/users/' + id,
        type: 'DELETE',
        success: function (result) {
            alert(result);
            listarTodos();
        }
    });
}

function criar() {
    $("#conteudo").load("form.html", function () {
        $("#acao").html("Criar");
        $("#acao").attr("onclick", "criar_editar('/MavenREST/rest/users/','POST');");
    });
}

function editar(u) {
    $("#conteudo").load("form.html", function () {
        $("#id").val(u.id);
        $("#nome").val(u.nome);
        $("#email").val(u.email);
        $("#acao").html("Editar");
        $("#acao").attr("onclick", "criar_editar('/MavenREST/rest/users/" + $("#id").val() + "','PUT');");
    });
}

function criar_editar(uri, verbo) {
    $.ajax({
        url: uri,
        type: verbo,
        data: {id: $("#id").val(), nome: $("#nome").val(), email: $("#email").val(), senha: $("#senha").val()},
        success: function (result) {
            alert(result);
            window.location.href = "/MavenREST/index.html";
        }
    });
}
