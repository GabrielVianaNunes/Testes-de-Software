const apiUrl = "/api/clientes";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("clienteForm");
    const btnCancelar = document.getElementById("btnCancelar");

    carregarClientes();

    form.addEventListener("submit", (e) => {
        e.preventDefault();
        salvarCliente();
    });

    btnCancelar.addEventListener("click", () => {
        limparFormulario();
    });
});

function mostrarMensagem(texto, tipo) {
    const mensagem = document.getElementById("mensagem");
    mensagem.textContent = texto;
    mensagem.className = "mensagem " + tipo;
    mensagem.style.display = "block";
    setTimeout(() => mensagem.style.display = "none", 3000);
}

function validarCamposObrigatorios() {
    const nome = document.getElementById("nome").value.trim();
    const email = document.getElementById("email").value.trim();
    const telefone = document.getElementById("telefone").value.trim();
    const cidade = document.getElementById("cidade").value.trim();

    if (!nome || !email || !telefone || !cidade) {
        mostrarMensagem("Todos os campos são obrigatórios.", "erro");
        return false;
    }
    return true;
}

function salvarCliente() {
    if (!validarCamposObrigatorios()) {
        return;
    }

    const id = document.getElementById("clienteId").value;
    const cliente = {
        nome: document.getElementById("nome").value.trim(),
        email: document.getElementById("email").value.trim(),
        telefone: document.getElementById("telefone").value.trim(),
        cidade: document.getElementById("cidade").value.trim()
    };

    const metodo = id ? "PUT" : "POST";
    const url = id ? `${apiUrl}/${id}` : apiUrl;

    fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cliente)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao salvar cliente");
            }
            return response.json();
        })
        .then(() => {
            mostrarMensagem("Cliente salvo com sucesso!", "sucesso");
            limparFormulario();
            carregarClientes();
        })
        .catch(() => {
            mostrarMensagem("Erro ao salvar cliente.", "erro");
        });
}

function carregarClientes() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(clientes => {
            const tbody = document.querySelector("#tabelaClientes tbody");
            tbody.innerHTML = "";
            clientes.forEach(cliente => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${cliente.id}</td>
                    <td>${cliente.nome}</td>
                    <td>${cliente.email}</td>
                    <td>${cliente.telefone}</td>
                    <td>${cliente.cidade}</td>
                    <td>
                        <button onclick="editarCliente(${cliente.id})">Editar</button>
                        <button onclick="excluirCliente(${cliente.id})">Excluir</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        });
}

function editarCliente(id) {
    fetch(`${apiUrl}/${id}`)
        .then(res => res.json())
        .then(cliente => {
            document.getElementById("clienteId").value = cliente.id;
            document.getElementById("nome").value = cliente.nome;
            document.getElementById("email").value = cliente.email;
            document.getElementById("telefone").value = cliente.telefone;
            document.getElementById("cidade").value = cliente.cidade;
            document.getElementById("btnCancelar").style.display = "inline-block";
            mostrarMensagem("Editando cliente ID " + cliente.id, "sucesso");
        });
}

function excluirCliente(id) {
    if (!confirm("Deseja realmente excluir este cliente?")) return;

    fetch(`${apiUrl}/${id}`, { method: "DELETE" })
        .then(res => {
            if (!res.ok && res.status !== 204) {
                throw new Error("Erro ao excluir");
            }
            mostrarMensagem("Cliente excluído com sucesso!", "sucesso");
            carregarClientes();
        })
        .catch(() => mostrarMensagem("Erro ao excluir cliente.", "erro"));
}

function limparFormulario() {
    document.getElementById("clienteId").value = "";
    document.getElementById("nome").value = "";
    document.getElementById("email").value = "";
    document.getElementById("telefone").value = "";
    document.getElementById("cidade").value = "";
    document.getElementById("btnCancelar").style.display = "none";
}
